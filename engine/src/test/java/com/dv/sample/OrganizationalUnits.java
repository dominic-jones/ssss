package com.dv.sample;

import org.qi4j.api.association.ManyAssociation;
import org.qi4j.api.entity.Aggregated;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.value.ValueBuilder;
import org.qi4j.api.value.ValueBuilderFactory;
import org.qi4j.library.constraints.annotation.MaxLength;

@Mixins(OrganizationalUnits.OrganizationsMixin.class)
public interface OrganizationalUnits {

    /* This method is a command, and hence is of the "please do this for me"
    form. Validation of input handles in the command method, and ONLY in
    the command method. Properties may not have validation settings, and
    event methods may not do validation, since events are "after the fact".
    If the domain comes up in an inconsistent state (e.g. names over 50
    chars), then compensating commands have to be issued to fix this rather
    than fail to load entirely. */
    @Command
    OrganizationalUnit createOrganizationalUnit(@MaxLength(50) String name);

    /* This is an event method. It is an "after the fact" method, and the
    tense used in the name follows this. It takes only one argument, which
    is an event object that is a subtype of DomainEvent which is a subtype
    of ValueComposite and Identity, signifying that it is immutable and
    identifiable. DomainEvent ALWAYS contains properties for date of firing
    and the user who fired it. Custom mandatory data can be added in
    subclasses. Event methods may NOT throw exceptions, and may NOT perform
    input data checking, since they are not allowed to say no. It *does*
    return the created OU, but this is for convenience of the calling
    command method only. @Event methods will have a SideEffect that creates
    a persistent event to be put on disk and distributed to whoever is
    interested in what is happening in the domain. */
    @Event
    OrganizationalUnit organizationalUnitCreated(CreatedEvent event);

    interface OrganizationalUnitsState {

        @Aggregated
        ManyAssociation<OrganizationalUnit> organizationalUnits();

    }

    class OrganizationsMixin
            implements OrganizationalUnits {

        /* The EBF delegates to ValueBuilderFactory and sets the mandatory
        fields before return the builder to client code */
        @Service
        ValueBuilderFactory ebf;

        /* Since the command is in charge of creating the id it is now vital to
        inject the id-generator explicitly rather than having the UoW create
        id's on the fly */
        @Service
        org.qi4j.api.entity.IdentityGenerator idGenerator;

        @This
        OrganizationalUnit.OrganizationalUnitState ouState;

        @This
        OrganizationalUnitsState state;

        @Structure
        org.qi4j.api.unitofwork.UnitOfWorkFactory uowf;

        /* Create a OU with a given name. This is implemented as two events:
        creation of OU and then changing its name, which in turn is done as a
        command. So, one command may lead to several other commands being
        triggered, which may lead to several events being fired. Command code
        MAY do validation, but it MAY NOT change any state. That is only done in
        event methods. */
        @Override
        public OrganizationalUnit createOrganizationalUnit(String name) {

            ValueBuilder<CreatedEvent> valueBuilder =
                    ebf.newValueBuilder(CreatedEvent.class);

            valueBuilder.prototype().createdId().set(idGenerator.generate(OrganizationalUnitEntity.class));
            OrganizationalUnitEntity ou =
                    organizationalUnitCreated(valueBuilder.newInstance());
            ou.describe(name);
            return ou;
        }

        /* This is the event that creates the OU. It is deterministic in that if
        we later start with an empty database and replays the events, i.e.
        invoke this method again with the same event, it should have the same
        result. The model MAY have changed though, so that the new state is
        different from what was created the first time. That is ok. The event
        method may NOT do validation or throw any exceptions. */
        @Override
        public OrganizationalUnitEntity organizationalUnitCreated(CreatedEvent event) {

            EntityBuilder<OrganizationalUnitEntity> ouBuilder =
                    uowf.currentUnitOfWork().newEntityBuilder(OrganizationalUnitEntity.class,
                            event.createdId().get());

            ouBuilder.instance().organization().set(ouState.organization().get());
            OrganizationalUnitEntity ou = ouBuilder.newInstance();

            state.organizationalUnits().add(state.organizationalUnits().count(), ou);
            return ou;
        }
    }
}
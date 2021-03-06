package com.dv.ssss.domain.faction;

import com.dv.ssss.domain.people.PersonEntity;

import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(FactionFactory.FactionFactoryMixin.class)
public interface FactionFactory {

    FactionEntity create(PersonEntity founder, String name);

    class FactionFactoryMixin implements FactionFactory {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public FactionEntity create(PersonEntity founder, String name) {

            EntityBuilder<FactionEntity> builder = unitOfWorkFactory.currentUnitOfWork()
                                                                    .newEntityBuilder(FactionEntity.class);
            FactionState template = builder.instanceFor(FactionState.class);

            template.founder().set(founder);
            template.members().add(founder);
            template.name().set(name);

            return builder.newInstance();
        }
    }

}

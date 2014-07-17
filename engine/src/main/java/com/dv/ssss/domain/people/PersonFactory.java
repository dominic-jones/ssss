package com.dv.ssss.domain.people;

import org.joda.time.LocalDate;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(PersonFactory.PersonFactoryMixin.class)
public interface PersonFactory {

    Person create(String name, String rank, LocalDate birthDate);

    class PersonFactoryMixin implements PersonFactory {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Person create(String name, String rank, LocalDate birthDate) {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            EntityBuilder<PersonEntity> builder = unitOfWork.newEntityBuilder(PersonEntity.class);

            Person.PersonState state = builder.instanceFor(Person.PersonState.class);
            state.name().set(name);
            state.rank().set(rank);
            state.birthDate().set(birthDate);

            return builder.newInstance();
        }
    }
}

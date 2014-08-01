package com.dv.ssss.domain.people;

import org.joda.time.LocalDate;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(PersonFactory.PersonFactoryMixin.class)
public interface PersonFactory {

    PersonEntity create(String name, String rank, LocalDate birthDate);

    class PersonFactoryMixin implements PersonFactory {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public PersonEntity create(String name, String rank, LocalDate birthDate) {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            EntityBuilder<PersonEntity> builder = unitOfWork.newEntityBuilder(PersonEntity.class);

            PersonState state = builder.instanceFor(PersonState.class);
            state.name().set(name);
            state.rank().set(rank);
            state.birthDate().set(birthDate);

            return builder.newInstance();
        }
    }
}

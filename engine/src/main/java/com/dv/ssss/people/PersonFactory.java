package com.dv.ssss.people;

import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(PersonFactory.PersonFactoryMixin.class)
public interface PersonFactory {

    Person create(String name, String rank, String age);

    class PersonFactoryMixin implements PersonFactory {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Person create(String name, String rank, String age) {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            EntityBuilder<PersonEntity> builder = unitOfWork.newEntityBuilder(PersonEntity.class);

            Person.PersonState state = builder.instanceFor(Person.PersonState.class);
            state.name().set(name);
            state.rank().set(rank);
            state.age().set(age);

            return builder.newInstance();
        }
    }
}

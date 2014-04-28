package com.dv.ssss.people;

import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

public class PersonFactoryMixin implements PersonFactory {

    @Structure
    UnitOfWorkFactory unitOfWorkFactory;

    @Override
    public Person create(String name, String rank, String age) {

        UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

        EntityBuilder<Person> builder = unitOfWork.newEntityBuilder(Person.class);

        PersonState prototype = builder.instanceFor(PersonState.class);
        prototype.name().set(name);
        prototype.rank().set(rank);
        prototype.age().set(age);

        return builder.newInstance();
    }
}

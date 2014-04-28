package com.dv.ssss;

import com.dv.ssss.people.Person;
import com.dv.ssss.people.PersonState;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

public class DataBootstrapImpl implements DataBootstrap {

    @Structure
    UnitOfWorkFactory unitOfWorkFactory;

    @Override
    public void bootstrap() {

        UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();

        //TODO Move to factory

        EntityBuilder<Person> builder = unitOfWork.newEntityBuilder(Person.class);

        PersonState prototype = builder.instanceFor(PersonState.class);
        prototype.name().set("Aegis");
        prototype.rank().set("Overlord");
        prototype.age().set("23");

        builder.newInstance();

        try {
            unitOfWork.complete();
        } catch (UnitOfWorkCompletionException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}

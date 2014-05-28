package com.dv.ssss;

import com.dv.ssss.people.PersonFactory;
import com.dv.ssss.turn.TurnFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

public class DataBootstrapImpl implements DataBootstrap {

    @Structure
    UnitOfWorkFactory unitOfWorkFactory;

    @Service
    PersonFactory personFactory;

    @Service
    TurnFactory turnFactory;

    @Override
    public void bootstrap() {

        UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();

        personFactory.create("Aegis", "Overlord", 23);
        turnFactory.create();

        try {
            unitOfWork.complete();
        } catch (UnitOfWorkCompletionException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}

package com.dv.ssss;

import com.dv.ssss.people.PersonnelRepository;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

public class EngineImpl implements Engine {

    @Service
    PersonnelRepository personnelRepository;

    @Structure
    UnitOfWorkFactory unitOfWorkFactory;

    @Override
    public void endTurn() {

        //TODO Do not sysout
        System.out.println("Ending turn");

        //TODO Handle it
        try {
            unitOfWorkFactory.currentUnitOfWork()
                    .complete();
        } catch (UnitOfWorkCompletionException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        unitOfWorkFactory.newUnitOfWork();
    }
}

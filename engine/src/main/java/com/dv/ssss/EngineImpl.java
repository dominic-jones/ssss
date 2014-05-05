package com.dv.ssss;

import com.dv.ssss.age.Age;
import com.dv.ssss.age.AgeRepository;
import com.dv.ssss.turn.Turn;
import com.dv.ssss.turn.TurnRepository;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

public class EngineImpl implements Engine {

    @Service
    AgeRepository ageRepository;

    @Service
    TurnRepository turnRepository;

    @Structure
    UnitOfWorkFactory unitOfWorkFactory;

    @Override
    public void endTurn() {

        Iterable<Age> ageables = ageRepository.findAgeables();
        for (Age ageable : ageables) {
            ageable.increaseAge(1);
        }

        Turn turn = turnRepository.get();
        turn.increaseTurn();

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
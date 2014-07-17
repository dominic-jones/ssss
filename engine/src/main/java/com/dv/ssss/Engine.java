package com.dv.ssss;

import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.inf.DataException;

import org.qi4j.api.composite.Composite;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(Engine.EngineImpl.class)
public interface Engine extends Composite {

    void endTurn(String gameIdentity);

    abstract class EngineImpl implements Engine {

        @Service
        GameService gameService;

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public void endTurn(String gameIdentity) {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();

            gameService.endTurn(gameIdentity);

            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new DataException(e);
            }
        }
    }
}

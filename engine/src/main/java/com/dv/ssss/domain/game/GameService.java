package com.dv.ssss.domain.game;

import com.dv.ssss.domain.DataBootstrap;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(GameService.GameServiceMixin.class)
public interface GameService {

    //TODO Return dto
    int turnCount(String gameIdentity);

    String newGame();

    class GameServiceMixin implements GameService {

        @Service
        DataBootstrap dataBootstrap;

        @Service
        GameRepository gameRepository;

        @Service
        GameFactory gameFactory;

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public String newGame() {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();

            dataBootstrap.bootstrap();

            Game game = gameFactory.create();
            String gameIdentity = game.identity().get();
            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
            return gameIdentity;
        }

        @Override
        public int turnCount(String gameIdentity) {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();
            int turn = gameRepository.get(gameIdentity)
                                     .turn()
                                     .get()
                                     .turn();
            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
            return turn;
        }
    }

}


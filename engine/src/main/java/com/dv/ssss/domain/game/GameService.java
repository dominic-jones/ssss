package com.dv.ssss.domain.game;

import com.dv.ssss.inf.DataException;

import org.joda.time.Period;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(GameService.GameServiceMixin.class)
public interface GameService {

    TurnDto currentTurn(String gameIdentity);

    Period elapsedTime(String gameIdentity);

    String newGame();

    void endTurn(String gameIdentity);

    class GameServiceMixin implements GameService {

        @Service
        GameRepository gameRepository;

        @Service
        GameFactory gameFactory;

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public String newGame() {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();

            NewGame game = gameFactory.create();
            String gameIdentity = game.identity().get();

            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new DataException(e);
            }

            progenate(gameIdentity);

            return gameIdentity;
        }

        private void progenate(String gameIdentity) {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();

            Game game = gameRepository.get(gameIdentity);
            game.progenate(currentTurn(gameIdentity).getDate());

            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new DataException(e);
            }
        }

        @Override
        public TurnDto currentTurn(String gameIdentity) {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();
            TurnDto turn = new TurnDto(gameRepository.get(gameIdentity));
            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new DataException(e);
            }
            return turn;
        }

        @Override
        public Period elapsedTime(String gameIdentity) {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();

            Turn turn = gameRepository.get(gameIdentity);
            Period period = turn.elapsedTime();

            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new DataException(e);
            }

            return period;
        }

        @Override
        public void endTurn(String gameIdentity) {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();

            Game game = gameRepository.get(gameIdentity);
            game.endTurn();
            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new DataException(e);
            }
        }
    }

}


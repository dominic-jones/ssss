package com.dv.ssss.domain.game;

import com.dv.ssss.inf.DataException;
import com.dv.ssss.inf.Transacted;
import com.dv.ssss.inf.UnitOfWorkConcern;
import org.joda.time.Period;
import org.qi4j.api.concern.Concerns;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Concerns(UnitOfWorkConcern.class)
@Mixins(GameService.GameServiceMixin.class)
public interface GameService {

    @Transacted
    TurnDto currentTurn(String gameIdentity);

    Period elapsedTime(String gameIdentity);

    String newGame();

    void endTurn(String gameIdentity);

    @Transacted
    void progenate(String gameIdentity);

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

        @Override
        public void progenate(String gameIdentity) {

            Game game = gameRepository.get(gameIdentity);
            game.progenate(currentTurn(gameIdentity).getDate());
        }

        @Override
        public TurnDto currentTurn(String gameIdentity) {

            return new TurnDto(gameRepository.get(gameIdentity));
        }

        @Override
        public Period elapsedTime(String gameIdentity) {

            Turn turn = gameRepository.get(gameIdentity);
            return turn.elapsedTime();
        }

        @Override
        public void endTurn(String gameIdentity) {

            Game game = gameRepository.get(gameIdentity);
            game.endTurn();
        }
    }

}


package com.dv.ssss.domain.game;

import com.dv.ssss.inf.uow.UnitOfWorkConcern;

import org.qi4j.api.concern.Concerns;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

@Concerns(UnitOfWorkConcern.class)
@Mixins(GameService.GameServiceMixin.class)
public interface GameService {

    String createNewGame();

    TurnDto currentTurn(String gameIdentity);

    void endTurn(String gameIdentity);

    void progenate(String gameIdentity);

    String startNewGame();

    class GameServiceMixin implements GameService {

        @Service
        GameRepository gameRepository;

        @Service
        GameFactory gameFactory;

        @Override
        public String startNewGame() {

            String gameIdentity = createNewGame();
            progenate(gameIdentity);

            return gameIdentity;
        }

        @Override
        public String createNewGame() {

            return gameFactory.create();
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
        public void endTurn(String gameIdentity) {

            Game game = gameRepository.get(gameIdentity);
            game.endTurn();
        }
    }

}


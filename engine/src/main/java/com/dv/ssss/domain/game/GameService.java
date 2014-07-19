package com.dv.ssss.domain.game;

import com.dv.ssss.inf.Transacted;
import com.dv.ssss.inf.UnitOfWorkConcern;
import org.qi4j.api.concern.Concerns;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

@Concerns(UnitOfWorkConcern.class)
@Mixins(GameService.GameServiceMixin.class)
public interface GameService {

    @Transacted
    String createNewGame();

    @Transacted
    TurnDto currentTurn(String gameIdentity);

    @Transacted
    void endTurn(String gameIdentity);

    @Transacted
    void progenate(String gameIdentity);

    @Transacted
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

            NewGame game = gameFactory.create();
            return game.identity().get();
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


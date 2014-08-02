package com.dv.ssss.domain.game;

import com.dv.ssss.domain.people.PersonEntity;
import com.dv.ssss.domain.people.PersonnelRepository;
import com.dv.ssss.inf.uow.UnitOfWorkConcern;
import org.qi4j.api.concern.Concerns;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.sideeffect.SideEffects;

@Concerns(UnitOfWorkConcern.class)
@SideEffects({PlayerTransferredSideEffect.class, TurnEndedSideEffect.class})
@Mixins(GameService.GameServiceMixin.class)
public interface GameService {

    String createNewGame();

    void endTurn(String gameIdentity);

    void progenate(String gameIdentity);

    String startNewGame();

    void transferPlayerTo(String gameIdentity, String personIdentity);

    class GameServiceMixin implements GameService {

        @Service
        GameRepository gameRepository;

        @Service
        GameFactory gameFactory;

        @Service
        PersonnelRepository personnelRepository;

        @Override
        public String startNewGame() {

            String gameIdentity = createNewGame();
            progenate(gameIdentity);

            return gameIdentity;
        }

        @Override
        public void transferPlayerTo(String gameIdentity, String personIdentity) {

            GameEntity game = gameRepository.get(gameIdentity);

            PersonEntity person = personnelRepository.get(personIdentity);
            game.transferPlayerTo(person);
        }

        @Override
        public String createNewGame() {

            return gameFactory.create();
        }

        @Override
        public void progenate(String gameIdentity) {

            GameEntity game = gameRepository.get(gameIdentity);
            game.progenate(game.currentDate());
        }

        @Override
        public void endTurn(String gameIdentity) {

            GameEntity game = gameRepository.get(gameIdentity);
            game.endTurn();
        }
    }

}


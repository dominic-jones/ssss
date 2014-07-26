package com.dv.ssss.domain.game;

import com.dv.ssss.domain.people.Person;
import com.dv.ssss.inf.uow.UnitOfWorkConcern;
import com.dv.ssss.personnel.PersonnelService;
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

    void transferPlayerTo(String gameIdentity, String personIdentity);

    class GameServiceMixin implements GameService {

        @Service
        GameRepository gameRepository;

        @Service
        GameFactory gameFactory;

        @Service
        PersonnelService personnelService;

        @Override
        public String startNewGame() {

            String gameIdentity = createNewGame();
            progenate(gameIdentity);

            return gameIdentity;
        }

        @Override
        public void transferPlayerTo(String gameIdentity, String personIdentity) {

            Game game = gameRepository.get(gameIdentity);
            Person person = personnelService.get(personIdentity);
            game.transferPlayerTo(person);
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


package com.dv.ssss.domain.game;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(GameService.GameServiceMixin.class)
public interface GameService {

    //TODO Return dto
    int turnCount(Property<String> identity);

    Property<String> newGame();

    class GameServiceMixin implements GameService {

        @Service
        GameRepository gameRepository;

        @Service
        GameFactory gameFactory;

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Property<String> newGame() {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();
            Game game = gameFactory.create();
            Property<String> gameIdentity = game.identity();
            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
            return gameIdentity;
        }

        @Override
        public int turnCount(Property<String> identity) {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();
            int turn = gameRepository.get(identity)
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


package com.dv.ssss.domain.game;

import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(GameRepository.GameRepositoryMixin.class)
public interface GameRepository {

    Game get(String gameIdentity);

    class GameRepositoryMixin implements GameRepository {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Game get(String gameIdentity) {

            return unitOfWorkFactory.currentUnitOfWork().get(Game.class, gameIdentity);
        }
    }

}

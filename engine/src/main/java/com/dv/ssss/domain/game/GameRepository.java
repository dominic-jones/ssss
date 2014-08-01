package com.dv.ssss.domain.game;

import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(GameRepository.GameRepositoryMixin.class)
public interface GameRepository {

    GameEntity get(String gameIdentity);

    class GameRepositoryMixin implements GameRepository {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public GameEntity get(String gameIdentity) {

            return unitOfWorkFactory.currentUnitOfWork().get(GameEntity.class, gameIdentity);
        }
    }

}

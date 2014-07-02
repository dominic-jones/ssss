package com.dv.ssss.domain.game;

import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(GameRepository.GameRepositoryMixin.class)
public interface GameRepository {

    Game get(Property<String> identity);

    class GameRepositoryMixin implements GameRepository {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Game get(Property<String> identity) {

            return unitOfWorkFactory.currentUnitOfWork().get(Game.class, identity.get());
        }
    }

}

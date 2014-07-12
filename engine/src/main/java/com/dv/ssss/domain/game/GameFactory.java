package com.dv.ssss.domain.game;

import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(GameFactory.GameFactoryMixin.class)
public interface GameFactory {

    NewGame create();

    class GameFactoryMixin implements GameFactory {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Game create() {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            EntityBuilder<Game> entityBuilder = unitOfWork.newEntityBuilder(Game.class);
            Turn.TurnState template = entityBuilder.instanceFor(Turn.TurnState.class);
            template.turn().set(1);

            return entityBuilder.newInstance();
        }
    }

}

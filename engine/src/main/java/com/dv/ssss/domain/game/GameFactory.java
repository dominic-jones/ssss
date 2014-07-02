package com.dv.ssss.domain.game;

import com.dv.ssss.domain.turn.Turn;
import com.dv.ssss.domain.turn.TurnFactory;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(GameFactory.GameFactoryMixin.class)
public interface GameFactory {

    Game create();

    class GameFactoryMixin implements GameFactory {

        @Service
        TurnFactory turnFactory;

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Game create() {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            Turn turn = turnFactory.create();

            EntityBuilder<Game> entityBuilder = unitOfWork.newEntityBuilder(Game.class);
            Game template = entityBuilder.instance();
            template.turn().set(turn);

            return entityBuilder.newInstance();
        }
    }

}

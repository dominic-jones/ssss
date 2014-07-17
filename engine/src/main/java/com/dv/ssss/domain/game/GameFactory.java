package com.dv.ssss.domain.game;

import org.joda.time.LocalDate;
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
            template.startingDate().set(LocalDate.parse("2088-6-14"));

            return entityBuilder.newInstance();
        }
    }

}

package com.dv.ssss.domain.game;

import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;

@Mixins({Game.GameMixin.class})
public interface Game extends EntityComposite, Turn {

    void endTurn();

    abstract class GameMixin implements Game {

        @This
        TurnState state;

        @Override
        public void endTurn() {

            state.turn().set(
                    state.turn()
                         .get() + 1
            );
        }
    }
}

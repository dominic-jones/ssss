package com.dv.ssss.domain.game;

import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(Game.GameMixin.class)
public interface Game extends EntityComposite {

    Property<Integer> turn();

    void endTurn();

    abstract class GameMixin implements Game {

        @Override
        public void endTurn() {

            turn().set(
                    turn().get() + 1
            );
        }

    }

}

package com.dv.ssss.domain.game;

import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(Turn.TurnMixin.class)
public interface Turn {

    int number();

    class TurnMixin implements Turn {

        @This
        TurnState state;

        @Override
        public int number() {

            return state.turn()
                        .get();
        }
    }

    interface TurnState {

        Property<Integer> turn();
    }
}

package com.dv.ssss.turn;

import org.qi4j.api.injection.scope.This;
import org.qi4j.api.property.Property;

public class TurnMixin implements Turn {

    public interface TurnState {

        Property<Integer> turn();
    }

    @This
    TurnState state;

    @Override
    public int turn() {

        return state.turn().get();
    }
}

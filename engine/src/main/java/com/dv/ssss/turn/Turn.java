package com.dv.ssss.turn;

import org.qi4j.api.mixin.Mixins;

@Mixins(TurnMixin.class)
public interface Turn {

    int turn();
}

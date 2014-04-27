package com.dv.ssss;

import org.qi4j.api.composite.Composite;
import org.qi4j.api.mixin.Mixins;

@Mixins(EngineImpl.class)
public interface Engine extends Composite {

    void endTurn();
}

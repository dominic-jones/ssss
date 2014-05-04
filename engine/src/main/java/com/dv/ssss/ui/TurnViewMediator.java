package com.dv.ssss.ui;

import com.dv.ssss.Engine;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

@Mixins(TurnViewMediator.TurnViewMediatorMixin.class)
public interface TurnViewMediator {

    class TurnViewMediatorMixin implements TurnViewMediator {

        @Service
        Engine engine;

    }
}
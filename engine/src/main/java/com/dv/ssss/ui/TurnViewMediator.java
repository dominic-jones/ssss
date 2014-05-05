package com.dv.ssss.ui;

import com.dv.ssss.Engine;
import com.dv.ssss.turn.TurnRepository;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;

@Mixins(TurnViewMediator.TurnViewMediatorMixin.class)
public interface TurnViewMediator {

    void turnEnded(TurnEndedEvent event);

    void initializeTurn();

    class TurnViewMediatorMixin implements TurnViewMediator {

        @Service
        Engine engine;

        @Service
        TurnRepository turnRepository;

        @Uses
        TurnView view;

        @Override
        public void turnEnded(TurnEndedEvent event) {

            initializeTurn();
        }

        @Override
        public void initializeTurn() {

            int turn = turnRepository.get().turn();
            view.update(turn);
        }
    }
}
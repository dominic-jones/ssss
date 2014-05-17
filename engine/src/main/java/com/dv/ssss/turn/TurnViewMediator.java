package com.dv.ssss.turn;

import com.dv.ssss.Engine;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;

@Mixins(TurnViewMediator.TurnViewMediatorMixin.class)
public interface TurnViewMediator {

    @Subscribe
    void endTurn(EndTurnCommand endTurnCommand);

    @Subscribe
    void turnEnded(TurnEndedEvent event);

    void initializeTurn();

    class TurnViewMediatorMixin implements TurnViewMediator {

        @Service
        Engine engine;

        @Service
        TurnEndedEventFactory turnEndedEventFactory;

        @Service
        TurnRepository turnRepository;

        @Uses
        EventBus eventBus;

        @Uses
        TurnView view;

        @Override
        public void endTurn(EndTurnCommand endTurnCommand) {

            engine.endTurn();
            eventBus.post(turnEndedEventFactory.create());
        }

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
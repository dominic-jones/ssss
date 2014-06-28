package com.dv.ssss.turn;

import com.dv.ssss.Engine;
import com.dv.ssss.event.EventPoster;
import com.google.common.eventbus.Subscribe;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.value.ValueBuilderFactory;

@Mixins(TurnViewPresenter.TurnViewPresenterMixin.class)
public interface TurnViewPresenter {

    @Subscribe
    void endTurn(EndTurnCommand endTurnCommand);

    @Subscribe
    void turnEnded(TurnEndedEvent event);

    void initializeTurn();

    class TurnViewPresenterMixin implements TurnViewPresenter {

        @Service
        Engine engine;

        @Service
        EventPoster eventPoster;

        @Structure
        ValueBuilderFactory valueBuilderFactory;

        @Service
        TurnRepository turnRepository;

        @Uses
        TurnView view;

        @Override
        public void endTurn(EndTurnCommand endTurnCommand) {

            engine.endTurn();
            eventPoster.post(
                    valueBuilderFactory.newValueBuilder(TurnEndedEvent.class).newInstance()
            );
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
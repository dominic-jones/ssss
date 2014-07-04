package com.dv.ssss.ui.turn;

import com.dv.ssss.Engine;
import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.turn.TurnEndedEvent;
import com.dv.ssss.inf.event.EventPoster;
import com.dv.ssss.ui.Presenter;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(TurnPresenter.TurnPresenterMixin.class)
public interface TurnPresenter extends Presenter {

    TurnView getView();

    void endTurn(EndTurnCommand endTurnCommand);

    class TurnPresenterMixin implements TurnPresenter {

        @Service
        Engine engine;

        @Service
        EventPoster eventPoster;

        @Service
        GameService gameService;

        @Structure
        TransientBuilderFactory transientBuilderFactory;

        @Uses
        Property<String> game;

        TurnView view;

        @Override
        public void init() {

            view = transientBuilderFactory.newTransient(
                    TurnView.class,
                    this
            );

            view.bindEndTurn(this::endTurn);

            updateTurn();
        }

        @Override
        public TurnView getView() {

            return view;
        }

        @Override
        public void endTurn(EndTurnCommand endTurnCommand) {

            engine.endTurn();
            eventPoster.post(new TurnEndedEvent());
            //TODO Fire through event
            updateTurn();
        }

        private void updateTurn() {

            int turn = gameService.turnCount(game);
            view.setTurn(turn);
        }
    }
}

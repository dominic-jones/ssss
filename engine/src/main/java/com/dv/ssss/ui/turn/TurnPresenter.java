package com.dv.ssss.ui.turn;

import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.game.TurnEndedEvent;
import com.dv.ssss.query.TurnQuery;
import com.dv.ssss.ui.Presenter;
import com.google.common.eventbus.Subscribe;

import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;

@Mixins(TurnPresenter.TurnPresenterMixin.class)
public interface TurnPresenter extends Presenter {

    TurnView getView();

    void endTurn(EndTurnCommand endTurnCommand);

    @Subscribe
    void updateTurn(TurnEndedEvent event);

    class TurnPresenterMixin implements TurnPresenter {

        @Service
        GameService gameService;

        @Service
        TurnQuery turnQuery;

        @Structure
        TransientBuilderFactory transientBuilderFactory;

        @Uses
        String gameIdentity;

        TurnView view;

        @Override
        public void init() {

            view = transientBuilderFactory.newTransient(
                    TurnView.class,
                    this,
                    gameIdentity
            );

            view.bindEndTurn(this::endTurn);

            updateTurnDisplay();
        }

        @Override
        public TurnView getView() {

            return view;
        }

        @Override
        public void endTurn(EndTurnCommand endTurnCommand) {

            gameService.endTurn(gameIdentity);
        }

        @Override
        public void updateTurn(TurnEndedEvent event) {

            updateTurnDisplay();
        }

        private void updateTurnDisplay() {

            view.setTurn(turnQuery.execute(gameIdentity));
        }
    }
}

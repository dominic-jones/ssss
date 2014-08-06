package com.dv.ssss.ui.turn;

import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.game.NewGameStartedEvent;
import com.dv.ssss.domain.game.TurnEndedEvent;
import com.dv.ssss.query.TurnQuery;
import com.dv.ssss.ui.Presenter;
import com.google.common.eventbus.Subscribe;

import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;

@Mixins(TurnPresenter.TurnPresenterMixin.class)
public interface TurnPresenter extends Presenter {

    TurnView getView();

    void endTurn(EndTurnCommand endTurnCommand);

    @Subscribe
    void newGameStarted(NewGameStartedEvent newGameStartedEvent);

    @Subscribe
    void turnEnded(TurnEndedEvent event);

    class TurnPresenterMixin implements TurnPresenter {

        @Service
        GameService gameService;

        @Service
        TurnQuery turnQuery;

        TurnView view;

        public TurnPresenterMixin(@Structure TransientBuilderFactory transientBuilderFactory) {

            view = transientBuilderFactory.newTransient(
                    TurnView.class,
                    this
            );

            view.bindEndTurn(this::endTurn);
        }

        @Override
        public TurnView getView() {

            return view;
        }

        @Override
        public void endTurn(EndTurnCommand command) {

            gameService.endTurn(command.getGameIdentity());
        }

        @Override
        public void newGameStarted(NewGameStartedEvent newGameStartedEvent) {

            String gameIdentity = newGameStartedEvent.getGameIdentity();

            view.setGame(gameIdentity);
            updateTurnDisplay(gameIdentity);
        }

        @Override
        public void turnEnded(TurnEndedEvent event) {

            updateTurnDisplay(event.getGameIdentity());
        }

        private void updateTurnDisplay(String gameIdentity) {

            view.setTurn(
                    turnQuery.execute(gameIdentity)
            );
        }
    }
}

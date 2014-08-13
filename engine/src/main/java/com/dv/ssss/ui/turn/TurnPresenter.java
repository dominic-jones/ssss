package com.dv.ssss.ui.turn;

import rx.Observable;

import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.game.NewGameStartedEvent;
import com.dv.ssss.domain.game.TurnEndedEvent;
import com.dv.ssss.query.TurnQuery;
import com.dv.ssss.ui.Presenter;
import com.dv.ssss.ui.other.ObservableEvent;
import com.google.common.eventbus.Subscribe;

import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;

@Mixins(TurnPresenter.TurnPresenterMixin.class)
public interface TurnPresenter extends Presenter {

    TurnView getView();

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

            view = transientBuilderFactory.newTransient(TurnView.class);
        }

        @Override
        public TurnView getView() {

            return view;
        }

        @Override
        public void newGameStarted(NewGameStartedEvent newGameStartedEvent) {

            String gameIdentity = newGameStartedEvent.getGameIdentity();

            Observable.create(new ObservableEvent<>(view.bindEndTurnButtonHandler()))
                      .map(event -> new EndTurnCommand(gameIdentity))
                      .subscribe(this::endTurn);

            updateTurnDisplay(gameIdentity);
        }

        @Override
        public void turnEnded(TurnEndedEvent event) {

            updateTurnDisplay(event.getGameIdentity());
        }

        void endTurn(EndTurnCommand command) {

            gameService.endTurn(command.getGameIdentity());
        }

        void updateTurnDisplay(String gameIdentity) {

            view.setTurn(
                    turnQuery.execute(gameIdentity)
            );
        }
    }
}

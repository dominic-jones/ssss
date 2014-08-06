package com.dv.ssss.ui.personnel;

import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.game.NewGameStartedEvent;
import com.dv.ssss.domain.game.TurnEndedEvent;
import com.dv.ssss.query.AllPersonnelQuery;
import com.dv.ssss.ui.Presenter;
import com.google.common.eventbus.Subscribe;

import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;

@Mixins(PersonnelPresenter.PersonnelPresenterMixin.class)
public interface PersonnelPresenter extends Presenter {

    PersonnelView getView();

    @Subscribe
    void choosePlayer(ChoosePlayerCommand choosePlayerCommand);

    @Subscribe
    void newGameStarted(NewGameStartedEvent event);

    @Subscribe
    void turnEnded(TurnEndedEvent event);

    class PersonnelPresenterMixin implements PersonnelPresenter {

        @Service
        GameService gameService;

        @Service
        AllPersonnelQuery allPersonnelQuery;

        PersonnelView view;

        public PersonnelPresenterMixin(@Structure TransientBuilderFactory transientBuilderFactory) {

            view = transientBuilderFactory.newTransient(
                    PersonnelView.class,
                    this
            );
        }

        @Override
        public PersonnelView getView() {

            return view;
        }

        @Override
        public void choosePlayer(ChoosePlayerCommand command) {

            gameService.transferPlayerTo(command.getGameIdentity(), command.getChosenPlayer());
        }

        @Override
        public void newGameStarted(NewGameStartedEvent event) {

            String gameIdentity = event.getGameIdentity();

            view.setGame(gameIdentity);
            setPeople(gameIdentity);
        }

        public void setPeople(String gameIdentity) {

            view.setPeople(
                    allPersonnelQuery.execute(gameIdentity)
            );
        }

        @Override
        public void turnEnded(TurnEndedEvent event) {

            setPeople(event.getGameIdentity());
        }
    }
}
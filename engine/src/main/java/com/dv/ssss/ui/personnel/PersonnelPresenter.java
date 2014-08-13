package com.dv.ssss.ui.personnel;

import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.game.NewGameStartedEvent;
import com.dv.ssss.domain.game.TurnEndedEvent;
import com.dv.ssss.query.AllPersonnelQuery;
import com.dv.ssss.ui.Presenter;
import com.dv.ssss.ui.other.ObservableEvent;
import com.google.common.eventbus.Subscribe;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(PersonnelPresenter.PersonnelPresenterMixin.class)
public interface PersonnelPresenter extends Presenter {

    PersonnelView getView();

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

            view = transientBuilderFactory.newTransient(PersonnelView.class);
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

            Observable.create(new ObservableEvent<>(view.bindTransferButtonHandler()))
                      .map(e -> new ChoosePlayerCommand(gameIdentity, view.getSelectedPerson()))
                      .subscribe(this::choosePlayer);

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
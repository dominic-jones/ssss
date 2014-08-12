package com.dv.ssss.ui.main;

import rx.Observable;

import com.dv.ssss.ui.Presenter;
import com.dv.ssss.ui.other.ObservableEvent;
import com.dv.ssss.ui.personnel.PersonnelPresenter;
import com.dv.ssss.ui.player.PlayerPresenter;
import com.dv.ssss.ui.test.TestView;
import com.dv.ssss.ui.turn.TurnPresenter;

import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;

@Mixins(MainPresenter.MainPresenterMixin.class)
public interface MainPresenter extends Presenter {

    MainView getView();

    void selectOtherScreen(SelectScreenCommand selectScreenCommand);

    class MainPresenterMixin implements MainPresenter {

        MainView view;

        public MainPresenterMixin(
                @Structure TransientBuilderFactory transientBuilderFactory,
                @Service PersonnelPresenter personnelPresenter,
                @Service PlayerPresenter playerPresenter,
                @Service TurnPresenter turnPresenter) {

            view = transientBuilderFactory.newTransient(MainView.class);

            view.setCenter(personnelPresenter.getView());

            Observable.create(new ObservableEvent<>(view.getTestButtonHandler()))
                      .map(event -> new SelectScreenCommand(new TestView()))
                      .subscribe(this::selectOtherScreen);

            Observable.create(new ObservableEvent<>(view.getPersonnelButtonHandler()))
                      .map(event -> new SelectScreenCommand(personnelPresenter.getView()))
                      .subscribe(this::selectOtherScreen);

            view.addToTop(turnPresenter.getView());
            view.addToTop(playerPresenter.getView());
        }

        @Override
        public MainView getView() {

            return view;
        }

        @Override
        public void selectOtherScreen(SelectScreenCommand selectScreenCommand) {

            view.setCenter(selectScreenCommand.getView());
        }
    }
}

package com.dv.ssss.ui.main;

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
import rx.Observable;

@Mixins(MainPresenter.MainPresenterMixin.class)
public interface MainPresenter extends Presenter {

    MainView getView();

    class MainPresenterMixin implements MainPresenter {

        MainView view;

        public MainPresenterMixin(
                @Structure TransientBuilderFactory transientBuilderFactory,
                @Service PersonnelPresenter personnelPresenter,
                @Service PlayerPresenter playerPresenter,
                @Service TurnPresenter turnPresenter) {

            view = transientBuilderFactory.newTransient(MainView.class);

            view.setCenter(personnelPresenter.getView());

            Observable.create(new ObservableEvent<>(view.bindTestButtonHandler()))
                      .map(event -> new SelectScreenCommand(new TestView()))
                      .subscribe(this::selectOtherScreen);

            Observable.create(new ObservableEvent<>(view.bindPersonnelButtonHandler()))
                      .map(event -> new SelectScreenCommand(personnelPresenter.getView()))
                      .subscribe(this::selectOtherScreen);

            view.addToTop(turnPresenter.getView());
            view.addToTop(playerPresenter.getView());
        }

        @Override
        public MainView getView() {

            return view;
        }

        void selectOtherScreen(SelectScreenCommand selectScreenCommand) {

            view.setCenter(selectScreenCommand.getView());
        }
    }
}

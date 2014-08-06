package com.dv.ssss.ui.main;

import com.dv.ssss.inf.event.EventHandler;
import com.dv.ssss.ui.Presenter;
import com.dv.ssss.ui.personnel.PersonnelPresenter;
import com.dv.ssss.ui.player.PlayerPresenter;
import com.dv.ssss.ui.test.TestView;
import com.dv.ssss.ui.turn.TurnPresenter;

import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;

@Mixins(MainPresenter.MainPresenterMixin.class)
public interface MainPresenter extends Presenter, EventHandler {

    MainView getView();

    void selectOtherScreen(SelectScreenCommand selectScreenCommand);

    class MainPresenterMixin implements MainPresenter {

        MainView view;

        @Override
        public void init() {

        }

        public MainPresenterMixin(
                @Structure TransientBuilderFactory transientBuilderFactory,
                @Service PersonnelPresenter personnelPresenter,
                @Service PlayerPresenter playerPresenter,
                @Service TurnPresenter turnPresenter) {

            view = transientBuilderFactory.newTransient(MainView.class, this);

            view.setCenter(personnelPresenter.getView());

            view.addNavigation("Test", new TestView());
            view.addNavigation("Personnel", personnelPresenter.getView());

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

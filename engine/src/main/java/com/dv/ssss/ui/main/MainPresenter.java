package com.dv.ssss.ui.main;

import com.dv.ssss.ui.Presenter;
import com.dv.ssss.ui.PresenterFactory;
import com.dv.ssss.ui.personnel.PersonnelPresenter;
import com.dv.ssss.ui.player.PlayerPresenter;
import com.dv.ssss.ui.test.TestView;
import com.dv.ssss.ui.turn.TurnPresenter;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;

@Mixins(MainPresenter.MainPresenterMixin.class)
public interface MainPresenter extends Presenter {

    MainView getView();

    void selectOtherScreen(SelectScreenCommand selectScreenCommand);

    class MainPresenterMixin implements MainPresenter {

        @Service
        PresenterFactory presenterFactory;

        @Structure
        TransientBuilderFactory transientBuilderFactory;

        @Uses
        String gameIdentity;

        MainView view;

        @Override
        public void init() {

            view = transientBuilderFactory.newTransient(MainView.class, this);

            PersonnelPresenter personnelPresenter = presenterFactory.create(PersonnelPresenter.class, gameIdentity);
            view.setCenter(personnelPresenter.getView());

            view.addNavigation("Main", new TestView());
            view.addNavigation("Personnel", personnelPresenter.getView());

            TurnPresenter turnPresenter = presenterFactory.create(TurnPresenter.class, gameIdentity);
            view.addToTop(turnPresenter.getView());

            PlayerPresenter playerPresenter = presenterFactory.create(PlayerPresenter.class, gameIdentity);
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

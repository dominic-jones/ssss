package com.dv.ssss.ui.main;

import com.dv.ssss.ui.Presenter;
import com.dv.ssss.ui.PresenterFactory;
import com.dv.ssss.ui.personnel.PersonnelPresenter;
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

    class MainPresenterMixin implements MainPresenter {

        @Service
        PresenterFactory presenterFactory;

        @Structure
        TransientBuilderFactory transientBuilderFactory;

        @Uses
        String gameIdentity;

        MainView view;

        PersonnelPresenter personnelPresenter;

        @Override
        public void init() {

            view = transientBuilderFactory.newTransient(MainView.class, this);

            TurnPresenter turnPresenter = presenterFactory.create(TurnPresenter.class, gameIdentity);

            personnelPresenter = presenterFactory.create(PersonnelPresenter.class);

            // TODO 2014-07-04 dom: These should change based on the button clicked, perhaps a [Name : Screen] ?
            view.addButton("Main", new SelectScreenCommand(), this::selectOtherScreen);
            view.addButton("Personnel", new SelectScreenCommand(), this::selectPersonnelScreen);

            view.setTop(turnPresenter.getView());
            //TODO Do through command?
            view.setCenter(personnelPresenter.getView());
        }

        @Override
        public MainView getView() {

            return view;
        }

        void selectOtherScreen(SelectScreenCommand selectScreenCommand) {

            // TODO 2014-07-04 dom: This is just a dummy to prove the case. Replace with something real.
            view.setCenter(new TestView());
        }

        void selectPersonnelScreen(SelectScreenCommand selectScreenCommand) {

            view.setCenter(personnelPresenter.getView());
        }
    }
}

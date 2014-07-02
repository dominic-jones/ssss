package com.dv.ssss.main;

import com.dv.ssss.personnel.PersonnelPresenter;
import com.dv.ssss.turn.TurnPresenter;
import com.dv.ssss.ui.OtherView;
import com.dv.ssss.ui.Presenter;
import com.dv.ssss.ui.PresenterFactory;
import com.dv.ssss.ui.SelectOtherScreenCommand;
import com.dv.ssss.ui.SelectPersonnelScreenCommand;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(MainPresenter.MainPresenterMixin.class)
public interface MainPresenter extends Presenter {

    MainView getView();

    void selectOtherScreen(SelectOtherScreenCommand selectOtherScreenCommand);

    void selectPersonnelScreen(SelectPersonnelScreenCommand selectPersonnelScreenCommand);

    public class MainPresenterMixin implements MainPresenter {

        @Service
        PresenterFactory presenterFactory;

        @Structure
        TransientBuilderFactory transientBuilderFactory;

        @Uses
        Property<String> game;

        MainView view;

        PersonnelPresenter personnelPresenter;

        @Override
        public void init() {

            view = transientBuilderFactory.newTransient(MainView.class, this);

            TurnPresenter turnPresenter = presenterFactory.create(TurnPresenter.class, game);

            personnelPresenter = presenterFactory.create(PersonnelPresenter.class);

            view.setTop(turnPresenter.getView());
            //TODO Do through command?
            view.setCenter(personnelPresenter.getView());
        }

        @Override
        public MainView getView() {

            return view;
        }

        @Override
        public void selectOtherScreen(SelectOtherScreenCommand selectOtherScreenCommand) {

            view.setCenter(new OtherView());
        }

        @Override
        public void selectPersonnelScreen(SelectPersonnelScreenCommand selectPersonnelScreenCommand) {

            view.setCenter(personnelPresenter.getView());
        }
    }
}

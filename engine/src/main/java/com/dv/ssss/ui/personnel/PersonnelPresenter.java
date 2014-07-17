package com.dv.ssss.ui.personnel;

import com.dv.ssss.personnel.PersonnelService;
import com.dv.ssss.ui.Presenter;
import com.dv.ssss.ui.turn.TurnEndedEvent;
import com.google.common.eventbus.Subscribe;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;

@Mixins(PersonnelPresenter.PersonnelPresenterMixin.class)
public interface PersonnelPresenter extends Presenter {

    PersonnelView getView();

    @Subscribe
    void turnEnded(TurnEndedEvent event);

    class PersonnelPresenterMixin implements PersonnelPresenter {

        @Service
        PersonnelService personnelService;

        @Structure
        TransientBuilderFactory transientBuilderFactory;

        @Uses
        String gameIdentity;

        PersonnelView view;

        @Override
        public void init() {

            view = transientBuilderFactory.newTransient(
                    PersonnelView.class,
                    this
            );
            setPeople(personnelService.all(gameIdentity));
        }

        @Override
        public PersonnelView getView() {

            return view;
        }

        public void setPeople(Iterable<PersonDto> people) {

            view.setPeople(people);
        }

        @Override
        public void turnEnded(TurnEndedEvent event) {

            setPeople(personnelService.all(gameIdentity));
        }
    }
}
package com.dv.ssss.ui.personnel;

import com.dv.ssss.domain.turn.TurnEndedEvent;
import com.dv.ssss.personnel.PersonnelService;
import com.dv.ssss.ui.Presenter;
import com.google.common.eventbus.Subscribe;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(PersonnelPresenter.PersonnelPresenterMixin.class)
public interface PersonnelPresenter extends Presenter {

    PersonnelView getView();

    void setPeople(Observable<PersonDto> people);

    @Subscribe
    void turnEnded(TurnEndedEvent event);

    class PersonnelPresenterMixin implements PersonnelPresenter {

        @Service
        PersonnelService personnelService;

        @Structure
        TransientBuilderFactory transientBuilderFactory;

        PersonnelView view;

        @Override
        public void init() {

            view = transientBuilderFactory.newTransient(
                    PersonnelView.class,
                    this
            );
            setPeople(Observable.from(personnelService.all()));
        }

        @Override
        public PersonnelView getView() {

            return view;
        }

        @Override
        public void setPeople(Observable<PersonDto> people) {

            people.toList()
                  .subscribe(view::setPeople);
        }

        @Override
        public void turnEnded(TurnEndedEvent event) {

            setPeople(Observable.from(personnelService.all()));
        }
    }
}
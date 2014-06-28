package com.dv.ssss.personnel;

import com.dv.ssss.people.Person;
import com.dv.ssss.people.PersonnelRepository;
import com.dv.ssss.turn.TurnEndedEvent;
import com.google.common.eventbus.Subscribe;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(PersonnelWidgetController.PersonnelWidgetControllerMixin.class)
public interface PersonnelWidgetController {

    void loadPeople(Observable<Person> aegis);

    @Subscribe
    void turnEnded(TurnEndedEvent event);

    class PersonnelWidgetControllerMixin implements PersonnelWidgetController {

        @Uses
        PersonnelWidget view;

        @Service
        PersonnelRepository personnelRepository;

        @Override
        public void loadPeople(Observable<Person> people) {

            view.update(people);
        }

        @Override
        public void turnEnded(TurnEndedEvent event) {

            loadPeople(Observable.from(personnelRepository.getByName("Aegis")));
        }
    }
}

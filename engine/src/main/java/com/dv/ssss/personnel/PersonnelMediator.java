package com.dv.ssss.personnel;

import com.dv.ssss.Engine;
import com.dv.ssss.people.PersonnelRepository;
import com.dv.ssss.turn.TurnEndedEvent;
import com.google.common.eventbus.Subscribe;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(PersonnelMediator.PersonnelMediatorMixin.class)
public interface PersonnelMediator {

    void loadPeople();

    @Subscribe
    void turnEnded(TurnEndedEvent event);

    class PersonnelMediatorMixin implements PersonnelMediator {

        @Uses
        PersonnelView view;

        @Service
        Engine engine;

        @Service
        PersonnelRepository personnelRepository;

        @Override
        public void loadPeople() {

            view.update(Observable.from(personnelRepository.getByName("Aegis")));
        }

        @Override
        public void turnEnded(TurnEndedEvent event) {

            engine.endTurn();
            loadPeople();
        }
    }
}

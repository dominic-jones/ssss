package com.dv.ssss.ui;

import com.dv.ssss.people.PersonnelRepository;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(PersonnelMediator.PersonnelMediatorMixin.class)
public interface PersonnelMediator {

    public void loadPeople();

    class PersonnelMediatorMixin implements PersonnelMediator {

        @Uses
        PersonnelView view;

        @Service
        PersonnelRepository personnelRepository;

            @Override
        public void loadPeople() {

            view.update(Observable.from(personnelRepository.getByName("Aegis")));
        }
    }
}

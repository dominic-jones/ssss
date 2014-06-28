package com.dv.ssss.personnel;

import com.dv.ssss.event.EventPoster;
import com.dv.ssss.people.PersonnelRepository;
import com.dv.ssss.turn.EndTurnCommand;
import com.dv.ssss.turn.TurnRepository;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(PersonnelViewPresenter.PersonnelViewPresenterMixin.class)
public interface PersonnelViewPresenter {

    void init(PersonnelView personnelView);

    void endTurn(EndTurnCommand endTurnCommand);

    public class PersonnelViewPresenterMixin implements PersonnelViewPresenter {

        @Service
        EventPoster eventPoster;

        @Service
        PersonnelRepository personnelRepository;

        @Service
        TurnRepository turnRepository;

        @Uses
        PersonnelView personnelView;

        @Override
        public void init(PersonnelView personnelView) {

            personnelView.attachPresenter(this);
            personnelView.init();
            personnelView.loadPeople(Observable.from(personnelRepository.getByName("Aegis")));
            personnelView.initializeTurn(turnRepository.get().turn());
        }

        @Override
        public void endTurn(EndTurnCommand endTurnCommand) {

            eventPoster.post(endTurnCommand);
        }
    }
}

package com.dv.ssss.personnel;

import com.dv.ssss.Engine;
import com.dv.ssss.event.EventPoster;
import com.dv.ssss.people.PersonnelRepository;
import com.dv.ssss.turn.EndTurnCommand;
import com.dv.ssss.turn.TurnEndedEvent;
import com.dv.ssss.turn.TurnRepository;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.value.ValueBuilderFactory;
import rx.Observable;

@Mixins(PersonnelViewPresenter.PersonnelViewPresenterMixin.class)
public interface PersonnelViewPresenter {

    void init(PersonnelView personnelView);

    void endTurn(EndTurnCommand endTurnCommand);

    public class PersonnelViewPresenterMixin implements PersonnelViewPresenter {

        @Service
        Engine engine;

        @Service
        EventPoster eventPoster;

        @Service
        PersonnelRepository personnelRepository;

        @Service
        TurnRepository turnRepository;

        @Structure
        ValueBuilderFactory valueBuilderFactory;

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

            engine.endTurn();
            eventPoster.post(
                    valueBuilderFactory.newValueBuilder(TurnEndedEvent.class).newInstance()
            );
        }
    }
}

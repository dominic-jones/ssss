package com.dv.ssss.personnel;

import com.dv.ssss.Engine;
import com.dv.ssss.event.EventPoster;
import com.dv.ssss.people.PersonnelRepository;
import com.dv.ssss.turn.EndTurnCommand;
import com.dv.ssss.turn.TurnEndedEvent;
import com.dv.ssss.turn.TurnRepository;
import com.dv.ssss.ui.Presenter;
import com.google.common.eventbus.Subscribe;
import javafx.stage.Stage;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.value.ValueBuilderFactory;
import rx.Observable;

@Mixins(PersonnelViewPresenter.PersonnelViewPresenterMixin.class)
public interface PersonnelViewPresenter extends Presenter<PersonnelView> {

    void display(Stage stage);

    void endTurn(EndTurnCommand endTurnCommand);

    @Subscribe
    void turnEnded(TurnEndedEvent event);

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
        public void display(Stage stage) {

            personnelView.display(stage);
        }

        @Override
        public void endTurn(EndTurnCommand endTurnCommand) {

            engine.endTurn();
            eventPoster.post(
                    valueBuilderFactory.newValueBuilder(TurnEndedEvent.class).newInstance()
            );
        }

        @Override
        public void turnEnded(TurnEndedEvent event) {

            personnelView.initializeTurn(turnRepository.get().turn());
            personnelView.loadPeople(Observable.from(personnelRepository.getByName("Aegis")));
        }
    }
}

package com.dv.ssss.personnel;

import com.dv.ssss.Engine;
import com.dv.ssss.event.EventPoster;
import com.dv.ssss.game.GameService;
import com.dv.ssss.turn.EndTurnCommand;
import com.dv.ssss.turn.TurnEndedEvent;
import com.dv.ssss.turn.TurnRepository;
import com.dv.ssss.ui.Presenter;
import com.google.common.eventbus.Subscribe;
import javafx.stage.Stage;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;
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
        PersonnelService personnelService;

        @Service
        GameService gameService;

        @Uses
        Property<String> game;

        @Uses
        PersonnelView personnelView;

        @Override
        public void init(PersonnelView personnelView) {

            personnelView.attachPresenter(this);
            personnelView.init();
            personnelView.initializeTurn(gameService.turnCount(game));
            personnelView.loadPeople(Observable.from(personnelService.all()));
        }

        @Override
        public void display(Stage stage) {

            personnelView.display(stage);
        }

        @Override
        public void endTurn(EndTurnCommand endTurnCommand) {

            engine.endTurn();
            eventPoster.post(new TurnEndedEvent());
        }

        @Override
        public void turnEnded(TurnEndedEvent event) {

            personnelView.initializeTurn(gameService.turnCount(game));
            personnelView.loadPeople(Observable.from(personnelService.all()));
        }
    }
}

package com.dv.ssss.turn;

import com.dv.ssss.Engine;
import com.dv.ssss.event.EventPoster;
import com.dv.ssss.ui.ObservableEvent;
import com.google.common.eventbus.Subscribe;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.value.ValueBuilderFactory;
import rx.Observable;
import rx.functions.Action1;

@Mixins(TurnWidget.TurnWidgetMixin.class)
public interface TurnWidget {

    Pane getView();

    void update(int turn);

    @Subscribe
    void endTurn(EndTurnCommand endTurnCommand);

    @Subscribe
    void turnEnded(TurnEndedEvent event);

    void initializeTurn(int turn);

    class TurnWidgetMixin implements TurnWidget {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);

        @Uses
        Action1<? super EndTurnCommand> endTurnEvent;

        @Service
        Engine engine;

        @Service
        EventPoster eventPoster;

        @Structure
        ValueBuilderFactory valueBuilderFactory;

        @Service
        TurnRepository turnRepository;

        Text turnCount = new Text();

        @Override
        public Pane getView() {

            Label label = new Label("Turn");

            Button endTurn = new Button("End Turn");

            Observable.create(new ObservableEvent<ActionEvent>(endTurn::setOnAction))
                      .map(event -> new EndTurnCommand())
                      .subscribe(this::endTurn);

            HBox pane = new HBox();
            pane.setSpacing(SPACING);
            pane.setPadding(INSETS);
            pane.getChildren()
                .addAll(label, turnCount, endTurn);
            return pane;
        }

        @Override
        public void update(int turn) {
            //TODO Observable this
            turnCount.textProperty().set(String.valueOf(turn));
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

            initializeTurn(turnRepository.get().turn());
        }

        @Override
        public void initializeTurn(int turn) {

            update(turn);
        }
    }
}

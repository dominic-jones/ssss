package com.dv.ssss.ui;

import com.dv.ssss.turn.TurnRepository;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(TurnView.TurnViewMixin.class)
public interface TurnView {

    HBox getView();

    Observable<TurnEndedEvent> getEvents();

    class TurnViewMixin implements TurnView {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);

        @Service
        TurnRepository turnRepository;

        Observable<TurnEndedEvent> events;

        @Override
        public Observable<TurnEndedEvent> getEvents() {

            return events;
        }

        @Override
        public HBox getView() {

            Label label = new Label("Turn");

            Text turnCount = new Text(
                    String.valueOf(turnRepository.get()
                                                 .turn())
            );

            Button endTurn = new Button("End Turn");

            events = Observable.create(new ObservableEvent<ActionEvent>(endTurn::setOnAction))
                               .map(event -> new TurnEndedEvent());

            HBox turn = new HBox();
            turn.setSpacing(SPACING);
            turn.setPadding(INSETS);
            turn.getChildren()
                .addAll(label, turnCount, endTurn);
            return turn;
        }
    }
}

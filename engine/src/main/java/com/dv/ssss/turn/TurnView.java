package com.dv.ssss.turn;

import com.dv.ssss.ui.ObservableEvent;
import com.dv.ssss.ui.events.FiresEvents;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(TurnView.TurnViewMixin.class)
public interface TurnView {

    HBox getView();

    void update(int turn);

    class TurnViewMixin implements TurnView {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);

        @This
        FiresEvents firesEvents;

        StringProperty turnString;

        @Override
        public HBox getView() {

            Label label = new Label("Turn");

            Text turnCount = new Text();
            turnString = turnCount.textProperty();

            Button endTurn = new Button("End Turn");

            Observable.create(new ObservableEvent<ActionEvent>(endTurn::setOnAction))
                      .map(event -> new TurnEndedEvent())
                      .subscribe(firesEvents::post);

            HBox turn = new HBox();
            turn.setSpacing(SPACING);
            turn.setPadding(INSETS);
            turn.getChildren()
                .addAll(label, turnCount, endTurn);
            return turn;
        }

        @Override
        public void update(int turn) {

            turnString.set(String.valueOf(turn));
        }
    }
}

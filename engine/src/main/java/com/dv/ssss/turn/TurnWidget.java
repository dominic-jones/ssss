package com.dv.ssss.turn;

import com.dv.ssss.event.EventPoster;
import com.dv.ssss.ui.ObservableEvent;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(TurnWidget.TurnWidgetMixin.class)
public interface TurnWidget {

    Pane getView();

    void update(int turn);

    class TurnWidgetMixin implements TurnWidget {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);

        @Service
        EventPoster eventPoster;

        StringProperty turnString;

        @Override
        public Pane getView() {

            Label label = new Label("Turn");

            Text turnCount = new Text();
            turnString = turnCount.textProperty();

            Button endTurn = new Button("End Turn");

            Observable.create(new ObservableEvent<ActionEvent>(endTurn::setOnAction))
                      .map(event -> new EndTurnCommand())
                      .subscribe(eventPoster::post);

            HBox pane = new HBox();
            pane.setSpacing(SPACING);
            pane.setPadding(INSETS);
            pane.getChildren()
                .addAll(label, turnCount, endTurn);
            return pane;
        }

        @Override
        public void update(int turn) {

            turnString.set(String.valueOf(turn));
        }
    }
}
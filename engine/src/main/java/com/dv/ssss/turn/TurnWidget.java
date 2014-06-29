package com.dv.ssss.turn;

import com.dv.ssss.ui.ObservableEvent;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;
import rx.functions.Action1;

@Mixins(TurnWidget.TurnWidgetMixin.class)
public interface TurnWidget {

    Pane getView();

    void initializeTurn(int turn);

    class TurnWidgetMixin implements TurnWidget {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);

        @Uses
        Action1<? super EndTurnCommand> endTurnEvent;

        Text turnCount = new Text();

        @Override
        public Pane getView() {

            Label label = new Label("Turn");

            Button endTurn = new Button("End Turn");

            Observable.create(new ObservableEvent<ActionEvent>(endTurn::setOnAction))
                      .map(event -> new EndTurnCommand())
                      .subscribe(endTurnEvent);

            HBox pane = new HBox();
            pane.setSpacing(SPACING);
            pane.setPadding(INSETS);
            pane.getChildren()
                .addAll(label, turnCount, endTurn);
            return pane;
        }

        @Override
        public void initializeTurn(int turn) {

            //TODO Observable this
            turnCount.textProperty().set(String.valueOf(turn));
        }

    }
}

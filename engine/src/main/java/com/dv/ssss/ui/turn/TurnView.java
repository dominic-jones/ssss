package com.dv.ssss.ui.turn;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import rx.functions.Action1;

import com.dv.ssss.domain.game.TurnDto;
import com.dv.ssss.ui.View;

import org.qi4j.api.mixin.Mixins;

@Mixins(TurnView.TurnViewMixin.class)
public interface TurnView extends View {

    Action1<EventHandler<ActionEvent>> bindEndTurnButtonHandler();

    void setTurn(TurnDto turn);

    class TurnViewMixin implements TurnView {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);

        Text turnCount = new Text();
        Text date = new Text();
        Button endTurnButton = new Button("End Turn");

        Pane pane = view(
                name()
        );

        @Override
        public Action1<EventHandler<ActionEvent>> bindEndTurnButtonHandler() {

            return endTurnButton::setOnAction;
        }

        @Override
        public Parent getView() {

            return pane;
        }

        @Override
        public void setTurn(TurnDto turn) {
            turnCount.setText(String.valueOf(turn.getTurnCount()));
            date.setText(turn.getDate().toString());
        }

        Label name() {

            return new Label("Turn");
        }

        HBox view(Label label) {

            HBox pane = new HBox();
            pane.setSpacing(SPACING);
            pane.setPadding(INSETS);
            pane.getChildren()
                .addAll(label, turnCount, date, endTurnButton);
            return pane;
        }
    }

}

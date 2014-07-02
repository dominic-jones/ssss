package com.dv.ssss.ui.turn;

import com.dv.ssss.ui.View;
import com.dv.ssss.ui.other.ObservableEvent;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(TurnView.TurnViewMixin.class)
public interface TurnView extends View {

    void setTurn(int turn);

    class TurnViewMixin implements TurnView {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);

        @Uses
        TurnPresenter turnPresenter;

        Text turnCount = new Text();

        @Override
        public Parent getView() {

            Label label = new Label("Turn");

            Button endTurn = new Button("End Turn");

            Observable.create(new ObservableEvent<ActionEvent>(endTurn::setOnAction))
                      .map(event -> new EndTurnCommand())
                      .subscribe(turnPresenter::endTurn);

            HBox pane = new HBox();
            pane.setSpacing(SPACING);
            pane.setPadding(INSETS);
            pane.getChildren()
                .addAll(label, turnCount, endTurn);
            return pane;
        }

        @Override
        public void setTurn(int turn) {

            turnCount.textProperty().set(String.valueOf(turn));
        }
    }

}

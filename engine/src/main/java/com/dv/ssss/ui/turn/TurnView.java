package com.dv.ssss.ui.turn;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import rx.Observable;
import rx.functions.Action1;

import com.dv.ssss.ui.View;
import com.dv.ssss.ui.other.ObservableEvent;

import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;

@Mixins(TurnView.TurnViewMixin.class)
public interface TurnView extends View {

    void setTurn(int turn);

    void bindEndTurn(Action1<? super EndTurnCommand> endTurn);

    class TurnViewMixin implements TurnView {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);

        @Uses
        String gameIdentity;

        Text turnCount = new Text();
        Pane pane = view(
                name()
        );

        @Override
        public Parent getView() {

            return pane;
        }

        Label name() {

            return new Label("Turn");
        }

        // TODO 2014-07-04 dom: Looks similar to MainView::addButton. Simplify?
        @Override
        public void bindEndTurn(Action1<? super EndTurnCommand> binding) {

            Button button = new Button("End Turn");
            Observable.create(new ObservableEvent<ActionEvent>(button::setOnAction))
                      .map(event -> new EndTurnCommand(gameIdentity))
                      .subscribe(binding);
            pane.getChildren()
                .add(button);
        }

        HBox view(Label label) {

            HBox pane = new HBox();
            pane.setSpacing(SPACING);
            pane.setPadding(INSETS);
            pane.getChildren()
                .addAll(label, turnCount);
            return pane;
        }

        @Override
        public void setTurn(int turn) {

            turnCount.textProperty()
                     .set(String.valueOf(turn));
        }
    }

}

package com.dv.ssss.ui.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import rx.functions.Action1;

import com.dv.ssss.ui.View;

import org.qi4j.api.mixin.Mixins;

@Mixins(MainView.MainViewMixin.class)
public interface MainView extends View {

    Action1<EventHandler<ActionEvent>> getTestButtonHandler();

    Action1<EventHandler<ActionEvent>> getPersonnelButtonHandler();

    void setCenter(View view);

    void addToTop(View view);

    class MainViewMixin implements MainView {

        private static final Insets INSETS = new Insets(10, 10, 10, 10);

        Button testButton = new Button("Test");
        Button personnelButton = new Button("Personnel");
        Pane controls = controls(
                testButton,
                personnelButton
        );
        VBox top = new VBox();
        BorderPane layout = layout(controls);

        @Override
        public Parent getView() {

            return layout;
        }

        private Pane controls(Button testButton,
                              Button personnelButton) {

            Pane controls = new VBox();
            controls.setPadding(INSETS);

            controls.getChildren().addAll(
                    testButton,
                    personnelButton
            );

            return controls;
        }

        @Override
        public Action1<EventHandler<ActionEvent>> getTestButtonHandler() {

            return testButton::setOnAction;
        }

        @Override
        public Action1<EventHandler<ActionEvent>> getPersonnelButtonHandler() {

            return personnelButton::setOnAction;
        }

        private BorderPane layout(Pane controls) {

            BorderPane layout = new BorderPane();
            layout.setRight(controls);
            layout.setTop(top);
            return layout;
        }

        @Override
        public void setCenter(View view) {

            layout.setCenter(view.getView());
        }

        @Override
        public void addToTop(View view) {

            top.getChildren().add(view.getView());
        }
    }
}

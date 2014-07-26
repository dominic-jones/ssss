package com.dv.ssss.ui.main;

import com.dv.ssss.ui.View;
import com.dv.ssss.ui.other.ObservableEvent;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(MainView.MainViewMixin.class)
public interface MainView extends View {

    void setCenter(View view);

    void addToTop(View view);

    void addNavigation(String name,
                       View view);

    class MainViewMixin implements MainView {

        private static final Insets INSETS = new Insets(10, 10, 10, 10);

        @Uses
        MainPresenter mainPresenter;

        Pane controls = controls();
        VBox top = new VBox();
        BorderPane layout = layout(controls);

        @Override
        public Parent getView() {

            return layout;
        }

        @Override
        public void addNavigation(String name,
                                  View view) {

            Button button = new Button(name);
            Observable.create(new ObservableEvent<ActionEvent>(button::setOnAction))
                      .map(event -> new SelectScreenCommand(view))
                      .subscribe(mainPresenter::selectOtherScreen);

            controls.getChildren()
                    .add(button);
        }

        private Pane controls() {

            Pane controls = new VBox();
            controls.setPadding(INSETS);
            return controls;
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

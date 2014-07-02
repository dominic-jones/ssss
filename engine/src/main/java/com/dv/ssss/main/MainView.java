package com.dv.ssss.main;

import com.dv.ssss.ui.ObservableEvent;
import com.dv.ssss.ui.SelectOtherScreenCommand;
import com.dv.ssss.ui.SelectPersonnelScreenCommand;
import com.dv.ssss.ui.View;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

@Mixins(MainView.MainViewMixin.class)
public interface MainView extends View {

    void setCenter(View view);

    void setTop(View view);

    class MainViewMixin implements MainView {

        private static final Insets INSETS = new Insets(10, 0, 0, 10);

        @Uses
        MainPresenter mainPresenter;

        BorderPane layout = new BorderPane();

        @Override
        public Parent getView() {

            VBox controls = new VBox();

            Button main = new Button("Main");
            Observable.create(new ObservableEvent<ActionEvent>(main::setOnAction))
                      .map(event -> new SelectOtherScreenCommand())
                      .subscribe(mainPresenter::selectOtherScreen);

            Button personnel = new Button("Personnel");
            Observable.create(new ObservableEvent<ActionEvent>(personnel::setOnAction))
                      .map(event -> new SelectPersonnelScreenCommand())
                      .subscribe(mainPresenter::selectPersonnelScreen);

            controls.setPadding(INSETS);
            controls.getChildren().addAll(
                    main,
                    personnel
            );

            layout.setRight(controls);

            return layout;
        }

        @Override
        public void setCenter(View view) {

            layout.setCenter(view.getView());
        }

        @Override
        public void setTop(View view) {

            layout.setTop(view.getView());
        }
    }
}

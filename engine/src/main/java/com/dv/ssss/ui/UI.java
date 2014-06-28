package com.dv.ssss.ui;

import com.dv.ssss.personnel.PersonnelViewPresenter;
import com.dv.ssss.personnel.PersonnelWidget;
import com.dv.ssss.turn.TurnViewPresenter;
import com.dv.ssss.turn.TurnWidget;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;

@Mixins(UI.UIMixin.class)
public interface UI {

    void display(Stage stage);

    class UIMixin implements UI {

        @Service
        PresenterFactory presenterFactory;

        @Structure
        TransientBuilderFactory transientBuilderFactory;

        @Override
        public void display(Stage stage) {

            Group group = new Group();
            Scene scene = new Scene(group);
            stage.setTitle("SSSS");
            stage.setWidth(300);
            stage.setHeight(500);

            Pane personnel = personnel();
            Pane turn = turn();

            BorderPane layout = new BorderPane();
            layout.setTop(turn);
            layout.setCenter(personnel);

            group.getChildren().addAll(layout);
            stage.setScene(scene);
            stage.show();
        }

        private Pane personnel() {

            PersonnelWidget personnelWidget = transientBuilderFactory.newTransient(PersonnelWidget.class);
            PersonnelViewPresenter personnelViewPresenter = presenterFactory.create(PersonnelViewPresenter.class, personnelWidget);
            Pane personnel = personnelWidget.getView();
            personnelViewPresenter.loadPeople();
            return personnel;
        }

        private Pane turn() {

            TurnWidget turnWidget = transientBuilderFactory.newTransient(TurnWidget.class);
            TurnViewPresenter turnViewPresenter = presenterFactory.create(TurnViewPresenter.class, turnWidget);
            Pane turn = turnWidget.getView();
            turnViewPresenter.initializeTurn();
            return turn;
        }

    }
}

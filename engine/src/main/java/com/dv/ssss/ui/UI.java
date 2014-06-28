package com.dv.ssss.ui;

import com.dv.ssss.personnel.PersonnelView;
import com.dv.ssss.personnel.PersonnelViewPresenter;
import com.dv.ssss.turn.TurnView;
import com.dv.ssss.turn.TurnViewPresenter;
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

            PersonnelView personnelView = transientBuilderFactory.newTransient(PersonnelView.class);
            PersonnelViewPresenter personnelViewPresenter = presenterFactory.create(PersonnelViewPresenter.class, personnelView);
            Pane personnel = personnelView.getView();
            personnelViewPresenter.loadPeople();
            return personnel;
        }

        private Pane turn() {

            TurnView turnView = transientBuilderFactory.newTransient(TurnView.class);
            TurnViewPresenter turnViewPresenter = presenterFactory.create(TurnViewPresenter.class, turnView);
            Pane turn = turnView.getView();
            turnViewPresenter.initializeTurn();
            return turn;
        }

    }
}

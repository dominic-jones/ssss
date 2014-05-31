package com.dv.ssss.ui;

import com.dv.ssss.people.PersonnelRepository;
import com.dv.ssss.personnel.PersonnelView;
import com.dv.ssss.personnel.PersonnelViewMediator;
import com.dv.ssss.turn.TurnView;
import com.dv.ssss.turn.TurnViewMediator;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.structure.Module;

@Mixins(UI.UIMixin.class)
public interface UI {

    void display(Stage stage);

    class UIMixin implements UI {

        @Service
        PersonnelRepository personnelRepository;

        @Structure
        Module module;

        @Service
        MediatorBuilder mediatorBuilder;

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

            PersonnelView personnelView = module.newTransient(PersonnelView.class);
            PersonnelViewMediator personnelViewMediator = mediatorBuilder.create(PersonnelViewMediator.class, personnelView);
            Pane personnel = personnelView.getView();
            personnelViewMediator.loadPeople();
            return personnel;
        }

        private Pane turn() {

            TurnView turnView = module.newTransient(TurnView.class);
            TurnViewMediator turnViewMediator = mediatorBuilder.create(TurnViewMediator.class, turnView);
            Pane turn = turnView.getView();
            turnViewMediator.initializeTurn();
            return turn;
        }

    }
}

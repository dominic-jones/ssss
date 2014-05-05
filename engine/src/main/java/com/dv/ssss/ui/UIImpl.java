package com.dv.ssss.ui;

import com.dv.ssss.people.PersonnelRepository;
import com.dv.ssss.personnel.PersonnelMediator;
import com.dv.ssss.personnel.PersonnelView;
import com.dv.ssss.turn.TurnView;
import com.dv.ssss.turn.TurnViewMediator;
import com.google.common.eventbus.EventBus;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.structure.Module;

public class UIImpl implements UI {

    @Service
    private PersonnelRepository personnelRepository;

    @Structure
    Module module;

    @Override
    public void display(Stage stage) {

        Group group = new Group();
        Scene scene = new Scene(group);
        stage.setTitle("SSSS");
        stage.setWidth(300);
        stage.setHeight(500);

        EventBus eventBus = new EventBus();

        PersonnelView personnelView = module.newTransient(PersonnelView.class);
        PersonnelMediator personnelMediator = module.newTransient(PersonnelMediator.class, personnelView);
        VBox personnel = personnelView.getView();
        personnelMediator.loadPeople();

        TurnView turnView = module.newTransient(TurnView.class, eventBus);
        TurnViewMediator turnViewMediator = module.newTransient(TurnViewMediator.class, turnView);
        HBox turn = turnView.getView();
        turnViewMediator.initializeTurn();

        eventBus.register(turnViewMediator);
        eventBus.register(personnelMediator);

        BorderPane layout = new BorderPane();
        layout.setTop(turn);
        layout.setCenter(personnel);

        group.getChildren().addAll(layout);
        stage.setScene(scene);
        stage.show();
    }

}

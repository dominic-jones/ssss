package com.dv.ssss.ui;

import com.dv.ssss.people.PersonnelRepository;
import com.dv.ssss.personnel.PersonnelView;
import com.dv.ssss.personnel.PersonnelViewMediator;
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

    @Service
    MediatorBuilder mediatorBuilder;

    @Override
    public void display(Stage stage) {

        Group group = new Group();
        Scene scene = new Scene(group);
        stage.setTitle("SSSS");
        stage.setWidth(300);
        stage.setHeight(500);

        EventBus eventBus = new EventBus();
        VBox personnel = personnel(eventBus);
        HBox turn = turn(eventBus);

        BorderPane layout = new BorderPane();
        layout.setTop(turn);
        layout.setCenter(personnel);

        group.getChildren().addAll(layout);
        stage.setScene(scene);
        stage.show();
    }

    private VBox personnel(EventBus eventBus) {

        PersonnelView personnelView = module.newTransient(PersonnelView.class);
        PersonnelViewMediator personnelViewMediator = mediatorBuilder.create(PersonnelViewMediator.class, personnelView, eventBus);
        VBox personnel = personnelView.getView();
        personnelViewMediator.loadPeople();
        return personnel;
    }

    private HBox turn(EventBus eventBus) {

        TurnView turnView = module.newTransient(TurnView.class, eventBus);
        TurnViewMediator turnViewMediator = mediatorBuilder.create(TurnViewMediator.class, turnView, eventBus);
        HBox turn = turnView.getView();
        turnViewMediator.initializeTurn();
        return turn;
    }

}

package com.dv.ssss.ui;

import com.dv.ssss.Engine;
import com.dv.ssss.people.Person;
import com.dv.ssss.people.PersonnelService;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UIImpl implements UI {

    private static final int SPACING = 5;
    private static final Insets INSETS = new Insets(10, 0, 0, 10);
    private static final Font FONT = new Font("Arial", 20);

    private Engine engine = new Engine();
    private PersonnelService personnelService = new PersonnelService();

    @Override
    public void display(Stage stage) {

        Group group = new Group();
        Scene scene = new Scene(group);
        stage.setTitle("SSSS");
        stage.setWidth(300);
        stage.setHeight(500);

        HBox turn = turnTrack();

        VBox personnel = personnel();

        BorderPane layout = new BorderPane();
        layout.setTop(turn);
        layout.setCenter(personnel);

        group.getChildren().addAll(layout);
        stage.setScene(scene);
        stage.show();
    }

    private HBox turnTrack() {

        Label label = new Label("Turn");

        Text turnCount = new Text("1");

        Button endTurn = new Button("End Turn");
        endTurn.setOnAction(event -> engine.endTurn());

        HBox turn = new HBox();
        turn.setSpacing(SPACING);
        turn.setPadding(INSETS);
        turn.getChildren().addAll(label, turnCount, endTurn);
        return turn;
    }

    private VBox personnel() {

        Label label = new Label("Personnel");
        label.setFont(FONT);

        TableView<Person> table = new AnnotatedTable().createTable(personnelService.get(), Person.class);
        table.setEditable(false);

        VBox vbox = new VBox();
        vbox.setSpacing(SPACING);
        vbox.setPadding(INSETS);
        vbox.getChildren().addAll(label, table);
        return vbox;
    }
}

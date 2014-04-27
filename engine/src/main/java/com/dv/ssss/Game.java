package com.dv.ssss;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {

    public static final int SPACING = 5;
    public static final Insets INSETS = new Insets(10, 0, 0, 10);
    public static final Font FONT = new Font("Arial", 20);
    private PersonnelService personnelService = new PersonnelService();

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {

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

        HBox turn = new HBox();
        turn.setSpacing(SPACING);
        turn.setPadding(INSETS);
        Label turnLabel = new Label("Turn");
        Text turnCount = new Text("1");
        turn.getChildren().addAll(turnLabel, turnCount);
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
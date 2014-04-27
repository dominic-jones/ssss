package com.dv.ssss;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.collections.FXCollections.observableArrayList;

public class Game extends Application {

    private PersonnelService personnelService = new PersonnelService();

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());
        stage.setTitle("Personnel");
        stage.setWidth(300);
        stage.setHeight(500);

        final Label label = new Label("Personnel");
        label.setFont(new Font("Arial", 20));

        TableView<Person> table = new TableView<>();

        table.setEditable(true);

        TableColumn<Person, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Person, String> rank = new TableColumn<>("Rank");
        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));

        table.setItems(observableArrayList(personnelService.get()));

        table.getColumns().addAll(name, rank);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

}
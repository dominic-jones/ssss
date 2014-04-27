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
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import java.lang.reflect.Field;
import java.util.Set;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
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

        TableView<Person> table = createTable(personnelService.get(), Person.class);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    private <T> TableView<T> createTable(Iterable<T> elements, Class<T> type) {

        TableView<T> table = new TableView<>();
        table.setEditable(false);

        Reflections reflections = new Reflections(new FieldAnnotationsScanner(), type);

        Set<Field> columnDefinitions = reflections.getFieldsAnnotatedWith(Column.class);

        Iterable<TableColumn<T, String>> columns = transform(columnDefinitions, columnDefinition -> {

            TableColumn<T, String> column = new TableColumn<>(columnDefinition.getAnnotation(Column.class).name());
            column.setCellValueFactory(new PropertyValueFactory<>(columnDefinition.getName()));
            return column;
        });
        table.setItems(observableArrayList(newArrayList(elements)));
        table.getColumns().addAll(newArrayList(columns));
        return table;
    }

}
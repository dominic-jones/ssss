package com.dv.ssss.ui;

import com.google.common.collect.Ordering;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import java.lang.reflect.Field;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static javafx.collections.FXCollections.observableArrayList;

public class AnnotatedTable {

    public <T> TableView<T> createTable(Iterable<T> elements, Class<T> type) {

        Iterable<Field> columnDefinitions = Ordering.natural()
                .<Field>onResultOf(f -> f.getAnnotation(Column.class).order())
                .sortedCopy(
                        new Reflections(new FieldAnnotationsScanner(), type)
                                .getFieldsAnnotatedWith(Column.class)
                );

        Iterable<TableColumn<T, String>> columns = createColumns(columnDefinitions);

        TableView<T> table = new TableView<>();
        table.setItems(observableArrayList(newArrayList(elements)));
        table.getColumns().addAll(newArrayList(columns));
        return table;
    }

    private <T> Iterable<TableColumn<T, String>> createColumns(Iterable<Field> columnDefinitions) {

        return transform(columnDefinitions, columnDefinition -> {

            TableColumn<T, String> column = new TableColumn<>(columnDefinition.getAnnotation(Column.class).name());
            column.setCellValueFactory(new PropertyValueFactory<>(columnDefinition.getName()));
            return column;
        });
    }
}
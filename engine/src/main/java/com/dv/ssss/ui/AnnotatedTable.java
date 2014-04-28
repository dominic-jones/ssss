package com.dv.ssss.ui;

import com.google.common.collect.Ordering;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.Method;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static javafx.collections.FXCollections.observableArrayList;

public class AnnotatedTable {

    public <T> TableView<T> createTable(Iterable<T> elements, Class<T> type) {

        Iterable<Method> columnDefinitions = Ordering.natural()
                .<Method>onResultOf(f -> f.getAnnotation(Column.class).order())
                .sortedCopy(
                        new Reflections(new MethodAnnotationsScanner(), type)
                                .getMethodsAnnotatedWith(Column.class)
                );

        Iterable<TableColumn<T, String>> columns = createColumns(columnDefinitions);

        TableView<T> table = new TableView<>();
        table.setItems(observableArrayList(newArrayList(elements)));
        table.getColumns().addAll(newArrayList(columns));
        return table;
    }

    private <T> Iterable<TableColumn<T, String>> createColumns(Iterable<Method> columnDefinitions) {

        return transform(columnDefinitions, columnDefinition -> {

            Column annotation = columnDefinition.getAnnotation(Column.class);
            TableColumn<T, String> column = new TableColumn<>(annotation.name());
            column.setCellValueFactory(new MethodValueFactory<>(columnDefinition));
            return column;
        });
    }
}
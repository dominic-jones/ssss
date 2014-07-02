package com.dv.ssss.ui.other;

import com.google.common.collect.Ordering;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.lang.reflect.Method;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

public class AnnotatedTable {

    public <T> TableView<T> createTable(ObservableList<T> items, Class<T> type) {

        Iterable<Method> columnDefinitions = Ordering.natural()
                                                     .<Method>onResultOf(f -> f.getAnnotation(Column.class).order())
                                                     .sortedCopy(filter(newArrayList(type.getMethods()), m -> m.isAnnotationPresent(Column.class)));

        Iterable<TableColumn<T, String>> columns = createColumns(columnDefinitions);

        TableView<T> table = new TableView<>();
        table.setItems(items);
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
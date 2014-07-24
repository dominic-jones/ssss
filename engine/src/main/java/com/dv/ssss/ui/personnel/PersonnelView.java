package com.dv.ssss.ui.personnel;

import static com.google.common.collect.Lists.newArrayList;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.scene.control.SelectionMode.MULTIPLE;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import rx.Observable;

import com.dv.ssss.ui.View;
import com.dv.ssss.ui.other.AnnotatedTable;
import com.dv.ssss.ui.other.ObservableEvent;

import org.qi4j.api.mixin.Mixins;

@Mixins(PersonnelView.PersonnelViewMixin.class)
public interface PersonnelView extends View {

    void setPeople(Iterable<PersonDto> people);

    class PersonnelViewMixin implements PersonnelView {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 10, 10, 10);
        private static final Font FONT = new Font("Arial", 20);

        ObservableList<PersonDto> items = observableArrayList();
        VBox pane = layout(
                title(),
                personnel()
        );

        @Override
        public Parent getView() {

            return pane;
        }

        private Label title() {

            Label label = new Label("Personnel");
            label.setFont(FONT);
            return label;
        }

        private TableView<PersonDto> personnel() {

            TableView<PersonDto> table = new AnnotatedTable()
                    .createTable(items, PersonDto.class);

            table.getSelectionModel().setSelectionMode(MULTIPLE);

            table.setRowFactory(tableView -> {

                TableRow<PersonDto> row = new TableRow<>();

                MenuItem transfer = new MenuItem("Transfer");

                Observable.create(new ObservableEvent<ActionEvent>(transfer::setOnAction))
                          .map(event -> new RowClickedEvent(tableView.getSelectionModel().getSelectedItem()))
                          .subscribe(p -> System.out.println(p.getSelectedPerson().getName()));

                ContextMenu contextMenu = new ContextMenu(transfer);
                contextMenu.setOnShowing(windowEvent -> {

                    boolean onePersonSelected = tableView.getSelectionModel().getSelectedCells().size() == 1;
                    transfer.setVisible(onePersonSelected);
                });
                row.setContextMenu(contextMenu);

                return row;
            });

            table.setEditable(false);
            return table;
        }

        private VBox layout(Label label,
                            TableView<PersonDto> table) {

            VBox pane = new VBox();

            pane.setSpacing(SPACING);
            pane.setPadding(INSETS);
            pane.getChildren()
                .addAll(label, table);
            return pane;
        }

        @Override
        public void setPeople(Iterable<PersonDto> people) {

            items.clear();
            items.addAll(newArrayList(people));
        }
    }

}

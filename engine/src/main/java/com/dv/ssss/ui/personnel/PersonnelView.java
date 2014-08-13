package com.dv.ssss.ui.personnel;

import com.dv.ssss.ui.View;
import com.dv.ssss.ui.other.AnnotatedTable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.qi4j.api.mixin.Mixins;
import rx.functions.Action1;

import static com.google.common.collect.Lists.newArrayList;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.scene.control.SelectionMode.MULTIPLE;
import static org.qi4j.functional.Iterables.toArray;

@Mixins(PersonnelView.PersonnelViewMixin.class)
public interface PersonnelView extends View {

    PersonDto getSelectedPerson();

    Action1<EventHandler<ActionEvent>> bindTransferButtonHandler();

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

        MenuItem transfer;
        TableView.TableViewSelectionModel<PersonDto> selectionModel;


        @Override
        public Parent getView() {

            return pane;
        }

        @Override
        public PersonDto getSelectedPerson() {

            return selectionModel.getSelectedItem();
        }

        @Override
        public Action1<EventHandler<ActionEvent>> bindTransferButtonHandler() {

            return transfer::setOnAction;
        }

        private Label title() {

            Label label = new Label("Personnel");
            label.setFont(FONT);
            return label;
        }

        private TableView<PersonDto> personnel() {

            TableView<PersonDto> table = new AnnotatedTable()
                    .createTable(items, PersonDto.class);

            table.setEditable(false);
            selectionModel = table.getSelectionModel();
            selectionModel.setSelectionMode(MULTIPLE);
            Iterable<MenuItem> singleSelectActions = newArrayList(
                    choosePlayer()
            );

            table.setRowFactory(tableView -> {

                TableRow<PersonDto> row = new TableRow<>();

                row.setContextMenu(contextMenu(selectionModel, singleSelectActions));

                return row;
            });
            return table;
        }

        private ContextMenu contextMenu(TableView.TableViewSelectionModel<PersonDto> selectionModel,
                                        Iterable<MenuItem> singleSelectActions) {

            ContextMenu contextMenu = new ContextMenu(toArray(MenuItem.class, singleSelectActions));
            contextMenu.setOnShowing(windowEvent -> {

                boolean onePersonSelected = selectionModel.getSelectedCells().size() == 1;
                singleSelectActions.forEach(a -> a.setVisible(onePersonSelected));
            });
            return contextMenu;
        }

        private MenuItem choosePlayer() {

            transfer = new MenuItem("Choose Player");
            return transfer;
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

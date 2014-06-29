package com.dv.ssss.personnel;

import com.dv.ssss.people.Person;
import com.dv.ssss.ui.AnnotatedTable;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

import static javafx.collections.FXCollections.observableArrayList;

@Mixins(PersonnelWidget.PersonnelWidgetMixin.class)
public interface PersonnelWidget {

    Pane getView();

    void loadPeople(Observable<Person> aegis);

    class PersonnelWidgetMixin implements PersonnelWidget {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);
        private static final Font FONT = new Font("Arial", 20);

        ObservableList<Person> items = observableArrayList();

        @Override
        public Pane getView() {

            Label label = new Label("Personnel");
            label.setFont(FONT);

            TableView<Person> table = new AnnotatedTable()
                    .createTable(items, Person.class);
            table.setEditable(false);

            VBox pane = new VBox();

            pane.setSpacing(SPACING);
            pane.setPadding(INSETS);
            pane.getChildren()
                .addAll(label, table);

            return pane;
        }

        @Override
        public void loadPeople(Observable<Person> people) {

            items.clear();
            people.toList()
                  .subscribe(items::addAll);
        }
    }
}
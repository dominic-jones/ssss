package com.dv.ssss.ui;

import com.dv.ssss.people.Person;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import rx.Observable;

import static javafx.collections.FXCollections.observableArrayList;

@Mixins(PersonnelView.PersonnelViewMixin.class)
public interface PersonnelView {

    VBox getView();

    void update(Observable<Person> events);

    class PersonnelViewMixin implements PersonnelView {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);
        private static final Font FONT = new Font("Arial", 20);

        @Uses
        VBox vbox;

        ObservableList<Person> items;

        @Override
        public VBox getView() {

            Label label = new Label("Personnel");
            label.setFont(FONT);

            items = observableArrayList();
            TableView<Person> table = new AnnotatedTable()
                    .createTable(items, Person.class);
            table.setEditable(false);

            vbox.setSpacing(SPACING);
            vbox.setPadding(INSETS);
            vbox.getChildren()
                .addAll(label, table);

            return vbox;
        }

        @Override
        public void update(Observable<Person> people) {

            items.clear();
            people.toList()
                  .subscribe(items::addAll);
        }
    }
}
package com.dv.ssss.ui;

import com.dv.ssss.people.Person;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.structure.Module;

import static com.google.common.collect.Lists.newArrayList;
import static javafx.collections.FXCollections.observableArrayList;

@Mixins(PersonnelView.PersonnelViewMixin.class)
public interface PersonnelView {

    VBox getView();

    void update(Iterable<Person> events);

    class PersonnelViewMixin implements PersonnelView {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);
        private static final Font FONT = new Font("Arial", 20);

        @Structure
        Module module;

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
            vbox.getChildren().addAll(label, table);

            module.newTransient(PersonnelMediator.class, this)
                    .loadPeople();

            return vbox;
        }

        @Override
        public void update(Iterable<Person> people) {

            items.removeAll();
            items.addAll(newArrayList(people));
        }
    }
}
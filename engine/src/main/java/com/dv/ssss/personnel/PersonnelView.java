package com.dv.ssss.personnel;

import com.dv.ssss.ui.AnnotatedTable;
import com.dv.ssss.ui.View;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.qi4j.api.mixin.Mixins;

import static com.google.common.collect.Lists.newArrayList;
import static javafx.collections.FXCollections.observableArrayList;

@Mixins(PersonnelView.PersonnelViewMixin.class)
public interface PersonnelView extends View {

    void setPeople(Iterable<PersonDto> people);

    class PersonnelViewMixin implements PersonnelView {

        private static final int SPACING = 5;
        private static final Insets INSETS = new Insets(10, 0, 0, 10);
        private static final Font FONT = new Font("Arial", 20);

        ObservableList<PersonDto> items = observableArrayList();

        @Override
        public Parent getView() {

            Label label = new Label("Personnel");
            label.setFont(FONT);

            TableView<PersonDto> table = new AnnotatedTable()
                    .createTable(items, PersonDto.class);
            table.setEditable(false);

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

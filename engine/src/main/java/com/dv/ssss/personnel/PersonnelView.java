package com.dv.ssss.personnel;

import com.dv.ssss.event.EventPoster;
import com.dv.ssss.turn.EndTurnCommand;
import com.dv.ssss.turn.TurnWidget;
import com.dv.ssss.turn.TurnWidgetController;
import com.dv.ssss.ui.PresenterFactory;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import rx.functions.Action1;

@Mixins(PersonnelView.PersonnelViewMixin.class)
public interface PersonnelView {

    void display(Stage stage);

    class PersonnelViewMixin implements PersonnelView {

        @Service
        EventPoster eventPoster;

        @Service
        PresenterFactory presenterFactory;

        @Structure
        TransientBuilderFactory transientBuilderFactory;

        @Override
        public void display(Stage stage) {

            Group group = new Group();
            Scene scene = new Scene(group);
            stage.setTitle("SSSS");
            stage.setWidth(300);
            stage.setHeight(500);

            Pane personnel = personnel();
            Pane turn = turn();

            BorderPane layout = new BorderPane();
            layout.setTop(turn);
            layout.setCenter(personnel);

            group.getChildren().addAll(layout);
            stage.setScene(scene);
            stage.show();
        }

        private Pane personnel() {

            PersonnelWidget personnelWidget = transientBuilderFactory.newTransient(PersonnelWidget.class);
            PersonnelWidgetController personnelWidgetController = presenterFactory.create(PersonnelWidgetController.class, personnelWidget);
            Pane personnel = personnelWidget.getView();
            personnelWidgetController.loadPeople();
            return personnel;
        }

        private Pane turn() {

            TurnWidget turnWidget = transientBuilderFactory.newTransient(
                    TurnWidget.class,
                    (Action1<? super EndTurnCommand>) eventPoster::post
            );
            TurnWidgetController turnWidgetController = presenterFactory.create(TurnWidgetController.class, turnWidget);
            Pane turn = turnWidget.getView();
            turnWidgetController.initializeTurn();
            return turn;
        }

    }
}

package com.dv.ssss.personnel;

import com.dv.ssss.event.EventRegistry;
import com.dv.ssss.people.Person;
import com.dv.ssss.turn.EndTurnCommand;
import com.dv.ssss.turn.TurnWidget;
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
import rx.Observable;
import rx.functions.Action1;

@Mixins(PersonnelView.PersonnelViewMixin.class)
public interface PersonnelView {

    void display(Stage stage);

    void attachPresenter(PersonnelViewPresenter personnelViewPresenter);

    void loadPeople(Observable<Person> people);

    void initializeTurn(int turn);

    //TODO Remove
    void init();

    class PersonnelViewMixin implements PersonnelView {

        @Service
        PresenterFactory presenterFactory;

        @Structure
        TransientBuilderFactory transientBuilderFactory;

        PersonnelViewPresenter personnelViewPresenter;
        PersonnelWidget personnelWidget;

        TurnWidget turnWidget;

        @Override
        public void display(Stage stage) {

            Group group = new Group();
            Scene scene = new Scene(group);
            stage.setTitle("SSSS");
            stage.setWidth(300);
            stage.setHeight(500);

            Pane personnel = personnelWidget.getView();

            Pane turn = turnWidget.getView();

            BorderPane layout = new BorderPane();
            layout.setTop(turn);
            layout.setCenter(personnel);

            group.getChildren().addAll(layout);
            stage.setScene(scene);
            stage.show();
        }

        @Override
        public void attachPresenter(PersonnelViewPresenter personnelViewPresenter) {

            this.personnelViewPresenter = personnelViewPresenter;
        }

        @Override
        public void loadPeople(Observable<Person> people) {

            personnelWidget.loadPeople(people);
        }

        @Override
        public void initializeTurn(int turn) {

            turnWidget.initializeTurn(turn);
        }

        @Service
        EventRegistry eventRegistry;

        @Override
        public void init() {

            personnelWidget = transientBuilderFactory.newTransient(PersonnelWidget.class);
            eventRegistry.register(personnelWidget);

            turnWidget = transientBuilderFactory.newTransient(
                    TurnWidget.class,
                    (Action1<? super EndTurnCommand>) personnelViewPresenter::endTurn
            );
            eventRegistry.register(turnWidget);
        }

    }
}

package com.dv.ssss;

import javafx.scene.Scene;
import javafx.stage.Stage;

import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.ui.PresenterFactory;
import com.dv.ssss.ui.main.MainPresenter;

import org.qi4j.api.activation.ActivationException;
import org.qi4j.api.structure.Application;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;

public class Bootstrap extends javafx.application.Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Application application = application();

        // TODO 2014-07-07 dom: Should eventually be instantiated from a new-game button.
        String gameIdentity = application.findModule("domain", "game")
                                         .findService(GameService.class)
                                         .get()
                                         .newGame();

        MainPresenter mainPresenter = application.findModule("user-interface", "all")
                                                 .findService(PresenterFactory.class)
                                                 .get()
                                                 .create(MainPresenter.class, gameIdentity);

        run(stage, mainPresenter);
    }

    private Application application() {

        Application application;
        try {
            application = new Energy4Java().newApplication(
                    new GameAssembler()
            );
            application.activate();
        } catch (AssemblyException | ActivationException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return application;
    }

    private void run(Stage stage, MainPresenter mainPresenter) {

        Scene scene = new Scene(mainPresenter.getView().getView());
        stage.setTitle("SSSS");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setScene(scene);
        stage.show();
    }
}
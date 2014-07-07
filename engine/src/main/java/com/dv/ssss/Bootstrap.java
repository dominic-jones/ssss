package com.dv.ssss;

import javafx.scene.Scene;
import javafx.stage.Stage;

import com.dv.ssss.domain.DataBootstrapService;
import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.ui.PresenterFactory;
import com.dv.ssss.ui.main.MainPresenter;

import org.qi4j.api.activation.ActivationException;
import org.qi4j.api.structure.Application;
import org.qi4j.api.structure.Module;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;

public class Bootstrap extends javafx.application.Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Energy4Java qi4j = new Energy4Java();
        Application application;
        try {
            application = qi4j.newApplication(new GameAssembler());
            application.activate();
        } catch (AssemblyException | ActivationException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        Module game = application.findModule("domain", "game");

        String gameIdentity = game.findService(GameService.class)
                                  .get()
                                  .newGame();

        Module userInterface = application.findModule("user-interface", "all");
        Module domain = application.findModule("domain", "root");

        domain.findService(DataBootstrapService.class)
              .get()
              .bootstrap();

        MainPresenter mainPresenter = userInterface.findService(PresenterFactory.class)
                                                   .get()
                                                   .create(MainPresenter.class, gameIdentity);

        run(stage, mainPresenter);
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
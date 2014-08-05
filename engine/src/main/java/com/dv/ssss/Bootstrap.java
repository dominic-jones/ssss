package com.dv.ssss;

import javafx.scene.Scene;
import javafx.stage.Stage;

import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.game.NewGameStartedEvent;
import com.dv.ssss.inf.BootstrapException;
import com.dv.ssss.inf.event.EventPoster;
import com.dv.ssss.inf.event.EventRegistrationService;
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

        application.findModule("application", "event")
                .findService(EventRegistrationService.class)
                .get()
                .register();

        // TODO 2014-07-07 dom: Should eventually be instantiated from a new-game button.
        String gameIdentity = application.findModule("application", "module")
                                         .findService(GameService.class)
                                         .get()
                                         .startNewGame();

        MainPresenter mainPresenter = application.findModule("user-interface", "all")
                                                 .findService(PresenterFactory.class)
                                                 .get()
                                                 .create(MainPresenter.class, gameIdentity);

        // TODO 2014-08-05 dom: Temporary, do not keep after new game event handler consumed
        application.findModule("infrastructure", "event")
                   .findService(EventPoster.class)
                   .get()
                   .post(new NewGameStartedEvent(gameIdentity));

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
            throw new BootstrapException(e);
        }
        return application;
    }

    private void run(Stage stage, MainPresenter mainPresenter) {

        Scene scene = new Scene(mainPresenter.getView().getView());
        stage.setTitle("SSSS");
        stage.setWidth(640);
        stage.setHeight(480);
        stage.setScene(scene);
        stage.show();
    }
}
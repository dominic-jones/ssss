package com.dv.ssss;

import static org.qi4j.api.common.Visibility.application;
import static org.qi4j.api.common.Visibility.layer;

import javafx.scene.Scene;
import javafx.stage.Stage;

import com.dv.ssss.domain.age.AgeRepository;
import com.dv.ssss.domain.game.Game;
import com.dv.ssss.domain.game.GameFactory;
import com.dv.ssss.domain.game.GameRepository;
import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.people.PersonEntity;
import com.dv.ssss.domain.people.PersonFactory;
import com.dv.ssss.domain.people.PersonnelRepository;
import com.dv.ssss.domain.turn.Turn;
import com.dv.ssss.domain.turn.TurnFactory;
import com.dv.ssss.domain.turn.TurnRepository;
import com.dv.ssss.inf.event.EventAssembler;
import com.dv.ssss.personnel.PersonnelService;
import com.dv.ssss.ui.PresenterFactory;
import com.dv.ssss.ui.main.MainPresenter;
import com.dv.ssss.ui.main.MainView;
import com.dv.ssss.ui.personnel.PersonnelPresenter;
import com.dv.ssss.ui.personnel.PersonnelView;
import com.dv.ssss.ui.turn.TurnPresenter;
import com.dv.ssss.ui.turn.TurnView;

import org.qi4j.api.activation.ActivationException;
import org.qi4j.api.property.Property;
import org.qi4j.api.structure.Application;
import org.qi4j.api.structure.Module;
import org.qi4j.bootstrap.ApplicationAssembler;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entitystore.memory.MemoryEntityStoreAssembler;
import org.qi4j.index.rdf.assembly.RdfMemoryStoreAssembler;

public class Bootstrap extends javafx.application.Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Energy4Java qi4j = new Energy4Java();
        Application application;
        try {
            application = qi4j.newApplication(assembler());
            application.activate();
        } catch (AssemblyException | ActivationException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        Module game = application.findModule("domain", "game");

        Property<String> gameIdentity = game.findService(GameService.class)
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

    private ApplicationAssembler assembler() {

        return factory -> {
            ApplicationAssembly assembly = factory.newApplicationAssembly();

            LayerAssembly userInterface = userInterface(assembly);
            LayerAssembly domain = domain(assembly);
            LayerAssembly persistence = persistence(assembly);

            userInterface.uses(domain);
            domain.uses(persistence);

            return assembly;
        };
    }

    private LayerAssembly userInterface(ApplicationAssembly assembly) throws AssemblyException {

        LayerAssembly userInterface = assembly.layer("user-interface");
        ModuleAssembly userInterfaceModules = userInterface.module("all");

        userInterfaceModules.services(
                PresenterFactory.class
        );

        userInterfaceModules.transients(
                MainPresenter.class,
                MainView.class,
                PersonnelPresenter.class,
                PersonnelView.class,
                TurnPresenter.class,
                TurnView.class
        );
        new EventAssembler().assemble(userInterfaceModules);
        return userInterface;
    }

    private LayerAssembly domain(ApplicationAssembly assembly) throws AssemblyException {

        LayerAssembly domain = assembly.layer("domain");

        ModuleAssembly game = domain.module("game");
        game.services(
                GameService.class
        ).visibleIn(application);
        game.services(
                GameFactory.class,
                GameRepository.class
        );
        game.entities(Game.class);

        ModuleAssembly domainModules = domain.module("root");

        domainModules.entities(
                PersonEntity.class,
                Turn.class
        ).visibleIn(layer);

        domainModules.services(
                PersonnelService.class
        ).visibleIn(application);

        domainModules.services(
                AgeRepository.class,
                PersonnelRepository.class,
                TurnRepository.class
        );

        domainModules.services(
                PersonFactory.class,
                TurnFactory.class
        ).visibleIn(layer);

        new EventAssembler().assemble(domainModules);

        domainModules.services(
                DataBootstrapService.class
        );

        domainModules.services(Engine.class)
                     .visibleIn(application);
        return domain;
    }

    private LayerAssembly persistence(ApplicationAssembly assembly) throws AssemblyException {

        LayerAssembly persistence = assembly.layer("persistence");
        ModuleAssembly infrastructure = persistence.module("infrastructure");

        new MemoryEntityStoreAssembler()
                .visibleIn(application)
                .assemble(infrastructure);
        new RdfMemoryStoreAssembler().assemble(infrastructure);
        return persistence;
    }

}
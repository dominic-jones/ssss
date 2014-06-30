package com.dv.ssss;

import com.dv.ssss.age.AgeRepository;
import com.dv.ssss.bootstrap.StartApplicationCommand;
import com.dv.ssss.event.EventActivator;
import com.dv.ssss.event.EventAssembler;
import com.dv.ssss.game.Game;
import com.dv.ssss.game.GameFactory;
import com.dv.ssss.game.GameRepository;
import com.dv.ssss.game.GameService;
import com.dv.ssss.people.PersonEntity;
import com.dv.ssss.people.PersonFactory;
import com.dv.ssss.people.PersonnelRepository;
import com.dv.ssss.personnel.PersonnelService;
import com.dv.ssss.personnel.PersonnelView;
import com.dv.ssss.personnel.PersonnelViewPresenter;
import com.dv.ssss.personnel.PersonnelWidget;
import com.dv.ssss.turn.Turn;
import com.dv.ssss.turn.TurnFactory;
import com.dv.ssss.turn.TurnRepository;
import com.dv.ssss.turn.TurnWidget;
import com.dv.ssss.ui.PresenterFactory;
import javafx.stage.Stage;
import org.qi4j.api.activation.ActivationException;
import org.qi4j.api.property.Property;
import org.qi4j.api.structure.Application;
import org.qi4j.api.structure.Module;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.bootstrap.ApplicationAssembler;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entitystore.memory.MemoryEntityStoreAssembler;
import org.qi4j.index.rdf.assembly.RdfMemoryStoreAssembler;

import static org.qi4j.api.common.Visibility.application;
import static org.qi4j.api.common.Visibility.layer;

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

        Module module = application.findModule("domain", "game");
        module.newUnitOfWork();
        GameFactory gameFactory = module.findService(GameFactory.class).get();
        Game game = gameFactory.create();
        Property<String> gameIdentity = game.identity();
        try {
            module.currentUnitOfWork().complete();
        } catch (UnitOfWorkCompletionException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        Module userInterface = application.findModule("user-interface", "all");
        Module domain = application.findModule("domain", "root");

        domain.findService(DataBootstrapService.class)
              .get()
              .bootstrap();

        PersonnelView personnelView = userInterface.newTransient(PersonnelView.class);
        PersonnelViewPresenter personnelViewPresenter = userInterface.findService(PresenterFactory.class)
                                                                     .get()
                                                                     .create(PersonnelViewPresenter.class, personnelView, gameIdentity);

        domain.findService(Engine.class)
              .get()
              .startApplication(new StartApplicationCommand(personnelViewPresenter, stage, game));
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
                PersonnelView.class,
                PersonnelViewPresenter.class,
                PersonnelWidget.class,
                TurnWidget.class
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
                     .withActivators(EventActivator.class)
                     .instantiateOnStartup()
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
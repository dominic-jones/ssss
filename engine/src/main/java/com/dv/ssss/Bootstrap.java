package com.dv.ssss;

import com.dv.ssss.age.AgeRepository;
import com.dv.ssss.bootstrap.StartApplicationCommand;
import com.dv.ssss.event.EventActivator;
import com.dv.ssss.event.EventAssembler;
import com.dv.ssss.people.PersonEntity;
import com.dv.ssss.people.PersonFactory;
import com.dv.ssss.people.PersonnelRepository;
import com.dv.ssss.personnel.PersonnelView;
import com.dv.ssss.personnel.PersonnelViewPresenter;
import com.dv.ssss.personnel.PersonnelWidget;
import com.dv.ssss.turn.Turn;
import com.dv.ssss.turn.TurnEndedEventFactory;
import com.dv.ssss.turn.TurnFactory;
import com.dv.ssss.turn.TurnRepository;
import com.dv.ssss.turn.TurnWidget;
import com.dv.ssss.ui.PresenterFactory;
import javafx.stage.Stage;
import org.qi4j.api.activation.ActivationException;
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

import static org.qi4j.api.common.Visibility.application;

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

        Module userInterface = application.findModule("user-interface", "all");
        Module domain = application.findModule("domain", "all");

        domain.findService(DataBootstrapService.class)
              .get()
              .bootstrap();

        domain.newUnitOfWork();

        PersonnelView personnelView = userInterface.newTransient(PersonnelView.class);
        PersonnelViewPresenter personnelViewPresenter = userInterface.findService(PresenterFactory.class)
                                                                     .get()
                                                                     .create(PersonnelViewPresenter.class, personnelView);

        domain.findService(Engine.class)
              .get()
              .startApplication(new StartApplicationCommand(personnelViewPresenter, stage));
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
        ModuleAssembly domainModules = domain.module("all");

        domainModules.entities(
                PersonEntity.class,
                Turn.class
        );

        domainModules.services(
                AgeRepository.class,
                PersonnelRepository.class,
                TurnRepository.class
        ).visibleIn(application);

        domainModules.services(
                PersonFactory.class,
                TurnFactory.class,
                TurnEndedEventFactory.class
        );

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
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
import javafx.application.Application;
import javafx.stage.Stage;
import org.qi4j.api.activation.ActivationException;
import org.qi4j.api.structure.Module;
import org.qi4j.bootstrap.ApplicationAssembler;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entitystore.memory.MemoryEntityStoreAssembler;
import org.qi4j.index.rdf.assembly.RdfMemoryStoreAssembler;

public class Game extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {

        ApplicationAssembler assembler = factory -> {

            ApplicationAssembly assembly = factory.newApplicationAssembly();

            LayerAssembly userInterface = assembly.layer("user-interface");
            ModuleAssembly userInterfaceModules = userInterface.module("all");

            userInterfaceModules.transients(
                    PersonnelView.class,
                    PersonnelViewPresenter.class,
                    PersonnelWidget.class,
                    TurnWidget.class
            );

            LayerAssembly remainder = assembly.layer("remainder");
            ModuleAssembly rest = remainder.module("rest");

            userInterface.uses(remainder);

            rest.entities(
                    PersonEntity.class,
                    Turn.class
            );

            rest.services(
                    AgeRepository.class,
                    PersonFactory.class,
                    PersonnelRepository.class,
                    TurnFactory.class,
                    TurnRepository.class,
                    TurnEndedEventFactory.class
            );

            new EventAssembler().assemble(rest);

            rest.services(
                    DataBootstrap.class,
                    PresenterFactory.class
            );

            rest.services(Engine.class)
                .withActivators(EventActivator.class)
                .instantiateOnStartup();

            rest.transients(
                    PersonnelView.class,
                    PersonnelViewPresenter.class,
                    PersonnelWidget.class,
                    TurnWidget.class
            );

            new MemoryEntityStoreAssembler().assemble(rest);
            new RdfMemoryStoreAssembler().assemble(rest);

            return assembly;
        };

        Energy4Java energy4Java = new Energy4Java();
        org.qi4j.api.structure.Application application;
        try {
            application = energy4Java.newApplication(assembler);
            application.activate();
        } catch (AssemblyException | ActivationException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        Module module = application.findModule("remainder", "rest");

        module.newUnitOfWork();

        module.findService(DataBootstrap.class)
              .get()
              .bootstrap();

        module.newUnitOfWork();

        PersonnelView personnelView = module.newTransient(PersonnelView.class);
        PersonnelViewPresenter personnelViewPresenter = module.findService(PresenterFactory.class)
                                                              .get()
                                                              .create(PersonnelViewPresenter.class, personnelView);

        module.findService(Engine.class)
              .get()
              .startApplication(new StartApplicationCommand(personnelViewPresenter, stage));
    }

}
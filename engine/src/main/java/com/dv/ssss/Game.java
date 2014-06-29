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
import com.dv.ssss.personnel.PersonnelWidgetController;
import com.dv.ssss.turn.Turn;
import com.dv.ssss.turn.TurnEndedEvent;
import com.dv.ssss.turn.TurnEndedEventFactory;
import com.dv.ssss.turn.TurnFactory;
import com.dv.ssss.turn.TurnRepository;
import com.dv.ssss.turn.TurnWidget;
import com.dv.ssss.ui.PresenterFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import org.qi4j.api.activation.ActivationException;
import org.qi4j.api.structure.Module;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.SingletonAssembler;
import org.qi4j.entitystore.memory.MemoryEntityStoreAssembler;
import org.qi4j.index.rdf.assembly.RdfMemoryStoreAssembler;

public class Game extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {

        SingletonAssembler assembler;
        try {
            assembler = new SingletonAssembler() {
                @Override
                public void assemble(ModuleAssembly assembly) throws AssemblyException {

                    assembly.entities(
                            PersonEntity.class,
                            Turn.class
                    );

                    assembly.values(
                            TurnEndedEvent.class
                    );

                    assembly.services(
                            AgeRepository.class,
                            PersonFactory.class,
                            PersonnelRepository.class,
                            TurnFactory.class,
                            TurnRepository.class,
                            TurnEndedEventFactory.class
                    );

                    new EventAssembler().assemble(assembly);

                    assembly.services(
                            DataBootstrap.class,
                            PresenterFactory.class
                    );

                    assembly.services(Engine.class)
                            .withActivators(EventActivator.class)
                            .instantiateOnStartup();

                    assembly.transients(
                            PersonnelView.class,
                            PersonnelViewPresenter.class,
                            PersonnelWidget.class,
                            PersonnelWidgetController.class,
                            TurnWidget.class
                    );

                    new MemoryEntityStoreAssembler().assemble(assembly);
                    new RdfMemoryStoreAssembler().assemble(assembly);
                }
            };
        } catch (AssemblyException | ActivationException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        Module module = assembler.module();

        module.newUnitOfWork();

        module.findService(DataBootstrap.class)
              .get()
              .bootstrap();

        PersonnelView personnelView = module.newTransient(PersonnelView.class);
        PersonnelViewPresenter personnelViewPresenter = module.newTransient(PersonnelViewPresenter.class, personnelView);
        personnelViewPresenter.init(personnelView);

        module.findService(Engine.class)
              .get()
              .startApplication(new StartApplicationCommand(personnelView, stage));
    }

}
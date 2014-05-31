package com.dv.ssss;

import com.dv.ssss.age.AgeRepository;
import com.dv.ssss.bootstrap.ApplicationStartedEvent;
import com.dv.ssss.event.EventActivator;
import com.dv.ssss.event.EventBusService;
import com.dv.ssss.event.EventPoster;
import com.dv.ssss.event.EventRegistry;
import com.dv.ssss.people.PersonEntity;
import com.dv.ssss.people.PersonFactory;
import com.dv.ssss.people.PersonnelRepository;
import com.dv.ssss.personnel.PersonnelView;
import com.dv.ssss.personnel.PersonnelViewMediator;
import com.dv.ssss.turn.Turn;
import com.dv.ssss.turn.TurnEndedEvent;
import com.dv.ssss.turn.TurnEndedEventFactory;
import com.dv.ssss.turn.TurnFactory;
import com.dv.ssss.turn.TurnRepository;
import com.dv.ssss.turn.TurnView;
import com.dv.ssss.turn.TurnViewMediator;
import com.dv.ssss.ui.MediatorBuilder;
import com.dv.ssss.ui.UI;
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

                    assembly.services(
                            EventBusService.class,
                            EventPoster.class,
                            EventRegistry.class
                    );

                    assembly.services(
                            DataBootstrap.class,
                            MediatorBuilder.class
                    );

                    assembly.services(Engine.class)
                            .withActivators(EventActivator.class)
                            .instantiateOnStartup();

                    assembly.transients(
                            UI.class,
                            PersonnelView.class,
                            PersonnelViewMediator.class,
                            TurnView.class,
                            TurnViewMediator.class
                    );

                    new MemoryEntityStoreAssembler().assemble(assembly);
                    new RdfMemoryStoreAssembler().assemble(assembly);
                }
            };
        } catch (AssemblyException | ActivationException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        Module module = assembler.module();

        EventPoster eventPoster = module.findService(EventPoster.class)
                                        .get();

        module.newUnitOfWork();

        UI ui = module.newTransient(UI.class);

        eventPoster.post(new ApplicationStartedEvent(ui, stage));
    }

}
package com.dv.ssss;

import com.dv.ssss.people.PersonImpl;
import com.dv.ssss.people.PersonnelServiceImpl;
import com.dv.ssss.ui.UI;
import javafx.application.Application;
import javafx.stage.Stage;
import org.qi4j.api.activation.ActivationException;
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

                    assembly.entities(PersonImpl.class);

                    assembly.services(
                            DataBootstrap.class,
                            EngineImpl.class,
                            PersonnelServiceImpl.class
                    );
                    assembly.transients(UI.class);

                    new MemoryEntityStoreAssembler().assemble(assembly);
                    new RdfMemoryStoreAssembler().assemble(assembly);
                }
            };
        } catch (AssemblyException | ActivationException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        assembler.module()
                .findService(DataBootstrap.class)
                .get()
                .bootstrap();

        assembler.module()
                .newUnitOfWork();

        assembler.module()
                .newTransient(UI.class)
                .display(stage);
    }

}
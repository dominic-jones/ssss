package com.dv.ssss;

import com.dv.ssss.ui.UI;
import javafx.application.Application;
import javafx.stage.Stage;
import org.qi4j.api.activation.ActivationException;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.SingletonAssembler;

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

                    assembly.transients(UI.class);
                }
            };
        } catch (AssemblyException | ActivationException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        assembler.module()
                .newTransient(UI.class)
                .display(stage);
    }

}
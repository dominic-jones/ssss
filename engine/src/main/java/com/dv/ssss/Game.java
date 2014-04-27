package com.dv.ssss;

import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application {

    private UI ui;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {

        ui.display(stage);
    }

}
package com.dv.ssss.ui;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class OtherView implements View {

    public Parent getView() {

        return new VBox(
                new Label("Test")
        );
    }

}

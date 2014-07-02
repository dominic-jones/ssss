package com.dv.ssss.ui.test;

import com.dv.ssss.ui.View;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TestView implements View {

    public Parent getView() {

        return new VBox(
                new Label("Test")
        );
    }

}

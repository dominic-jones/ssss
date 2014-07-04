package com.dv.ssss.ui.test;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.dv.ssss.ui.View;

public class TestView implements View {

    private VBox view = view();

    public Parent getView() {

        return view;
    }

    private VBox view() {

        return new VBox(
                new Label("Test")
        );
    }

}

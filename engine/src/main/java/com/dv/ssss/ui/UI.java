package com.dv.ssss.ui;

import javafx.stage.Stage;
import org.qi4j.api.mixin.Mixins;

@Mixins(UIImpl.class)
public interface UI {

    void display(Stage stage);
}

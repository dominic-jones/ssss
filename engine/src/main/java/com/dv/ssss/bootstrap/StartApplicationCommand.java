package com.dv.ssss.bootstrap;

import com.dv.ssss.ui.UI;
import javafx.stage.Stage;

public class StartApplicationCommand {

    private UI ui;
    private Stage stage;

    public StartApplicationCommand(UI ui, Stage stage) {

        this.ui = ui;
        this.stage = stage;
    }

    public Stage getStage() {

        return stage;
    }

    public UI getUi() {

        return ui;
    }
}

package com.dv.ssss.bootstrap;

import com.dv.ssss.ui.PersonnelView;
import javafx.stage.Stage;

public class StartApplicationCommand {

    private PersonnelView personnelView;
    private Stage stage;

    public StartApplicationCommand(PersonnelView personnelView, Stage stage) {

        this.personnelView = personnelView;
        this.stage = stage;
    }

    public Stage getStage() {

        return stage;
    }

    public PersonnelView getPersonnelView() {

        return personnelView;
    }
}

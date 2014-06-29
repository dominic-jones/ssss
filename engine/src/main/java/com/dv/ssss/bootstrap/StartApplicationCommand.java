package com.dv.ssss.bootstrap;

import com.dv.ssss.personnel.PersonnelViewPresenter;
import javafx.stage.Stage;

public class StartApplicationCommand {

    private PersonnelViewPresenter personnelViewPresenter;
    private Stage stage;

    public StartApplicationCommand(PersonnelViewPresenter personnelViewPresenter, Stage stage) {

        this.personnelViewPresenter = personnelViewPresenter;
        this.stage = stage;
    }

    public Stage getStage() {

        return stage;
    }

    public PersonnelViewPresenter getPersonnelViewPresenter() {

        return personnelViewPresenter;
    }
}

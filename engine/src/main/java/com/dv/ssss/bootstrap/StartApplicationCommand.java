package com.dv.ssss.bootstrap;

import com.dv.ssss.game.Game;
import com.dv.ssss.personnel.PersonnelViewPresenter;
import javafx.stage.Stage;

public class StartApplicationCommand {

    private PersonnelViewPresenter personnelViewPresenter;
    private Stage stage;
    private Game game;

    public StartApplicationCommand(PersonnelViewPresenter personnelViewPresenter, Stage stage, Game game) {

        this.personnelViewPresenter = personnelViewPresenter;
        this.stage = stage;
        this.game = game;
    }

    public Stage getStage() {

        return stage;
    }

    public PersonnelViewPresenter getPersonnelViewPresenter() {

        return personnelViewPresenter;
    }

    public Game getGame() {

        return game;
    }
}

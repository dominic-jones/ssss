package com.dv.ssss.ui.personnel;

public class ChoosePlayerCommand {

    private String chosenPlayer;

    public ChoosePlayerCommand(PersonDto chosenPlayer) {

        this.chosenPlayer = chosenPlayer.getIdentity();
    }

    public String getChosenPlayer() {

        return chosenPlayer;
    }
}

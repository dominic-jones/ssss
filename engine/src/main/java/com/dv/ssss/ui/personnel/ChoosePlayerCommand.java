package com.dv.ssss.ui.personnel;

public class ChoosePlayerCommand {

    private String gameIdentity;
    private String chosenPlayer;

    public ChoosePlayerCommand(String gameIdentity,
                               PersonDto chosenPlayer) {

        this.gameIdentity = gameIdentity;

        this.chosenPlayer = chosenPlayer.getIdentity();
    }

    public String getGameIdentity() {

        return gameIdentity;
    }

    public String getChosenPlayer() {

        return chosenPlayer;
    }
}

package com.dv.ssss.ui.turn;

public class EndTurnCommand {

    private String gameIdentity;

    public EndTurnCommand(String gameIdentity) {

        this.gameIdentity = gameIdentity;
    }

    public String getGameIdentity() {

        return gameIdentity;
    }
}

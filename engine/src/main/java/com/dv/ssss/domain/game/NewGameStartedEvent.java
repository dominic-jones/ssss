package com.dv.ssss.domain.game;

public class NewGameStartedEvent {

    private String gameIdentity;

    public NewGameStartedEvent(String gameIdentity) {

        this.gameIdentity = gameIdentity;
    }

    public String getGameIdentity() {

        return gameIdentity;
    }
}

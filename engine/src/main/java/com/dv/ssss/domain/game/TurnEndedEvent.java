package com.dv.ssss.domain.game;

public class TurnEndedEvent {

    private String gameIdentity;

    public TurnEndedEvent(String gameIdentity) {

        this.gameIdentity = gameIdentity;
    }

    public String getGameIdentity() {

        return gameIdentity;
    }

    public void setGameIdentity(String gameIdentity) {

        this.gameIdentity = gameIdentity;
    }
}

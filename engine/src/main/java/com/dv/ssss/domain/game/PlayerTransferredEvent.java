package com.dv.ssss.domain.game;

public class PlayerTransferredEvent {

    private String personIdentity;

    public PlayerTransferredEvent(String personIdentity) {

        this.personIdentity = personIdentity;
    }

    public String getPersonIdentity() {

        return personIdentity;
    }
}

package com.dv.ssss.ui.player;

import com.dv.ssss.domain.people.PersonEntity;

public class PlayerDto {

    private final String name;

    public PlayerDto(PersonEntity person) {

        name = person.name().get();
    }

    public String getName() {

        return name;
    }
}

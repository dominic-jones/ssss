package com.dv.ssss.domain.game;

import org.joda.time.LocalDate;

public class TurnDto {

    private final int turnCount;
    private final LocalDate date;

    public TurnDto(Turn turn) {

        turnCount = turn.number();
        date = turn.currentDate();
    }

    public int getTurnCount() {

        return turnCount;
    }

    public LocalDate getDate() {

        return date;
    }
}

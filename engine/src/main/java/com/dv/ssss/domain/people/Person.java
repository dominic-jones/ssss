package com.dv.ssss.domain.people;

import com.dv.ssss.domain.age.Age;

public interface Person extends Age, Name, Rank {

    interface PersonState extends AgeState, NameState, RankState {

    }
}

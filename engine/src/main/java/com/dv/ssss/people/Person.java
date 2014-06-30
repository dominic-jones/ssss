package com.dv.ssss.people;

import com.dv.ssss.age.Age;

public interface Person extends Age, Name, Rank {

    interface PersonState extends AgeState, NameState, RankState {

    }
}

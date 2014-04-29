package com.dv.ssss.people;

import com.dv.ssss.age.Age;

public interface Person extends Age, Name, Rank, PersonView {

    interface PersonState extends AgeState, NameState, RankState {

    }
}

package com.dv.ssss.ui.personnel;

import com.dv.ssss.domain.faction.FactionEntity;
import com.dv.ssss.domain.people.PersonEntity;
import com.dv.ssss.ui.other.Column;

public class PersonDto {

    private final String name;
    private final Integer age;
    private final String rank;
    private final String factionName;

    public PersonDto(PersonEntity person, FactionEntity faction) {

        name = person.name().get();
        age = person.age().get();
        rank = person.rank().get();
        //TODO Correct place to decide this logic?
        factionName = faction == null ? "Unaligned" : faction.name().get();
    }

    @Column(name = "Name", order = 0)
    public String getName() {

        return name;
    }

    @Column(name = "Rank", order = 1)
    public String getRank() {

        return rank;
    }

    @Column(name = "Age", order = 2)
    public String getAge() {

        return String.valueOf(age);
    }

    @Column(name = "Faction", order = 3)
    public String getFaction() {

        return factionName;
    }
}

package com.dv.ssss.personnel;

import com.dv.ssss.people.PersonEntity;
import com.dv.ssss.ui.Column;

public class PersonDto {

    private final String name;
    private final Integer age;
    private final String rank;

    public PersonDto(PersonEntity person) {

        name = person.name().get();
        age = person.age().get();
        rank = person.rank().get();
    }

    @Column(name = "Name", order = 0)
    public String getName() {

        return name;
    }

    @Column(name = "Age", order = 2)
    public String getAge() {

        return String.valueOf(age);
    }

    @Column(name = "Rank", order = 1)
    public String getRank() {

        return rank;
    }
}

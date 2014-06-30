package com.dv.ssss.personnel;

import com.dv.ssss.people.Person;
import com.dv.ssss.ui.Column;

public class PersonDto {

    private final String name;
    private final String age;
    private final String rank;

    public PersonDto(Person person) {

        name = person.getName();
        age = person.getAge();
        rank = person.getRank();
    }

    @Column(name = "Name", order = 0)
    public String getName() {

        return name;
    }

    @Column(name = "Age", order = 2)
    public String getAge() {

        return age;
    }

    @Column(name = "Rank", order = 1)
    public String getRank() {

        return rank;
    }
}

package com.dv.ssss.people;

import com.dv.ssss.ui.Column;

public class Person {

    @Column(name = "Name")
    private String name;

    @Column(name = "Rank")
    private String rank;

    public Person(String name, String rank) {

        this.name = name;
        this.rank = rank;
    }

    public String getName() {

        return name;
    }

    public String getRank() {

        return rank;
    }
}

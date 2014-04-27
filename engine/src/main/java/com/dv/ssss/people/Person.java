package com.dv.ssss.people;

import com.dv.ssss.ui.Column;

public class Person {

    @Column(name = "Name", order = 0)
    private String name;

    @Column(name = "Rank", order = 1)
    private String rank;

    @Column(name = "Age", order = 2)
    private int age;

    public Person(String name, String rank, int age) {

        this.name = name;
        this.rank = rank;
        this.age = age;
    }

    public String getName() {

        return name;
    }

    public String getRank() {

        return rank;
    }

    public String getAge() {

        return String.valueOf(age);
    }
}

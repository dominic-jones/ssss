package com.dv.ssss.people;

import static com.google.common.collect.Lists.newArrayList;

public class PersonnelServiceImpl implements PersonnelService {

    private Iterable<Person> people = newArrayList(new Person("Aegis", "Overlord", 23));

    @Override
    public Iterable<Person> get() {

        return people;
    }
}

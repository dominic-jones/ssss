package com.dv.ssss.people;

import static com.google.common.collect.Lists.newArrayList;

public class PersonnelService {

    public Iterable<Person> get() {

        return newArrayList(new Person("Aegis", "Overlord", 23));
    }
}

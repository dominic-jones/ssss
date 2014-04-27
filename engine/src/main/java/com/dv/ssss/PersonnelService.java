package com.dv.ssss;

import static com.google.common.collect.Lists.newArrayList;

public class PersonnelService {

    Iterable<Person> get() {

        return newArrayList(new Person("Aegis", "Overlord"));
    }
}

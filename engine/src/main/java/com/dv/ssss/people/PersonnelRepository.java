package com.dv.ssss.people;

import org.qi4j.api.composite.Composite;
import org.qi4j.api.mixin.Mixins;

@Mixins(PersonnelRepositoryMixin.class)
public interface PersonnelRepository extends Composite {

    Iterable<Person> getByName(String name);
}

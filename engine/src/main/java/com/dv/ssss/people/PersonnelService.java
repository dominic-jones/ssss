package com.dv.ssss.people;

import org.qi4j.api.composite.Composite;
import org.qi4j.api.mixin.Mixins;

@Mixins(PersonnelServiceImpl.class)
public interface PersonnelService extends Composite {

    Iterable<Person> get();
}

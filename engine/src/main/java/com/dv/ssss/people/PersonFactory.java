package com.dv.ssss.people;

import org.qi4j.api.mixin.Mixins;

@Mixins(PersonFactoryMixin.class)
public interface PersonFactory {

    Person create(String name, String rank, String age);
}

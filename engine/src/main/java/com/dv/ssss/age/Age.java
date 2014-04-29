package com.dv.ssss.age;

import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(Age.AgeMixin.class)
public interface Age {

    class AgeMixin implements Age {

    }

    interface AgeState {

        Property<String> age();
    }
}

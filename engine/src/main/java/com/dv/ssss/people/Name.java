package com.dv.ssss.people;

import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(Name.NameMixin.class)
public interface Name {

    class NameMixin implements Name {

    }

    interface NameState {

        Property<String> name();
    }

}

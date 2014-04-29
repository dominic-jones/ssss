package com.dv.ssss.people;

import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(Name.NameMixin.class)
public interface Name {

    String getName();

    class NameMixin implements Name {

        @This
        NameState state;

        @Override
        public String getName() {

            return state.name().get();
        }
    }

    interface NameState {

        Property<String> name();
    }

}

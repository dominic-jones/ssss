package com.dv.ssss.age;

import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(Age.AgeMixin.class)
public interface Age {

    void increaseAge(int increment);

    class AgeMixin implements Age {

        @This
        AgeState state;

        @Override
        public void increaseAge(int increment) {

            state.age().set(
                    state.age().get() + increment
            );
        }
    }

    interface AgeState {

        Property<Integer> age();
    }
}

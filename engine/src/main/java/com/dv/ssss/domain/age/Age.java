package com.dv.ssss.domain.age;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(Age.AgeMixin.class)
public interface Age {

    int age(LocalDate currentDate);

    class AgeMixin implements Age {

        @This
        AgeState state;

        @Override
        public int age(LocalDate currentDate) {

            LocalDate birthDate = state.birthDate().get();

            return Years.yearsBetween(birthDate, currentDate).getYears();
        }

    }

    interface AgeState {

        Property<LocalDate> birthDate();
    }
}

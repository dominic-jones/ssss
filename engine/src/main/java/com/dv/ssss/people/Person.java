package com.dv.ssss.people;

import com.dv.ssss.ui.Column;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(Person.PersonMixin.class)
public interface Person extends EntityComposite {

    @Column(name = "Name", order = 0)
    String getName();

    @Column(name = "Rank", order = 1)
    String getRank();

    @Column(name = "Age", order = 2)
    String getAge();

    abstract class PersonMixin implements Person {

        @This
        PersonState state;

        @Override
        public String getName() {

            return state.name().get();
        }

        @Override
        public String getRank() {

            return state.rank().get();
        }

        @Override
        public String getAge() {

            return state.age().get();
        }
    }

    interface PersonState {

        Property<String> name();

        Property<String> rank();

        Property<String> age();
    }
}

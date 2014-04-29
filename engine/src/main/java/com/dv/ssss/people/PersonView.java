package com.dv.ssss.people;

import com.dv.ssss.age.Age;
import com.dv.ssss.ui.Column;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;

@Mixins(PersonView.PersonViewMixin.class)
public interface PersonView {

    @Column(name = "Name", order = 0)
    String getName();

    @Column(name = "Rank", order = 1)
    String getRank();

    @Column(name = "Age", order = 2)
    String getAge();

    class PersonViewMixin implements PersonView {

        @This
        Name.NameState nameState;

        @This
        Rank.RankState rankState;

        @This
        Age.AgeState ageState;

        @Override
        public String getName() {

            return nameState.name().get();
        }

        @Override
        public String getRank() {

            return rankState.rank().get();
        }

        @Override
        public String getAge() {

            return ageState.age().get();
        }
    }
}

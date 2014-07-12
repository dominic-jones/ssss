package com.dv.ssss.domain.people;

import com.dv.ssss.domain.age.Age;
import com.dv.ssss.domain.faction.FactionFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;

@Mixins(Person.PersonMixin.class)
public interface Person extends Age, Name, Rank {

    void foundFaction(String name);

    abstract class PersonMixin implements Person {

        @Service
        FactionFactory factionFactory;

        @This
        PersonEntity personEntity;

        @Override
        public void foundFaction(String name) {

            factionFactory.create(personEntity, name);
        }
    }

    interface PersonState extends AgeState, NameState, RankState {

    }
}

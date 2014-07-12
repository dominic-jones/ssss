package com.dv.ssss.domain.people;

import com.dv.ssss.domain.age.Age;
import com.dv.ssss.domain.faction.FactionEntity;
import com.dv.ssss.domain.faction.FactionFactory;
import com.dv.ssss.domain.faction.FactionRepository;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;

@Mixins(Person.PersonMixin.class)
public interface Person extends Age, Name, Rank {

    String faction();

    void foundFaction(String name);

    abstract class PersonMixin implements Person {

        @Service
        FactionFactory factionFactory;

        @Service
        FactionRepository factionRepository;

        @This
        PersonEntity personEntity;

        @Override
        public String faction() {

            FactionEntity factionEntity = factionRepository.factionFor(personEntity);

            return factionEntity == null ? "Unaligned" : factionEntity.name().get();
        }

        @Override
        public void foundFaction(String name) {

            factionFactory.create(personEntity, name);
        }
    }

    interface PersonState extends AgeState, NameState, RankState {

    }
}

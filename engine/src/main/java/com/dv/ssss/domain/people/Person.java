package com.dv.ssss.domain.people;

import com.dv.ssss.domain.age.Age;
import com.dv.ssss.domain.faction.FactionEntity;
import com.dv.ssss.domain.faction.FactionFactory;
import com.dv.ssss.domain.faction.FactionRepository;
import com.google.common.base.Optional;

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
        PersonEntity thisEntity;

        @Override
        public String faction() {

            Optional<FactionEntity> factionEntity = factionRepository.factionFor(thisEntity);

            return factionEntity.isPresent() ? factionEntity.get().name().get() : "Unaligned";
        }

        @Override
        public void foundFaction(String name) {

            factionFactory.create(thisEntity, name);
        }
    }

    interface PersonState extends AgeState, NameState, RankState {

    }
}

package com.dv.ssss.domain.people;

import com.dv.ssss.domain.faction.FactionFactory;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;

@Mixins(FactionFounder.PersonMixin.class)
public interface FactionFounder {

    void foundFaction(String name);

    abstract class PersonMixin implements FactionFounder {

        @Service
        FactionFactory factionFactory;

        @This
        PersonEntity thisEntity;

        @Override
        public void foundFaction(String name) {

            factionFactory.create(thisEntity, name);
        }
    }
}

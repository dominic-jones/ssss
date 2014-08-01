package com.dv.ssss.domain.game;

import com.dv.ssss.domain.people.FactionFounder;
import com.dv.ssss.domain.people.PersonFactory;

import org.joda.time.LocalDate;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

@Mixins(NewGame.NewGameMixin.class)
public interface NewGame {

    void progenate(LocalDate startingDate);

    class NewGameMixin implements NewGame {

        @Service
        PersonFactory personFactory;

        @Override
        public void progenate(LocalDate startingDate) {

            personFactory.create("Riesz", "Warrior", startingDate.minusYears(27).plusWeeks(3));

            FactionFounder aegis = personFactory.create("Aegis", "Overlord", startingDate.minusYears(23).plusWeeks(1));
            aegis.foundFaction("Vandals");
        }
    }

}

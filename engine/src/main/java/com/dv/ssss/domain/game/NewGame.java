package com.dv.ssss.domain.game;

import com.dv.ssss.domain.people.Person;
import com.dv.ssss.domain.people.PersonFactory;
import org.joda.time.LocalDate;
import org.qi4j.api.association.ManyAssociation;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;

@Mixins(NewGame.NewGameMixin.class)
public interface NewGame {

    void progenate(LocalDate startingDate);

    abstract class NewGameMixin implements NewGame {

        @This
        Game.GameState state;

        @Service
        PersonFactory personFactory;

        @Override
        public void progenate(LocalDate startingDate) {

            ManyAssociation<Person> people = state.people();
            Person aegis = personFactory.create("Aegis", "Overlord", startingDate.minusYears(23).plusWeeks(1));
            people.add(aegis);
            people.add(personFactory.create("Riesz", "Warrior", startingDate.minusYears(27).plusWeeks(3)));

            aegis.foundFaction("Vandals");
        }
    }

}

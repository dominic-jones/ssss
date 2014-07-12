package com.dv.ssss.domain.game;

import com.dv.ssss.domain.people.Person;
import com.dv.ssss.domain.people.PersonFactory;
import org.qi4j.api.association.ManyAssociation;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;

@Mixins(NewGame.NewGameMixin.class)
public interface NewGame extends EntityComposite {

    void progenate();

    abstract class NewGameMixin implements NewGame {

        @This
        Game.GameState state;

        @Service
        PersonFactory personFactory;

        @Override
        public void progenate() {

            ManyAssociation<Person> people = state.people();
            people.add(personFactory.create("Aegis", "Overlord", 23));
            people.add(personFactory.create("Riesz", "Warrior", 27));
        }
    }

}

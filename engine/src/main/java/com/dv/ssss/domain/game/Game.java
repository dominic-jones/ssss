package com.dv.ssss.domain.game;

import com.dv.ssss.domain.people.Person;
import org.qi4j.api.association.Association;
import org.qi4j.api.common.Optional;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;

@Mixins({Game.GameMixin.class})
public interface Game extends Turn, NewGame {

    void transferPlayerTo(Person person);

    abstract class GameMixin implements Game {

        @This
        GameState state;

        @Override
        public void transferPlayerTo(Person person) {

            state.player().set(person);
        }
    }

    interface GameState {

        @Optional
        Association<Person> player();
    }
}

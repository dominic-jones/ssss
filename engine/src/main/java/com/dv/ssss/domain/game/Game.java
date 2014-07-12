package com.dv.ssss.domain.game;

import com.dv.ssss.domain.people.Person;
import org.qi4j.api.association.ManyAssociation;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;

@Mixins({Game.GameMixin.class})
public interface Game extends Turn, NewGame {

    void endTurn();

    abstract class GameMixin implements Game {

        @This
        TurnState state;

        @Override
        public void endTurn() {

            state.turn().set(
                    state.turn()
                         .get() + 1
            );
        }
    }

    interface GameState {

        ManyAssociation<Person> people();

    }
}

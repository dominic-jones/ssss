package com.dv.ssss.domain.game;

import com.dv.ssss.domain.people.Person;
import org.qi4j.api.association.ManyAssociation;
import org.qi4j.api.mixin.Mixins;

@Mixins({Game.GameMixin.class})
public interface Game extends Turn, NewGame {

    abstract class GameMixin implements Game {

    }

    interface GameState {

        ManyAssociation<Person> people();

    }
}

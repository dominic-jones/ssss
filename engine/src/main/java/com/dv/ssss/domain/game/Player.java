package com.dv.ssss.domain.game;

import com.dv.ssss.domain.people.Person;

import org.qi4j.api.association.Association;
import org.qi4j.api.common.Optional;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.sideeffect.SideEffects;

@Mixins(Player.PlayerMixin.class)
public interface Player {

    void transferPlayerTo(Person person);

    class PlayerMixin implements Player {

        @This
        PlayerState state;

        @Override
        public void transferPlayerTo(Person person) {

            state.player().set(person);
        }
    }

    interface PlayerState {

        @Optional
        Association<Person> player();
    }
}

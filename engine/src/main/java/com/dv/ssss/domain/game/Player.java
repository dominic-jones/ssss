package com.dv.ssss.domain.game;

import com.dv.ssss.domain.people.FactionFounder;

import org.qi4j.api.association.Association;
import org.qi4j.api.common.Optional;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;

@Mixins(Player.PlayerMixin.class)
public interface Player {

    void transferPlayerTo(FactionFounder person);

    class PlayerMixin implements Player {

        @This
        PlayerState state;

        @Override
        public void transferPlayerTo(FactionFounder person) {

            state.player().set(person);
        }
    }

    interface PlayerState {

        @Optional
        Association<FactionFounder> player();
    }
}

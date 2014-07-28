package com.dv.ssss;

import com.dv.ssss.domain.people.PersonnelRepository;
import com.dv.ssss.ui.player.PlayerDto;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

@Mixins(PlayerQuery.PlayerQueryMixin.class)
public interface PlayerQuery {

    PlayerDto execute(String personIdentity);

    class PlayerQueryMixin implements PlayerQuery {

        @Service
        PersonnelRepository personnelRepository;

        @Override
        public PlayerDto execute(String personIdentity) {

            return new PlayerDto(personnelRepository.get(personIdentity));
        }
    }
}

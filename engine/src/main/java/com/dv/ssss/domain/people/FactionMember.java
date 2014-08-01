package com.dv.ssss.domain.people;

import com.dv.ssss.domain.faction.FactionEntity;
import com.dv.ssss.domain.faction.FactionRepository;
import com.google.common.base.Optional;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;

@Mixins(FactionMember.FactionMemberMixin.class)
public interface FactionMember {

    String faction();

    class FactionMemberMixin implements FactionMember {

        @Service
        FactionRepository factionRepository;

        @This
        PersonEntity thisEntity;

        @Override
        public String faction() {

            Optional<FactionEntity> factionEntity = factionRepository.factionFor(thisEntity);

            return factionEntity.isPresent() ? factionEntity.get().name().get() : "Unaligned";
        }
    }
}

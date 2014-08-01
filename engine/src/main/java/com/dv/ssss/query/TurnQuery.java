package com.dv.ssss.query;

import com.dv.ssss.domain.game.GameRepository;
import com.dv.ssss.domain.game.TurnDto;
import com.dv.ssss.inf.uow.UnitOfWorkConcern;

import org.qi4j.api.concern.Concerns;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

@Concerns(UnitOfWorkConcern.class)
@Mixins(TurnQuery.TurnQueryMixin.class)
public interface TurnQuery {

    TurnDto execute(String gameIdentity);

    class TurnQueryMixin implements TurnQuery {

        @Service
        GameRepository gameRepository;

        @Override
        public TurnDto execute(String gameIdentity) {

            return new TurnDto(gameRepository.get(gameIdentity));
        }
    }
}

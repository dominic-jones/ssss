package com.dv.ssss.domain.game;

import com.dv.ssss.inf.event.EventPoster;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.sideeffect.SideEffectOf;

// TODO 2014-07-28 dom: Make more generic
public abstract class TurnEndedSideEffect extends SideEffectOf<GameService> implements GameService {

    @Service
    EventPoster eventPoster;

    @Override
    public void endTurn(String gameIdentity) {

        eventPoster.post(new TurnEndedEvent(gameIdentity));
    }
}

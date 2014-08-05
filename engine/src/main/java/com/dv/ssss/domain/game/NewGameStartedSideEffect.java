package com.dv.ssss.domain.game;

import com.dv.ssss.inf.event.EventPoster;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.sideeffect.SideEffectOf;

// TODO 2014-08-05 dom: Make more generic
public abstract class NewGameStartedSideEffect extends SideEffectOf<GameService> implements GameService  {

    @Service
    EventPoster eventPoster;

    @Override
    public String startNewGame() {

        eventPoster.post(new NewGameStartedEvent(result.startNewGame()));

        return null;
    }
}

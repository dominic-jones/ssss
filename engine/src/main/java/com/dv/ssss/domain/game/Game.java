package com.dv.ssss.domain.game;

import org.qi4j.api.mixin.Mixins;

@Mixins({Game.GameMixin.class})
public interface Game extends Turn, NewGame {

    abstract class GameMixin implements Game {

    }

    interface GameState {

    }
}

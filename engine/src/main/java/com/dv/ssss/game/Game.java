package com.dv.ssss.game;

import com.dv.ssss.turn.Turn;
import org.qi4j.api.association.Association;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.mixin.Mixins;

@Mixins(Game.GameMixin.class)
public interface Game extends EntityComposite {

    Association<Turn> turn();

    abstract class GameMixin implements Game {

    }

}

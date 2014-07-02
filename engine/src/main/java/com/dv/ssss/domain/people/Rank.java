package com.dv.ssss.domain.people;

import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(Rank.RankMixin.class)
public interface Rank {

    class RankMixin implements Rank {

    }

    interface RankState {

        Property<String> rank();
    }
}

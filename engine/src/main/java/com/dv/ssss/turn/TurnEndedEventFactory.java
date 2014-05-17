package com.dv.ssss.turn;

import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.value.ValueBuilder;
import org.qi4j.api.value.ValueBuilderFactory;

@Mixins(TurnEndedEventFactory.TurnEndedEventFactoryMixin.class)
public interface TurnEndedEventFactory {

    TurnEndedEvent create();

    class TurnEndedEventFactoryMixin implements TurnEndedEventFactory {

        @Structure
        ValueBuilderFactory valueBuilderFactory;

        @Override
        public TurnEndedEvent create() {

            ValueBuilder<TurnEndedEvent> builder = valueBuilderFactory.newValueBuilder(TurnEndedEvent.class);
            return builder.newInstance();
        }
    }
}

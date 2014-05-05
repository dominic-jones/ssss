package com.dv.ssss.ui.events;

import com.google.common.eventbus.EventBus;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;

@Mixins(FiresEvents.FiresEventsMixin.class)
public interface FiresEvents {

    void post(Object event);

    class FiresEventsMixin implements FiresEvents {

        @Uses
        EventBus eventBus;

        @Override
        public void post(Object event) {

            eventBus.post(event);
        }
    }
}

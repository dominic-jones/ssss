package com.dv.ssss.inf.event;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

@Mixins(EventRegistry.EventRegistryMixin.class)
public interface EventRegistry {

    void register(Object object);

    class EventRegistryMixin implements EventRegistry {

        @Service
        EventBusService eventBusService;

        @Override
        public void register(Object object) {

            eventBusService.register(object);
        }
    }

}

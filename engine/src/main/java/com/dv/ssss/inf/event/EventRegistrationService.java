package com.dv.ssss.inf.event;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

@Mixins(EventRegistrationService.EventRegistrationServiceMixin.class)
public interface EventRegistrationService {

    void register();

    class EventRegistrationServiceMixin implements EventRegistrationService {

        @Service
        Iterable<EventHandler> eventHandlers;

        @Service
        EventRegistry eventRegistry;

        @Override
        public void register() {

            eventHandlers.forEach(eventRegistry::register);
        }
    }
}

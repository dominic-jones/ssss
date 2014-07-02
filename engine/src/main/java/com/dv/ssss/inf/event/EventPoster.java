package com.dv.ssss.inf.event;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

@Mixins(EventPoster.EventPosterMixin.class)
public interface EventPoster {

    void post(Object object);

    class EventPosterMixin implements EventPoster {

        @Service
        EventBusService eventBusService;

        @Override
        public void post(Object object) {

            eventBusService.post(object);
        }
    }

}



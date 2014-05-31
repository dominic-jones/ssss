package com.dv.ssss.event;

import com.google.common.eventbus.EventBus;
import org.qi4j.api.mixin.Mixins;

@Mixins(EventBusService.EventBusServiceMixin.class)
public interface EventBusService {

    void register(Object object);

    void post(Object object);

    class EventBusServiceMixin implements EventBusService {

        EventBus eventBus = new EventBus();

        @Override
        public void register(Object object) {

            eventBus.register(object);
        }

        @Override
        public void post(Object object) {

            eventBus.post(object);
        }
    }

}

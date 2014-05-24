package com.dv.ssss.event;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.service.ServiceComposite;

@Mixins(EventSubscriber.EventSubscriberMixin.class)
public interface EventSubscriber extends ServiceComposite {

    void register(ServiceComposite serviceComposite);

    abstract class EventSubscriberMixin implements EventSubscriber {

        @Service
        EventRepository eventRepository;

        @Override
        public void register(ServiceComposite serviceComposite) {

            eventRepository.register(serviceComposite);
        }
    }

}

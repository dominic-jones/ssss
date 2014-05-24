package com.dv.ssss.ui.events;

import com.dv.ssss.event.EventRepository;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

@Mixins(FiresEvents.FiresEventsMixin.class)
public interface FiresEvents {

    void post(Object event);

    class FiresEventsMixin implements FiresEvents {

        @Service
        EventRepository eventRepository;

        @Override
        public void post(Object event) {

            eventRepository.post(event);
        }
    }
}

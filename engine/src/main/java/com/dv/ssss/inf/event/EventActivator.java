package com.dv.ssss.inf.event;

import org.qi4j.api.activation.ActivatorAdapter;
import org.qi4j.api.service.ServiceReference;

public class EventActivator extends ActivatorAdapter<ServiceReference<EventSubscriber>> {

    @Override
    public void afterActivation(ServiceReference<EventSubscriber> activated) throws Exception {

        EventSubscriber eventSubscriber = activated.get();

        eventSubscriber.register(eventSubscriber);
    }
}

package com.dv.ssss.ui;

import com.google.common.eventbus.EventBus;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.structure.Module;

@Mixins(MediatorBuilder.MediatorBuilderMixin.class)
public interface MediatorBuilder {

    //TODO Do not leave as object.
    <T> T create(Class<T> mediatorClass, Object view, EventBus eventBus);

    class MediatorBuilderMixin implements MediatorBuilder {

        @Structure
        Module module;

        @Override
        public <T> T create(Class<T> mediatorClass, Object view, EventBus eventBus) {

            T mediator = module.newTransient(mediatorClass, view, eventBus);

            eventBus.register(mediator);

            return mediator;
        }
    }

}

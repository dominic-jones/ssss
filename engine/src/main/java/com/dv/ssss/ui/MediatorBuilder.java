package com.dv.ssss.ui;

import com.dv.ssss.event.EventRegistry;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.structure.Module;

@Mixins(MediatorBuilder.MediatorBuilderMixin.class)
public interface MediatorBuilder {

    //TODO Do not leave as object.
    <T> T create(Class<T> mediatorClass, Object view);

    class MediatorBuilderMixin implements MediatorBuilder {

        @Structure
        Module module;

        @Service
        EventRegistry eventRegistry;

        @Override
        public <T> T create(Class<T> mediatorClass, Object view) {

            T mediator = module.newTransient(mediatorClass, view);

            //TODO SideEffect or PostConstruct this or similar
            eventRegistry.register(mediator);

            return mediator;
        }
    }

}

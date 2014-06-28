package com.dv.ssss.ui;

import com.dv.ssss.event.EventRegistry;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.structure.Module;

@Mixins(PresenterFactory.PresenterFactoryMixin.class)
public interface PresenterFactory {

    //TODO Do not leave as object.
    <T> T create(Class<T> presenterClass, Object view);

    class PresenterFactoryMixin implements PresenterFactory {

        @Structure
        Module module;

        @Service
        EventRegistry eventRegistry;

        @Override
        public <T> T create(Class<T> presenterClass, Object view) {

            T presenter = module.newTransient(presenterClass, view);

            //TODO SideEffect or PostConstruct this or similar
            eventRegistry.register(presenter);

            return presenter;
        }
    }

}

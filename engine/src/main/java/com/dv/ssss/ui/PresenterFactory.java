package com.dv.ssss.ui;

import com.dv.ssss.event.EventRegistry;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.structure.Module;

@Mixins(PresenterFactory.PresenterFactoryMixin.class)
public interface PresenterFactory {

    <T extends Presenter> T create(Class<T> presenterClass, Object... params);

    class PresenterFactoryMixin implements PresenterFactory {

        @Structure
        Module module;

        @Service
        EventRegistry eventRegistry;

        @Override
        public <T extends Presenter> T create(Class<T> presenterClass, Object... params) {

            T presenter = module.newTransient(presenterClass, params);

            presenter.init();

            //TODO SideEffect or PostConstruct this or similar
            eventRegistry.register(presenter);

            return presenter;
        }
    }

}

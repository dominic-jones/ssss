package com.dv.ssss.ui;

import com.dv.ssss.event.EventRegistry;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.structure.Module;

@Mixins(PresenterFactory.PresenterFactoryMixin.class)
public interface PresenterFactory {

    <T extends Presenter<V>, V extends View> T create(Class<T> presenterClass, V view);

    class PresenterFactoryMixin implements PresenterFactory {

        @Structure
        Module module;

        @Service
        EventRegistry eventRegistry;

        @Override
        public <T extends Presenter<V>, V extends View> T create(Class<T> presenterClass, V view) {

            T presenter = module.newTransient(presenterClass, view);

            //TODO SideEffect or PostConstruct this or similar
            presenter.init(view);
            eventRegistry.register(presenter);

            return presenter;
        }
    }

}

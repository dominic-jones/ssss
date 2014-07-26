package com.dv.ssss.ui.player;

import com.dv.ssss.ui.Presenter;
import com.dv.ssss.ui.View;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;

@Mixins(PlayerPresenter.PlayerPresenterMixin.class)
public interface PlayerPresenter extends Presenter {

    class PlayerPresenterMixin implements PlayerPresenter {

        PlayerView view;

        @Structure
        TransientBuilderFactory transientBuilderFactory;

        @Uses
        String gameIdentity;

        @Override
        public void init() {

            view = transientBuilderFactory.newTransient(PlayerView.class, gameIdentity);
        }

        @Override
        public View getView() {

            return view;
        }
    }

}

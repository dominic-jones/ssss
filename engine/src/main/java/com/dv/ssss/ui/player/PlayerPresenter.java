package com.dv.ssss.ui.player;

import com.dv.ssss.PlayerQuery;
import com.dv.ssss.domain.game.PlayerTransferredEvent;
import com.dv.ssss.inf.event.EventHandler;
import com.dv.ssss.ui.Presenter;
import com.dv.ssss.ui.View;
import com.google.common.eventbus.Subscribe;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

@Mixins(PlayerPresenter.PlayerPresenterMixin.class)
public interface PlayerPresenter extends Presenter, EventHandler {

    @Subscribe
    void playerTransferred(PlayerTransferredEvent event);

    class PlayerPresenterMixin implements PlayerPresenter {

        @Service
        PlayerView view;

        @Service
        PlayerQuery playerQuery;

        @Override
        public void init() {

        }

        @Override
        public View getView() {

            return view;
        }

        @Override
        public void playerTransferred(PlayerTransferredEvent event) {

            String personIdentity = event.getPersonIdentity();
            PlayerDto player = playerQuery.execute(personIdentity);
            view.setPlayer(player);
        }
    }

}

package com.dv.ssss.ui.player;

import com.dv.ssss.domain.game.PlayerTransferredEvent;
import com.dv.ssss.personnel.PersonnelService;
import com.dv.ssss.ui.Presenter;
import com.dv.ssss.ui.View;
import com.dv.ssss.ui.personnel.PersonDto;
import com.google.common.eventbus.Subscribe;
import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;

@Mixins(PlayerPresenter.PlayerPresenterMixin.class)
public interface PlayerPresenter extends Presenter {

    @Subscribe
    void playerTransferred(PlayerTransferredEvent event);

    class PlayerPresenterMixin implements PlayerPresenter {

        PlayerView view;

        @Service
        PersonnelService personnelService;

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

        @Override
        public void playerTransferred(PlayerTransferredEvent event) {

            PersonDto person = personnelService.getPerson(gameIdentity, event.getPersonIdentity());
            view.setPlayer(person);
        }
    }

}

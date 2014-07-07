package com.dv.ssss.ui;

import com.dv.ssss.inf.LayerAssembler;
import com.dv.ssss.inf.event.EventAssembler;
import com.dv.ssss.ui.main.MainPresenter;
import com.dv.ssss.ui.main.MainView;
import com.dv.ssss.ui.personnel.PersonnelPresenter;
import com.dv.ssss.ui.personnel.PersonnelView;
import com.dv.ssss.ui.turn.TurnPresenter;
import com.dv.ssss.ui.turn.TurnView;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;

public class UserInterfaceAssembler implements LayerAssembler {

    @Override
    public LayerAssembly assemble(ApplicationAssembly assembly) {

        LayerAssembly userInterface = assembly.layer("user-interface");
        ModuleAssembly userInterfaceModules = userInterface.module("all");

        userInterfaceModules.services(
                PresenterFactory.class
        );

        userInterfaceModules.transients(
                MainPresenter.class,
                MainView.class,
                PersonnelPresenter.class,
                PersonnelView.class,
                TurnPresenter.class,
                TurnView.class
        );
        new EventAssembler().assemble(userInterfaceModules);
        return userInterface;
    }
}
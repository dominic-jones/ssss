package com.dv.ssss.domain;

import static org.qi4j.api.common.Visibility.application;

import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.inf.LayerAssembler;
import com.dv.ssss.inf.event.EventRegistrationService;
import com.dv.ssss.personnel.PersonnelService;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;

public class ApplicationAssembler implements LayerAssembler {

    @Override
    public LayerAssembly assemble(ApplicationAssembly assembly) throws AssemblyException {

        LayerAssembly layerAssembly = assembly.layer("application");

        ModuleAssembly moduleAssembly = layerAssembly.module("module");

        moduleAssembly.services(
                PersonnelService.class,
                GameService.class
        ).visibleIn(application);

        ModuleAssembly event = layerAssembly.module("event");
        event.services(
                EventRegistrationService.class
        );

        return layerAssembly;
    }
}

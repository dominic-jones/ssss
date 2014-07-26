package com.dv.ssss.inf;

import com.dv.ssss.inf.event.EventAssembler;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;

public class InfrastructureAssembler {

    public LayerAssembly assemble(ApplicationAssembly assembly) {

        LayerAssembly infrastructure = assembly.layer("infrastructure");
        ModuleAssembly event = infrastructure.module("event");
        new EventAssembler().assemble(event);
        return infrastructure;
    }
}

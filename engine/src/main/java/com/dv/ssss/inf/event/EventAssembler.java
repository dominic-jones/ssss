package com.dv.ssss.inf.event;

import org.qi4j.bootstrap.Assembler;
import org.qi4j.bootstrap.ModuleAssembly;

public class EventAssembler implements Assembler {

    @Override
    public void assemble(ModuleAssembly assembly) {

        assembly.services(
                EventBusService.class,
                EventPoster.class,
                EventRegistry.class,
                EventSubscriber.class
        );
    }
}

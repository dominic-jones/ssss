package com.dv.ssss.inf;

import static org.qi4j.api.common.Visibility.application;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entitystore.memory.MemoryEntityStoreAssembler;
import org.qi4j.index.rdf.assembly.RdfMemoryStoreAssembler;

public class PersistenceAssembler implements LayerAssembler {

    @Override
    public LayerAssembly assemble(ApplicationAssembly assembly) throws AssemblyException {

        LayerAssembly persistence = assembly.layer("persistence");
        ModuleAssembly infrastructure = persistence.module("infrastructure");

        new MemoryEntityStoreAssembler()
                .visibleIn(application)
                .assemble(infrastructure);
        new RdfMemoryStoreAssembler().assemble(infrastructure);
        return persistence;
    }
}
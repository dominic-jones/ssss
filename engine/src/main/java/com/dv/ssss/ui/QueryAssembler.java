package com.dv.ssss.ui;

import static org.qi4j.api.common.Visibility.application;

import com.dv.ssss.inf.LayerAssembler;
import com.dv.ssss.query.PlayerQuery;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;

public class QueryAssembler implements LayerAssembler {

    @Override
    public LayerAssembly assemble(ApplicationAssembly assembly) throws AssemblyException {

        LayerAssembly layerAssembly = assembly.layer("query");
        ModuleAssembly moduleAssembly = layerAssembly.module("query");

        moduleAssembly.services(
                PlayerQuery.class
        ).visibleIn(application);

        return layerAssembly;
    }
}

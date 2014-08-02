package com.dv.ssss.ui;

import com.dv.ssss.inf.LayerAssembler;
import com.dv.ssss.query.PlayerQuery;
import com.dv.ssss.query.TurnQuery;
import com.dv.ssss.ui.personnel.AllPersonnelQuery;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;

import static org.qi4j.api.common.Visibility.application;

public class QueryAssembler implements LayerAssembler {

    @Override
    public LayerAssembly assemble(ApplicationAssembly assembly) throws AssemblyException {

        LayerAssembly layerAssembly = assembly.layer("query");
        ModuleAssembly moduleAssembly = layerAssembly.module("query");

        moduleAssembly.services(
                AllPersonnelQuery.class,
                PlayerQuery.class,
                TurnQuery.class
        ).visibleIn(application);

        return layerAssembly;
    }
}

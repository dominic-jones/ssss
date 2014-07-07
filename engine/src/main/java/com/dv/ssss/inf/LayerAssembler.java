package com.dv.ssss.inf;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;

public interface LayerAssembler {

    LayerAssembly assemble(ApplicationAssembly assembly) throws AssemblyException;
}

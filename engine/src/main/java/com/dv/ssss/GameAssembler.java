package com.dv.ssss;

import com.dv.ssss.domain.DomainAssembler;
import com.dv.ssss.inf.PersistenceAssembler;
import com.dv.ssss.ui.UserInterfaceAssembler;

import org.qi4j.bootstrap.ApplicationAssembler;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.ApplicationAssemblyFactory;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;

public class GameAssembler implements ApplicationAssembler {

    @Override
    public ApplicationAssembly assemble(ApplicationAssemblyFactory factory) throws AssemblyException {

        ApplicationAssembly assembly = factory.newApplicationAssembly();

        LayerAssembly userInterface = new UserInterfaceAssembler().assemble(assembly);
        LayerAssembly domain = new DomainAssembler().assemble(assembly);
        LayerAssembly persistence = new PersistenceAssembler().assemble(assembly);

        userInterface.uses(domain);
        domain.uses(persistence);

        return assembly;
    }
}
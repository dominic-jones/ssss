package com.dv.ssss;

import com.dv.ssss.domain.DomainAssembler;
import com.dv.ssss.inf.InfrastructureAssembler;
import com.dv.ssss.inf.PersistenceAssembler;
import com.dv.ssss.ui.QueryAssembler;
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
        LayerAssembly application = new com.dv.ssss.domain.ApplicationAssembler().assemble(assembly);
        LayerAssembly query = new QueryAssembler().assemble(assembly);
        LayerAssembly domain = new DomainAssembler().assemble(assembly);
        LayerAssembly persistence = new PersistenceAssembler().assemble(assembly);
        LayerAssembly infrastructure = new InfrastructureAssembler().assemble(assembly);

        userInterface.uses(application, query, infrastructure);
        query.uses(domain);
        application.uses(domain, infrastructure, userInterface);
        // TODO 2014-07-30 dom: Uncertain if domain should depend on ui directory or on a presenter layer...?
        domain.uses(persistence);

        return assembly;
    }
}
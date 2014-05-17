package com.dv.ssss.turn

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.qi4j.api.injection.scope.Service
import org.qi4j.api.injection.scope.Structure
import org.qi4j.api.unitofwork.UnitOfWorkFactory
import org.qi4j.bootstrap.AssemblyException
import org.qi4j.bootstrap.ModuleAssembly
import org.qi4j.test.AbstractQi4jTest
import org.qi4j.test.EntityTestAssembler

class TurnFactoryTest extends AbstractQi4jTest {

    @Service
    TurnFactory factory

    @Structure
    UnitOfWorkFactory unitOfWorkFactory

    @Before
    void setUp() {
        super.setUp()

        unitOfWorkFactory.newUnitOfWork()
    }

    @After
    void tearDown() {
        unitOfWorkFactory.currentUnitOfWork().discard()
    }

    @Test
    void 'Should initialize turns at one'() {
        def result = factory.create()

        assert 1 == result.turn()
    }

    @Override
    void assemble(ModuleAssembly module) throws AssemblyException {
        module.entities(Turn)
        module.services(TurnFactory)

        new EntityTestAssembler().assemble(module)
    }
}

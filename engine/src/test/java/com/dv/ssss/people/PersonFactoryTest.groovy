package com.dv.ssss.people

import com.dv.ssss.domain.people.PersonEntity
import com.dv.ssss.domain.people.PersonFactory
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

class PersonFactoryTest extends AbstractQi4jTest {

    @Service
    PersonFactory factory

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
        def age = '23'

        def result = factory.create('Aegis', 'Overlord', age)

        assert age == result.age
    }

    @Override
    void assemble(ModuleAssembly module) throws AssemblyException {
        module.entities(PersonEntity)
        module.services(PersonFactory)

        new EntityTestAssembler().assemble(module)
    }
}

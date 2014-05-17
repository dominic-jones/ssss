package com.dv.ssss.turn

import org.junit.Test
import org.qi4j.api.injection.scope.Service
import org.qi4j.bootstrap.AssemblyException
import org.qi4j.bootstrap.ModuleAssembly
import org.qi4j.test.AbstractQi4jTest

class TurnEndedEventFactoryTest extends AbstractQi4jTest {

    @Service
    TurnEndedEventFactory turnEndedEventBuilder

    @Test
    void 'Test'() {
        def result = turnEndedEventBuilder.create()

        assert null != result
    }

    @Override
    void assemble(ModuleAssembly module) throws AssemblyException {
        module.addServices(TurnEndedEventFactory)
        module.values(TurnEndedEvent)
    }
}

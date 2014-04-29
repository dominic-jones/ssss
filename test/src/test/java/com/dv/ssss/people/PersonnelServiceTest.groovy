package com.dv.ssss.people

import org.junit.Test

class PersonnelServiceTest {

    @Test
    void 'Should retrieve personnel'() {
        def result = new PersonnelRepository.PersonnelRepositoryMixin().getByName("Aegis")

        assert null != result.first()
    }
}
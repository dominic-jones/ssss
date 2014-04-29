package ssss

import com.dv.ssss.people.PersonnelRepository

import org.junit.Test

class PersonnelServiceTest {

    @Test
    void 'Should retrieve personnel'() {
        def result = new PersonnelRepository.PersonnelRepositoryMixin().getByName("Aegis")

        assert null != result.first()
    }
}
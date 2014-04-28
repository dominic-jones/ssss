package ssss

import com.dv.ssss.people.PersonnelRepositoryMixin
import org.junit.Test

class PersonnelServiceTest {

    @Test
    void 'Should retrieve personnel'() {
        def result = new PersonnelRepositoryMixin().getByName("Aegis")

        assert null != result.first()
    }
}
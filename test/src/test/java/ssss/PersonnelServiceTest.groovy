package ssss

import com.dv.ssss.people.PersonnelRepositoryMixin
import org.testng.annotations.Test

class PersonnelServiceTest {

    @Test
    void 'Should retrieve personnel'() {
        def result = new PersonnelRepositoryMixin().getByName("Aegis")

        assert null != result.first()
    }
}
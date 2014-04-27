package ssss

import com.dv.ssss.people.PersonnelServiceImpl
import org.testng.annotations.Test

class PersonnelServiceTest {

    @Test
    void 'Should retrieve personnel'() {
        def result = new PersonnelServiceImpl().get()

        assert null != result.first()
    }
}
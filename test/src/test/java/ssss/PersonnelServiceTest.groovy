package ssss

import com.dv.ssss.PersonnelService
import org.testng.annotations.Test

class PersonnelServiceTest {

    @Test
    void 'Should retrieve personnel'() {
        def result = new PersonnelService().get()

        assert null != result.first()
    }
}
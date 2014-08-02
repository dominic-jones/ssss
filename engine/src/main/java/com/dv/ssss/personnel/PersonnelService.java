package com.dv.ssss.personnel;

import com.dv.ssss.inf.uow.UnitOfWorkConcern;
import org.qi4j.api.concern.Concerns;
import org.qi4j.api.mixin.Mixins;

@Concerns(UnitOfWorkConcern.class)
@Mixins(PersonnelService.PersonnelServiceMixin.class)
public interface PersonnelService {

    class PersonnelServiceMixin implements PersonnelService {

    }
}

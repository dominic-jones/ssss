package com.dv.ssss.domain;

import com.dv.ssss.domain.people.PersonFactory;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(DataBootstrapService.DataBootstrapServiceMixin.class)
public interface DataBootstrapService {

    void bootstrap();

    class DataBootstrapServiceMixin implements DataBootstrapService {

        @Service
        PersonFactory personFactory;

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public void bootstrap() {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();

            personFactory.create("Aegis", "Overlord", 23);

            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }
    }
}

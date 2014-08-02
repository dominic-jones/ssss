package com.dv.ssss.domain.people;

import org.qi4j.api.composite.Composite;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.structure.Module;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

import static com.google.common.collect.Lists.newArrayList;

@Mixins(PersonnelRepository.PersonnelRepositoryMixin.class)
public interface PersonnelRepository extends Composite {

    Iterable<PersonEntity> all();

    PersonEntity get(String personIdentity);

    class PersonnelRepositoryMixin implements PersonnelRepository {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Structure
        Module module;

        @Override
        public Iterable<PersonEntity> all() {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            QueryBuilder<PersonEntity> builder = module
                    .newQueryBuilder(PersonEntity.class);
            Query<PersonEntity> query = unitOfWork.newQuery(builder);

            return newArrayList(query);
        }

        @Override
        public PersonEntity get(String personIdentity) {

            // TODO 2014-07-28 dom: is this the correct approach?
            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();
            PersonEntity personEntity = unitOfWork.get(PersonEntity.class, personIdentity);
            unitOfWork.pause();

            return personEntity;
        }
    }
}

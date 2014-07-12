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

    Iterable<PersonEntity> all(String name);

    class PersonnelRepositoryMixin implements PersonnelRepository {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Structure
        Module module;

        @Override
        public Iterable<PersonEntity> all(String name) {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            QueryBuilder<PersonEntity> builder = module
                    .newQueryBuilder(PersonEntity.class);
            Query<PersonEntity> query = unitOfWork.newQuery(builder);

            return newArrayList(query);
        }
    }
}

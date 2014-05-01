package com.dv.ssss.age;

import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.structure.Module;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

import static com.google.common.collect.Lists.newArrayList;

@Mixins(AgeRepository.AgeRepositoryMixin.class)
public interface AgeRepository {

    Iterable<Age> findAgeables();

    class AgeRepositoryMixin implements AgeRepository {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Structure
        Module module;

        @Override
        public Iterable<Age> findAgeables() {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            QueryBuilder<Age> builder = module
                    .newQueryBuilder(Age.class);
            Query<Age> query = unitOfWork.newQuery(builder);

            return newArrayList(query);
        }
    }

}

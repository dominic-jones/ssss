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
import static org.qi4j.api.query.QueryExpressions.templateFor;

@Mixins(PersonnelRepository.PersonnelRepositoryMixin.class)
public interface PersonnelRepository extends Composite {

    Iterable<PersonEntity> getByName(String name);

    class PersonnelRepositoryMixin implements PersonnelRepository {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Structure
        Module module;

        @Override
        public Iterable<PersonEntity> getByName(String name) {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            Name.NameState template = templateFor(Name.NameState.class);

            QueryBuilder<PersonEntity> builder = module
                    .newQueryBuilder(PersonEntity.class)
//                    .where(eq(template.name(), name))
                    ;
            Query<PersonEntity> query = unitOfWork.newQuery(builder);

            return newArrayList(query);
        }
    }
}

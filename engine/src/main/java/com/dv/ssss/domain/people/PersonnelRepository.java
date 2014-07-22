package com.dv.ssss.domain.people;

import static com.google.common.collect.Lists.newArrayList;
import static org.qi4j.api.query.QueryExpressions.eq;
import static org.qi4j.api.query.QueryExpressions.templateFor;

import org.qi4j.api.composite.Composite;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.structure.Module;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(PersonnelRepository.PersonnelRepositoryMixin.class)
public interface PersonnelRepository extends Composite {

    Iterable<PersonEntity> all(String name);

    Person getByName(String name);

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

        @Override
        public Person getByName(String name) {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            Name.NameState nameTemplate = templateFor(Name.NameState.class);
            QueryBuilder<Person> builder = module.newQueryBuilder(Person.class)
                                                 .where(eq(nameTemplate.name(), name));
            Query<Person> query = unitOfWork.newQuery(builder);

            return query.iterator().next();
        }
    }
}

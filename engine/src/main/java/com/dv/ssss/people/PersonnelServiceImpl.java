package com.dv.ssss.people;

import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.structure.Module;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

import static com.google.common.collect.Lists.newArrayList;
import static org.qi4j.api.query.QueryExpressions.eq;
import static org.qi4j.api.query.QueryExpressions.templateFor;

public class PersonnelServiceImpl implements PersonnelService {

    @Structure
    UnitOfWorkFactory unitOfWorkFactory;

    @Structure
    Module module;

    @Override
    public Iterable<Person> get() {

        UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

        PersonState template = templateFor(PersonState.class);

        //TODO Pass in query params
        QueryBuilder<Person> builder = module
                .newQueryBuilder(Person.class)
                .where(eq(template.name(), "Aegis"));
        Query<Person> query = unitOfWork.newQuery(builder);

        return newArrayList(query);
    }
}

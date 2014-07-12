package com.dv.ssss.domain.faction;

import com.dv.ssss.domain.people.PersonEntity;
import com.google.common.base.Optional;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

import static com.google.common.base.Optional.fromNullable;
import static org.qi4j.api.query.QueryExpressions.contains;
import static org.qi4j.api.query.QueryExpressions.templateFor;

@Mixins(FactionRepository.FactionRepositoryMixin.class)
public interface FactionRepository {

    Optional<FactionEntity> factionFor(PersonEntity person);

    class FactionRepositoryMixin implements FactionRepository {

        @Structure
        QueryBuilderFactory queryBuilderFactory;

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Optional<FactionEntity> factionFor(PersonEntity person) {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            Faction.FactionState template = templateFor(Faction.FactionState.class);
            QueryBuilder<FactionEntity> queryBuilder = queryBuilderFactory.newQueryBuilder(FactionEntity.class)
                                                                          .where(contains(template.members(), person));

            return fromNullable(unitOfWork.newQuery(queryBuilder).find());
        }
    }

}


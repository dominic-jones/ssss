package com.dv.ssss.domain.turn;

import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.structure.Module;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(TurnRepository.TurnRepositoryMixin.class)
public interface TurnRepository {

    Turn get();

    class TurnRepositoryMixin implements TurnRepository {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Structure
        Module module;

        @Override
        public Turn get() {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();

            QueryBuilder<Turn> builder = module
                    .newQueryBuilder(Turn.class);
            Query<Turn> query = unitOfWork.newQuery(builder);

            return query.iterator().next();
        }
    }
}

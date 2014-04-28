package com.dv.ssss.turn;

import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(TurnFactory.TurnFactoryMixin.class)
public interface TurnFactory {

    Turn create();

    class TurnFactoryMixin implements TurnFactory {

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Turn create() {

            UnitOfWork unitOfWork = unitOfWorkFactory.currentUnitOfWork();
            EntityBuilder<Turn> builder = unitOfWork.newEntityBuilder(Turn.class);

            TurnMixin.TurnState prototype = builder.instanceFor(TurnMixin.TurnState.class);
            prototype.turn().set(1);

            return builder.newInstance();
        }
    }
}

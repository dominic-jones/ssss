package com.dv.ssss.query;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

import com.dv.ssss.domain.game.GameEntity;
import com.dv.ssss.domain.game.GameRepository;
import com.dv.ssss.domain.people.PersonEntity;
import com.dv.ssss.domain.people.PersonnelRepository;
import com.dv.ssss.inf.DataException;
import com.dv.ssss.inf.uow.UnitOfWorkConcern;
import com.dv.ssss.ui.personnel.PersonDto;

import org.qi4j.api.concern.Concerns;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Concerns(UnitOfWorkConcern.class)
@Mixins(AllPersonnelQuery.AllPersonnelQueryMixin.class)
public interface AllPersonnelQuery {

    Iterable<PersonDto> execute(String gameIdentity);

    class AllPersonnelQueryMixin implements AllPersonnelQuery {

        @Service
        GameRepository gameRepository;

        @Service
        PersonnelRepository personnelRepository;

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Iterable<PersonDto> execute(String gameIdentity) {

            GameEntity currentTurn = gameRepository.get(gameIdentity);

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();
            Iterable<PersonEntity> people = personnelRepository.all();
            Iterable<PersonDto> personDtos = newArrayList(transform(people, p -> new PersonDto(p, currentTurn.currentDate())));

            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new DataException(e);
            }

            return personDtos;
        }
    }

}


package com.dv.ssss.personnel;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.game.TurnDto;
import com.dv.ssss.domain.people.PersonEntity;
import com.dv.ssss.domain.people.PersonnelRepository;
import com.dv.ssss.inf.DataException;
import com.dv.ssss.ui.personnel.PersonDto;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins(PersonnelService.PersonnelServiceMixin.class)
public interface PersonnelService {

    Iterable<PersonDto> all(String gameIdentity);

    class PersonnelServiceMixin implements PersonnelService {

        @Service
        GameService gameService;

        @Service
        PersonnelRepository personnelRepository;

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Iterable<PersonDto> all(String gameIdentity) {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();

            TurnDto currentTurn = gameService.currentTurn(gameIdentity);

            Iterable<PersonEntity> people = personnelRepository.all("Aegis");
            Iterable<PersonDto> personDtos = newArrayList(transform(people, p-> new PersonDto(p, currentTurn.getDate())));
            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new DataException(e);
            }
            return personDtos;
        }
    }
}

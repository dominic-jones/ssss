package com.dv.ssss.personnel;

import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.game.TurnDto;
import com.dv.ssss.domain.people.Person;
import com.dv.ssss.domain.people.PersonEntity;
import com.dv.ssss.domain.people.PersonnelRepository;
import com.dv.ssss.inf.uow.UnitOfWorkConcern;
import com.dv.ssss.ui.personnel.PersonDto;
import org.qi4j.api.concern.Concerns;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.mixin.Mixins;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

@Concerns(UnitOfWorkConcern.class)
@Mixins(PersonnelService.PersonnelServiceMixin.class)
public interface PersonnelService {

    Iterable<PersonDto> all(String gameIdentity);

    Person get(String personIdentity);

    // TODO 2014-07-21 dom: This is temporary.
    Person getByName(String name);

    class PersonnelServiceMixin implements PersonnelService {

        @Service
        GameService gameService;

        @Service
        PersonnelRepository personnelRepository;

        @Override
        public Iterable<PersonDto> all(String gameIdentity) {

            TurnDto currentTurn = gameService.currentTurn(gameIdentity);

            Iterable<PersonEntity> people = personnelRepository.all("Aegis");
            return newArrayList(transform(people, p -> new PersonDto(p, currentTurn.getDate())));
        }

        @Override
        public Person get(String personIdentity) {

            return personnelRepository.get(personIdentity);
        }

        @Override
        public Person getByName(String name) {

            return personnelRepository.getByName(name);
        }
    }
}

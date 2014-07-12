package com.dv.ssss.personnel;

import com.dv.ssss.domain.faction.FactionRepository;
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

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

@Mixins(PersonnelService.PersonnelServiceMixin.class)
public interface PersonnelService {

    Iterable<PersonDto> all();

    class PersonnelServiceMixin implements PersonnelService {

        @Service
        PersonnelRepository personnelRepository;

        @Service
        FactionRepository factionRepository;

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Iterable<PersonDto> all() {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();
            //TODO Get all
            //TODO Give better name, not just people
            Iterable<PersonEntity> people = personnelRepository.getByName("Aegis");
            Iterable<PersonDto> personDtos = newArrayList(transform(people, p -> new PersonDto(p, factionRepository.factionFor(p))));
            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new DataException(e);
            }
            return personDtos;
        }
    }

}

package com.dv.ssss.personnel;

import com.dv.ssss.people.PersonEntity;
import com.dv.ssss.people.PersonnelRepository;
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

        @Structure
        UnitOfWorkFactory unitOfWorkFactory;

        @Override
        public Iterable<PersonDto> all() {

            UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();
            //TODO Get all
            //TODO Give better name, not just people
            Iterable<PersonEntity> people = personnelRepository.getByName("Aegis");
            Iterable<PersonDto> personDtos = newArrayList(transform(people, PersonDto::new));
            try {
                unitOfWork.complete();
            } catch (UnitOfWorkCompletionException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
            return personDtos;
        }
    }

}

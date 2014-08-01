package com.dv.ssss.domain.people;

import com.dv.ssss.domain.age.Age;

import org.qi4j.api.entity.EntityComposite;

public interface PersonEntity extends EntityComposite, Age, Name, Rank, FactionMember, FactionFounder, PersonState {

}

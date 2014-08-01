package com.dv.ssss.domain.faction;

import com.dv.ssss.domain.people.FactionFounder;

import org.qi4j.api.association.Association;
import org.qi4j.api.association.ManyAssociation;
import org.qi4j.api.property.Property;

public interface FactionState {

    Association<FactionFounder> founder();

    ManyAssociation<FactionFounder> members();

    Property<String> name();
}

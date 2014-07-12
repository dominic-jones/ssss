package com.dv.ssss.domain.faction;

import com.dv.ssss.domain.people.Person;
import org.qi4j.api.association.Association;
import org.qi4j.api.association.ManyAssociation;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(Faction.FactionMixin.class)
public interface Faction {

    class FactionMixin implements Faction {

    }

    interface FactionState {

        Association<Person> founder();

        ManyAssociation<Person> members();

        Property<String> name();
    }
}

package com.dv.ssss.people;

import org.qi4j.api.property.Property;

public interface PersonState {

    Property<String> name();

    Property<String> rank();

    Property<String> age();
}

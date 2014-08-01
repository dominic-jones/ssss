package com.dv.sample;

import org.qi4j.api.property.Property;

public interface OrganizationalUnit {

    void describe(String name);

    public interface OrganizationalUnitState {

        Property<OrganizationalUnit> organization();
    }
}

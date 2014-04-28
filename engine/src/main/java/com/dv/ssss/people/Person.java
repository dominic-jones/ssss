package com.dv.ssss.people;

import com.dv.ssss.ui.Column;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.mixin.Mixins;

@Mixins(PersonImpl.class)
public interface Person extends EntityComposite {

    @Column(name = "name", displayName = "Name", order = 0)
    String getName();

    @Column(name = "rank", displayName = "Rank", order = 1)
    String getRank();

    @Column(name = "age", displayName = "Age", order = 2)
    String getAge();
}

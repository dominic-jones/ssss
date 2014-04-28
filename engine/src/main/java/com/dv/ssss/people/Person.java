package com.dv.ssss.people;

import com.dv.ssss.ui.Column;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.mixin.Mixins;

@Mixins(PersonMixin.class)
public interface Person extends EntityComposite {

    @Column(name = "Name", order = 0)
    String getName();

    @Column(name = "Rank", order = 1)
    String getRank();

    @Column(name = "Age", order = 2)
    String getAge();
}

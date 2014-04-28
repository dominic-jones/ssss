package com.dv.ssss;

import org.qi4j.api.mixin.Mixins;

@Mixins(DataBootstrapImpl.class)
public interface DataBootstrap {

    void bootstrap();
}

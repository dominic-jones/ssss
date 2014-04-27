package com.dv.ssss;

import com.dv.ssss.people.PersonnelService;
import org.qi4j.api.injection.scope.Service;

public class EngineImpl implements Engine {

    @Service
    PersonnelService personnelService;

    @Override
    public void endTurn() {

        System.out.println("Ending turn");
    }
}

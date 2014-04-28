package com.dv.ssss.people;

import org.qi4j.api.injection.scope.This;

public abstract class PersonMixin implements Person {

    @This
    PersonState state;

    @Override
    public String getName() {

        return state.name().get();
    }

    @Override
    public String getRank() {

        return state.rank().get();
    }

    @Override
    public String getAge() {

        return state.age().get();
    }
}

package com.dv.ssss.inf.uow;

import org.qi4j.api.common.AppliesToFilter;

import java.lang.reflect.Method;

public class TransactedFilter implements AppliesToFilter {

    @Override
    public boolean appliesTo(Method method, Class<?> mixin, Class<?> compositeType, Class<?> fragmentClass) {

        return true;
    }
}

package com.dv.ssss.inf.uow;

import com.dv.ssss.inf.DataException;
import org.qi4j.api.common.AppliesTo;
import org.qi4j.api.concern.GenericConcern;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

import java.lang.reflect.Method;

@AppliesTo(Transacted.class)
public class UnitOfWorkConcern extends GenericConcern {

    @Structure
    UnitOfWorkFactory unitOfWorkFactory;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();

        Object result = next.invoke(proxy, method, args);

        try {
            unitOfWork.complete();
        } catch (UnitOfWorkCompletionException e) {
            unitOfWork.discard();
            throw new DataException(e);
        }
        return result;
    }
}

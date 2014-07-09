package com.dv.ssss.inf;

import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;

public class DataException extends RuntimeException {

    public DataException(UnitOfWorkCompletionException e) {

        super(e);
    }
}

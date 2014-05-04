package com.ams.sharedkernel.domain.model.measuresandunits.exception;

import com.ams.sharedkernel.domain.DomainException;

public class InvalidCurrencyException extends DomainException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidCurrencyException() {

    }

    public InvalidCurrencyException(String msg) {
        super(msg);
    }

}

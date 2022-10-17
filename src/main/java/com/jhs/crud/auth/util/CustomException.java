package com.jhs.crud.auth.util;

import com.jhs.crud.enm.ErrorCode;

public class CustomException extends RuntimeException{

    public ErrorCode getCode() {
        return code;
    }

    private final ErrorCode code;

    public CustomException(ErrorCode notFoundEvent) {
        super(notFoundEvent.getErrorMessage());
        this.code = notFoundEvent;
    }
}

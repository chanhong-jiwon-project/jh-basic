package com.jhs.crud.auth.util;

import com.jhs.crud.enm.ErrorCode;

public class EmptyTokenException extends CustomException {
    public EmptyTokenException(ErrorCode notFoundEvent) {
        super(notFoundEvent);
    }
}

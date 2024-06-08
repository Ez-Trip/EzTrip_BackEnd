package com.eztrip.global.error.exception;

import com.eztrip.global.error.ErrorCode;

public class TokenValidationException extends BusinessException{

    public TokenValidationException(ErrorCode errorCode) {
        super(errorCode);
    }
}

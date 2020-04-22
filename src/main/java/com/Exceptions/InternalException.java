package com.Exceptions;

public class InternalException extends Exception {
    private InternalErrorCodes code;

    public InternalException(InternalErrorCodes code){
        this.code = code;
    }

    @Override
    public String getMessage() {
        return "ERROR CODE: " + code.getCode() + "\n" +
                "DESCRIPTION: " + code.getDescription();
    }

    public InternalErrorCodes getCode() {
        return code;
    }
}

package com.Exceptions;

public class ExternalException extends Exception {
    ExternalErrorCodes code;
    int lineNumber = -1;
    String infringingExpression;

    public ExternalException(ExternalErrorCodes code, String infringingExpression) {
        this.code = code;
        this.infringingExpression = infringingExpression;
    }

    public ExternalException(ExternalErrorCodes code) {
        this.code = code;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public ExternalErrorCodes getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        String line = lineNumber == -1 ? "" : "LINE NUMBER: " + lineNumber + "\n";
        String details = infringingExpression.equals("") ? "" : "DETAILS: " + code.getErrorDetails(infringingExpression);
        StringBuilder s = new StringBuilder();
        s.append("\nERROR: ").append(code.getDescription()).append("\n")
                .append(line).append(details).append("\n\n");
        return s.toString();
    }
}

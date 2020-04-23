package com.Exceptions;

public enum ExternalErrorCodes {
    UNKNOWN_PRIMITIVE_DATA(0, "Unknown type of primitive data", "Unknown Data: "),
    UNDEFINED_VARIABLE(1, "Undefined Variable", "Variable Name: "),
    VARIABLE_REDECLARATION(2, "Cannot redeclare variable", "Variable Name: "),
    UNSUPPORTED_OPERAND(3, "Operand cannot be used with data type", "Expression: "),
    TYPE_MISALIGNMENT(4, "Cannot operate on different types", "Expression outline: "),
    TYPE_ERROR(5, "Incorrect data type supplied", "incorrect type, "),
    LOOP_ERROR(6, "Improper loop syntax", "syntax provided: "),
    ILLEGAL_EXPRESSION(7, "Expression does not meet syntax requirements", "specifically, "),
    INTERNAL_LANGUAGE_FAILURE(8, "Something went wrong, please open a issue with your code snippet"),
    CURLY_BRACKET_MISALIGNMENT(9, "Brackets are poorly formatted in code"),
    QUOTES_MISALIGNMENT(10, "quotes do not close"),
    FUNCTION_CALL_ON_NON_FUNCTION(11, "call name: "),
    ILLEGAL_ARGUMENT(12, "function name: ");

    private final int code;
    private final String description;
    private final String infringingExpressionWrapper;

    ExternalErrorCodes(int code, String description) {
        this(code,description, "");
    }

    ExternalErrorCodes(int code, String description, String infringingExpressionWrapper) {
        this.code = code;
        this.description = description;
        this.infringingExpressionWrapper = infringingExpressionWrapper;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    public String getErrorDetails(String expression){
        return infringingExpressionWrapper + expression;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}

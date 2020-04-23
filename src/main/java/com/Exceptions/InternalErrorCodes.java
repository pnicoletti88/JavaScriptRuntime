package com.Exceptions;

public enum InternalErrorCodes {
    ILLEGAL_EXPRESSION_QUEUE_ADD(0, "Illegal insert into expression queue"),
    ILLEGAL_EXPRESSION_QUEUE_POLL(1, "Illegal removal from expression queue"),
    UNRECOGNIZED_OPERAND(2, "Attempt to create operand with unknown string"),
    UNREACHABLE_CODE(3, "Code which should be unreachable has been hit"),
    TYPE_MISMATCH(4, "Attempt to use operand on two different types"),
    INVALID_LOOP_CALL(5, "Loop called with non-loop code"),
    UNKNOWN_BLOCK_TYPE(6, "Unknown code block type"),
    UNKNOWN_VARIABLE(7, "Cannot find variable in any scope"),
    STRING_PARSING_FAILURE(8, "String parser could not perform as designed"),
    INVALID_BLOCK_CALL(9, "Cannot create code block from invalid block"),
    POORLY_FORMATTED_CONDITIONAL(10, "Invalid conditional block passed"),
    IMPROPER_RUN_FUNCTION_CALL(11, "Cannot run invalid function"),
    FAILED_TO_LOAD_BUILTIN_FUNCS(12, "Error loading built in functions"),
    NEGATIVE_TASK_COUNT(13, "Error in the current task count");

    private final int code;
    private final String description;

    InternalErrorCodes(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}

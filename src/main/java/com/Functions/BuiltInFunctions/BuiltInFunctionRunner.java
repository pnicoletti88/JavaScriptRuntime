package com.Functions.BuiltInFunctions;

import com.Data.Data;
import com.Scopes.Scope;
import com.Process;

import java.util.List;

public class BuiltInFunctionRunner {
    public static Data runFunction(BuiltInFunction func, List<Data> params, Scope parentScope, Process process) throws Exception{
        return func.run(params, parentScope, process);
    }
}

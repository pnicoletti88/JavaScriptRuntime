package com.Functions.BuiltInFunctions;

import com.Data.Data;
import com.Scopes.Scope;
import com.Process;

import java.util.List;

public interface BuiltInFunction {
    Data run(List<Data> params, Scope callBackParentScope, Process process) throws Exception;
}

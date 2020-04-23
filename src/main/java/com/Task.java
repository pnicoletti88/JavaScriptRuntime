package com;

import com.Data.Data;
import com.Scopes.Scope;

import java.util.List;

public class Task {
    private final List<Data> callBackParams;
    private final Scope scope;
    private final Data callBack;

    public Task(Scope scope, Data callBack, List<Data> callBackParams) {
        this.scope = scope;
        this.callBack = callBack;
        this.callBackParams = callBackParams;
    }

    public Scope getScope() {
        return scope;
    }

    public Data getCallBack() {
        return callBack;
    }

    public List<Data> getCallBackParams() {
        return callBackParams;
    }
}

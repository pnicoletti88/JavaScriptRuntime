package com;

import com.Scopes.StandardScope;

public class Process {
    private String code;
    private StandardScope topLevelScope;

    public Process(String code) {
        this.code = code;
        this.topLevelScope = new StandardScope();
    }

    public void start() throws Exception {
        topLevelScope = new StandardScope();
        (new Executor(code, topLevelScope)).run();
    }


    public String serializeState(){
        return topLevelScope.serialize();
    }
}

package com.Scopes;

import com.Data.Data;

public interface Scope {
    public Data getVariable(String name);
    public void createVariable(String name)  throws Exception;
    public void createVariable(String name, Data data)  throws Exception;
    public void updateVariable(String name, Data data)  throws Exception;
    public Scope findScope(String name) throws Exception;
}

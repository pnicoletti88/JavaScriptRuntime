package com.Scopes;

import com.Data.Data;

public interface Scope {
    Data getVariable(String name) throws Exception;
    void createVariable(String name)  throws Exception;
    void createVariable(String name, Data data)  throws Exception;
    void updateVariable(String name, Data data)  throws Exception;
    Scope findScope(String name) throws Exception;
    String serialize();
}

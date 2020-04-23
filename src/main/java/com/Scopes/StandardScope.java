package com.Scopes;

import com.Data.Data;
import com.DataStructures.MemoryMap;
import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Exceptions.InternalErrorCodes;
import com.Exceptions.InternalException;

public class StandardScope implements Scope {
    private Scope parent;
    private MemoryMap memory = new MemoryMap();

    public StandardScope() {
    }

    public StandardScope(Scope parent) {
        this.parent = parent;
    }

    public Data getVariable(String name) throws Exception{
        Data value = memory.getVariable(name);
        if (value == null) {
            if(parent != null){
                return parent.getVariable(name);
            }
            throw new ExternalException(ExternalErrorCodes.UNDEFINED_VARIABLE);
        }
        return value;
    }

    public void createVariable(String name) throws Exception {
        memory.createVariable(name, new Data());
    }

    public void createVariable(String name, Data data) throws Exception {
        memory.createVariable(name, data);
    }

    public void updateVariable(String name, Data data) throws Exception {
        if(memory.getVariable(name) != null){
            memory.updateVariable(name, data);
        } else if(parent != null){
            parent.updateVariable(name, data);
        } else {
            throw new ExternalException(ExternalErrorCodes.UNDEFINED_VARIABLE, name);
        }
    }

    public void setParent(StandardScope parent) {
        this.parent = parent;
    }

    public String serialize() {
        return memory.serialize();
    }

    public Scope findScope(String name) throws Exception{
        if(memory.getVariable(name) != null){
            return this;
        } else if(parent != null){
            return parent.findScope(name);
        } else {
            throw new InternalException(InternalErrorCodes.UNKNOWN_VARIABLE);
        }
    }
}

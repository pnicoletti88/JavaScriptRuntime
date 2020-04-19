package com.Scopes;

import com.Data.Data;
import com.DataStructures.MemoryMap;

public class LoopScope implements Scope{
    private MemoryMap loopConfigData = new MemoryMap();
    private Scope loopBody;
    private Scope parent;
    private boolean isLoopInfoInsert = true;

    public LoopScope(Scope parent){
        this.parent = parent;
        loopBody = new StandardScope(parent);
    }

    public void createVariable(String name) throws Exception {
        if(isLoopInfoInsert){
            loopConfigData.createVariable(name, new Data());
        } else {
            loopBody.createVariable(name);
        }
    }

    public void createVariable(String name, Data data) throws Exception {
        if(isLoopInfoInsert){
            loopConfigData.createVariable(name, data);
        } else{
            loopBody.createVariable(name, data);
        }
    }

    public void updateVariable(String name, Data data) throws Exception {
        if(loopConfigData.getVariable(name) != null){
            loopConfigData.updateVariable(name, data);
        } else {
            loopBody.updateVariable(name, data);
        }
    }

    public Data getVariable(String name){
        Data loopConfig = loopConfigData.getVariable(name);
        if(loopConfig != null){
            return loopConfig;
        }
        return loopBody.getVariable(name);
    }

    public Scope findScope(String name) throws Exception{
        Data loopConfig = loopConfigData.getVariable(name);
        if(loopConfig != null){
            return this;
        }
        return loopBody.findScope(name);
    }

    public void loopDataInsertComplete(){
        isLoopInfoInsert = false;
    }

    public void resetLoopBody(){
        loopBody = new StandardScope(parent);
    }

    public String serialize(){
        return loopConfigData.serialize() + loopBody.serialize();
    }
}

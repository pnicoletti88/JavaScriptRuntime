package com.DataStructures;

import com.Data.Data;
import com.Data.DataTypes;
import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Util.StringHelpers;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryMap {
    private HashMap<String, Data> memory = new HashMap<>();

    public Data getVariable(String name){
        return memory.get(name);
    }

    public void createVariable(String name, Data data) throws Exception{
        if(memory.containsKey(name)){
            throw new ExternalException(ExternalErrorCodes.VARIABLE_REDECLARATION, name);
        }
        memory.put(name, data);
    }

    public void updateVariable(String name, Data data) throws Exception{
        if(!memory.containsKey(name)){
            throw new ExternalException(ExternalErrorCodes.UNDEFINED_VARIABLE, name);
        }
        memory.put(name, data);
    }

    public String serialize(){
        ArrayList<String> data = new ArrayList<>();
        for(String key:memory.keySet()){
            if(memory.get(key).getType() != DataTypes.BuiltInFunction){
                String item = key + "-" + memory.get(key).getData().toString() + "-" + memory.get(key).getType();
                data.add(item);
            }
        }
        data.sort(String::compareTo);
        return StringHelpers.joinString(data);
    }
}

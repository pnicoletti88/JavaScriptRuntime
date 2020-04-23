package com.Functions.BuiltInFunctions;

import com.Data.Data;
import com.Data.DataTypes;
import com.Scopes.Scope;
import com.Process;

import java.util.List;

public class ToString implements BuiltInFunction {
    public Data run(List<Data> params, Scope unusedS, Process unusedP) throws Exception {
        Data param;
        if(params.size() > 0){
            param = params.get(0);
        } else {
            param = new Data();
        }
        return functionBody(param);
    }

    private Data functionBody(Data d) throws Exception {
        return new Data(d.getData().toString(), DataTypes.String);
    }
}

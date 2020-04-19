package com.Functions;

import com.SingleLineHandlers.CodeLine;
import com.Data.Data;
import com.Data.DataTypes;
import com.Executor;
import com.Scopes.Scope;
import com.Scopes.StandardScope;
import com.Util.StringHelpers;

import java.util.ArrayList;
import java.util.List;

public class Function {
    private String functionCall;
    private Scope callScope;
    private Scope functionScope;
    private String functionCode;

    public Function(String functionCall, Scope callScope) throws Exception{
        String functionName = functionCall.substring(0, functionCall.indexOf('(')).trim();
        Scope functionDeclarationScope = callScope.findScope(functionName);

        this.functionCall = functionCall;
        this.callScope = callScope;
        this.functionScope = new StandardScope(functionDeclarationScope);
        this.functionCode = (String) functionDeclarationScope.getVariable(functionName).getData();
    }

    public static void declareFunction(String declaration, Scope scope) throws Exception{
        int firstSpace = declaration.indexOf(' ');
        int paramsStart = declaration.indexOf('(');
        String functionName = declaration.substring(firstSpace+1,paramsStart).trim();
        scope.createVariable(functionName, new Data(declaration, DataTypes.Function));
    }

    public Data executeFunction() throws Exception{
        insertParamsIntoScope();
        String functionBody = StringHelpers.parseBlockBody(functionCode);
        return (new Executor(functionBody, functionScope)).run().getData();
    }

    private void insertParamsIntoScope() throws Exception{
        List<String> paramNames = parseParams(functionCode);
        List<String> paramValues = parseParams(functionCall);

        for(int i=0;i<paramNames.size(); i++){
            Data value;
            if(i<paramValues.size()){
                value = (new CodeLine(paramValues.get(i), callScope)).runAndReturnResult();
            } else {
                value = new Data();
            }
            functionScope.createVariable(paramNames.get(i), value);
        }
    }

    private List<String> parseParams(String str) throws Exception{
        int[] bracketIndex = StringHelpers.findFirstAndLastBracketIndex(str,'(',')');
        String params = str.substring(bracketIndex[0]+1, bracketIndex[1]);
        if(params.trim().equals("")){
            return new ArrayList<>();
        }
        return StringHelpers.quoteRespectingSplit(params, ',');
    }
}

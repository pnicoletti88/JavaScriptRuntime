package com.Functions;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Exceptions.InternalErrorCodes;
import com.Exceptions.InternalException;
import com.Functions.BuiltInFunctions.BuiltInFunction;
import com.Functions.BuiltInFunctions.BuiltInFunctionRunner;
import com.SingleLineHandlers.CodeLine;
import com.Data.Data;
import com.Data.DataTypes;
import com.Executor;
import com.Scopes.Scope;
import com.Scopes.StandardScope;
import com.Util.StringHelpers;
import com.Process;

import java.util.ArrayList;
import java.util.List;

public class Function {
    private Scope functionDeclarationScope;
    private Scope functionScope;
    private Data function;
    private Process process;
    private List<Data> paramValues;

    public static void declareFunction(String declaration, Scope scope) throws Exception{
        int firstSpace = declaration.indexOf(' ');
        int paramsStart = declaration.indexOf('(');
        String functionName = declaration.substring(firstSpace+1,paramsStart).trim();
        scope.createVariable(functionName, new Data(declaration, DataTypes.Function));
    }

    public Function(Data function, List<Data> paramValues, Scope declarationScope, Process process) throws Exception{
        this.function = function;
        this.process = process;
        this.functionDeclarationScope = declarationScope;
        this.functionScope = new StandardScope(declarationScope);
        this.paramValues = paramValues;
        validateFunction();
    }

    public Function(String functionCall, Scope callScope, Process process) throws Exception{
        String functionName = functionCall.substring(0, functionCall.indexOf('(')).trim();
        Scope functionDeclarationScope = callScope.findScope(functionName);

        this.functionDeclarationScope = functionDeclarationScope;
        this.function = functionDeclarationScope.getVariable(functionName);
        this.paramValues = convertStrParamsToData(parseParams(functionCall), callScope);
        this.process = process;
        this.functionScope = new StandardScope(functionDeclarationScope);
        validateFunction();
    }

    private void validateFunction() throws Exception{
        if(!isFunction()){
            throw new ExternalException(ExternalErrorCodes.FUNCTION_CALL_ON_NON_FUNCTION);
        }
    }

    private boolean isFunction(){
        return function.getType() == DataTypes.BuiltInFunction || function.getType() == DataTypes.Function;
    }

    public Data executeFunction() throws Exception{
        if(function.getType() == DataTypes.BuiltInFunction){
            return BuiltInFunctionRunner.runFunction((BuiltInFunction)function.getData(), paramValues, functionDeclarationScope, process);
        } else if(function.getType() == DataTypes.Function){
            String functionCode = (String) function.getData();
            insertParamsIntoScope(functionCode);
            String functionBody = StringHelpers.parseBlockBody(functionCode);
            return (new Executor(functionBody, functionScope, process)).run().getData();
        }
        throw new InternalException(InternalErrorCodes.UNREACHABLE_CODE);
    }

    private List<Data> convertStrParamsToData(List<String> strs, Scope scope) throws Exception{
        List<Data> out = new ArrayList<>(strs.size());
        for(String str:strs){
            out.add((new CodeLine(str, scope, process)).runAndReturnResult());
        }
        return out;
    }

    private void insertParamsIntoScope(String functionCode) throws Exception{
        List<String> paramNames = parseParams(functionCode);
        for(int i=0;i<paramNames.size(); i++){
            Data value;
            if(i<paramValues.size()){
                value = paramValues.get(i);
            } else {
                value = new Data();
            }
            functionScope.createVariable(paramNames.get(i), value);
        }
    }

    private List<String> parseParams(String str) throws Exception{
        int[] bracketIndex;
        try {
            bracketIndex = StringHelpers.findFirstAndLastBracketIndex(str, '(', ')');
        } catch(InternalException e){
            throw new InternalException(InternalErrorCodes.IMPROPER_RUN_FUNCTION_CALL);
        }
        String params = str.substring(bracketIndex[0]+1, bracketIndex[1]);
        if(params.trim().equals("")){
            return new ArrayList<>();
        }
        return StringHelpers.quoteRespectingSplit(params, ',');
    }
}

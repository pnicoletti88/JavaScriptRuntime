package com.Data;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Util.StringHelpers;

public class Data {
    private DataTypes type;
    private Object data;

    public Data(){
        type = DataTypes.Undefined;
    }

    public Data(Double num){
        this.type = DataTypes.Number;
        data = num;
    }

    public Data(Boolean data){
        this.type = DataTypes.Boolean;
        this.data = data;
    }

    public Data(String str, DataTypes type) throws Exception{
        this.type = type;
        castAndAssignData(str);
    }

    public Data(String data) throws Exception{
        String trimmedData = data.trim();
        determineAndValidateType(trimmedData);
        castAndAssignData(trimmedData);
    }

    public Object getData(){
        return data;
    }

    public DataTypes getType(){
        return type;
    }

    private void determineAndValidateType(String data) throws Exception{
        if (data.length() == 0){
            throw new Exception("Illegal empty data");
        }
        if(data.equals("True") || data.equals("False")){
            this.type = DataTypes.Boolean;
        } else if (data.charAt(0) == '\"'){
            validateString(data);
            this.type = DataTypes.String;
        } else if (Character.isDigit(data.charAt(0))){
            validateNumber(data);
            this.type = DataTypes.Number;
        } else {
            throw new ExternalException(ExternalErrorCodes.UNKNOWN_PRIMITIVE_DATA, data);
        }
    }

    private void validateString(String pontentialStr) throws Exception{
        boolean isValid =  pontentialStr.charAt(0) == '\"';
        isValid = isValid && pontentialStr.charAt(pontentialStr.length() -1 ) == '\"';
        isValid = isValid && StringHelpers.characterCount(pontentialStr, '\"') == 2;

        if(!isValid){
            throw new ExternalException(ExternalErrorCodes.UNKNOWN_PRIMITIVE_DATA, pontentialStr);
        }
    }

    private void validateNumber(String potentialNumber) throws Exception{
        try {
            Double.parseDouble(potentialNumber);
        } catch(Exception e) {
            throw new ExternalException(ExternalErrorCodes.UNKNOWN_PRIMITIVE_DATA, potentialNumber);
        }

    }

    private void castAndAssignData(String input){
        if(type == DataTypes.String){
            this.data = input.replaceAll("\"", "");
        } else if(type == DataTypes.Number) {
            this.data = Double.parseDouble(input);
        } else if(type == DataTypes.Boolean){
            this.data = input.equals("True");
        } else if(type == DataTypes.Function){
            this.data = input;
        }
    }
}

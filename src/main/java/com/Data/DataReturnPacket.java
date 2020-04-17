package com.Data;

public class DataReturnPacket {
    private boolean returnWasCalled = false;
    private Data data = null;

    public DataReturnPacket(){}

    public DataReturnPacket(Data data, boolean returnWasCalled){
        this.data = data;
        this.returnWasCalled = returnWasCalled;
    }

    public Data getData() {
        return data;
    }

    public boolean wasReturnCalled() {
        return returnWasCalled;
    }
}

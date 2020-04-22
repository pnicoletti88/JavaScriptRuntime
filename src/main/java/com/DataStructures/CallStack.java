package com.DataStructures;

import java.util.Iterator;
import java.util.Stack;

public class CallStack extends Stack<String> {
    @Override
    public String toString(){
        Iterator<String> iter = this.listIterator();
        StringBuilder stackTrace = new StringBuilder();
        while(iter.hasNext()){
            stackTrace.append(iter.next()).append("\n");
        }
        return stackTrace.toString();
    }

}

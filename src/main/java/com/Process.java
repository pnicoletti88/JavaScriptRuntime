package com;

import com.Data.Data;
import com.Exceptions.ExternalException;
import com.Exceptions.InternalErrorCodes;
import com.Exceptions.InternalException;
import com.Functions.BuiltInFunctions.SetTimeOut;
import com.Functions.BuiltInFunctions.ToString;
import com.Functions.Function;
import com.Scopes.Scope;
import com.Scopes.StandardScope;
import com.sun.corba.se.spi.monitoring.MonitoredObject;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Process {
    private String code;
    private StandardScope topLevelScope;
    private AtomicInteger pendingTaskCount = new AtomicInteger(0);
    private Queue<Task> taskQ = new LinkedList<>();
    private final Object taskQMutex = new Object();

    public Process(String code) {
        this.code = code;
        this.topLevelScope = new StandardScope();
    }

    public void start() throws Exception {
        topLevelScope = new StandardScope();
        loadBuiltInFunctionsToScope(topLevelScope);
        (new Executor(code, topLevelScope, this)).run();

        while (pendingTaskCount.get() > 0 || !isTaskQEmptySync()) {
            synchronized (taskQMutex) {
                while (taskQ.isEmpty()) {
                    taskQMutex.wait();
                }
            }
            runNextTask();
        }

    }

    private void loadBuiltInFunctionsToScope(Scope scp) throws Exception {
        try {
            scp.createVariable("toString", new Data(new ToString()));
            scp.createVariable("setTimeOut", new Data(new SetTimeOut()));
        } catch (Exception e) {
            throw new InternalException(InternalErrorCodes.FAILED_TO_LOAD_BUILTIN_FUNCS);
        }
    }

    public boolean isTaskQEmptySync() {
        synchronized (taskQMutex) {
            return taskQ.isEmpty();
        }
    }

    public void addPendingTask() {
        pendingTaskCount.getAndIncrement();
    }

    public void addToEventQueue(Task t) throws Exception {
        if (pendingTaskCount.get() <= 0) {
            throw new InternalException(InternalErrorCodes.NEGATIVE_TASK_COUNT);
        }
        synchronized (taskQMutex) {
            taskQ.add(t);
            pendingTaskCount.getAndDecrement();
            taskQMutex.notify();
        }

    }

    private void runNextTask() throws Exception {
        Task next;
        synchronized (taskQMutex) {
            next = taskQ.poll();
        }
        Function f = new Function(next.getCallBack(), next.getCallBackParams(), next.getScope(), this);
        f.executeFunction();
    }

    public String serializeState() {
        return topLevelScope.serialize();
    }
}

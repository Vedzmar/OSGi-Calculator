package com.epam.training.helper;


import com.epam.training.helper.operations.Operation;

import java.util.Iterator;
import java.util.Set;

public class OperationHolder implements Iterable  {
    private final Set<Operation> operationSet;

    public OperationHolder(Set<Operation> operationSet) {
        this.operationSet = operationSet;
    }

    public void add(Operation operation) {
        operationSet.add(operation);
    }

    public void remove(Operation operation) {
        operationSet.remove(operation);
    }

    public Iterator<Operation> iterator() {
        return operationSet.iterator();
    }
}

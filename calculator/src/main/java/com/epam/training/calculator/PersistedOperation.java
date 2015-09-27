package com.epam.training.calculator;

import com.epam.training.helper.operations.Operation;

public class PersistedOperation implements Operation {
    private final Operation operation;

    public PersistedOperation(Operation operation) {
        this.operation = operation;
    }

    public long operate(long left, long right) {
        return operation.operate(left, right);
    }

    public long getRanking() {
        return operation.getRanking();
    }

    public String getName() {
        return operation.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return ((Operation)o).getName().equals( this.getName() );
    }

    @Override
    public int hashCode() {
        return operation != null ? operation.getName().hashCode() : 0;
    }
}

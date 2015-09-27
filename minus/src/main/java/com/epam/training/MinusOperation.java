package com.epam.training;

import com.epam.training.helper.operations.Operation;

public class MinusOperation implements Operation {
    public long operate(long left, long right) {
        return left - right;
    }

    public long getRanking() {
        return 0;
    }

    public String getName() {
        return "-";
    }

    @Override
    public String toString() {
        return " " + getName() + " ";
    }
}

package com.epam.training.operations.impl;

import com.epam.training.operations.Operation;

/**
 * Created by Dzianis on 13.09.2015.
 */
public class MultiplicationOperation implements Operation {
    public long operate(long left, long right) {
        return left * right;
    }

    public long getRanking() {
        return 1;
    }

    public String getName() {
        return "*";
    }

    @Override
    public String toString() {
        return " " + getName() + " ";
    }
}

package com.epam.training.operations.impl;

import com.epam.training.operations.Operation;

/**
 * Created by Dzianis on 09.09.2015.
 */
public class PlusOperation implements Operation {
    public long operate(long left, long right) {
        return left + right;
    }

    public long getRanking() {
        return 0;
    }

    public String getName() {
        return "+";
    }

    @Override
    public String toString() {
        return " " + getName() + " ";
    }
}

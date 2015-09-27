package com.epam.training.calculator;

import com.epam.training.helper.OperationHolder;
import com.epam.training.helper.operations.impl.*;

public class Main {
    public static void main(String[] args) {
        CalculatorWindow calculator = new CalculatorWindow();

        OperationHolder holder = calculator.getOperationHolder();
        holder.add(new MinusOperation());
        holder.add(new PlusOperation());

    }
}

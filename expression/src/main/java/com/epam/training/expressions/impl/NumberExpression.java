package com.epam.training.expressions.impl;

import com.epam.training.expressions.Expression;
import com.epam.training.expressions.ExpressionException;

import static java.lang.String.format;

public class NumberExpression implements Expression {
    private long number;

    public NumberExpression(String number) throws ExpressionException {
        if (number.trim().isEmpty()) {
            this.number = 0;
            return;
        }
        try {
            this.number = Long.parseLong(number);
        } catch (NumberFormatException e){
            throw new ExpressionException(format("Given string '%s' is not a valid number", number), e);
        }
    }

    public long calculate() {
        return number;
    }
}

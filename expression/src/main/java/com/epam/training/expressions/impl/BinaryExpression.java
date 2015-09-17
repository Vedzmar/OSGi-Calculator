package com.epam.training.expressions.impl;

import com.epam.training.operations.Operation;
import com.epam.training.expressions.Expression;
import com.epam.training.expressions.ExpressionException;

import static com.epam.training.ExpressionHelper.createExpression;

public class BinaryExpression implements Expression {

    private Expression leftExpression;
    private Expression rightExpression;
    private Operation operation;

    public BinaryExpression(String leftExpressionString, String rightExpressionString, Operation operation) throws ExpressionException {
        this.leftExpression = createExpression(leftExpressionString);
        this.rightExpression = createExpression(rightExpressionString);
        this.operation = operation;
    }


    public long calculate() {
        long leftResult = leftExpression.calculate();
        long rightResult = rightExpression.calculate();
        long result = operation.operate(
                leftResult,
                rightResult
        );

        System.out.println("" + leftResult + operation + rightResult + " = " + result);

        return result;
    }
}

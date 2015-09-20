package com.epam.training.helper.expressions.impl;

import com.epam.training.helper.ExpressionHelper;
import com.epam.training.helper.expressions.Expression;
import com.epam.training.helper.expressions.ExpressionException;
import com.epam.training.helper.operations.Operation;

public class BinaryExpression implements Expression {

    private Expression leftExpression;
    private Expression rightExpression;
    private Operation operation;

    public BinaryExpression(String leftExpressionString, String rightExpressionString, Operation operation) throws ExpressionException {
        this.leftExpression = ExpressionHelper.createExpression(leftExpressionString);
        this.rightExpression = ExpressionHelper.createExpression(rightExpressionString);
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

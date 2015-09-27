package com.epam.training.helper;

import com.epam.training.helper.expressions.ExpressionException;
import com.epam.training.helper.operations.impl.*;

import static com.epam.training.helper.ExpressionHelper.createExpression;

public class Main {
    public static void main( String[] args ) throws ExpressionException {
        String expressionString = "((1+1)+(3+1-(8-12)))";

        OperationHolder holder = ExpressionHelper.operationHolder;
        holder.add(new MinusOperation());
        holder.add(new PlusOperation());

        System.out.println(
                expressionString + "=" + createExpression(expressionString).calculate()
        );
    }
}

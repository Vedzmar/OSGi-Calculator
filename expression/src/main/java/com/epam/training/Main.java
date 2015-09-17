package com.epam.training;

import com.epam.training.expressions.ExpressionException;

public class Main {
    public static void main( String[] args ) throws ExpressionException {
        String expressionString = "((1+1)+(3+1-(8-12)))*5";
        System.out.println(
                expressionString + "=" + ExpressionHelper.createExpression(expressionString).calculate()
        );
    }
}

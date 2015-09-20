package com.epam.training.helper.expressions;

/**
 * Created by Dzianis on 09.09.2015.
 */
public class ExpressionException extends Exception {
    public ExpressionException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ExpressionException(String message) {
        super(message);
    }
}

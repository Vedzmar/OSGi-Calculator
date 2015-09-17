package com.epam.training.operations;

/**
 * Created by Dzianis on 09.09.2015.
 */
public interface Operation {
    long operate(long left, long right);
    long getRanking();
    String getName();
}

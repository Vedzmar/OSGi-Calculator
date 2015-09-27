package com.epam.training.helper;


import com.epam.training.helper.expressions.Expression;
import com.epam.training.helper.expressions.ExpressionException;
import com.epam.training.helper.expressions.impl.BinaryExpression;
import com.epam.training.helper.expressions.impl.NumberExpression;
import com.epam.training.helper.operations.Operation;

import java.util.*;


public class ExpressionHelper{
    private static Set<Operation> operationSet;
    public static OperationHolder operationHolder;

    static {
        init();
    }
    private static void init(){
        operationSet = new TreeSet<Operation>(new Comparator<Operation>() {
            public int compare(Operation o1, Operation o2) {
                if ( o1.getRanking() != o2.getRanking() )
                    return (int) (o1.getRanking() - o2.getRanking());

                return o1.getName().compareTo( o2.getName() );
            }
        });

        ExpressionHelper.operationHolder = new OperationHolder( ExpressionHelper.operationSet );
    }

    public static Expression createExpression(String str) throws ExpressionException {
        str = normaliseString(str);
        int pos = getLatestOperationPosition(str);
        if (pos == -1) return new NumberExpression(str);

        Operation operation         = getOperationByPosition(str, pos);
        String[] expressionSides    = splitStringByOperation(str, pos, operation);

        return new BinaryExpression(
            expressionSides[0],
            expressionSides[1],
            operation
        );
    }

    private static String normaliseString(String str) {
        str = str.trim();
        while (str.startsWith("(") && getBracketLessPositionsSet(str).size() == 0) {
            str = str.substring(1, str.length() - 1).trim();
        }
        return str;
    }

    private static int getLatestOperationPosition(String str) {
        Set<Integer> bracketsLessPositions = getBracketLessPositionsSet(str);

        int position = -1;
        Operation operation = null;
        for (Operation op : getAllOperations()){
            if ( operation != null && op.getRanking() < operation.getRanking() ) break;

            int lastIndexOfOperation = getLastIndexOfOperationInBracketLessPosition(str, op, bracketsLessPositions);
            if (lastIndexOfOperation > position) {
                operation = op;
                position = lastIndexOfOperation;
            }
        }

        return position;
    }

    private static Set<Integer> getBracketLessPositionsSet(String str) {
        Set<Integer> bracketsLessPositions = new HashSet<Integer>( str.length() );
        int counter = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') counter++;
            else if (str.charAt(i) == ')') counter--;
            else if (counter == 0) bracketsLessPositions.add(i);
        }
        return bracketsLessPositions;
    }

    private static int getLastIndexOfOperationInBracketLessPosition(String str,
                                                                    Operation op,
                                                                    Set<Integer> bracketsLessPositions) {
        int lastIndexOfOperation = str.length() + 1;
        while ( ( lastIndexOfOperation = str.lastIndexOf(op.getName(),lastIndexOfOperation - 1) ) != -1){
            if (bracketsLessPositions.contains(lastIndexOfOperation))
                return lastIndexOfOperation;
        }

        return -1;
    }

    private static Operation getOperationByPosition(String str, int pos) {
        for (Operation op : getAllOperations()){
            if (str.startsWith(op.getName(), pos))
                return op;
        }

        return null;
    }

    private static Iterable<Operation> getAllOperations() {
        return operationHolder;
    }

    private static String[] splitStringByOperation(String str, int pos, Operation operation) {
        return new String[]{
            str.substring(0, pos),
            str.substring(pos + operation.getName().length())
        };
    }
}

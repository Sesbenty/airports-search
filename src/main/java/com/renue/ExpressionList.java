package com.renue;

import java.util.ArrayList;
import java.util.List;

public class ExpressionList implements Expressionable{
    private Query.TypeOperator Operator;
    private List<Expressionable> e;

    public ExpressionList(Query.TypeOperator operator){
        Operator = operator;
        e = new ArrayList<>();
    }

    public boolean Execute(String[] str){
        if (Operator == Query.TypeOperator.Or)
            return ExecuteOr(str);
        else
            return ExecuteAnd(str);
    }

    public void Add(Expressionable exp){
        e.add(exp);
    }

    private boolean ExecuteAnd(String[] str) {
        boolean result = e.get(0).Execute(str);
        for (int i = 1; i < e.size(); i++)
            result = result && e.get(i).Execute(str);

        return result;
    }

    private boolean ExecuteOr(String[] str) {
        boolean result = e.get(0).Execute(str);
        for (int i = 1; i < e.size(); i++)
            result = result || e.get(i).Execute(str);

        return result;
    }
}

package com.renue;

public class ExpressionInt implements Expressionable {
    private int Column;
    private Query.TypeMark Mark;
    private int Value;

    public ExpressionInt(int Column, Query.TypeMark Mark, int Value){
        this.Column = Column;
        this.Mark = Mark;
        this.Value = Value;
    }

    public boolean Execute(String[] str){
        boolean result = false;
        int i = Integer.parseInt(str[Column - 1]);
        switch(Mark){
            case Equals:
                result = i == Value;
                break;
            case Greater:
                result = i > Value;
                break;
            case Less:
                result = i < Value;
                break;
            case Unequals:
                result = i != Value;
                break;
        }
        return result;
    }
}

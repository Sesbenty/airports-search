package com.renue;

public class ExpressionString implements Expressionable{
    private int Column;
    private Query.TypeMark Mark;
    private String Value;

    public ExpressionString(int Column, Query.TypeMark Mark, String Value){
        this.Column = Column;
        this.Mark = Mark;
        this.Value = Value;
    }
    public boolean Execute(String[] str){
        boolean result = false;
        switch(Mark){
            case Equals:
                result = str[Column - 1].equals(Value);
                break;
            //case Greater: b = str[Column] > Value;
            //case Less: b = str[Column] < Value;
            case Unequals:
                result = !str[Column - 1].equals(Value);
                break;
            default:
                break;
        }
        return result;
    }
}

package com.renue;

public class ExpressionDouble implements Expressionable{
    private int Column;
    private Query.TypeMark Mark;
    private double Value;

    public ExpressionDouble(int Column, Query.TypeMark Mark, double Value){
        this.Column = Column;
        this.Mark = Mark;
        this.Value = Value;
    }

    public boolean Execute(String[] str){
        boolean result;
        double i = Double.parseDouble(str[Column - 1]);
        switch(Mark){
            case Greater:
                result = i > Value;
                break;
            case Less:
                result = i < Value;
                break;
            default:
                try {
                    throw new Exception("Не возможно сравнить тип double");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
        return result;
    }
}

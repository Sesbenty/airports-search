package com.renue;

import static com.renue.Query.TypeMark.Equals;

public class Query {
    private int currentChar;
    private final String column = "column[";

    private int Number(String query) {
        int end = currentChar;
        if (query.charAt(currentChar) == '-')
            end++;
        while (end < query.length() && (Character.isDigit(query.charAt(end)))) {
            end++;
        }
        int numb = Integer.parseInt(query.substring(currentChar, end));
        currentChar = end;
        return numb;
    }

    private TypeMark Mark(String query) {
        switch (query.charAt(currentChar++)) {
            case '>':
                return TypeMark.Greater;
            case '<':
                if (query.charAt(currentChar) == '>') {
                    currentChar++;
                    return TypeMark.Unequals;
                } else
                    return TypeMark.Less;
            case '=':
                return Equals;
        }
        return null;
    }

    private TypeOperator Operator(String query) {
        switch (query.charAt(currentChar)) {
            case '&':
                return TypeOperator.And;
            case '|':
                return TypeOperator.Or;
        }
        return null;
    }

    private String Text(String query) {
        int end = ++currentChar;
        while (query.charAt(end) != '\"') {
            end++;
        }
        String value = query.substring(currentChar, end);
        currentChar = end + 1;
        return value;
    }

    public enum TypeMark {
        Greater,
        Less,
        Equals,
        Unequals
    }

    public enum TypeOperator {
        And,
        Or
    }


    private String query;
    private Expressionable BuildQuery;

    public boolean Execute(String[] str) {
        return BuildQuery.Execute(str);
    }

    public void BuildQuery(String query) {
        currentChar = 0;
        this.query = query;
        BuildQuery = Parse();
    }

    private Expressionable Parse() {
        Expressionable result = Expression();
        if (currentChar != query.length())
            System.out.println("jsdfkl");
        return result;
    }

    private Expressionable Expression() {
        Expressionable first = Term();
        if (currentChar < query.length()) {
            TypeOperator o = Operator(query);
            if (o == TypeOperator.And) {
                currentChar++;
                ExpressionList list = new ExpressionList(o);
                Expressionable second = Factor();
                list.Add(first);
                list.Add(second);
                first = list;
                while (currentChar < query.length()) {
                    o = Operator(query);
                    if (o != TypeOperator.Or)
                        break;
                    else
                        currentChar++;

                    second = Term();
                    list.Add(second);
                }
            }
        }

        return first;
    }

    private Expressionable Term() {
        Expressionable first = Factor();
        if (currentChar < query.length()) {
            TypeOperator o = Operator(query);
            if (o == TypeOperator.Or) {
                currentChar++;
                ExpressionList list = new ExpressionList(o);
                Expressionable second = Factor();
                list.Add(first);
                list.Add(second);
                first = list;
                while (currentChar < query.length()) {
                    o = Operator(query);
                    if (o != TypeOperator.And)
                        break;
                    else
                        currentChar++;

                    second = Factor();
                    list.Add(second);
                }
            }
        }

        return first;
    }

    private Expressionable Factor() {
        if (query.charAt(currentChar) == '(') {
            currentChar++;
            Expressionable result = Expression();
            if (currentChar < query.length() && query.charAt(currentChar) == ')') {
                currentChar++;
                return result;
            }

            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return GetExpression();
    }

    private Expressionable GetExpression() {
        if (query.startsWith(column, currentChar)) {
            currentChar += column.length();
            return CreateExpression();
        } else
            try {
                throw new Exception();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        return null;
    }

    private Expressionable CreateExpression() {

        int column = Number(query);
        currentChar++;
        TypeMark mark = Mark(query);
        if (query.charAt(currentChar) == '\"') {
            ExpressionString e = new ExpressionString(column, mark, Text(query));
            return e;
        } else if (Character.isDigit(query.charAt(currentChar)) || query.charAt(currentChar) == '-') {
            ExpressionInt e = new ExpressionInt(column, mark, Number(query));
            return e;
        }

        try {
            throw new Exception("Ошибка определение типа переменной");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

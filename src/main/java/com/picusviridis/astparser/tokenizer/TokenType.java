package com.picusviridis.astparser.tokenizer;

public enum TokenType implements IType
{
    NONE,
    PLUS(FunctionType.PLUS, "+"),
    MINUS(FunctionType.MINUS, "-"),
    MULT(FunctionType.MULT, "*"),
    DIV(FunctionType.DIV, "/"),
    POW(FunctionType.POW, "^"),
    MOD(FunctionType.MOD, "%"),
    OR(FunctionType.OR, "||"),
    AND(FunctionType.AND, "&&"),
    SUP(FunctionType.SUP, ">"),
    SUP_EQ(FunctionType.SUP_EQ, ">="),
    INF(FunctionType.INF, "<"),
    INF_EQ(FunctionType.INF_EQ, "<="),
    EQ(FunctionType.EQ, "=="),
    NOT(FunctionType.NOT, "!"),
    NOT_EQ(FunctionType.NOT_EQ, "!="),
    NUMBER,
    STRING,
    FUNCTION,
    VARIABLE,
    OPERATOR,
    OPEN_PAR("("),
    CLOSE_PAR(")"),
    QUESTION_MARK("?"),
    COLON(":"),
    COMMA(","),
    END_OF_INPUT,
    ERROR;

    private FunctionType functionType;
    private String[]     indentifiers;

    TokenType()
    {
    }

    TokenType(String... indentifiers)
    {
        this.indentifiers = indentifiers;
    }

    TokenType(FunctionType functionType, String... indentifiers)
    {
        this(indentifiers);
        this.functionType = functionType;
    }

    public FunctionType getFunctionType()
    {
        return this.functionType;
    }

    @Override
    public String getIdentifier()
    {
        return this.indentifiers[0];
    }
}

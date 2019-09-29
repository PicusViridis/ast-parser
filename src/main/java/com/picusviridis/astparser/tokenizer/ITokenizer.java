package com.picusviridis.astparser.tokenizer;

import com.picusviridis.astparser.utils.Result;

public interface ITokenizer
{

    TokenType currentToken();

    TokenType getNextToken();

    boolean match(TokenType t);

    boolean matchOne(TokenType... t);

    boolean matchInteger(int expected);

    Result<Integer> matchInteger();

    Result<Double> matchDouble();

    Result<String> matchString();

    Result<String> matchFunction();

    Result<String> matchError();

    Result<String> matchVariable();
}

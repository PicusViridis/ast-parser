package com.picusviridis.astparser.analyzer;

import java.util.ArrayList;
import java.util.List;

import com.picusviridis.astparser.tokenizer.ConstantType;
import com.picusviridis.astparser.tokenizer.FunctionType;
import com.picusviridis.astparser.tokenizer.ITokenizer;
import com.picusviridis.astparser.tokenizer.TokenType;
import com.picusviridis.astparser.utils.Result;

public class Analyzer
{
    private ITokenizer tokenizer;

    public Node analyse(ITokenizer tokenizer)
    {
        this.tokenizer = tokenizer;
        if (this.tokenizer.currentToken() == TokenType.NONE)
        {
            this.tokenizer.getNextToken();
        }
        return this.handleSuperExpression();
    }

    private Node handleSuperExpression()
    {
        Node cond = this.handleExpression();
        if (this.tokenizer.match(TokenType.QUESTION_MARK))
        {
            Node whenTrue = this.handleSuperExpression();
            if (!this.tokenizer.match(TokenType.COLON))
            {
                return new ErrorNode("Expected ':'.");
            }
            Node whenFalse = this.handleSuperExpression();
            return new TernaryNode(cond, whenTrue, whenFalse);
        }
        return cond;
    }

    private Node handleExpression()
    {
        Node left = this.handleOr();
        while (this.tokenizer.currentToken() == TokenType.OR)
        {
            TokenType type = this.tokenizer.currentToken();
            this.tokenizer.getNextToken();
            left = new BinaryNode(type, left, this.handleOr());
        }
        return left;
    }

    private Node handleOr()
    {
        Node left = this.handleAnd();
        while (this.tokenizer.currentToken() == TokenType.AND)
        {
            TokenType type = this.tokenizer.currentToken();
            this.tokenizer.getNextToken();
            left = new BinaryNode(type, left, this.handleAnd());
        }
        return left;
    }

    private Node handleAnd()
    {
        Node left = this.handleComparison();
        while (this.tokenizer.currentToken() == TokenType.INF || this.tokenizer.currentToken() == TokenType.INF_EQ
                || this.tokenizer.currentToken() == TokenType.SUP || this.tokenizer.currentToken() == TokenType.SUP_EQ
                || this.tokenizer.currentToken() == TokenType.EQ || this.tokenizer.currentToken() == TokenType.NOT_EQ)
        {
            TokenType type = this.tokenizer.currentToken();
            this.tokenizer.getNextToken();
            left = new BinaryNode(type, left, this.handleComparison());
        }
        return left;
    }

    private Node handleComparison()
    {
        Node left = this.handleTerm();
        while (this.tokenizer.currentToken() == TokenType.PLUS || this.tokenizer.currentToken() == TokenType.MINUS
                || this.tokenizer.currentToken() == TokenType.POW)
        {
            TokenType type = this.tokenizer.currentToken();
            this.tokenizer.getNextToken();
            left = new BinaryNode(type, left, this.handleTerm());
        }
        return left;
    }

    private Node handleTerm()
    {
        Node left = this.handleFactor();
        while (this.tokenizer.currentToken() == TokenType.MULT || this.tokenizer.currentToken() == TokenType.DIV
                || this.tokenizer.currentToken() == TokenType.MOD)
        {
            TokenType type = this.tokenizer.currentToken();
            this.tokenizer.getNextToken();
            left = new BinaryNode(type, left, this.handleFactor());
        }
        return left;
    }

    private Node handleFactor()
    {
        if (this.tokenizer.currentToken() == TokenType.MINUS || this.tokenizer.currentToken() == TokenType.NOT)
        {
            TokenType type = this.tokenizer.currentToken();
            this.tokenizer.getNextToken();
            Node e = this.handlePositiveFactor();
            return new UnaryNode(type, e);
        }
        return this.handlePositiveFactor();
    }

    private Node handlePositiveFactor()
    {
        Result<String> functionResult = this.tokenizer.matchFunction();
        if (functionResult.isSuccess())
        {
            if (this.tokenizer.match(TokenType.OPEN_PAR))
            {
                List<Node> nodes = new ArrayList<>();
                while (this.tokenizer.currentToken() != TokenType.CLOSE_PAR
                        && this.tokenizer.currentToken() != TokenType.END_OF_INPUT)
                {
                    if (!this.tokenizer.match(TokenType.COMMA))
                    {
                        nodes.add(this.handleSuperExpression());
                    }
                }
                if (!this.tokenizer.match(TokenType.CLOSE_PAR))
                {
                    return new ErrorNode("Expected ).");
                }
                FunctionType type = FunctionType.get(functionResult.getValue());
                if (type == FunctionType.ERROR)
                {
                    return new ErrorNode(String.format("Unknown function %s", functionResult.getValue()));
                }
                return new FunctionNode(type, nodes.toArray(new Node[0]));
            }
            else
            {
                return new ErrorNode("Missing (");
            }
        }
        Result<Double> doubleResult = this.tokenizer.matchDouble();
        if (doubleResult.isSuccess())
        {
            return new DoubleNode(doubleResult.getValue());
        }
        Result<String> stringResult = this.tokenizer.matchString();
        if (stringResult.isSuccess())
        {
            return new StringNode(stringResult.getValue());
        }
        Result<String> varResult = this.tokenizer.matchVariable();
        if (varResult.isSuccess())
        {
            ConstantType type = ConstantType.get(varResult.getValue());
            if (type == ConstantType.ERROR)
            {
                return new ErrorNode(String.format("Unknown constant name: %s", varResult.getValue()));
            }
            return new DoubleNode(type.getValue());
        }
        if (this.tokenizer.match(TokenType.OPEN_PAR))
        {
            Node e = this.handleSuperExpression();
            Result<String> errorResult = this.tokenizer.matchError();
            if (errorResult.isSuccess())
            {
                return new ErrorNode(errorResult.getValue());
            }
            if (!this.tokenizer.match(TokenType.CLOSE_PAR))
            {
                return new ErrorNode("Expected ).");
            }
            return e;
        }
        Result<String> errorResult = this.tokenizer.matchError();
        if (errorResult.isSuccess())
        {
            return new ErrorNode(errorResult.getValue());
        }
        return new ErrorNode("Expected number or (expression).");
    }
}

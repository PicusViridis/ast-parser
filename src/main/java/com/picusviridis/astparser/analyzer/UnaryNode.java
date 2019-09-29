package com.picusviridis.astparser.analyzer;

import org.json.JSONObject;

import com.picusviridis.astparser.tokenizer.TokenType;
import com.picusviridis.astparser.visitor.AbstractNodeVisitor;

public class UnaryNode implements Node
{
    private TokenType operatorType;

    private Node      right;

    public UnaryNode(TokenType operatorType, Node right)
    {
        if (operatorType != TokenType.MINUS && operatorType != TokenType.NOT)
        {
            throw new IllegalArgumentException("operatorType: " + operatorType);
        }
        this.operatorType = operatorType;
        this.right = right;
    }

    @Override
    public Node accept(AbstractNodeVisitor visitor)
    {
        return visitor.visit(this);
    }

    @Override
    public int compareWith(Node o)
    {
        UnaryNode node = (UnaryNode) o;
        int result = this.operatorType.compareTo(node.getOperatorType());
        if (result == 0)
        {
            result = this.right.compareWith(node.getRight());
        }
        return result;
    }

    @Override
    public String toString()
    {
        return String.format("%s( %s )", this.operatorType.getIdentifier(), this.right.toString());
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject object = new JSONObject();
        object.put("nodeType", "unary");
        object.put("right", this.right.toJson());
        object.put("operator", this.operatorType.getIdentifier());
        return object;
    }

    public TokenType getOperatorType()
    {
        return this.operatorType;
    }

    public Node getRight()
    {
        return this.right;
    }
}

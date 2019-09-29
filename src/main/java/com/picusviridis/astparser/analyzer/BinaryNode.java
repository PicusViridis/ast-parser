package com.picusviridis.astparser.analyzer;

import org.json.JSONObject;

import com.picusviridis.astparser.tokenizer.TokenType;
import com.picusviridis.astparser.visitor.AbstractNodeVisitor;

public class BinaryNode implements Node
{
    private TokenType operatorType;
    private Node      left;
    private Node      right;

    public BinaryNode(TokenType operatorType, Node left, Node right)
    {
        this.operatorType = operatorType;
        this.left = left;
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
        BinaryNode node = (BinaryNode) o;
        int result = this.operatorType.compareTo(node.getOperatorType());
        if (result == 0)
        {
            result = this.left.compareWith(node.getLeft());
        }
        if (result == 0)
        {
            result = this.right.compareWith(node.getRight());
        }
        return result;
    }

    @Override
    public String toString()
    {
        return String.format("( %s %s %s )", this.left.toString(), this.operatorType.getIdentifier(),
                this.right.toString());
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject object = new JSONObject();
        object.put("nodeType", "binary");
        object.put("left", this.left.toJson());
        object.put("right", this.right.toJson());
        object.put("operator", this.operatorType.getIdentifier());
        return object;
    }

    public TokenType getOperatorType()
    {
        return this.operatorType;
    }

    public Node getLeft()
    {
        return this.left;
    }

    public Node getRight()
    {
        return this.right;
    }
}
package com.picusviridis.astparser.analyzer;

import org.json.JSONObject;

import com.picusviridis.astparser.visitor.AbstractNodeVisitor;

public class TernaryNode implements Node
{
    private Node condition;
    private Node whenTrue;
    private Node whenFalse;

    public TernaryNode(Node condition, Node whenTrue, Node whenFalse)
    {
        this.condition = condition;
        this.whenTrue = whenTrue;
        this.whenFalse = whenFalse;
    }

    @Override
    public Node accept(AbstractNodeVisitor visitor)
    {
        return visitor.visit(this);
    }

    @Override
    public int compareWith(Node o)
    {
        TernaryNode node = (TernaryNode) o;
        int result = this.condition.compareWith(node.getCondition());
        if (result == 0)
        {
            result = this.whenTrue.compareWith(node.getWhenTrue());
        }
        if (result == 0)
        {
            result = this.whenFalse.compareWith(node.getWhenFalse());
        }
        return result;
    }

    @Override
    public String toString()
    {
        return String.format("(%s ? %s : %s)", this.condition.toString(), this.whenTrue.toString(),
                this.whenFalse.toString());
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject object = new JSONObject();
        object.put("nodeType", "ternary");
        object.put("whenTrue", this.whenTrue.toJson());
        object.put("whenFalse", this.whenFalse.toJson());
        object.put("condition", this.condition.toJson());
        return object;
    }

    public Node getCondition()
    {
        return this.condition;
    }

    public Node getWhenTrue()
    {
        return this.whenTrue;
    }

    public Node getWhenFalse()
    {
        return this.whenFalse;
    }
}

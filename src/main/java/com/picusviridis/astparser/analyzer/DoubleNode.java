package com.picusviridis.astparser.analyzer;

import org.json.JSONObject;

import com.picusviridis.astparser.visitor.AbstractNodeVisitor;

public class DoubleNode implements Node
{
    private Double value;

    public DoubleNode(Double value)
    {
        this.value = value;
    }

    @Override
    public Node accept(AbstractNodeVisitor visitor)
    {
        return visitor.visit(this);
    }

    @Override
    public int compareWith(Node o)
    {
        DoubleNode node = (DoubleNode) o;
        return this.value.compareTo(node.getValue());
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.value);
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject object = new JSONObject();
        object.put("nodeType", "double");
        object.put("value", this.value);
        return object;
    }

    public Double getValue()
    {
        return this.value;
    }
}

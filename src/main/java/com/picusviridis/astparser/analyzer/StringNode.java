package com.picusviridis.astparser.analyzer;

import org.json.JSONObject;

import com.picusviridis.astparser.visitor.AbstractNodeVisitor;

public class StringNode implements Node
{
    private String value;

    public StringNode(String value)
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
        StringNode node = (StringNode) o;
        return this.value.compareTo(node.getValue());
    }

    @Override
    public String toString()
    {
        return String.format("\"%s\"", this.value);
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject object = new JSONObject();
        object.put("nodeType", "string");
        object.put("value", this.value);
        return object;
    }

    public String getValue()
    {
        return this.value;
    }
}

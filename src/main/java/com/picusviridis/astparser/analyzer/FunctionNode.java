package com.picusviridis.astparser.analyzer;

import org.json.JSONArray;
import org.json.JSONObject;

import com.picusviridis.astparser.tokenizer.FunctionType;
import com.picusviridis.astparser.visitor.AbstractNodeVisitor;

public class FunctionNode implements Node
{
    private FunctionType functionType;
    private Node[]       parameters;

    public FunctionNode(FunctionType functionType, Node... parameters)
    {
        this.functionType = functionType;
        this.parameters = parameters == null ? new Node[0] : parameters;
    }

    @Override
    public Node accept(AbstractNodeVisitor visitor)
    {
        return visitor.visit(this);
    }

    @Override
    public int compareWith(Node o)
    {
        FunctionNode node = (FunctionNode) o;
        int result = this.functionType.compareTo(node.getFunctionType());
        if (result == 0)
        {
            result = Integer.valueOf(this.parameters.length).compareTo(Integer.valueOf(node.getParameters().length));
        }
        if (result == 0)
        {
            for (int i = 0; i < this.parameters.length && result == 0; i++)
            {
                result = this.parameters[i].compareWith(node.getParameters()[i]);
            }
        }
        return result;
    }

    @Override
    public String toString()
    {
        String[] params = new String[this.parameters.length];
        for (int i = 0; i < this.parameters.length; i++)
        {
            params[i] = this.parameters[i].toString();
        }
        return String.format("%s( %s )", this.functionType.name().toLowerCase(), String.join(", ", params));
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject object = new JSONObject();
        object.put("nodeType", "function");
        object.put("type", this.functionType.getIdentifier());
        JSONArray nodesArray = new JSONArray();
        for (Node node : this.parameters)
        {
            nodesArray.put(node.toJson());
        }
        object.put("parameters", nodesArray);
        return object;
    }

    public FunctionType getFunctionType()
    {
        return this.functionType;
    }

    public Node[] getParameters()
    {
        return this.parameters;
    }
}

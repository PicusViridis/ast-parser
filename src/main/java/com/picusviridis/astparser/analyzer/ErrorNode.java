package com.picusviridis.astparser.analyzer;

import org.json.JSONObject;

import com.picusviridis.astparser.visitor.AbstractNodeVisitor;

public class ErrorNode implements Node
{
    private String    message;
    private ErrorNode cause;
    private String    name;
    private String    operation;

    public ErrorNode(String message)
    {
        this.message = message;
    }

    public ErrorNode(String message, ErrorNode cause)
    {
        this.message = message;
        this.cause = cause;
    }

    @Override
    public Node accept(AbstractNodeVisitor visitor)
    {
        return visitor.visit(this);
    }

    @Override
    public int compareWith(Node o)
    {
        ErrorNode node = (ErrorNode) o;
        int result = this.message.compareTo(node.getMessage());
        if (result == 0)
        {
            result = this.cause.compareWith(node.getCause());
        }
        if (result == 0)
        {
            result = this.name.compareTo(node.getName());
        }
        if (result == 0)
        {
            result = this.operation.compareTo(node.getOperation());
        }
        return result;
    }

    @Override
    public String toString()
    {
        return String.format("{ message: %s, cause: %s, sensor: %s, operation: %s }", this.message, this.cause,
                this.name, this.operation);
    }

    public String getMessage()
    {
        return this.message;
    }

    public ErrorNode getCause()
    {
        return this.cause;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getOperation()
    {
        return this.operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject object = new JSONObject();
        object.put("nodeType", "error");
        object.put("message", this.message);
        if (this.cause != null)
        {
            object.put("cause", this.cause.toJson());
        }
        object.put("sensor", this.name);
        object.put("operation", this.operation);
        return object;
    }
}
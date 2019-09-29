package com.picusviridis.astparser.executioner;

import java.util.List;

import com.picusviridis.astparser.analyzer.ErrorNode;
import com.picusviridis.astparser.analyzer.Node;

public abstract class AbstractOneValueExecutioner extends AbstractExecutioner
{
    @Override
    public Node execute(List<Node> nodes)
    {
        if (nodes.size() != 1)
        {
            return new ErrorNode(String.format("Function %s takes one parameter, got %s", this.type.getIdentifier(), nodes.size()));
        }
        return super.execute(nodes);
    }

    @Override
    protected double execute(double... values)
    {
        return this.execute(values[0]);
    }

    protected abstract double execute(double value1);
}

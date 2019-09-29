package com.picusviridis.astparser.executioner.ternary;

import java.util.List;

import com.picusviridis.astparser.analyzer.ErrorNode;
import com.picusviridis.astparser.analyzer.Node;
import com.picusviridis.astparser.executioner.AbstractExecutioner;
import com.picusviridis.astparser.utils.DoubleCondition;

public class TernaryExecutioner extends AbstractExecutioner
{
    @Override
    public Node execute(List<Node> nodes)
    {
        if (nodes.size() != 3)
        {
            return new ErrorNode(String.format("Function %s takes three parameters, got %s", this.type.getIdentifier(), nodes.size()));
        }
        return super.execute(nodes);
    }

    @Override
    protected double execute(double... values)
    {
        if (DoubleCondition.eqZero(values[0]))
        {
            return values[2];
        }
        return values[1];
    }
}

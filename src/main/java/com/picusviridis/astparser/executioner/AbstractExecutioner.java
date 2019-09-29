package com.picusviridis.astparser.executioner;

import java.util.List;

import com.picusviridis.astparser.analyzer.DoubleNode;
import com.picusviridis.astparser.analyzer.Node;
import com.picusviridis.astparser.tokenizer.IType;

public abstract class AbstractExecutioner
{
    protected IType type;

    public void setType(IType type)
    {
        this.type = type;
    }

    protected abstract double execute(double... values);

    public Node execute(List<Node> nodes)
    {
        double[] values = new double[nodes.size()];
        for (int i = 0; i < nodes.size(); i++)
        {
            values[i] = ((DoubleNode) nodes.get(i)).getValue();
        }
        return new DoubleNode(this.execute(values));
    }
}

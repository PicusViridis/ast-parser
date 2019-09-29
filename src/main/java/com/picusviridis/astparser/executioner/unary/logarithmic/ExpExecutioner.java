package com.picusviridis.astparser.executioner.unary.logarithmic;

import com.picusviridis.astparser.executioner.AbstractOneValueExecutioner;

public class ExpExecutioner extends AbstractOneValueExecutioner
{
    @Override
    public double execute(double value)
    {
        return Math.exp(value);
    }
}

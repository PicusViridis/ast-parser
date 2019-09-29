package com.picusviridis.astparser.executioner.unary.algebra;

import com.picusviridis.astparser.executioner.AbstractOneValueExecutioner;

public class CeilExecutioner extends AbstractOneValueExecutioner
{
    @Override
    public double execute(double value)
    {
        return Math.ceil(value);
    }
}

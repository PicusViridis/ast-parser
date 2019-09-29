package com.picusviridis.astparser.executioner.unary.algebra;

import com.picusviridis.astparser.executioner.AbstractOneValueExecutioner;

public class AbsExecutioner extends AbstractOneValueExecutioner
{
    @Override
    public double execute(double value)
    {
        return Math.abs(value);
    }
}

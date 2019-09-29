package com.picusviridis.astparser.executioner.unary.algebra;

import com.picusviridis.astparser.executioner.AbstractOneValueExecutioner;

public class SqrtExecutioner extends AbstractOneValueExecutioner
{
    @Override
    public double execute(double value)
    {
        return Math.sqrt(value);
    }
}

package com.picusviridis.astparser.executioner.unary.logarithmic;

import com.picusviridis.astparser.executioner.AbstractOneValueExecutioner;

public class LogExecutioner extends AbstractOneValueExecutioner
{
    @Override
    public double execute(double value)
    {
        return Math.log10(value);
    }
}

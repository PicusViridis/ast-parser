package com.picusviridis.astparser.executioner.unary.trigonometry;

import com.picusviridis.astparser.executioner.AbstractOneValueExecutioner;

public class CosExecutioner extends AbstractOneValueExecutioner
{
    @Override
    public double execute(double value)
    {
        return Math.cos(value);
    }
}

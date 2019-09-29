package com.picusviridis.astparser.executioner.binary.algebra;

import com.picusviridis.astparser.executioner.AbstractTwoValuesExecutioner;

public class PowExecutioner extends AbstractTwoValuesExecutioner
{
    @Override
    public double execute(double value1, double value2)
    {
        return Math.pow(value1, value2);
    }
}

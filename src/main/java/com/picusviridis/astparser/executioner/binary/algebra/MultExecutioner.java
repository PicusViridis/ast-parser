package com.picusviridis.astparser.executioner.binary.algebra;

import com.picusviridis.astparser.executioner.AbstractTwoValuesExecutioner;

public class MultExecutioner extends AbstractTwoValuesExecutioner
{
    @Override
    public double execute(double value1, double value2)
    {
        return value1 * value2;
    }
}

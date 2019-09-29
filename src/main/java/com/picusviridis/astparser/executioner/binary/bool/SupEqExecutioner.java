package com.picusviridis.astparser.executioner.binary.bool;

import com.picusviridis.astparser.executioner.AbstractTwoValuesExecutioner;

public class SupEqExecutioner extends AbstractTwoValuesExecutioner
{
    @Override
    public double execute(double value1, double value2)
    {
        return value1 >= value2 ? 1 : 0;
    }
}

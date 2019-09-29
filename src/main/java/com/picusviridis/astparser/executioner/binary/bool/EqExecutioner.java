package com.picusviridis.astparser.executioner.binary.bool;

import com.picusviridis.astparser.executioner.AbstractTwoValuesExecutioner;
import com.picusviridis.astparser.utils.DoubleCondition;

public class EqExecutioner extends AbstractTwoValuesExecutioner
{
    @Override
    public double execute(double value1, double value2)
    {
        return DoubleCondition.eq(value1, value2) ? 1 : 0;
    }
}

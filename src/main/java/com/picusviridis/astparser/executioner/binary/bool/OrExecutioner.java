package com.picusviridis.astparser.executioner.binary.bool;

import com.picusviridis.astparser.executioner.AbstractTwoValuesExecutioner;
import com.picusviridis.astparser.utils.DoubleCondition;

public class OrExecutioner extends AbstractTwoValuesExecutioner
{
    @Override
    public double execute(double value1, double value2)
    {
        return DoubleCondition.neqZero(value1) || DoubleCondition.neqZero(value2) ? 1 : 0;
    }
}

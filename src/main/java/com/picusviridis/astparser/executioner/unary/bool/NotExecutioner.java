package com.picusviridis.astparser.executioner.unary.bool;

import com.picusviridis.astparser.executioner.AbstractOneValueExecutioner;
import com.picusviridis.astparser.utils.DoubleCondition;

public class NotExecutioner extends AbstractOneValueExecutioner
{
    @Override
    public double execute(double value)
    {
        return DoubleCondition.eqZero(value) ? 1 : 0;
    }
}

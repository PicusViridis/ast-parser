package com.picusviridis.astparser.executioner.nary;

import com.picusviridis.astparser.executioner.AbstractExecutioner;

public class MaxExecutioner extends AbstractExecutioner
{
    @Override
    public double execute(double... values)
    {
        double max = Double.NaN;
        for (double value : values)
        {
            if (Double.isNaN(max) || max < value)
            {
                max = value;
            }
        }
        return max;
    }
}

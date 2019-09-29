package com.picusviridis.astparser.executioner.nary;

import com.picusviridis.astparser.executioner.AbstractExecutioner;

public class MinExecutioner extends AbstractExecutioner
{
    @Override
    public double execute(double... values)
    {
        double min = Double.NaN;
        for (double value : values)
        {
            if (Double.isNaN(min) || min > value)
            {
                min = value;
            }
        }
        return min;
    }
}

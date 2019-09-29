package com.picusviridis.astparser.utils;

public final class DoubleCondition
{
    private DoubleCondition()
    {
    }

    public static boolean eq(double d1, double d2)
    {
        return Double.valueOf(d1).compareTo(Double.valueOf(d2)) == 0;
    }

    public static boolean neq(double d1, double d2)
    {
        return !eq(d1, d2);
    }

    public static boolean eqZero(double d)
    {
        return eq(d, 0);
    }

    public static boolean neqZero(double d)
    {
        return !eq(d, 0);
    }
}

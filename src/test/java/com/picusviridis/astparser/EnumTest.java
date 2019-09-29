package com.picusviridis.astparser;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import com.picusviridis.astparser.tokenizer.ConstantType;
import com.picusviridis.astparser.tokenizer.FunctionType;

public class EnumTest
{
    @Test
    public void functionTest()
    {
        Map<FunctionType, String[]> data = new HashMap<>();
        data.put(FunctionType.ABS, new String[] { "abs", "Abs", "ABS" });
        data.put(FunctionType.ERROR, new String[] { "zere", "deault", "pi", null, "" });
        for (Entry<FunctionType, String[]> entry : data.entrySet())
        {
            for (String id : entry.getValue())
            {
                Assert.assertEquals(entry.getKey(), FunctionType.get(id));
            }
        }
    }

    @Test
    public void constantTest()
    {
        Map<ConstantType, String[]> data = new HashMap<>();
        data.put(ConstantType.PI, new String[] { "PI", "Pi", "pi" });
        data.put(ConstantType.E, new String[] { "e", "E" });
        data.put(ConstantType.NAN, new String[] { "NaN", "NAN", "nan", "NULL", "null" });
        data.put(ConstantType.ERROR, new String[] { "zere", "default", null });
        for (Entry<ConstantType, String[]> entry : data.entrySet())
        {
            for (String id : entry.getValue())
            {
                Assert.assertEquals(entry.getKey(), ConstantType.get(id));
            }
        }
    }
}

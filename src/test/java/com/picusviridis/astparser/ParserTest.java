package com.picusviridis.astparser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.junit.Assert;
import org.junit.Test;

import com.picusviridis.astparser.analyzer.Analyzer;
import com.picusviridis.astparser.analyzer.Node;
import com.picusviridis.astparser.tokenizer.StringTokenizer;
import com.picusviridis.astparser.visitor.AbstractNodeVisitor;
import com.picusviridis.astparser.visitor.EvalVisitor;

public class ParserTest
{
    @Test
    public void simple_factors()
    {
        Analyzer a = new Analyzer();
        Node node = a.analyse(new StringTokenizer("8*12/5*12"));
        Assert.assertEquals("( ( ( 8.0 * 12.0 ) / 5.0 ) * 12.0 )", node.toString());
    }

    @Test
    public void factors_with_addition()
    {
        Analyzer a = new Analyzer();
        Node node = a.analyse(new StringTokenizer("8+12*12"));
        Assert.assertEquals("( 8.0 + ( 12.0 * 12.0 ) )", node.toString());
    }

    @Test
    public void factors_with_minus()
    {
        Analyzer a = new Analyzer();
        Node node = a.analyse(new StringTokenizer("8-12*12+5"));
        Assert.assertEquals("( ( 8.0 - ( 12.0 * 12.0 ) ) + 5.0 )", node.toString());
    }

    @Test
    public void addition_and_factors()
    {
        Analyzer a = new Analyzer();
        Node node = a.analyse(new StringTokenizer("8*12+12"));
        Assert.assertEquals("( ( 8.0 * 12.0 ) + 12.0 )", node.toString());
    }

    @Test
    public void unary_minus_at_work()
    {
        Analyzer a = new Analyzer();
        Node node = a.analyse(new StringTokenizer("12*-7"));
        Assert.assertEquals("( 12.0 * -( 7.0 ) )", node.toString());
    }

    @Test
    public void expressions()
    {
        Analyzer a = new Analyzer();

        Map<String, Double> data = new HashMap<>();
        data.put("3", 3.0);
        data.put("3+8", 3.0 + 8.0);
        data.put("3*7/2", 3.0 * 7.0 / 2.0);
        data.put("3712/(45+98)*12*(58/12)", 3712.0 / (45.0 + 98.0) * 12.0 * (58.0 / 12.0));
        data.put("37*(12+4)/(45+98/(4+5+68-8-7+10))*1+(41/9*7+6-72)+2*(5+8/1-2)",
                37.0 * (12.0 + 4.0) / (45.0 + 98.0 / (4.0 + 5.0 + 68.0 - 8.0 - 7.0 + 10.0)) * 1.0
                        + (41.0 / 9.0 * 7.0 + 6.0 - 72) + 2.0 * (5.0 + 8.0 / 1.0 - 2.0));
        data.put("37 ? 8 : 5", 8.0);
        data.put("3+7 ? 1+8 : 4+1", 9.0);
        data.put("3-3 ? 1+8 : (-4+4 ? -8 : -24 )", -24.0);
        data.put("3*PI + 18", 3.0 * Math.PI + 18);
        data.put("13+5*6&&7?5>10&&2||10+5*3:5/8", 1.0);

        data.forEach(new BiConsumer<String, Double>()
        {
            @Override
            public void accept(String expression, Double result)
            {
                Node node = a.analyse(new StringTokenizer(expression));
                AbstractNodeVisitor visitor = new EvalVisitor();
                node = visitor.visitNode(node);
                Assert.assertEquals(String.valueOf(result), node.toString());
            }
        });

    }
}

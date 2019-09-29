package com.picusviridis.astparser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.picusviridis.astparser.analyzer.BinaryNode;
import com.picusviridis.astparser.analyzer.DoubleNode;
import com.picusviridis.astparser.analyzer.FunctionNode;
import com.picusviridis.astparser.analyzer.Node;
import com.picusviridis.astparser.analyzer.UnaryNode;
import com.picusviridis.astparser.tokenizer.FunctionType;
import com.picusviridis.astparser.tokenizer.TokenType;
import com.picusviridis.astparser.visitor.EvalVisitor;

public class EvalTest
{
    private EvalVisitor visitor;

    @Before
    public void before()
    {
        this.visitor = new EvalVisitor();
    }

    @Test
    public void add()
    {
        DoubleNode expected = new DoubleNode(7.0 + 10.0);
        Node node = new BinaryNode(TokenType.PLUS, new DoubleNode(7.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void sub()
    {
        DoubleNode expected = new DoubleNode(10.0 - 7.0);
        Node node = new BinaryNode(TokenType.MINUS, new DoubleNode(10.0), new DoubleNode(7.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void minus()
    {
        DoubleNode expected = new DoubleNode(-10.0);
        Node node = new UnaryNode(TokenType.MINUS, new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void div()
    {
        DoubleNode expected = new DoubleNode(7.0 / 10.0);
        Node node = new BinaryNode(TokenType.DIV, new DoubleNode(7.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void mult()
    {
        DoubleNode expected = new DoubleNode(7.0 * 10.0);
        Node node = new BinaryNode(TokenType.MULT, new DoubleNode(7.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void mod()
    {
        DoubleNode expected = new DoubleNode(10.0 % 7.0);
        Node node = new BinaryNode(TokenType.MOD, new DoubleNode(10.0), new DoubleNode(7.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void pow()
    {
        DoubleNode expected = new DoubleNode(Math.pow(10.0, 7.0));
        Node node = new BinaryNode(TokenType.POW, new DoubleNode(10.0), new DoubleNode(7.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void sqrt()
    {
        DoubleNode expected = new DoubleNode(Math.sqrt(10.0));
        Node node = new FunctionNode(FunctionType.SQRT, new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void abs()
    {
        DoubleNode expected = new DoubleNode(Math.abs(-10.0));
        Node node = new FunctionNode(FunctionType.ABS, new DoubleNode(-10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void ceil_up()
    {
        DoubleNode expected = new DoubleNode(Math.ceil(10.7));
        Node node = new FunctionNode(FunctionType.CEIL, new DoubleNode(10.7));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void ceil_down()
    {
        DoubleNode expected = new DoubleNode(Math.ceil(10.1));
        Node node = new FunctionNode(FunctionType.CEIL, new DoubleNode(10.1));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void floor_up()
    {
        DoubleNode expected = new DoubleNode(Math.floor(10.7));
        Node node = new FunctionNode(FunctionType.FLOOR, new DoubleNode(10.7));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void floor_down()
    {
        DoubleNode expected = new DoubleNode(Math.floor(10.1));
        Node node = new FunctionNode(FunctionType.FLOOR, new DoubleNode(10.1));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void round_up()
    {
        DoubleNode expected = new DoubleNode((double) Math.round(10.7));
        Node node = new FunctionNode(FunctionType.ROUND, new DoubleNode(10.7));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void round_down()
    {
        DoubleNode expected = new DoubleNode((double) Math.round(10.1));
        Node node = new FunctionNode(FunctionType.ROUND, new DoubleNode(10.1));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void exp()
    {
        DoubleNode expected = new DoubleNode(Math.exp(10.0));
        Node node = new FunctionNode(FunctionType.EXP, new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void ln()
    {
        DoubleNode expected = new DoubleNode(Math.log(10.0));
        Node node = new FunctionNode(FunctionType.LN, new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void log()
    {
        DoubleNode expected = new DoubleNode(Math.log10(10.0));
        Node node = new FunctionNode(FunctionType.LOG, new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void cos()
    {
        DoubleNode expected = new DoubleNode(Math.cos(10.0));
        Node node = new FunctionNode(FunctionType.COS, new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void sin()
    {
        DoubleNode expected = new DoubleNode(Math.sin(10.0));
        Node node = new FunctionNode(FunctionType.SIN, new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void tan()
    {
        DoubleNode expected = new DoubleNode(Math.tan(10.0));
        Node node = new FunctionNode(FunctionType.TAN, new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void acos()
    {
        DoubleNode expected = new DoubleNode(Math.acos(10.0));
        Node node = new FunctionNode(FunctionType.ACOS, new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void asin()
    {
        DoubleNode expected = new DoubleNode(Math.asin(10.0));
        Node node = new FunctionNode(FunctionType.ASIN, new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void atan()
    {
        DoubleNode expected = new DoubleNode(Math.atan(10.0));
        Node node = new FunctionNode(FunctionType.ATAN, new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void and_true()
    {
        DoubleNode expected = new DoubleNode(1.0);
        Node node = new BinaryNode(TokenType.AND, new DoubleNode(1.0), new DoubleNode(1.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void and_false()
    {
        DoubleNode expected = new DoubleNode(0.0);
        Node node = new BinaryNode(TokenType.AND, new DoubleNode(0.0), new DoubleNode(1.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void or_true()
    {
        DoubleNode expected = new DoubleNode(1.0);
        Node node = new BinaryNode(TokenType.OR, new DoubleNode(0.0), new DoubleNode(1.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void or_false()
    {
        DoubleNode expected = new DoubleNode(0.0);
        Node node = new BinaryNode(TokenType.OR, new DoubleNode(0.0), new DoubleNode(0.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void not_true()
    {
        DoubleNode expected = new DoubleNode(1.0);
        Node node = new UnaryNode(TokenType.NOT, new DoubleNode(0.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void not_false()
    {
        DoubleNode expected = new DoubleNode(0.0);
        Node node = new UnaryNode(TokenType.NOT, new DoubleNode(1.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void eq_true()
    {
        DoubleNode expected = new DoubleNode(1.0);
        Node node = new BinaryNode(TokenType.EQ, new DoubleNode(10.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void eq_false()
    {
        DoubleNode expected = new DoubleNode(0.0);
        Node node = new BinaryNode(TokenType.EQ, new DoubleNode(7.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void notEq_true()
    {
        DoubleNode expected = new DoubleNode(1.0);
        Node node = new BinaryNode(TokenType.NOT_EQ, new DoubleNode(7.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void notEq_false()
    {
        DoubleNode expected = new DoubleNode(0.0);
        Node node = new BinaryNode(TokenType.NOT_EQ, new DoubleNode(10.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void inf_true()
    {
        DoubleNode expected = new DoubleNode(1.0);
        Node node = new BinaryNode(TokenType.INF, new DoubleNode(7.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void inf_false()
    {
        DoubleNode expected = new DoubleNode(0.0);
        Node node = new BinaryNode(TokenType.INF, new DoubleNode(10.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void infEq_true()
    {
        DoubleNode expected = new DoubleNode(1.0);
        Node node = new BinaryNode(TokenType.INF_EQ, new DoubleNode(7.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void infEq_false()
    {
        DoubleNode expected = new DoubleNode(0.0);
        Node node = new BinaryNode(TokenType.INF_EQ, new DoubleNode(10.0), new DoubleNode(7.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void sup_true()
    {
        DoubleNode expected = new DoubleNode(1.0);
        Node node = new BinaryNode(TokenType.SUP, new DoubleNode(10.0), new DoubleNode(7.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void sup_false()
    {
        DoubleNode expected = new DoubleNode(0.0);
        Node node = new BinaryNode(TokenType.SUP, new DoubleNode(10.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void supEq_true()
    {
        DoubleNode expected = new DoubleNode(1.0);
        Node node = new BinaryNode(TokenType.SUP_EQ, new DoubleNode(10.0), new DoubleNode(7.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void supEq_false()
    {
        DoubleNode expected = new DoubleNode(0.0);
        Node node = new BinaryNode(TokenType.SUP_EQ, new DoubleNode(7.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void max()
    {
        DoubleNode expected = new DoubleNode(10.0);
        Node node = new FunctionNode(FunctionType.MAX, new DoubleNode(7.0), new DoubleNode(5.0), new DoubleNode(2.0),
                new DoubleNode(8.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void min()
    {
        DoubleNode expected = new DoubleNode(2.0);
        Node node = new FunctionNode(FunctionType.MIN, new DoubleNode(7.0), new DoubleNode(5.0), new DoubleNode(2.0),
                new DoubleNode(8.0), new DoubleNode(10.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void ternary_true()
    {
        DoubleNode expected = new DoubleNode(5.0);
        Node node = new FunctionNode(FunctionType.TERNARY, new DoubleNode(1.0), new DoubleNode(5.0),
                new DoubleNode(2.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }

    @Test
    public void ternary_false()
    {
        DoubleNode expected = new DoubleNode(2.0);
        Node node = new FunctionNode(FunctionType.TERNARY, new DoubleNode(0.0), new DoubleNode(5.0),
                new DoubleNode(2.0));
        Node result = this.visitor.visitNode(node);
        Assert.assertThat(result, new NodeMatcher(DoubleNode.class, expected));
    }
}

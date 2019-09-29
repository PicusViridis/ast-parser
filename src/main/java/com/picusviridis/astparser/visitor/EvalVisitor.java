package com.picusviridis.astparser.visitor;

import java.util.ArrayList;
import java.util.List;

import com.picusviridis.astparser.analyzer.BinaryNode;
import com.picusviridis.astparser.analyzer.DoubleNode;
import com.picusviridis.astparser.analyzer.ErrorNode;
import com.picusviridis.astparser.analyzer.FunctionNode;
import com.picusviridis.astparser.analyzer.Node;
import com.picusviridis.astparser.analyzer.TernaryNode;
import com.picusviridis.astparser.analyzer.UnaryNode;
import com.picusviridis.astparser.executioner.AbstractTwoValuesExecutioner;
import com.picusviridis.astparser.tokenizer.FunctionType;

public class EvalVisitor extends NodeVisitor
{
    @Override
    public Node visit(UnaryNode n)
    {
        n = (UnaryNode) super.visit(n);

        Node right = this.visitNode(n.getRight());

        if (right instanceof ErrorNode)
        {
            return new ErrorNode("Error while evaluating unary node", (ErrorNode) right);
        }

        if (n.getOperatorType().getFunctionType().getExecutioner() instanceof AbstractTwoValuesExecutioner)
        {
            return this.visit(new FunctionNode(n.getOperatorType().getFunctionType(), new DoubleNode(0.0), right));
        }

        return this.visit(new FunctionNode(n.getOperatorType().getFunctionType(), right));
    }

    @Override
    public Node visit(BinaryNode n)
    {
        n = (BinaryNode) super.visit(n);

        Node left = this.visitNode(n.getLeft());

        if (left instanceof ErrorNode)
        {
            return new ErrorNode("Error while evaluating binary node", (ErrorNode) left);
        }

        Node right = this.visitNode(n.getRight());

        if (right instanceof ErrorNode)
        {
            return new ErrorNode("Error while evaluating binary node", (ErrorNode) right);
        }

        return this.visit(new FunctionNode(n.getOperatorType().getFunctionType(), left, right));
    }

    @Override
    public Node visit(TernaryNode n)
    {
        n = (TernaryNode) super.visit(n);

        Node cond = this.visitNode(n.getCondition());

        if (cond instanceof ErrorNode)
        {
            return new ErrorNode("Error while evaluating ternary node", (ErrorNode) cond);
        }

        Node whenFalse = this.visitNode(n.getWhenFalse());

        if (whenFalse instanceof ErrorNode)
        {
            return new ErrorNode("Error while evaluating ternary node", (ErrorNode) whenFalse);
        }

        Node whenTrue = this.visitNode(n.getWhenTrue());

        if (whenTrue instanceof ErrorNode)
        {
            return new ErrorNode("Error while evaluating ternary node", (ErrorNode) whenTrue);
        }

        return this.visit(new FunctionNode(FunctionType.TERNARY, cond, whenTrue, whenFalse));
    }

    @Override
    public Node visit(FunctionNode n)
    {
        n = (FunctionNode) super.visit(n);

        List<Node> parameters = new ArrayList<>();
        for (int i = 0; i < n.getParameters().length; i++)
        {
            Node node = this.visitNode(n.getParameters()[i]);
            if (node instanceof ErrorNode)
            {
                return node;
            }
            else
            {
                parameters.add(node);
            }
        }

        return n.getFunctionType().getExecutioner().execute(parameters);
    }
}

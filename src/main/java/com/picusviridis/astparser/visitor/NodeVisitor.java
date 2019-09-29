package com.picusviridis.astparser.visitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.picusviridis.astparser.analyzer.BinaryNode;
import com.picusviridis.astparser.analyzer.DoubleNode;
import com.picusviridis.astparser.analyzer.ErrorNode;
import com.picusviridis.astparser.analyzer.FunctionNode;
import com.picusviridis.astparser.analyzer.Node;
import com.picusviridis.astparser.analyzer.StringNode;
import com.picusviridis.astparser.analyzer.TernaryNode;
import com.picusviridis.astparser.analyzer.UnaryNode;

public class NodeVisitor extends AbstractNodeVisitor
{
    private static final Logger LOGGER = LogManager.getLogger(NodeVisitor.class);

    @Override
    public Node visit(ErrorNode n)
    {
        LOGGER.info("Visiting ErrorNode : " + n.toString());

        return n;
    }

    @Override
    public Node visit(UnaryNode n)
    {
        LOGGER.info("Visiting UnaryNode : " + n.toString());

        Node r = this.visitNode(n.getRight());
        return r != n.getRight() ? new UnaryNode(n.getOperatorType(), r) : n;
    }

    @Override
    public Node visit(BinaryNode n)
    {
        LOGGER.info("Visiting BinaryNode : " + n.toString());

        Node left = this.visitNode(n.getLeft());
        Node right = this.visitNode(n.getRight());
        return left != n.getLeft() || right != n.getRight() ? new BinaryNode(n.getOperatorType(), left, right) : n;
    }

    @Override
    public Node visit(TernaryNode n)
    {
        LOGGER.info("Visiting TernaryNode : " + n.toString());

        Node c = this.visitNode(n.getCondition());
        Node t = this.visitNode(n.getWhenTrue());
        Node f = this.visitNode(n.getWhenFalse());
        return c != n.getCondition() || t != n.getWhenTrue() || f != n.getWhenFalse() ? new TernaryNode(c, t, f) : n;
    }

    @Override
    public Node visit(FunctionNode n)
    {
        LOGGER.info("Visiting FunctionNode : " + n.toString());

        Node[] params = n.getParameters();
        Node[] nodes = new Node[n.getParameters().length];
        for (int i = 0; i < params.length; i++)
        {
            nodes[i] = this.visitNode(params[i]);
        }
        return new FunctionNode(n.getFunctionType(), nodes);
    }

    @Override
    public Node visit(DoubleNode n)
    {
        LOGGER.info("Visiting DoubleNode : " + n.toString());

        return n;
    }

    @Override
    public Node visit(StringNode n)
    {
        LOGGER.info("Visiting StringNode : " + n.toString());

        return n;
    }
}

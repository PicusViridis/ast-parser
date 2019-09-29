package com.picusviridis.astparser.visitor;

import com.picusviridis.astparser.analyzer.BinaryNode;
import com.picusviridis.astparser.analyzer.DoubleNode;
import com.picusviridis.astparser.analyzer.ErrorNode;
import com.picusviridis.astparser.analyzer.FunctionNode;
import com.picusviridis.astparser.analyzer.Node;
import com.picusviridis.astparser.analyzer.StringNode;
import com.picusviridis.astparser.analyzer.TernaryNode;
import com.picusviridis.astparser.analyzer.UnaryNode;

public abstract class AbstractNodeVisitor
{
    public final Node visitNode(Node n)
    {
        return n.accept(this);
    }

    public abstract Node visit(ErrorNode n);

    public abstract Node visit(UnaryNode n);

    public abstract Node visit(BinaryNode n);

    public abstract Node visit(TernaryNode n);

    public abstract Node visit(FunctionNode n);

    public abstract Node visit(DoubleNode n);

    public abstract Node visit(StringNode n);
}

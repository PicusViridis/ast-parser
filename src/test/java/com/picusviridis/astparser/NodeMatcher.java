package com.picusviridis.astparser;

import org.hamcrest.CustomMatcher;

import com.picusviridis.astparser.analyzer.Node;

public class NodeMatcher extends CustomMatcher<Node>
{
    public final Node                  matchableNode;
    public final Class<? extends Node> matchableClass;

    public NodeMatcher(Class<? extends Node> matchableClass, Node matchableNode)
    {
        super(matchableNode.toString());
        this.matchableNode = matchableNode;
        this.matchableClass = matchableClass;
    }

    @Override
    public boolean matches(Object item)
    {
        if (null == item)
        {
            return false;
        }

        if (!this.matchableClass.isInstance(item))
        {
            return false;
        }

        Node node = (Node) item;
        if (this.matchableNode.compareWith(node) != 0)
        {
            return false;
        }
        return true;
    }
}

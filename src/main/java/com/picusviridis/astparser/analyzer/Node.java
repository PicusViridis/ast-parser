package com.picusviridis.astparser.analyzer;

import org.json.JSONObject;

import com.picusviridis.astparser.visitor.AbstractNodeVisitor;

public interface Node extends Comparable<Node>
{
    Node accept(AbstractNodeVisitor visitor);

    JSONObject toJson();

    @Override
    default int compareTo(Node o)
    {
        if (o == null)
        {
            return -1;
        }
        return this.compareWith(o);
    }

    int compareWith(Node o);
}

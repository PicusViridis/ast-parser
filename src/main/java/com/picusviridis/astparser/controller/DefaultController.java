package com.picusviridis.astparser.controller;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.picusviridis.astparser.analyzer.Analyzer;
import com.picusviridis.astparser.analyzer.DoubleNode;
import com.picusviridis.astparser.analyzer.ErrorNode;
import com.picusviridis.astparser.analyzer.Node;
import com.picusviridis.astparser.tokenizer.ITokenizer;
import com.picusviridis.astparser.tokenizer.StringTokenizer;
import com.picusviridis.astparser.visitor.AbstractNodeVisitor;
import com.picusviridis.astparser.visitor.EvalVisitor;

@RestController
@Scope("request")
public class DefaultController
{
    @RequestMapping(value = "/api/compute", produces = { "application/json; charset=UTF-8" })
    public @ResponseBody ResponseEntity<String> getSeries(
            @RequestParam(value = "expression", required = false) String expression,
            HttpSession session)
    {
        ITokenizer tokenizer = new StringTokenizer(expression);
        Analyzer analyzer = new Analyzer();
        Node n = analyzer.analyse(tokenizer);
        AbstractNodeVisitor visitor = new EvalVisitor();
        n = visitor.visitNode(n);

        if (n instanceof DoubleNode)
        {
            return new ResponseEntity<>(n.toString(), HttpStatus.OK);
        }

        if (n instanceof ErrorNode)
        {
            return new ResponseEntity<>(n.toJson().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(String.format("Cannot handle node of type %s", n.getClass()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

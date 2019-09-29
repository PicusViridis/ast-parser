package com.picusviridis.astparser.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/error")
public class SimpleErrorController implements ErrorController
{

    private final ErrorAttributes errorAttributes;

    @Autowired
    public SimpleErrorController(ErrorAttributes errorAttributes)
    {
        if (errorAttributes == null)
        {
            throw new IllegalArgumentException("Error attributes should not be null");
        }

        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath()
    {
        return "/error";
    }

    @RequestMapping
    public Map<String, Object> error(HttpServletRequest aRequest)
    {
        Map<String, Object> body = this.getErrorAttributes(aRequest, hasTraceParameter(aRequest));
        String trace = (String) body.get("trace");
        if (trace != null)
        {
            String[] lines = trace.split("\n\t");
            body.put("trace", lines);
        }
        return body;
    }

    private static boolean hasTraceParameter(HttpServletRequest request)
    {
        String parameter = request.getParameter("trace");
        if (parameter == null)
        {
            return false;
        }
        return parameter.equalsIgnoreCase("false");
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace)
    {
        RequestAttributes requestAttributes = new ServletRequestAttributes(aRequest);
        return this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}

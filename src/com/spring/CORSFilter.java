/*
 * Copyright (c) 2018 Du Tengfei. All Rights Reserved.
 */
package com.spring;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * CORSFilter.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Feb 27, 2018$
 * @since 1.0
 */
@Component
public class CORSFilter extends OncePerRequestFilter 
{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException 
    {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "1800");//30 min
        filterChain.doFilter(request, response);
    }
}

/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Cors filter.
 */
public class CorsFilter implements Filter {

    private static final String ENABLECORS_INIT_PARAM = "enablecors";
    private boolean enableCors = false;

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        if (enableCors) {
            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        }
        chain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String sEnableCors = filterConfig.getInitParameter(ENABLECORS_INIT_PARAM);
        if ("true".equals(sEnableCors)) {
            enableCors = true;
        } else {
            enableCors = false;
        }
    }

    @Override
    public void destroy() {
        //Destroy method
    }

}
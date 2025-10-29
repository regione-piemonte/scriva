/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.filter;

import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import java.io.IOException;

/**
 * SecurityFilter class
 *
 * @author CSI PIEMONTE
 */
public class SecurityFilter implements Filter {
    private static final String ENABLE_SECURITY_INIT_PARAM = "enablesecurity";
    private boolean enableSecurity = false;

    /**
     * The Context.
     */
    @Context
    ContainerRequestContext context;

    /**
     * The Istanza dao.
     */
    IstanzaDAO istanzaDAO;

    /**
     * doFilter
     *
     * @param servletRequest  servletRequest
     * @param servletResponse servletResponse
     * @param filterChain     filterChain
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (enableSecurity) {
            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * init
     *
     * @param filterConfig filterConfig
     * @throws ServletException ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String sEnableSecurity = filterConfig.getInitParameter(ENABLE_SECURITY_INIT_PARAM);
        enableSecurity = "true".equals(sEnableSecurity);
    }

    /**
     * destroy
     */
    @Override
    public void destroy() {
        // Do nothing
    }

}
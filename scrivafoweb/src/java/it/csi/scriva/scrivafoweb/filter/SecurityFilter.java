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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * SecurityFilter class
 *
 * @author CSI PIEMONTE
 */
public class SecurityFilter implements Filter {
    private static final String ENABLE_SECURITY_INIT_PARAM = "enablesecurity";
    private static final String HEADER_KEY_ATTORE_SCRIVA = "Attore-Scriva";

    private boolean enableSecurity = false;

    /**
     * The Http servlet response.
     */
    HttpServletResponse httpServletResponse;

    /**
     * The Http servlet request.
     */
    HttpServletRequest httpServletRequest;

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
            httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletRequest = (HttpServletRequest) servletRequest;

            if (!attoreScrivaMatchSessionUser()) {
                ErrorDTO err = new ErrorDTO("403", null, "Attore-Scriva non corrisponde all'utente in sessione", null, null);
                httpServletResponse.setStatus(HttpStatus.SC_FORBIDDEN);
                httpServletResponse.setContentType("application/json");
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                PrintWriter out = httpServletResponse.getWriter();
                out.print(mapper.writeValueAsString(err));
                out.flush();
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
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
        // destroy
    }

    private UserInfo getSessionUser() {
        return (UserInfo) httpServletRequest.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
    }

    private Map<String, List<String>> getHeadersMap() {
        Map<String, List<String>> result = null;

        if (httpServletRequest != null) {
            result = (Map<String, List<String>>) Collections.list(httpServletRequest.getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            h -> Collections.list(httpServletRequest.getHeaders((String) h))
                    ));
        }
        return result;
    }

    private List<String> getHeaderValues(String value) {
        List<String> result = null;
        if (httpServletRequest != null && StringUtils.isNotBlank(value)) {
            Map<String, List<String>> requestHeaders = getHeadersMap();
            result = requestHeaders != null && !requestHeaders.isEmpty() ? requestHeaders.get(value) : null;
        }
        return result;
    }

    private boolean attoreScrivaMatchSessionUser() {
        boolean result = false;
        UserInfo userInfo = getSessionUser();
        if (userInfo != null) {
            List<String> headerAttoreIstanzaList = getHeaderValues(HEADER_KEY_ATTORE_SCRIVA);
            String headerAttoreIstanza = headerAttoreIstanzaList != null ? headerAttoreIstanzaList.get(0) : null;
            if (headerAttoreIstanza != null) {
                String[] headerAttoreIstanzaSplitted = headerAttoreIstanza.split("/");
                if (headerAttoreIstanzaSplitted.length > 1) {
                    String cfAttoreScriva = headerAttoreIstanzaSplitted[1];
                    result = cfAttoreScriva.equalsIgnoreCase(userInfo.getCodFisc());
                }
            } else {
                result = true;
            }

        }
        return result;
    }

}
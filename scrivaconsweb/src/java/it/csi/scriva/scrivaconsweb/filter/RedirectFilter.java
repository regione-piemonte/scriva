/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

package  it.csi.scriva.scrivaconsweb.filter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import it.csi.scriva.scrivaconsweb.common.Constants;

public class RedirectFilter implements Filter {

	
	private static Logger logger = Logger.getLogger(RedirectFilter.class);
	
    public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";
    public static final String IRIDE_ID_SESSIONATTR = "iride2_id";
	private boolean devmode = false;
    

	@Override
	public void destroy() {
		// nothing to do
	}

	//SCRIVA-957
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hresp = (HttpServletResponse) resp;
		
		try {
		if(hreq.getRequestURI().endsWith("/"+Constants.COMPONENT_NAME+"/") || hreq.getRequestURI().endsWith("/"+Constants.COMPONENT_NAME+"/home")) {

			hresp.sendRedirect("/"+Constants.COMPONENT_NAME+"/index.html");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		
		/*
		 * if(!hreq.getRequestURI().contains("/api/v1/")) {
		 * hresp.sendRedirect(hreq.getRequestURI()); }
		 */
		

		chain.doFilter(req, resp);
	}

	

/*	
	public String getToken(HttpServletRequest httpreq) {
        String marker = httpreq.getHeader(AUTH_ID_MARKER);
        if (marker == null && devmode) {
            return getTokenDevMode(httpreq);
        } else {
            // gestione dell'encoding
            String decodedMarker = new String(marker.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            return decodedMarker;
        }
    }
	
	
	private String getTokenDevMode(HttpServletRequest httpreq) {
        String marker = httpreq.getParameter(AUTH_ID_MARKER);
        return marker;
    }
	
	   private String normalizeToken(String token) {
	        return token;
	    }
	*/
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}

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

import it.csi.iride2.policy.entity.Identita;
import it.csi.iride2.policy.exceptions.MalformedIdTokenException;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Inserisce in sessione:
 * <ul>
 *  <li>l'identit&agrave; digitale relativa all'utente autenticato.
 *  <li>l'oggetto <code>currentUser</code>
 * </ul>
 * Funge da adapter tra il filter del metodo di autenticaizone previsto e la
 * logica applicativa.
 *
 * @author CSIPiemonte
 */
public class IrideIdAdapterFilter implements Filter {

    public static final String IRIDE_ID_SESSIONATTR = "iride2_id";

    public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";

    public static final String USERINFO_SESSIONATTR = "appDatacurrentUser";

    /**
     *
     */
    protected static final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME + ".security");

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fchn) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) req;
        //LOG.debug("[IrideIdAdapterFilter::doFilter] Identita IRIDE: " + getIdentita(hreq));

        if (hreq.getSession().getAttribute(IRIDE_ID_SESSIONATTR) == null || hreq.getSession().getAttribute(USERINFO_SESSIONATTR) == null) {
            String marker = getToken(hreq);
            if (marker != null) {
                try {
                    Identita identita = new Identita(normalizeToken(marker));
                    LOG.debug("[IrideIdAdapterFilter::doFilter] Inserito in sessione marcatore IRIDE:" + identita);
                    hreq.getSession().setAttribute(IRIDE_ID_SESSIONATTR, identita);
                    UserInfo userInfo = new UserInfo();
                    userInfo.setNome(identita.getNome());
                    userInfo.setCognome(identita.getCognome());
                    userInfo.setEnte("--");
                    userInfo.setRuolo("--");
                    userInfo.setCodFisc(identita.getCodFiscale());
                    userInfo.setLivAuth(identita.getLivelloAutenticazione());
                    userInfo.setCommunity(identita.getIdProvider());
                    hreq.getSession().setAttribute(USERINFO_SESSIONATTR, userInfo);
                } catch (MalformedIdTokenException e) {
                    LOG.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
                }
            } else {
                // il marcatore deve sempre essere presente altrimenti e' una
                // condizione di errore (escluse le pagine home e di servizio)
                if (mustCheckPage(hreq.getRequestURI())) {
                    LOG.error(
                            "[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
                    throw new ServletException(
                            "Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
                }
            }
        }

        fchn.doFilter(req, resp);

    }

    private boolean mustCheckPage(String requestURI) {

        return true;
    }

    public void destroy() {
        // NOP
    }

    private static final String DEVMODE_INIT_PARAM = "devmode";

    private boolean devmode = false;

    public void init(FilterConfig fc) throws ServletException {
        String sDevmode = fc.getInitParameter(DEVMODE_INIT_PARAM);
        if ("true".equals(sDevmode)) {
            devmode = true;
        } else {
            devmode = false;
        }
    }

    public String getToken(HttpServletRequest httpreq) {
        String marker = httpreq.getHeader(AUTH_ID_MARKER);
        if (marker == null && devmode) {
            return getTokenDevMode(httpreq);
        } else {
            // gestione dell'encoding
            return marker != null ? new String(marker.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8) : null;
        }
    }

    private String getTokenDevMode(HttpServletRequest httpreq) {
        return httpreq.getParameter(AUTH_ID_MARKER);
    }

    private String normalizeToken(String token) {
        return token;
    }

    private Identita getIdentita(HttpServletRequest httpServletRequest) {
        Identita identita = null;
        String marker = getToken(httpServletRequest);
        try {
            identita = marker != null ? new Identita(normalizeToken(marker)) : null;
        } catch (MalformedIdTokenException e) {
            LOG.error(e.getMessage());
        }
        return identita;
    }

}
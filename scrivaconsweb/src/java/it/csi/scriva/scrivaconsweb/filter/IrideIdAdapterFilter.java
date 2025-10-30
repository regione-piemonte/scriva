/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.filter;

import it.csi.iride2.policy.entity.Identita;
import it.csi.iride2.policy.exceptions.MalformedIdTokenException;
import it.csi.scriva.scrivaconsweb.business.service.proxy.impl.ProxyServiceImpl;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @author CSI PIEMONTE
 */
public class IrideIdAdapterFilter implements Filter {

    /**
     * The constant IRIDE_ID_SESSIONATTR.
     */
    public static final String IRIDE_ID_SESSIONATTR = "iride2_id";

    /**
     * The constant AUTH_ID_MARKER.
     */
    public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";

    /**
     * The constant USERINFO_SESSIONATTR.
     */
    public static final String USERINFO_SESSIONATTR = "appDatacurrentUser";

    private static final String DEVMODE_INIT_PARAM = "devmode";

    private boolean devmode = false;

    /**
     * The constant LOG.
     */
   // protected static final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME + ".security");
    private static Logger logger = Logger.getLogger(IrideIdAdapterFilter.class);
    /**
     * doFilter
     *
     * @param req req
     * @param resp resp
     * @param filterChain filterChain
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) req;
        if (hreq.getSession().getAttribute(IRIDE_ID_SESSIONATTR) == null) {
            String marker = getToken(hreq);
            if (marker != null) {
                try {
                    Identita identita = new Identita(normalizeToken(marker));
                    logger.debug("[IrideIdAdapterFilter::doFilter] Inserito in sessione marcatore IRIDE:" + identita);
                    hreq.getSession().setAttribute(IRIDE_ID_SESSIONATTR, identita);
                    it.csi.scriva.scrivaconsweb.dto.UserInfo userInfo = new it.csi.scriva.scrivaconsweb.dto.UserInfo();
                    userInfo.setNome(identita.getNome());
                    userInfo.setCognome(identita.getCognome());
                    userInfo.setEnte("--");
                    userInfo.setRuolo("--");
                    userInfo.setCodFisc(identita.getCodFiscale());
                    userInfo.setLivAuth(identita.getLivelloAutenticazione());
                    userInfo.setCommunity(identita.getIdProvider());
                    hreq.getSession().setAttribute(USERINFO_SESSIONATTR, userInfo);

                } catch (MalformedIdTokenException e) {
                	logger.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
                }
            } else {
                // il marcatore deve sempre essere presente altrimenti e' una
                // condizione di errore (escluse le pagine home e di servizio)
                if (mustCheckPage(hreq.getRequestURI())) {
                	logger.error(
                            "[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
                    throw new ServletException(
                            "Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
                }
            }
        }
        filterChain.doFilter(req, resp);
    }

    /**
     * mustCheckPage
     *
     * @param requestURI requestURI
     * @return booelan
     */
    private boolean mustCheckPage(String requestURI) {
        return true;
    }

    /**
     * destroy
     */
    public void destroy() {
        // NOP
    }

    /**
     * init
     *
     * @param fc fc
     * @throws ServletException ServletException
     */
    public void init(FilterConfig fc) throws ServletException {
        String sDevmode = fc.getInitParameter(DEVMODE_INIT_PARAM);
        devmode = "true".equals(sDevmode);
    }

    /**
     * getToken
     *
     * @param httpreq httpreq
     * @return string token
     */
    public String getToken(HttpServletRequest httpreq) {
        String marker = httpreq.getHeader(AUTH_ID_MARKER);
        if (marker == null && devmode) {
            return getTokenDevMode(httpreq);
        } else {
            // gestione dell'encoding
            String decodedMarker = new String(marker.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            return decodedMarker;
            
           // Originale 
           // return marker;
        }
    }

    /**
     * getTokenDevMode
     *
     * @param httpreq httpreq
     * @return string
     */
    private String getTokenDevMode(HttpServletRequest httpreq) {
        String marker = httpreq.getParameter(AUTH_ID_MARKER);
        return marker;
    }

    /**
     * normalizeToken
     *
     * @param token token
     * @return string
     */
    private String normalizeToken(String token) {
        return token;
    }

}

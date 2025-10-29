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

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.iride2.policy.exceptions.MalformedIdTokenException;
import it.csi.scriva.scrivabesrv.business.be.helper.iride.IrideServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.iride.dto.Identita;
import it.csi.scriva.scrivabesrv.dto.UserInfo;
import it.csi.scriva.scrivabesrv.dto.XRequestAuth;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.util.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    protected static final Logger LOGGER = Logger.getLogger(Constants.COMPONENT_NAME + ".security");

    /**
     * The Iride service helper.
     */
    protected IrideServiceHelper irideServiceHelper;

    /**
     * doFilter
     *
     * @param req         req
     * @param resp        resp
     * @param filterChain filterChain
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) req;
        Identita identita = getIdentita(hreq);
        LOGGER.debug("[IrideIdAdapterFilter::doFilter] IRIDE Identita : " + identita);
        LOGGER.debug("[IrideIdAdapterFilter::doFilter] IRIDE_ID_SESSIONATTR : " + hreq.getSession().getAttribute(IRIDE_ID_SESSIONATTR));

        if (identita != null && StringUtils.isNotBlank(identita.getTimestamp()) && !isIdentitaAutentica(identita)) {
            LOGGER.debug("[IrideIdAdapterFilter::doFilter] IRIDE Identita isIdentitaAutentica: " + identita);
            HttpServletResponse response = (HttpServletResponse) resp;
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            hreq.getSession().invalidate();
            return;
        } else if (hreq.getSession().getAttribute(IRIDE_ID_SESSIONATTR) == null) {
            LOGGER.debug("[IrideIdAdapterFilter::doFilter] IRIDE_ID_SESSIONATTR : " + hreq.getSession().getAttribute(IRIDE_ID_SESSIONATTR));
            String marker = getToken(hreq);
            LOGGER.debug("[IrideIdAdapterFilter::doFilter] marker : " + marker);
            if (marker != null) {
                LOGGER.debug("[IrideIdAdapterFilter::doFilter] marker not null");
                try {
                    identita = new Identita(normalizeToken(marker));
                    LOGGER.debug("[IrideIdAdapterFilter::doFilter] Inserito in sessione marcatore IRIDE:" + identita);
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
                    LOGGER.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
                }
            }
            /*
            else {
                // il marcatore deve sempre essere presente altrimenti e' una
                // condizione di errore (escluse le pagine home e di servizio)
                if (mustCheckPage(hreq.getRequestURI())) {
                    LOGGER.error("[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
                    throw new ServletException("Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
                }
            }
            */
        }
        LOGGER.debug("[IrideIdAdapterFilter::doFilter] END");
        filterChain.doFilter(req, resp);
    }

    /**
     * mustCheckPage
     *
     * @param requestURI requestURI
     * @return booelan
     */
    private boolean mustCheckPage(String requestURI) {
        //return true;
        return false;
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
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(fc.getServletContext());
        irideServiceHelper = ctx.getBean(IrideServiceHelper.class);

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
            return marker != null ? new String(marker.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8) : null;
        }
    }

    /**
     * getTokenDevMode
     *
     * @param httpreq httpreq
     * @return string
     */
    private String getTokenDevMode(HttpServletRequest httpreq) {
        return httpreq.getParameter(AUTH_ID_MARKER);
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

    /**
     * Gets identita.
     *
     * @param httpServletRequest the http servlet request
     * @return the identita
     */
    protected Identita getIdentita(HttpServletRequest httpServletRequest) {
        LOGGER.debug("[IrideIdAdapterFilter::getIdentita] BEGIN");
        Identita identita = null;
        String marker = getToken(httpServletRequest);
        XRequestAuth xRequestAuth = getXRequestAuth(getHeaderRequestAuth(httpServletRequest));
        try {
            if (StringUtils.isNotBlank(marker)) {
                identita = new Identita(marker);
            } else if (xRequestAuth != null) {
                identita = xRequestAuth.getIdentita();
            }
            LOGGER.debug("[IrideIdAdapterFilter::getIdentita] IRIDE Identita : \n" + identita);
        } catch (MalformedIdTokenException e) {
            LOGGER.error("[IrideIdAdapterFilter::getIdentita] ERROR : " + e.getMessage());
        }
        LOGGER.debug("[IrideIdAdapterFilter::getIdentita] END");
        return identita;
    }

    /**
     * Gets request auth.
     *
     * @param httpreq the httpreq
     * @return the request auth
     */
    protected String getHeaderRequestAuth(HttpServletRequest httpreq) {
        return httpreq.getHeader(Constants.HEADER_X_REQUEST_AUTH);
    }

    /**
     * Gets x request auth.
     *
     * @param headerXRequestAuth the header x request auth
     * @return the x request auth
     */
    protected XRequestAuth getXRequestAuth(String headerXRequestAuth) {
        LOGGER.debug("[IrideIdAdapterFilter::getXRequestAuth] BEGIN");
        XRequestAuth result = null;
        if (StringUtils.isNotBlank(headerXRequestAuth)) {
            try {
                String xRequestAuth = new String(Base64.decode(headerXRequestAuth));
                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(xRequestAuth, XRequestAuth.class);
            } catch (Exception e) {
                LOGGER.error("[IrideIdAdapterFilter::getXRequestAuth] ERROR : " + e);
            } finally {
                LOGGER.debug("[IrideIdAdapterFilter::getXRequestAuth] END");
            }
        }
        return result;
    }

    /**
     * Is identita autentica boolean.
     *
     * @param identita the identita
     * @return the boolean
     */
    protected boolean isIdentitaAutentica(Identita identita) {
        LOGGER.debug("[IrideIdAdapterFilter::isIdentitaAutentica] BEGIN");
        boolean result = devmode || identita == null ? Boolean.TRUE : irideServiceHelper.isIdentitaAutentica(identita);
        //boolean result = Boolean.TRUE;
        LOGGER.debug("[IrideIdAdapterFilter::isIdentitaAutentica] END");
        return result;
    }

}
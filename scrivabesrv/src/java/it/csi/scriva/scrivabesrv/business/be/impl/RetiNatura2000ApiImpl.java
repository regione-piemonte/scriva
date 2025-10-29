/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import it.csi.scriva.scrivabesrv.business.be.RetiNatura2000Api;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.service.ParchiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RetiNatura2000ApiImpl extends BaseApiServiceImpl implements RetiNatura2000Api {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private ParchiService parchiService;

    /**
     * Gets reti natura 2000.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param codIstatComuni   the cod istat comuni
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the aree protette
     * @throws GenericException the generic exception
     */
    @Override
    public Response getRn2000(Long idOggettoIstanza, String codIstatComuni, Boolean checkComuni,
                              SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, "idOggettoIstanza [" + idOggettoIstanza + "] - codIstatComuni [" + codIstatComuni + "] - checkComuni [" + checkComuni + "]");

        if (idOggettoIstanza != null) {
            return getResponseList(parchiService.getSitiNatura2000(idOggettoIstanza, checkComuni), className);
        }
        if (StringUtils.isNotBlank(codIstatComuni)) {
            return getResponseList(parchiService.getSitiNatura2000ByCodIstatComuni(codIstatComuni), className);
        }

        return getResponseList(parchiService.getSitiNatura2000(), className);
    }


}
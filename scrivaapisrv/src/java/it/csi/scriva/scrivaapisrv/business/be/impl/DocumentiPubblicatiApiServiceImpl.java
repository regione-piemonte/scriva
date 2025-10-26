/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import it.csi.scriva.scrivaapisrv.business.be.DocumentiPubblicatiApi;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.ApiServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * Documenti pubblicati api.
 *
 * @author CSI PIEMONTE
 */
@Component
public class DocumentiPubblicatiApiServiceImpl extends AbstractApiServiceImpl implements DocumentiPubblicatiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    ApiServiceHelper apiServiceHelper;

    /**
     * Gets documenti pubblicati.
     *
     * @param xRequestAuth         the x request auth
     * @param xRequestId           the x request id
     * @param idIstanza            the id istanza
     * @param codAllegato          the cod allegato
     * @param codClasseAllegato    the cod classe allegato
     * @param codCategoriaAllegato the cod categoria allegato
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param flgLinkDocumento     the flg link documento
     * @param offset               the offset
     * @param limit                the limit
     * @param sort                 the sort
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @return the documenti pubblicati
     */
    @Override
    public Response getDocumentiPubblicati(String xRequestAuth, String xRequestId, Long idIstanza, String codAllegato, String codClasseAllegato, String codCategoriaAllegato, String codTipologiaAllegato, String flgLinkDocumento, Integer offset, Integer limit, String sort, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, "idIstanza : [" + idIstanza + "] - codAllegato : [" + codAllegato + "] - codClasseAllegato : [" + codClasseAllegato + "] - codCategoriaAllegato : [" + codCategoriaAllegato + "] - codTipologiaAllegato : [" + codTipologiaAllegato + "] - flgLinkDocumento : " + flgLinkDocumento);
        try {
            return apiServiceHelper.getResponseGET("/allegati", securityContext, httpHeaders, httpRequest, uriInfo);
        } finally {
            logEnd(className);
        }
    }
}
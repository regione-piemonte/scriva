/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.impl;

import it.csi.scriva.scrivafoweb.business.be.SearchApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.SearchApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SearchDTO;
import it.csi.scriva.scrivafoweb.dto.IstanzaSearchResultFoDTO;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Component
public class SearchApiServiceImpl extends AbstractApiServiceImpl implements SearchApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    SearchApiServiceHelper searchApiServiceHelper;

    /**
     * Search istanze response.
     *
     * @param searchCriteria  the search criteria
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param oggApp          the ogg app
     * @param compare         the compare
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response searchIstanze(SearchDTO searchCriteria,
                                  Integer offset, Integer limit, String sort,
                                  Boolean oggApp,
                                  String compare,
                                  SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, searchCriteria);
        IstanzaSearchResultFoDTO istanzeSearchResult = null;
        try {
            istanzeSearchResult = searchApiServiceHelper.searchIstanze(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), searchCriteria, offset, limit, sort, oggApp, compare);
            if (null == istanzeSearchResult) {
                String errorMessage = "Risorsa non trovata con i criteria di ricerca selezionati ";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                logError(className, err);
                return Response.serverError().entity(err).status(404).build();
            }
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
        return Response.ok(istanzeSearchResult.getIstanzaSearchResultDTOList()).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header(Constants.HEADER_PAGINATION_INFO, istanzeSearchResult.getPaginationInfoHeader()).header("CountIstanze", istanzeSearchResult.getCountIstanzeHeader()).build();
    }

}
/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.scriva.scrivaapisrv.business.be.SearchApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.SearchApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SearchDTO;
import it.csi.scriva.scrivaapisrv.dto.IstanzaSearchResultFoDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Component
public class SearchApiServiceImpl extends AbstractApiServiceImpl implements SearchApi {

    @Autowired
    SearchApiServiceHelper searchApiServiceHelper;

    @Override
    public Response searchIstanze(SearchDTO searchCriteria, Integer offset, Integer limit, String sort, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[SearchApiServiceImpl::searchIstanze] BEGIN");
        LOGGER.debug("[SearchApiServiceImpl::searchIstanze] Parametro in input searchCriteria: \n" + searchCriteria.toString() + "\n");
        IstanzaSearchResultFoDTO istanzeSearchResult = null;
        try {
            istanzeSearchResult = searchApiServiceHelper.searchIstanze(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), searchCriteria, offset, limit, sort);
            if (null == istanzeSearchResult) {
                String errorMessage = "Risorsa non trovata con i criteria di ricerca selezionati ";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                LOGGER.error("[SearchApiServiceImpl::searchIstanze] ERROR : " + errorMessage);
                return Response.serverError().entity(err).status(404).build();
            }
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[SearchApiServiceImpl::searchIstanze] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[SearchApiServiceImpl::searchIstanze] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[SearchApiServiceImpl::searchIstanze] END");
        }
        return Response.ok(istanzeSearchResult.getIstanzaSearchResultDTOList()).header(HttpHeaders.CONTENT_ENCODING, "identity").header("PaginationInfo", istanzeSearchResult.getPaginationInfoHeader()).header("CountIstanze", istanzeSearchResult.getCountIstanzeHeader()).build();
    }

}
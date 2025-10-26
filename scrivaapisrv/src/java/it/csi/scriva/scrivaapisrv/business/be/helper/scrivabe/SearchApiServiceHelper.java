/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaSearchResultDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SearchDTO;
import it.csi.scriva.scrivaapisrv.dto.IstanzaSearchResultFoDTO;

import java.util.List;

public class SearchApiServiceHelper extends AbstractServiceHelper {

    public SearchApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public IstanzaSearchResultFoDTO searchIstanze(MultivaluedMap<String, Object> requestHeaders, SearchDTO searchCriteria, Integer offset, Integer limit, String sort) throws GenericException {
        LOGGER.debug("[SearchApiServiceHelper::searchIstanze] BEGIN");
        IstanzaSearchResultFoDTO result = new IstanzaSearchResultFoDTO();
        String targetUrl = this.endpointBase + "/search/istanze?offset=" + offset + "&limit=" + limit + "&sort=" + sort;
        try {
            Entity<SearchDTO> entity = Entity.json(searchCriteria);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[SearchApiServiceHelper::searchIstanze] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<IstanzaSearchResultDTO>> istanzaSearchResultDTOType = new GenericType<List<IstanzaSearchResultDTO>>() {
            };
            LOGGER.info("[SearchApiServiceHelper::searchIstanze] : " + resp.getHeaderString("PaginationInfo"));
            result.setIstanzaSearchResultDTOList(resp.readEntity(istanzaSearchResultDTOType));
            result.setPaginationInfoHeader(resp.getHeaderString("PaginationInfo"));
            result.setCountIstanzeHeader(resp.getHeaderString("CountIstanze"));
        } catch (ProcessingException e) {
            LOGGER.error("[SearchApiServiceHelper::searchIstanze] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
        LOGGER.debug("[SearchApiServiceHelper::searchIstanze] END");
        return result;
    }

}
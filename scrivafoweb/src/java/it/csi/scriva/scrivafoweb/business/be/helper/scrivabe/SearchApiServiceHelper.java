/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaSearchResultDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SearchDTO;
import it.csi.scriva.scrivafoweb.dto.IstanzaSearchResultFoDTO;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;

public class SearchApiServiceHelper extends AbstractServiceHelper {

    public SearchApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public IstanzaSearchResultFoDTO searchIstanze(MultivaluedMap<String, Object> requestHeaders, SearchDTO searchCriteria,
                                                  Integer offset, Integer limit, String sort,
                                                  Boolean oggApp,
                                                  String compare) throws GenericException {
        logger.debug("[SearchApiServiceHelper::searchIstanze] BEGIN");
        IstanzaSearchResultFoDTO result = new IstanzaSearchResultFoDTO();
        String targetUrl = this.endpointBase + "/search/istanze?offset=" + offset + "&limit=" + limit + "&sort=" + sort + "&ogg_app=" + oggApp;
        targetUrl = StringUtils.isNotBlank(compare) ? targetUrl + "&compare=" + compare : targetUrl;
        try {
            Entity<SearchDTO> entity = Entity.json(searchCriteria);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[SearchApiServiceHelper::searchIstanze] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<IstanzaSearchResultDTO>> istanzaSearchResultDTOType = new GenericType<List<IstanzaSearchResultDTO>>() {
            };
            logger.info("[SearchApiServiceHelper::searchIstanze] : " + resp.getHeaderString(Constants.HEADER_PAGINATION_INFO));
            result.setIstanzaSearchResultDTOList(resp.readEntity(istanzaSearchResultDTOType));
            result.setPaginationInfoHeader(resp.getHeaderString(Constants.HEADER_PAGINATION_INFO));
            result.setCountIstanzeHeader(resp.getHeaderString("CountIstanze"));
        } catch (ProcessingException e) {
            logger.error("[SearchApiServiceHelper::searchIstanze] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        }
        logger.debug("[SearchApiServiceHelper::searchIstanze] END");
        return result;
    }

}
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

import it.csi.scriva.scrivabesrv.business.be.TipiMessaggioApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoMessaggioDAO;
import it.csi.scriva.scrivabesrv.dto.TipoMessaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The type Tipi messaggio api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TipiMessaggioApiServiceImpl extends BaseApiServiceImpl implements TipiMessaggioApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private TipoMessaggioDAO tipoMessaggioDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipiMessaggio(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(tipoMessaggioDAO.loadTipiMessaggio(), className);
    }

    /**
     * @param idTipoMessaggio idTipoMessaggio
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipoMessaggio(Long idTipoMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idTipoMessaggio);
        return getResponseList(tipoMessaggioDAO.loadTipoMessaggio(idTipoMessaggio), className);
    }

    /**
     * @param codTipoMessaggio codTipoMessaggio
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipoMessaggioByCode(String codTipoMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codTipoMessaggio);
        return getResponseList(tipoMessaggioDAO.loadTipoMessaggioByCode(codTipoMessaggio), className);
    }

    /**
     * @param tipoMessaggio   TipoMessaggioDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response saveTipoMessaggio(TipoMessaggioDTO tipoMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, tipoMessaggio);
        Long idTipoMessaggio = tipoMessaggioDAO.saveTipoMessaggio(tipoMessaggio);
        return getResponseSave(idTipoMessaggio != null ? tipoMessaggioDAO.loadTipoMessaggio(idTipoMessaggio).get(0) : null, className, "/tipi-messaggio/id-tipo-messaggio/" + idTipoMessaggio);
    }

    /**
     * @param tipoMessaggio   TipoMessaggioDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response updateTipoMessaggio(TipoMessaggioDTO tipoMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, tipoMessaggio);
        return getResponseSaveUpdate(tipoMessaggioDAO.updateTipoMessaggio(tipoMessaggio), className);
    }

    /**
     * @param idTipoMessaggio idTipoMessaggio
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response deleteTipoMessaggio(Long idTipoMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idTipoMessaggio);
        return getResponseDelete(tipoMessaggioDAO.deleteTipoMessaggio(idTipoMessaggio), className);
    }

}
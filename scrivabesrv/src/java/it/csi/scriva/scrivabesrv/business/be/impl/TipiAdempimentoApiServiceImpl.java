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

import it.csi.scriva.scrivabesrv.business.be.TipiAdempimentoApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.TipiAdempimentoService;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

/**
 * The type Tipi adempimento api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TipiAdempimentoApiServiceImpl extends BaseApiServiceImpl implements TipiAdempimentoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private TipoAdempimentoDAO tipoAdempimentoDAO;

    @Autowired
    private TipiAdempimentoService tipiAdempimentoService;

    /**
     * Load tipi adempimento response.
     *
     * @param xRequestAuth       the x request auth
     * @param xRequestId         the x request id
     * @param idAmbito           the id ambito
     * @param idCompilante       the id compilante
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAmbito          the cod ambito
     * @param securityContext    SecurityContext
     * @param httpHeaders        HttpHeaders
     * @param httpRequest        HttpServletRequest
     * @return Response response
     */
    @Override
    public Response loadTipiAdempimento(String xRequestAuth, String xRequestId, Long idAmbito, Long idCompilante, String codTipoAdempimento, String codAmbito, Boolean flgAttivo, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAmbito [" + idAmbito + "] - idCompilante [" + idCompilante + "] - codTipoAdempimento [" + codTipoAdempimento + "] - codAmbito [" + codAmbito + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
        }
        List<TipoAdempimentoExtendedDTO> listTipiAdempimenti = attoreScriva != null && ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ?
                tipiAdempimentoService.loadTipiAdempimentoByCfFunzionario(attoreScriva.getCodiceFiscale(), idAmbito, codTipoAdempimento, codAmbito, flgAttivo) :
                tipiAdempimentoService.loadTipiAdempimento(idAmbito, idCompilante, codTipoAdempimento, codAmbito, flgAttivo);
        return getResponseList(listTipiAdempimenti, className);
    }

    /**
     * @param idTipoAdempimento idTipoAdempimento
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipoAdempimento(String xRequestAuth, String xRequestId, Long idTipoAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idTipoAdempimento);
        return getResponseList(tipiAdempimentoService.loadTipoAdempimento(idTipoAdempimento), className);
    }

    /**
     * @param tipoAdempimentoDTO TipoAdempimentoExtendedDTO
     * @param securityContext    SecurityContext
     * @param httpHeaders        HttpHeaders
     * @param httpRequest        HttpServletRequest
     * @return Response
     */
    @Override
    public Response saveTipoAdempimento(TipoAdempimentoExtendedDTO tipoAdempimentoDTO, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, tipoAdempimentoDTO);
        Long id = tipoAdempimentoDAO.saveTipoAdempimento(tipoAdempimentoDTO.getDTO());
        return getResponseSave(id != null ? tipoAdempimentoDAO.loadTipoAdempimento(id).get(0) : null, className, "/procedimenti/id/" + id);
    }

    /**
     * @param tipoAdempimentoDTO TipoAdempimentoExtendedDTO
     * @param securityContext    SecurityContext
     * @param httpHeaders        HttpHeaders
     * @param httpRequest        HttpServletRequest
     * @return Response
     */
    @Override
    public Response updateTipoAdempimento(TipoAdempimentoExtendedDTO tipoAdempimentoDTO, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, tipoAdempimentoDTO);
        return getResponseSaveUpdate(tipoAdempimentoDAO.updateTipoAdempimento(tipoAdempimentoDTO.getDTO()), className);
    }

}
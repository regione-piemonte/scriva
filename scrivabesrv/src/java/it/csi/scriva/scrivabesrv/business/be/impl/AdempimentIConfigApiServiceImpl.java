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

import it.csi.scriva.scrivabesrv.business.be.AdempimentIConfigApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The type Adempiment i config api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdempimentIConfigApiServiceImpl extends BaseApiServiceImpl implements AdempimentIConfigApi {

    public static final String DES_ADEMPIMENTO = "desAdempimento [";
    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AdempimentoConfigDAO adempimentoConfigDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadAdempimentiConfig(String codTipoAdempimento, String info, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codTipoAdempimento [" + codTipoAdempimento + "] - info [" + info + "]");
        return getResponseList(adempimentoConfigDAO.loadAdempimentiConfig(codTipoAdempimento, info), className);
    }

    /**
     * @param desAdempimento  desAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadAdempimentoConfigByAdempimento(String desAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, DES_ADEMPIMENTO + desAdempimento + "]");
        return getResponseList(adempimentoConfigDAO.loadAdempimentoConfigByAdempimento(desAdempimento), className);
    }

    /**
     * @param desAdempimento  desAdempimento
     * @param infoScriva      infoScriva
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadAdempimentoConfigByAdempimentoInformazione(String desAdempimento, String infoScriva, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, DES_ADEMPIMENTO + desAdempimento + "] - infoScriva [" + infoScriva + "]");
        return getResponseList(adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazione(desAdempimento, infoScriva), className);
    }

    /**
     * @param desAdempimento  desAdempimento
     * @param infoScriva      infoScriva
     * @param chiave          chiave
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadAdempimentoConfigByAdempimentoInformazioneChiave(String desAdempimento, String infoScriva, String chiave, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, DES_ADEMPIMENTO + desAdempimento + "] - infoScriva [" + infoScriva + "] - chiave [" + chiave + "]");

         try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
        
        List<AdempimentoConfigDTO> adempimenti = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(desAdempimento, infoScriva, chiave);
        if(adempimenti != null && !adempimenti.isEmpty()) {
            String valore = adempimenti.get(0).getValore();
            if(valore != null && !valore.isEmpty() && valore.contains("COMPONENTE")) {

                adempimenti = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoComponenteChiave(desAdempimento, attoreScriva.getComponente(),chiave);
                
            }
        }
        return getResponseList(adempimenti, className);
    }

}
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

import it.csi.scriva.scrivabesrv.business.be.SoggettiApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.aaep.AAEPServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.MasterdataDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoMasterDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoSoggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.SoggettiService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.MasterdataDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.InformazioniScrivaEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.oauth2.Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.xml.rpc.ServiceException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Soggetti api.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SoggettiApiServiceImpl extends BaseApiServiceImpl implements SoggettiApi {

    private final String className = this.getClass().getSimpleName();

    private static final String CODICE_APPLICATIVO = "SCRIVA";

    @Autowired
    private AdempimentoConfigDAO adempimentoConfigDAO;

    @Autowired
    private MasterdataDAO masterdataDAO;

    @Autowired
    private SoggettoDAO soggettoDAO;

    @Autowired
    private TipoAdempimentoMasterDAO tipoAdempimentoMasterDAO;

    @Autowired
    private TipoSoggettoDAO tipoSoggettoDAO;

    @Autowired
    private AAEPServiceHelper aaepServiceHelper;

    @Autowired
    private SoggettiService soggettiService;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadSoggetti(String xRequestAuth, String xRequestId,
                                 Long idSoggetto, String codiceFiscale, String tipoSoggetto, String codAdempimento, String codiceFiscaleImpresa,
                                 SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idSoggetto [" + idSoggetto + "] - codiceFiscale [" + Util.maskForLog(codiceFiscale) + "] - tipoSoggetto [" + tipoSoggetto + "] - codAdempimento [" + codAdempimento + "] - codiceFiscaleImpresa [" + codiceFiscaleImpresa + "]\n");
        List<SoggettoExtendedDTO> listSoggetti = null;
        if (idSoggetto == null && StringUtils.isBlank(codiceFiscale) && StringUtils.isBlank(tipoSoggetto) && StringUtils.isBlank(codAdempimento) && StringUtils.isBlank(codiceFiscaleImpresa)) {
            listSoggetti = soggettoDAO.loadSoggetti();
        } else if (idSoggetto != null) {
            return loadSoggettoById(idSoggetto, securityContext, httpHeaders, httpRequest);
        } else if (StringUtils.isNotBlank(codiceFiscale) && StringUtils.isNotBlank(tipoSoggetto)) {
            return loadSoggettoByCodiceFiscaleAndTipo(codiceFiscale, tipoSoggetto, codAdempimento, securityContext, httpHeaders, httpRequest);
        } else if (StringUtils.isNotBlank(codiceFiscale) && StringUtils.isNotBlank(tipoSoggetto) && StringUtils.isNotBlank(codiceFiscaleImpresa)) {
            return loadSoggettoImpresa(codiceFiscaleImpresa, codiceFiscale, tipoSoggetto, codAdempimento, securityContext, httpHeaders, httpRequest);
        } else {
            ErrorDTO error = getErrorManager().getError("400", "", "Parametri in ingresso non validi.", null, null);
            return getResponseError(className, error);
        }
        if (listSoggetti == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        logEnd(className);
        String messageCode = listSoggetti.isEmpty() ? "I001" : null;
        return Response.ok(listSoggetti).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header("MessageCode", messageCode).build();
    }

    /**
     * @param idTipoSoggetto  idTipoSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadSoggettiByTipoSoggetto(Long idTipoSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idTipoSoggetto);
        List<SoggettoExtendedDTO> listSoggetti = soggettoDAO.loadSoggettiByTipoSoggetto(idTipoSoggetto);
        if (listSoggetti == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        logEnd(className);
        String messageCode = listSoggetti.isEmpty() ? "I001" : null;
        return Response.ok(listSoggetti).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header("MessageCode", messageCode).build();
    }

    /**
     * @param idSoggetto      idSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadSoggettoById(Long idSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idSoggetto);
        return getResponseList(soggettoDAO.loadSoggetto(idSoggetto), className);
    }

    /**
     * @param codiceFiscale   codiceFiscaleSoggetto
     * @param tipoSoggetto    tipoSoggetto
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadSoggettoByCodiceFiscaleAndTipo(String codiceFiscale, String tipoSoggetto, String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codiceFiscale [" + Util.maskForLog(codiceFiscale) + "] - tipoSoggetto [" + tipoSoggetto + "] - codAdempimento [" + codAdempimento + "]\n");
        String cf = codiceFiscale.trim();
        return getResponseList(getSoggettiFromFontiEsterne(null, cf, /*codiceFiscale,*/ tipoSoggetto, codAdempimento), className);
    }

    /**
     * @param codiceFiscaleImpresa  codiceFiscaleImpresa
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @param tipoSoggetto          tipoSoggetto
     * @param codAdempimento        codAdempimento
     * @param securityContext       SecurityContext
     * @param httpHeaders           HttpHeaders
     * @param httpRequest           HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadSoggettoImpresa(String codiceFiscaleImpresa, String codiceFiscaleSoggetto, String tipoSoggetto, String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codiceFiscaleImpresa [" + codiceFiscaleImpresa + "] - coiceFiscaleSoggetto [" + Util.maskForLog(codiceFiscaleSoggetto) + "] - tipoSoggetto [" + tipoSoggetto + "] - codAdempimento [" + codAdempimento + "]\n");
        return getResponseList(getSoggettiFromFontiEsterne(codiceFiscaleImpresa, codiceFiscaleSoggetto, tipoSoggetto, codAdempimento), className);
    }

    /**
     * @param soggetto        SoggettoExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response saveSoggetto(SoggettoExtendedDTO soggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, soggetto);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }

        ErrorDTO error = soggettiService.validateDTO(soggetto);
        if (null != error) {
            return getResponseError(className, error);
        }

        Long id = soggettiService.saveSoggetto(soggetto, attoreScriva);
        if (null == id) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        } else {
            soggetto = soggettoDAO.loadSoggetto(id).get(0);
            URI uri = null;
            try {
                uri = new URI("soggetti/id/" + id);
            } catch (URISyntaxException e) {
                logError(className, e);
            }
            logEnd(className);
            return Response.ok(soggetto).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).status(201).location(uri).build();
        }
    }

    /**
     * @param soggetto        List<SoggettoExtendedDTO>
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response updateSoggetto(SoggettoExtendedDTO soggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, soggetto);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }

        ErrorDTO error = soggettiService.validateDTO(soggetto);
        if (null != error) {
            return getResponseError(className, error);
        }

        if (StringUtils.isBlank(soggetto.getGestAttoreUpd())) {
            soggetto.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        }
        Integer res = soggettiService.updateSoggetto(soggetto, attoreScriva);

        if (res == null) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        } else if (res < 1) {
            error = getErrorManager().getError("404", "", "Elemento non aggiornato;  causa: elemento con codice fiscale [" + soggetto.getCfSoggetto() + "] non trovato", null, null);
            return getResponseError(className, error);
        } else {
            soggetto = soggettoDAO.loadSoggetto(soggetto.getIdSoggetto()).get(0);
            logEnd(className);
            return Response.ok(soggetto).status(200).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
        }
    }

    /**
     * @param uid             uidSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response deleteSoggetto(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uid);
        return getResponseDelete(soggettoDAO.deleteSoggetto(uid), className);
    }

    /**
     * @param id              idSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response deleteSoggettoById(Long id, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, id);
        return getResponseDelete(soggettoDAO.deleteSoggettoById(id), className);
    }

    /**
     * Gets soggetti from fonti esterne.
     *
     * @param codiceFiscaleImpresa  the codice fiscale impresa
     * @param codiceFiscaleSoggetto the codice fiscale soggetto
     * @param tipoSoggetto          the tipo soggetto
     * @param codAdempimento        the cod adempimento
     * @return the soggetti from fonti esterne
     */
    private List<SoggettoExtendedDTO> getSoggettiFromFontiEsterne(String codiceFiscaleImpresa, String codiceFiscaleSoggetto, String tipoSoggetto, String codAdempimento) {
        logBeginInfo(className, "codiceFiscaleImpresa [" + codiceFiscaleImpresa + "] - codiceFiscaleSoggetto [" + Util.maskForLog(codiceFiscaleSoggetto) + "] - tipoSoggetto [" + tipoSoggetto + "] - codAdempimento [" + codAdempimento + "]\n");
        List<SoggettoExtendedDTO> result = null;
        String tipoInfo = InformazioniScrivaEnum.valueOf(tipoSoggetto.toUpperCase()).name();
        try {
            if (StringUtils.isNotBlank(codAdempimento)) {
                List<AdempimentoConfigDTO> adempimentoConfigList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(codAdempimento, tipoSoggetto, Constants.CONFIG_KEY_FONTE_RICERCA);
                for (AdempimentoConfigDTO dto : adempimentoConfigList) {
                    String masterData = dto.getValore().toUpperCase();
                    switch (masterData) {
                        case "AAEP":
                            logInfo(className, "Searching in " + masterData);
                            if (tipoInfo.equalsIgnoreCase(InformazioniScrivaEnum.PF.name()) && codiceFiscaleImpresa != null) {
                                try {
                                    result = new ArrayList<>();
                                    result = aaepServiceHelper.loadSoggettoFromAAEP(null, codiceFiscaleImpresa, codiceFiscaleSoggetto, Boolean.FALSE);
                                } catch (ServiceException e) {
                                    logError(className, "Searching in AAEP failed : " + e);
                                }
                            } else if (tipoInfo.equalsIgnoreCase(InformazioniScrivaEnum.PG.name()) || tipoInfo.equalsIgnoreCase(InformazioniScrivaEnum.PB.name())) {
                                try {
                                    SoggettoExtendedDTO soggetto = aaepServiceHelper.cercaImpresaByCodiceFiscale(codiceFiscaleImpresa != null ? codiceFiscaleImpresa : codiceFiscaleSoggetto);
                                    List<TipoSoggettoDTO> tipoSoggettoList = tipoSoggettoDAO.loadTipoSoggettoByCode(tipoInfo);
                                    soggetto.setTipoSoggetto(tipoSoggettoList != null && !tipoSoggettoList.isEmpty() ? tipoSoggettoList.get(0) : null);
                                    MasterdataDTO masterdataDTO = masterdataDAO.loadByCode(masterData);
                                    Long idMasterData = masterdataDTO != null ? masterdataDTO.getIdMasterdata() : null;
                                    soggetto.setIdMasterdata(idMasterData);
                                    soggetto.setIdMasterdataOrigine(idMasterData);
                                    result = new ArrayList<>();
                                    result.add(soggetto);
                                } catch (ServiceException e) {
                                    logError(className, "Searching in " + masterData + " failed : " + e);
                                }
                            }
                            break;
                        default:
                            logInfo(className, "Searching in " + masterData);
                            result = soggettoDAO.loadSoggettoByCodiceFiscaleAndTipo(codiceFiscaleSoggetto, tipoSoggetto);
                            break;
                    }
                    if (result != null && !result.isEmpty()) {
                        break;
                    }
                }
            } else {
                result = soggettoDAO.loadSoggettoByCodiceFiscaleAndTipo(codiceFiscaleSoggetto, tipoSoggetto);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

}
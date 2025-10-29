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

import it.csi.scriva.scrivabesrv.business.be.SoggettiIstanzaApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.RecapitoAlternativoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.RuoloCompilanteDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.GruppoSoggettoService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RecapitoAlternativoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RuoloCompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ProfiloAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipoSoggettoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.IstanzaAttoreManager;
import it.csi.scriva.scrivabesrv.util.manager.JsonDataManager;
import it.csi.scriva.scrivabesrv.util.validation.ValidationUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Soggetti istanza api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)

public class SoggettiIstanzaApiServiceImpl extends BaseApiServiceImpl implements SoggettiIstanzaApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private IstanzaDAO istanzaDAO;

    @Autowired
    private IstanzaAttoreDAO istanzaAttoreDAO;

    @Autowired
    private RecapitoAlternativoDAO recapitoAlternativoDAO;

    @Autowired
    private SoggettoIstanzaDAO soggettoIstanzaDAO;

    @Autowired
    private IstanzaAttoreManager istanzaAttoreManager;

    @Autowired
    private JsonDataManager jsonDataManager;

    @Autowired
    private GruppoSoggettoService gruppoSoggettoService;

    @Autowired
    private IstanzaService istanzaService;

    @Autowired
    private RuoloCompilanteDAO ruoloCompilanteDAO;

    @Autowired
    private ConfigurazioneDAO configurazioneDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadSoggettiIstanza(String xRequestAuth, String xRequestId,
                                        Long idIstanza,
                                        SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = idIstanza != null ? soggettoIstanzaDAO.loadSoggettoIstanzaByIdIstanza(idIstanza) : soggettoIstanzaDAO.loadSoggettiIstanze();
        addRecapitiAlternativi(soggettoIstanzaList);
        return getResponseList(soggettoIstanzaList, className);
    }

    /**
     * @param idSoggettoIstanza idSoggettoIstanza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadSoggettoIstanza(Long idSoggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idSoggettoIstanza);
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanza(idSoggettoIstanza);
        if (soggettoIstanzaList == null || soggettoIstanzaList.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        Response response = setAttoreRight(httpHeaders, soggettoIstanzaList.get(0).getIdIstanza());
        if (response != null) {
            return response;
        }
        addRecapitiAlternativi(soggettoIstanzaList);
        return getResponseList(soggettoIstanzaList, className);
    }

    /**
     * @param idSoggettoPadre idSoggettoPadre
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadSoggettoIstanzaByIdSoggettoPadre(Long idSoggettoPadre, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idSoggettoPadre);
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanzaByIdSoggettoPadre(idSoggettoPadre);
        if (soggettoIstanzaList == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        Response response = setAttoreRight(httpHeaders, soggettoIstanzaList.get(0).getIdIstanza());
        if (response != null) {
            return response;
        }
        addRecapitiAlternativi(soggettoIstanzaList);
        return getResponseList(soggettoIstanzaList, className);
    }

    /**
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadSoggettoIstanzaByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanzaByIdIstanza(idIstanza);
        addRecapitiAlternativi(soggettoIstanzaList);
        return getResponseList(soggettoIstanzaList, className);
    }

    /**
     * @param codiceFiscale   codiceFiscale
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadSoggettiIstanzaByCodiceFiscaleSoggetto(String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codiceFiscale);
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanzaByCodiceFiscaleSoggetto(codiceFiscale);
        if (soggettoIstanzaList == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        Response response = setAttoreRight(httpHeaders, soggettoIstanzaList.get(0).getIdIstanza());
        if (response != null) {
            return response;
        }
        addRecapitiAlternativi(soggettoIstanzaList);
        return getResponseList(soggettoIstanzaList, className);
    }

    /**
     * @param soggettoIstanza SoggettoIstanzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response saveSoggettoIstanza(SoggettoIstanzaExtendedDTO soggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, soggettoIstanza);
        Long idIstanza = soggettoIstanza.getIdIstanza();
        Response response = setAttoreRight(httpHeaders, idIstanza, Boolean.TRUE);
        if (response != null) {
            return response;
        }

        ErrorDTO error = this.validateDTO(soggettoIstanza);
        if (null != error) {
            return getResponseError(className, error);
        }

        if (StringUtils.isBlank(soggettoIstanza.getGestAttoreIns())) {
            soggettoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
        }

        IstanzaExtendedDTO istanza = istanzaService.getIstanza(soggettoIstanza.getIdIstanza());
        Long idIstanzaAttoreCompilante = ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ?
                istanzaAttoreManager.getIdIstanzaAttoreCompilanteForSoggettoIstanza(Constants.CF_COMPILANTE_FITTIZIO_BO, idIstanza, ComponenteAppEnum.FO, attoreScriva.getCodiceFiscale()) :
                istanzaAttoreManager.getIdIstanzaAttoreCompilanteForSoggettoIstanza(attoreScriva.getCodiceFiscale(), idIstanza, ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
        /*
        Long idIstanzaAttoreRichiedente = TipoSoggettoEnum.PF.name().equalsIgnoreCase(soggettoIstanza.getTipoSoggetto().getCodiceTipoSoggetto()) &&
                (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) &&
                        istanza.getAdempimento().getIndVisibile().contains(ComponenteAppEnum.FO.name())) ?
                istanzaAttoreManager.getIdIstanzaAttoreRichiedente(soggettoIstanza.getCfSoggetto(), soggettoIstanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale()) : null;
        */
        // SCRIVA - 1185
        if (TipoSoggettoEnum.PF.name().equalsIgnoreCase(soggettoIstanza.getTipoSoggetto().getCodiceTipoSoggetto())) {
            istanzaAttoreManager.getIdIstanzaAttoreRichiedente(soggettoIstanza.getCfSoggetto(), soggettoIstanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
        }

        soggettoIstanza.setIdIstanzaAttore(idIstanzaAttoreCompilante);
        soggettoIstanza.setIdFunzionario(ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
        Long id = soggettoIstanzaDAO.saveSoggettoIstanza(soggettoIstanza.getDTO());
        Long idIstanzaAttore = ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null;
        // L'id_istanza_attore_owner della T_ISTANZA deve essere aggiornato solo in fase di creazione istanza (POST /istanze) e non più modficato. JIRA SCRIVA-1450
        Integer numIstanzeUpdated = istanzaDAO.updateIstanzaAttore(idIstanza, idIstanzaAttoreCompilante, idIstanzaAttore, attoreScriva.getCodiceFiscale());
        

        //if (id < 0 || numIstanzeUpdated < 1) { JIRA SCRIVA-1450
        if (id < 0 ) {    
            error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        } else {
            /*
            List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanzaByIdIstanza(idIstanza);
            jsonDataManager.updateJsonDataSoggettoIstanza(idIstanza, soggettoIstanzaList);
             */
            if (soggettoIstanza.getGruppoSoggetto() != null) {
                soggettoIstanza.getGruppoSoggetto().setIdIstanzaAttore(idIstanzaAttoreCompilante);
                gruppoSoggettoService.addSoggettoIstanzaToGruppo(id, soggettoIstanza.getGruppoSoggetto(), soggettoIstanza.getFlgCapogruppo(), attoreScriva);
            }
            List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanza(id);
            addRecapitiAlternativi(soggettoIstanzaList);
            soggettoIstanza = soggettoIstanzaList.get(0);

            URI uri = null;
            try {
                uri = new URI("soggetti_istanza/" + id);
            } catch (URISyntaxException e) {
                logError(className, e);
            }
            updateIstanzaPraticaTimestampAttore(idIstanza, attoreScriva);
            logEnd(className);
            return Response.ok(soggettoIstanza).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva.getProfiloAppIstanza()).status(201).location(uri).build();
        }
    }

    /**
     * @param soggettoIstanza SoggettoIstanzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response updateSoggettoIstanza(SoggettoIstanzaExtendedDTO soggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, soggettoIstanza);
        Long idIstanza = soggettoIstanza.getIdIstanza();
        Response response = setAttoreRight(httpHeaders, idIstanza, Boolean.TRUE);
        if (response != null) {
            return response;
        }

        ErrorDTO error = this.validateDTO(soggettoIstanza);
        if (null != error) {
            return getResponseError(className, error);
        }

        if (StringUtils.isBlank(soggettoIstanza.getGestAttoreUpd())) {
            soggettoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        }
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanza(soggettoIstanza.getIdSoggettoIstanza());
        if (CollectionUtils.isNotEmpty(soggettoIstanzaList) && !soggettoIstanzaList.get(0).getCfSoggetto().equalsIgnoreCase(soggettoIstanza.getCfSoggetto())) {
            updateIstanzaAttore(soggettoIstanzaList, soggettoIstanza.getCfSoggetto(), attoreScriva);
            /*
            if (deleteIstanzaAttore(soggettoIstanzaList) < 1) {
                error = getErrorManager().getError("500", "E100", null, null, null);
                return getResponseError(className, error);
            }
            */
        }
        //Long idIstanzaAttoreCompilante = istanzaAttoreManager.getIdIstanzaAttoreCompilante(attoreScriva.getCodiceFiscale(), idIstanza, ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
        //Long idIstanzaAttoreRichiedente = TipoSoggettoEnum.PF.name().equalsIgnoreCase(soggettoIstanza.getTipoSoggetto().getCodiceTipoSoggetto()) ? istanzaAttoreManager.getIdIstanzaAttoreRichiedente(soggettoIstanza.getCfSoggetto(), idIstanza, ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale()) : null;

        IstanzaExtendedDTO istanza = istanzaService.getIstanza(soggettoIstanza.getIdIstanza());
        Long idIstanzaAttoreCompilante = ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ?
                istanzaAttoreManager.getIdIstanzaAttoreCompilanteForSoggettoIstanza(Constants.CF_COMPILANTE_FITTIZIO_BO, idIstanza, ComponenteAppEnum.FO, attoreScriva.getCodiceFiscale()) :
                istanzaAttoreManager.getIdIstanzaAttoreCompilanteForSoggettoIstanza(attoreScriva.getCodiceFiscale(), idIstanza, ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
        /*
        Long idIstanzaAttoreRichiedente = TipoSoggettoEnum.PF.name().equalsIgnoreCase(soggettoIstanza.getTipoSoggetto().getCodiceTipoSoggetto()) &&
                (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) && istanza.getAdempimento().getIndVisibile().contains(ComponenteAppEnum.FO.name())) ?
                istanzaAttoreManager.getIdIstanzaAttoreRichiedente(soggettoIstanza.getCfSoggetto(), soggettoIstanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale()) : null;
        */
        // SCRIVA - 1185
        if (TipoSoggettoEnum.PF.name().equalsIgnoreCase(soggettoIstanza.getTipoSoggetto().getCodiceTipoSoggetto())) {
            istanzaAttoreManager.getIdIstanzaAttoreRichiedente(soggettoIstanza.getCfSoggetto(), soggettoIstanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
        }

        soggettoIstanza.setIdIstanzaAttore(idIstanzaAttoreCompilante);
        soggettoIstanza.setIdFunzionario(ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
        Integer res = soggettoIstanzaDAO.updateSoggettoIstanza(soggettoIstanza.getDTO());
        Long idIstanzaAttore = ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null;
        // L'id_istanza_attore_owner della T_ISTANZA deve essere aggiornato solo in fase di creazione istanza (POST /istanze) e non più modficato. JIRA SCRIVA-1450
        // istanzaDAO.updateIstanzaAttore(idIstanza, idIstanzaAttoreCompilante, idIstanzaAttore, attoreScriva.getCodiceFiscale());

        if (null == res) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        } else if (res < 1) {
            error = getErrorManager().getError("404", "", "Errore nell'aggiornamento dell'elemento; causa: elemento con id [" + soggettoIstanza.getIdSoggettoIstanza() + "] per il soggetto con cf [" + soggettoIstanza.getCfSoggetto() + "] non trovato", null, null);
            return getResponseError(className, error);
        } else {
            // Eventuale aggiornamento dei dati del gruppo
            gruppoSoggettoService.updateSoggettoIstanzaGruppo(soggettoIstanza.getIdSoggettoIstanza(), soggettoIstanza.getGruppoSoggetto(), soggettoIstanza.getFlgCapogruppo(), attoreScriva);

            soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanza(soggettoIstanza.getIdSoggettoIstanza());
            addRecapitiAlternativi(soggettoIstanzaList);
            soggettoIstanza = soggettoIstanzaList.get(0);
            updateIstanzaPraticaTimestampAttore(idIstanza, attoreScriva);
            logEnd(className);
            return Response.ok(soggettoIstanza).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva.getProfiloAppIstanza()).status(200).build();
        }
    }

    /**
     * @param uid             uidSoggettoIStanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response deleteSoggettoIstanza(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uid);
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanzaByUid(uid);
        if (soggettoIstanzaList == null || soggettoIstanzaList.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        Long idIstanza = soggettoIstanzaList.get(0).getIdIstanza();
        Long idSoggettoIstanza = soggettoIstanzaList.get(0).getIdSoggettoIstanza();
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }

        deleteIstanzaAttore(soggettoIstanzaList);
        gruppoSoggettoService.removeSoggettoIstanzaFromGruppo(idSoggettoIstanza);
        removeRecapitiAlternativi(idSoggettoIstanza);
        Integer res = soggettoIstanzaDAO.deleteSoggettoIstanza(uid);

        if (res == null) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        } else if (res < 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            ErrorDTO error = getErrorManager().getError("404", "", "Errore nella cancellazione dell'elemento; causa: elemento con uid [" + uid + "] non trovato", null, null);
            return getResponseError(className, error);
        } else {
            updateIstanzaPraticaTimestampAttore(idIstanza, attoreScriva);
            logEnd(className);
            return Response.noContent().build();
        }
    }

    /**
     * @param idSoggettoIstanza idSoggettoIstanza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response deleteSoggettoIstanzaById(Long idSoggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idSoggettoIstanza);
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanza(idSoggettoIstanza);
        if (soggettoIstanzaList == null || !soggettoIstanzaList.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        Long idIstanza = soggettoIstanzaList.get(0).getIdIstanza();
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }

        if (deleteIstanzaAttore(soggettoIstanzaList) < 1) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        return getResponseDelete(soggettoIstanzaDAO.deleteSoggettoIstanzaById(idSoggettoIstanza), className);
    }

    /**
     * Load recapiti alternativi by id soggetto istanza response.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response loadRecapitiAlternativiByIdSoggettoIstanza(Long idSoggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idSoggettoIstanza);
        return getResponseList(recapitoAlternativoDAO.loadRecapitoAlternativoByIdSoggettoIstanza(idSoggettoIstanza), className);
    }

    /**
     * Save recapito alternativo response.
     *
     * @param recapitoAlternativo the recapito alternativo
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @Override
    @Transactional
    public Response saveRecapitoAlternativo(RecapitoAlternativoExtendedDTO recapitoAlternativo, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, recapitoAlternativo);
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanza(recapitoAlternativo.getIdSoggettoIstanza());
        if (soggettoIstanzaList == null || soggettoIstanzaList.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        SoggettoIstanzaDTO soggettoIstanza = soggettoIstanzaList.get(0).getDTO();
        // Verifica permessi di scrittura su istanza
        Response response = setAttoreRight(httpHeaders, soggettoIstanza.getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }
        recapitoAlternativo.setIdFunzionario(ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
        recapitoAlternativo.setIdIstanzaAttore(!ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
        recapitoAlternativo.setGestAttoreIns(attoreScriva.getCodiceFiscale());

        Long idRecapitoAlternativo = recapitoAlternativoDAO.saveRecapitoAlternativo(recapitoAlternativo.getDTO());
        if (idRecapitoAlternativo == null) {
            return getResponseSaveUpdate(null, className);
        }
        else
        {
            updateIstanzaPraticaTimestampAttore(soggettoIstanza.getIdIstanza(), attoreScriva);
        }
        List<RecapitoAlternativoExtendedDTO> recapitoAlternativoList = recapitoAlternativoDAO.loadRecapitoAlternativoById(idRecapitoAlternativo);
        return getResponseSave(recapitoAlternativoList != null && !recapitoAlternativoList.isEmpty() ? recapitoAlternativoList.get(0) : recapitoAlternativoList, className, "soggetti-istanza/recapiti-alternativi/id/" + recapitoAlternativo.getIdSoggettoIstanza());
    }

    /**
     * Update recapito alternativo response.
     *
     * @param recapitoAlternativo the recapito alternativo
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @Override
    @Transactional
    public Response updateRecapitoAlternativo(RecapitoAlternativoExtendedDTO recapitoAlternativo, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, recapitoAlternativo);
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanza(recapitoAlternativo.getIdSoggettoIstanza());
        if (soggettoIstanzaList == null || soggettoIstanzaList.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        SoggettoIstanzaDTO soggettoIstanza = soggettoIstanzaList.get(0).getDTO();
        // Verifica permessi di scrittura su istanza
        Response response = setAttoreRight(httpHeaders, soggettoIstanza.getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }

        recapitoAlternativo.setIdFunzionario(ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
        recapitoAlternativo.setIdIstanzaAttore(!ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
        recapitoAlternativo.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        Integer res = recapitoAlternativoDAO.updateRecapitoAlternativo(recapitoAlternativo.getDTO());
        if (res == null || res < 1) {
            return getResponseSaveUpdate(null, className);
        }
        else
        {
            updateIstanzaPraticaTimestampAttore(soggettoIstanza.getIdIstanza(), attoreScriva);
        }
        List<RecapitoAlternativoExtendedDTO> recapitoAlternativoList = recapitoAlternativoDAO.loadRecapitoAlternativoById(recapitoAlternativo.getIdRecapitoAlternativo());
        return getResponseSaveUpdate((recapitoAlternativoList != null && !recapitoAlternativoList.isEmpty() ? recapitoAlternativoList.get(0) : recapitoAlternativoList), className);
    }

    /**
     * Delete recapito alternativo response.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    @Transactional
    public Response deleteRecapitoAlternativo(Long idSoggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idSoggettoIstanza);
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanza(idSoggettoIstanza);
        if (soggettoIstanzaList == null || soggettoIstanzaList.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        SoggettoIstanzaDTO soggettoIstanza = soggettoIstanzaList.get(0).getDTO();
        // Verifica permessi di scrittura su istanza
        Response response = setAttoreRight(httpHeaders, soggettoIstanza.getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }

        Integer resultDelete = removeRecapitiAlternativi(idSoggettoIstanza);

        if (resultDelete != null && resultDelete > 0) {
          
            updateIstanzaPraticaTimestampAttore(soggettoIstanza.getIdIstanza(), attoreScriva);
        }

        return getResponseDelete(resultDelete, className);
    }


    /**
     * @param soggettoIstanzaList List<SoggettoIstanzaExtendedDTO>
     * @return Integer
     */

    private ErrorDTO validateDTO(SoggettoIstanzaExtendedDTO soggettoIstanza) {
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();

        if (null == soggettoIstanza.getSoggetto() || (null != soggettoIstanza.getSoggetto() && null == soggettoIstanza.getSoggetto().getIdSoggetto())) {
            details.put("soggetto", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (null == soggettoIstanza.getTipoSoggetto() || (null != soggettoIstanza.getTipoSoggetto() && null == soggettoIstanza.getTipoSoggetto().getIdTipoSoggetto())) {
            details.put("tipo_soggetto", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (null == soggettoIstanza.getIdIstanza()) {
            details.put("istanza", ValidationResultEnum.MANDATORY.getDescription());
        }

        if ((null != soggettoIstanza.getTipoSoggetto() && "PG".equalsIgnoreCase(soggettoIstanza.getTipoSoggetto().getCodiceTipoSoggetto())) &&
                (null == soggettoIstanza.getTipoNaturaGiuridica() || (null != soggettoIstanza.getTipoNaturaGiuridica() && null == soggettoIstanza.getTipoNaturaGiuridica().getIdTipoNaturaGiuridica()))) {
            details.put("tipo_natura_giuridica", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (null == soggettoIstanza.getRuoloCompilante() || (null != soggettoIstanza.getRuoloCompilante() && null == soggettoIstanza.getRuoloCompilante().getIdRuoloCompilante())) {
        	Long idIstanza = soggettoIstanza.getIdIstanza();
        	
        	List<RuoloCompilanteDTO> ruoloComp = new ArrayList<RuoloCompilanteDTO>();
            ruoloComp = ruoloCompilanteDAO.loadRuoloCompilanteByAttore(attoreScriva.getComponente(), idIstanza);

            

            if (ruoloComp != null && ruoloComp.size() > 0 && ruoloComp.get(0).getIdRuoloCompilante() != null)
                soggettoIstanza.setRuoloCompilante(ruoloComp.get(0));
            else {
                //details.put("ruolo_compilante", ValidationResultEnum.MANDATORY.getDescription());

                error = getErrorManager().getError("500", "E076", null, null, null);
                setPlaceHolderValues(error, idIstanza);
                return error;
            }
        }

        if (null == soggettoIstanza.getIdMasterdata()) {
            details.put("id_masterdata", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (null == soggettoIstanza.getIdMasterdataOrigine()) {
            details.put("id_masterdata_origine", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (StringUtils.isBlank(soggettoIstanza.getCfSoggetto())) {
            details.put("cf_soggetto", ValidationResultEnum.MANDATORY.getDescription());
        } else {
            String res = null;
            if (TipoSoggettoEnum.PG.name().equalsIgnoreCase(soggettoIstanza.getTipoSoggetto().getCodiceTipoSoggetto()) ||
                    TipoSoggettoEnum.PB.name().equalsIgnoreCase(soggettoIstanza.getTipoSoggetto().getCodiceTipoSoggetto())
            ) {
                if (soggettoIstanza.getCfSoggetto().trim().length() == 11) {
                    res = ValidationUtil.isValidPIva(soggettoIstanza.getCfSoggetto());
                } else {
                    res = ValidationUtil.validateCF(soggettoIstanza.getCfSoggetto());
                }
            } else {
                res = ValidationUtil.validateCF(soggettoIstanza.getCfSoggetto());
            }
            if (!res.equals(ValidationResultEnum.VALID.name())) {
                details.put("cf_soggetto", ValidationResultEnum.valueOf(res).getDescription());
            }
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getPartitaIvaSoggetto())) {
            String res = ValidationUtil.isValidPIva(soggettoIstanza.getPartitaIvaSoggetto());
            if (!res.equals(ValidationResultEnum.VALID.name())) {
                details.put("partita_iva_soggetto", ValidationResultEnum.valueOf(res).getDescription());
            }
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getNome()) && soggettoIstanza.getNome().length() > 100) {
            details.put("nome", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getCognome()) && soggettoIstanza.getCognome().length() > 100) {
            details.put("cognome", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getIndirizzoSoggetto()) && soggettoIstanza.getIndirizzoSoggetto().length() > 100) {
            details.put("indirizzo_soggetto", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getNumCivicoIndirizzo()) && soggettoIstanza.getNumCivicoIndirizzo().length() > 30) {
            details.put("num_civico_indirizzo", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getDesEmail())) {
            if (soggettoIstanza.getDesEmail().length() > 100) {
                details.put("des_email", ValidationResultEnum.INVALID_LENGTH.getDescription());
            } else if (!ValidationUtil.isValidEmail(soggettoIstanza.getDesEmail())) {
                details.put("des_email", ValidationResultEnum.INVALID_EMAIL.getDescription());
            }
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getDesPec())) {
            if (soggettoIstanza.getDesPec().length() > 100) {
                details.put("des_pec", ValidationResultEnum.INVALID_LENGTH.getDescription());
            } else if (!ValidationUtil.isValidEmail(soggettoIstanza.getDesPec())) {
                details.put("des_pec", ValidationResultEnum.INVALID_EMAIL.getDescription());
            }
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getNumTelefono()) && soggettoIstanza.getNumTelefono().length() > 25) {
            details.put("num_telefono", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getDenProvinciaCCIAA()) && soggettoIstanza.getDenProvinciaCCIAA().length() > 20) {
            details.put("den_provincia_cciaa", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getDenNumeroCCIAA()) && soggettoIstanza.getDenNumeroCCIAA().length() > 20) {
            details.put("den_numero_cciaa", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getCittaEsteraNascita()) && soggettoIstanza.getCittaEsteraNascita().length() > 100) {
            details.put("citta_estera_nascita", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getStatoEsteroNascita()) && soggettoIstanza.getStatoEsteroNascita().length() > 100) {
            details.put("stato_estero_nascita", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggettoIstanza.getCittaEsteraResidenza()) && soggettoIstanza.getCittaEsteraResidenza().length() > 100) {
            details.put("citta_estera_residenza", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (!details.isEmpty()) {
            error = getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
        }

        return error;
    }

    /**
     * Delete istanza attore integer.
     *
     * @param soggettoIstanzaList the soggetto istanza list
     * @return the integer
     */
    private Integer deleteIstanzaAttore(List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList) {
        Integer numRecordDeleted = 0;
        SoggettoIstanzaExtendedDTO soggettoIstanza = soggettoIstanzaList != null && !soggettoIstanzaList.isEmpty() ? soggettoIstanzaList.get(0) : null;
        if (soggettoIstanza != null) {
            numRecordDeleted = 1;
            if (TipoSoggettoEnum.PF.name().equalsIgnoreCase(soggettoIstanza.getTipoSoggetto().getCodiceTipoSoggetto())) {
                soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanzaByIdIstanzaAndCodiceFiscaleSoggetto(soggettoIstanza.getIdIstanza(), soggettoIstanza.getCfSoggetto());
                if (soggettoIstanzaList != null && soggettoIstanzaList.size() == 1) {
                    //JIRA SCRIVA-1450 l'id_istanza_attore_owner della T_ISTANZA deve essere aggiornato solo in fase di creazione istanza (POST /istanze) e non più modficato.
                    istanzaAttoreDAO.deleteRefIstanzaAttoreByIdIstanzaCFAttoreCodProfApp(soggettoIstanza.getIdIstanza(), soggettoIstanza.getCfSoggetto(), ProfiloAppEnum.RICHIEDENTE.name()); 
                    numRecordDeleted = istanzaAttoreDAO.deleteIstanzaAttoreByIdIstanzaCFAttoreCodProfApp(soggettoIstanza.getIdIstanza(), soggettoIstanza.getCfSoggetto(), ProfiloAppEnum.RICHIEDENTE.name());
                }
            }
        }
        return numRecordDeleted;
    }

    /**
     * Update istanza attore integer.
     *
     * @param soggettoIstanzaList the soggetto istanza list
     * @return the integer
     */
    private Integer updateIstanzaAttore(List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList, String cfAttoreNew, AttoreScriva attoreScriva) {
        Integer numUpdated = 0;
        SoggettoIstanzaExtendedDTO soggettoIstanza = soggettoIstanzaList != null && !soggettoIstanzaList.isEmpty() ? soggettoIstanzaList.get(0) : null;
        if (soggettoIstanza != null) {
            numUpdated = 1;
            if (TipoSoggettoEnum.PF.name().equalsIgnoreCase(soggettoIstanza.getTipoSoggetto().getCodiceTipoSoggetto())) {
                soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanzaByIdIstanzaAndCodiceFiscaleSoggetto(soggettoIstanza.getIdIstanza(), soggettoIstanza.getCfSoggetto());
                if (soggettoIstanzaList != null && soggettoIstanzaList.size() == 1) {
                    numUpdated = istanzaAttoreDAO.updateIstanzaAttoreByIdIstanzaCFAttoreCodProfApp(soggettoIstanza.getIdIstanza(), soggettoIstanza.getCfSoggetto(), ProfiloAppEnum.RICHIEDENTE.name(), cfAttoreNew, attoreScriva.getCodiceFiscale());
                }
            }
        }
        return numUpdated;
    }

    private void addRecapitiAlternativi(List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList) {
        if (soggettoIstanzaList != null && !soggettoIstanzaList.isEmpty()) {
            soggettoIstanzaList.forEach(soggettoIstanza -> {
                List<RecapitoAlternativoExtendedDTO> recapitoAlternativoList = recapitoAlternativoDAO.loadRecapitoAlternativoByIdSoggettoIstanza(soggettoIstanza.getIdSoggettoIstanza());
                soggettoIstanza.setRecapitoAlternativo(recapitoAlternativoList);
            });
        }
    }


    private Integer removeRecapitiAlternativi(Long idSoggettoIstanza) {
        return recapitoAlternativoDAO.deleteRecapitoAlternativoByIdSoggettoIstanza(idSoggettoIstanza);
    }

    /**
     * Sets place holder values.
     *
     * @param error     the error
     * @param idIstanza the id istanza
     */
    private void setPlaceHolderValues(ErrorDTO error, Long idIstanza) {
        logBegin(className);
        if (error != null) {
            String errorTitle = error.getTitle();
            if (StringUtils.isNotBlank(errorTitle)) {
                List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
                if (CollectionUtils.isNotEmpty(istanzaList)) {
                    List<ConfigurazioneDTO> configurazioneList = configurazioneDAO.loadConfigByKey("SCRIVA_EMAIL_ASSISTENZA_" + istanzaList.get(0).getAdempimento().getTipoAdempimento().getCodTipoAdempimento());
                    if (CollectionUtils.isNotEmpty(configurazioneList)) {
                        errorTitle = errorTitle.replace("{PH_SCRIVA_EMAIL_ASSISTENZA_<COD_TIPO_ADEMPIMENTO>}", configurazioneList.get(0).getValore());
                    }
                }
                errorTitle = errorTitle.replace("{PH_SCRIVA_EMAIL_ASSISTENZA_<COD_TIPO_ADEMPIMENTO>}", "");
                error.setTitle(errorTitle);
            }
        }
    }

}
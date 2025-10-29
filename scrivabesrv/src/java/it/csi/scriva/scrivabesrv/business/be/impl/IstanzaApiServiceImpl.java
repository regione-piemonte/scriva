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

import it.csi.scriva.scrivabesrv.business.be.IstanzaApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaResponsabiliDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaStatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaTemplateQuadroDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ReferenteIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoIstanzaAdempimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaEventoService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaService;
import it.csi.scriva.scrivabesrv.business.be.service.TemplateService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaStatoDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloOggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ReferenteIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaAdempimentoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoOggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipoEventoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipologiaAllegatoEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.AllegatiManager;
import it.csi.scriva.scrivabesrv.util.manager.IstanzaAttoreManager;
import it.csi.scriva.scrivabesrv.util.manager.JsonDataManager;
import it.csi.scriva.scrivabesrv.util.manager.MandatoryInfoIstanzaManager;
import it.csi.scriva.scrivabesrv.util.templating.TemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The type Istanza api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IstanzaApiServiceImpl extends BaseApiServiceImpl implements IstanzaApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    TemplateUtil templateUtil;

    @Autowired
    private AdempimentoDAO adempimentoDAO;

    @Autowired
    private AllegatoIstanzaDAO allegatoIstanzaDAO;

    @Autowired
    private IstanzaDAO istanzaDAO;

    @Autowired
    private IstanzaAttoreDAO istanzaAttoreDAO;

    @Autowired
    private IstanzaStatoDAO istanzaStatoDAO;

    @Autowired
    private IstanzaTemplateQuadroDAO istanzaTemplateQuadroDAO;

    @Autowired
    private ReferenteIstanzaDAO referenteIstanzaDAO;

    @Autowired
    private StatoIstanzaAdempimentoDAO statoIstanzaAdempimentoDAO;

    @Autowired
    private StatoIstanzaDAO statoIstanzaDAO;

    @Autowired
    private IstanzaService istanzaService;

    @Autowired
    private IstanzaEventoService istanzaEventoService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private AllegatiManager allegatiManager;

    @Autowired
    private JsonDataManager jsonDataManager;

    @Autowired
    private IstanzaAttoreManager istanzaAttoreManager;

    @Autowired
    private MandatoryInfoIstanzaManager mandatoryInfoIstanzaManager;

    @Autowired
    private IstanzaResponsabiliDAO istanzaResponsabiliDAO;
    
    @Autowired
    private ConfigurazioneDAO configurazioneDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadIstanze(String codIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(StringUtils.isNotBlank(codIstanza) ? istanzaDAO.loadIstanza(codIstanza) : istanzaDAO.loadIstanze(), className);
    }

    /**
     * @param cfCompilante    codice fiscale compilante
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadIstanzeByCfCompilante(String cfCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, cfCompilante);
        return getResponseList(istanzaDAO.loadIstanzeByCfCompilante(cfCompilante), className);
    }

    /**
     * @param idCompilante    idCompilante
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadIstanzeByIdCompilante(Long idCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idCompilante);
        return getResponseList(istanzaDAO.loadIstanzeByIdCompilante(idCompilante), className);
    }

    /**
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        

        
        if (null == istanzaList) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, error);
            return Response.serverError().entity(error).status(500).build();
        }
        if (istanzaList.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("404", "", "Elemento non trovato con id [" + idIstanza + "]", null, null);
            logError(className, error);
            return Response.serverError().entity(error).status(404).build();
        }

        
        for (IstanzaExtendedDTO istanzaExtendedDTO : istanzaList) {
    		istanzaExtendedDTO.setTipiAdempimentoOggettoApp(istanzaService.getTipoAdempimentoOggAppIstanza(idIstanza, attoreScriva));
			istanzaExtendedDTO.setProfiloOggettoAppList(getProfiloAppExtended(idIstanza, attoreScriva));
		}

        logEnd(className);

        return Response.ok(istanzaList)
                .header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY)
                .header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva.getProfiloAppIstanza())
                .header(Constants.HEADER_PROFILI_APP, getProfiloAppExtendedJson(idIstanza, attoreScriva))
                //.header(Constants.HEADER_TIPO_ADEMPI_OGG_APP, istanzaService.getTipoAdempimentoOggAppIstanzaJson(idIstanza, attoreScriva))
                .build();
    }

    /**
     * Load json data istanza response.
     *
     * @param idIstanza       the id istanza
     * @param codTipoQuadro   the cod tipo quadro
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadJsonDataIstanza(Long idIstanza, String codTipoQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ParseException {
        logBegin(className);
        return Response.ok(jsonDataManager.generateJsonDataFromConfig(idIstanza, codTipoQuadro).toString()).build();
    }

    /**
     * @param istanza         IstanzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response saveIstanza(IstanzaExtendedDTO istanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, istanza);
        Response response = setAttoreRight(httpHeaders, null, Boolean.FALSE);
        if (response != null) {
            return response;
        }

        ErrorDTO error = istanzaService.validateDTO(istanza, Boolean.FALSE);
        if (null != error) {
            logError(className, error);
            return getResponseError(className, error);
        }
        List<AdempimentoExtendedDTO> adempimentoList = adempimentoDAO.loadAdempimentoById(istanza.getAdempimento().getIdAdempimento());
        if (StringUtils.isBlank(istanza.getGestAttoreIns())) {
            istanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
        }
        if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
            istanza.setIdFunzionario(attoreScriva.getIdAttore());
        }
        Long idIstanza = istanzaDAO.saveIstanza(istanza.getDTO(), adempimentoList.get(0).getTipoAdempimento().getCodTipoAdempimento(), adempimentoList.get(0).getCodAdempimento(), Boolean.FALSE, attoreScriva.getComponente() );
        //Long idIstanzaAttore = istanzaAttoreDAO.saveIstanzaAttore(istanzaAttoreManager.createIstanzaAttoreCompilante(attoreScriva.getCodiceFiscale(), idIstanza, ComponenteAppEnum.findByDescr(attoreScriva.getComponente())));
        
        //Salva l'attore Istanza nella t__istanza_attore

        Long idIstanzaAttore = istanzaService.saveIstanzaAttore(idIstanza, attoreScriva);
        if (null == idIstanza || null == idIstanzaAttore) {
            logError(className, "idIstanza null OR idIstanzaAttore null");
            error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        } else {

            //Salva l'attore Istanza e l'onwer nella t__istanza

            istanzaDAO.updateIstanzaAttore(idIstanza, idIstanzaAttore, idIstanzaAttore, attoreScriva.getCodiceFiscale());
            istanza = istanzaDAO.loadIstanza(idIstanza).get(0);
            istanza = istanzaService.updateJsonDataIstanza(istanza);
            istanzaDAO.updateJsonDataIstanza(istanza.getDTO());
            try {
                istanzaEventoService.traceIstanzaEventoByCodeTipoEvento(idIstanza, ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? TipoEventoEnum.INS_PRATICA_BO.name() : TipoEventoEnum.INS_ISTANZA.name(), null, null, null, null, null, attoreScriva);
            } catch (GenericException e) {
                logError(className, e);
            }

            if (istanza.getIstanzaResponsabile() != null) {
                List<IstanzaResponsabileExtendedDTO> istResp = istanza.getIstanzaResponsabile();
                for (IstanzaResponsabileExtendedDTO istanzaResp : istResp)
                    istanzaResponsabiliDAO.saveIstanzaResponsabile(istanzaResp);
            }

            if (istanza.getDataProtocolloIstanza() != null && StringUtils.isNotBlank(istanza.getNumProtocolloIstanza())) {
                istanzaService.updateProtocolloAllegatiIstanza(istanza, attoreScriva);
            }

            return getResponseSave(istanza, className, "/istanze/" + idIstanza);
        }
    }

    /**
     * @param istanza         IstanzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response updateIstanza(Boolean flgCreaPratica, IstanzaExtendedDTO istanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, istanza);
        Response response = setAttoreRight(httpHeaders, istanza.getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }

        ErrorDTO error = istanzaService.validateDTO(istanza, Boolean.TRUE);
        if (null != error) {
            logError(className, error);
            return getResponseError(className, error);
        }

        if (StringUtils.isBlank(istanza.getGestAttoreUpd())) {
            istanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        }

        if (attoreScriva.getIdAttore() != null) {
            if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                istanza.setIdFunzionario(attoreScriva.getIdAttore());
            } else {
                istanza.setIdIstanzaAttore(attoreScriva.getIdAttore());
            }
        }

        try {
        	List<IstanzaResponsabileExtendedDTO> respIst = istanza.getIstanzaResponsabile();
        	if(respIst != null)
        		updateResponsabili(istanza);
            return Response.ok(istanzaService.updateIstanza(flgCreaPratica, istanza,attoreScriva)).status(200).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva.getProfiloAppIstanza()).build();
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        } finally {
            logEnd(className);
        }
    }

    private void updateResponsabili(IstanzaExtendedDTO istanza) {
        logBegin(className);
        List<IstanzaResponsabileExtendedDTO> istRespNew = istanza.getIstanzaResponsabile();
        List<IstanzaResponsabileExtendedDTO> istRespToSave = istanza.getIstanzaResponsabile();
        
        List<IstanzaResponsabileExtendedDTO> istRespOnDb = istanzaResponsabiliDAO.loadIstanzaResponsabiliByIstanza(istanza.getIdIstanza());
        
        if(CollectionUtils.isEmpty(istRespNew)) {
        	try {
				List<Long> istRespOnDbId = new ArrayList<Long>();
				for(IstanzaResponsabileExtendedDTO istRespId : istRespOnDb) {
					istRespOnDbId.add(istRespId.getIdIstanzaResponsabile());
				}
				if(CollectionUtils.isNotEmpty(istRespOnDbId))
					istanzaResponsabiliDAO.deleteIstanzaResponsabile(istRespOnDbId);
			} catch (Exception e) {
				logError(className, e);
			}
        }
        
        if (CollectionUtils.isNotEmpty(istRespNew)) {
            try {
                //List<IstanzaResponsabileExtendedDTO> istRespOnDb = istanzaResponsabiliDAO.loadIstanzaResponsabiliByIstanza(istanza.getIdIstanza());

                //Qualora siano presenti dei responsabili precedentemente inseriti, va gestita la cancellazione e aggiornamento di quelli esistenti
                if (CollectionUtils.isNotEmpty(istRespOnDb)) {
                    // Lista degli id_istanza_responsabile non passati dal FE
                    List<Long> idIstRespToDel = istRespOnDb.stream()
                            .filter(irDB -> istRespNew.stream()
                                    .noneMatch(irNew -> irNew.getTipoResponsabile().getIdTipoResponsabile().equals(irDB.getTipoResponsabile().getIdTipoResponsabile())
                                            && irNew.getNominativoResponsabile().equals(irDB.getNominativoResponsabile())))
                            .map(IstanzaResponsabileExtendedDTO::getIdIstanzaResponsabile)
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(idIstRespToDel)) {
                        istanzaResponsabiliDAO.deleteIstanzaResponsabile(idIstRespToDel);
                    }

                    // Lista degli oggetti istanza_responsabile da aggiornare
                    List<IstanzaResponsabileExtendedDTO> istRespToUpd = istRespNew.stream()
                            .filter(irNew -> istRespOnDb.stream()
                                    .anyMatch(irDB -> irDB.getTipoResponsabile().getIdTipoResponsabile().equals(irNew.getTipoResponsabile().getIdTipoResponsabile())
                                            && irDB.getNominativoResponsabile().equals(irNew.getNominativoResponsabile())))
                            .collect(Collectors.toList());

                    for (IstanzaResponsabileExtendedDTO istanzaResp : istRespToUpd) {
                        istanzaResp.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                        istanzaResp.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                        istanzaResp.setLabelResponsabile(istanzaResp.getTipoResponsabile().getDescrizioneTipoResponsabile());
                        istanzaResponsabiliDAO.updateIstanzaResponsabile(istanzaResp.getDTO());
                    }

                    // Lista degli oggetti istanza_responsabile nuovi da salvare
                    istRespToSave = istRespNew.stream()
                            .filter(irNew -> istRespOnDb.stream()
                                    .noneMatch(irDB -> irDB.getTipoResponsabile().getIdTipoResponsabile().equals(irNew.getTipoResponsabile().getIdTipoResponsabile())
                                            && irDB.getNominativoResponsabile().equals(irNew.getNominativoResponsabile())))
                            .collect(Collectors.toList());

                }

                for (IstanzaResponsabileExtendedDTO istanzaResp : istRespToSave) {
                    istanzaResp.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                    istanzaResp.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                    istanzaResp.setLabelResponsabile(istanzaResp.getTipoResponsabile().getDescrizioneTipoResponsabile());
                    istanzaResponsabiliDAO.saveIstanzaResponsabile(istanzaResp.getDTO());
                }

            } catch (Exception e) {
                logError(className, e);
            }
        }
        logEnd(className);
    }

    /**
     * @param uid             uidIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    //@Transactional
    public Response deleteIstanza(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uid);
        IstanzaDTO istanza = istanzaDAO.findByUID(uid);
        if (istanza == null) {
            ErrorDTO error = getErrorManager().getError("404", "", "Errore nella cancellazione dell'elemento;  causa: elemento non trovato", null, null);
            logError(className, error);
            logEnd(className);
            return getResponseError(className, error);
        }

        Response response = setAttoreRight(httpHeaders, istanza.getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }

        String uuidIndexIstanza = istanza.getUuidIndex();
        Integer res = istanzaDAO.deleteIstanzaById(istanza.getIdIstanza());
        if (res == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, error);
            logEnd(className);
            return Response.serverError().entity(error).status(500).build();
        }
        if (uuidIndexIstanza != null) {
            allegatiManager.deleteContenutoByUuid(uuidIndexIstanza);
        }
        return getResponseDelete(istanza, className);
    }

    /**
     * @param id              idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response deleteIstanzaById(Long id, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, id);
        Response response = setAttoreRight(httpHeaders, id, Boolean.TRUE);
        if (response != null) {
            return response;
        }
        return getResponseDelete(istanzaDAO.deleteIstanzaById(id), className);
    }


    // REFERENTI

    /**
     * @param idIstanza   idIstanza
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadReferentiByIstanza(Long idIstanza, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
 
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }

     

        return getResponseList(referenteIstanzaDAO.loadReferentiIstanzaByIdIstanza(idIstanza), className);
    }

    /**
     * @param codeIstanza codeIstanza
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadReferentiByCodeIstanza(String codeIstanza, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codeIstanza);
        return getResponseList(referenteIstanzaDAO.loadReferentiIstanzaByCodeIstanza(codeIstanza), className);
    }

    /**
     * @param referenteIstanza ReferenteIstanzaDTO
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response
     */
    @Override
    public Response addReferente(ReferenteIstanzaDTO referenteIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, referenteIstanza);
        Response response = setAttoreRight(httpHeaders, referenteIstanza.getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }
        ErrorDTO error = istanzaService.validateReferenteDTO(referenteIstanza);
        if (null != error) {
            logError(className, error);
            return getResponseError(className, error);
        }

        // test fix problema prod jira SCRIVA-1537 DA RIMUOVERE DOPO I TEST
        /* 
        try {
            // Metti un delay di n secondi           
            int n = 15;
            Thread.sleep(n * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        if (StringUtils.isBlank(referenteIstanza.getGestAttoreIns())) {
            referenteIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
        }
        Long idReferente = referenteIstanzaDAO.saveReferenteIstanza(referenteIstanza);

        if (idReferente == null) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, error);
            return getResponseError(className, error);
        } else {
            referenteIstanza = referenteIstanzaDAO.loadReferenteIstanza(idReferente).get(0);
            
            updateIstanzaPraticaTimestampAttore(referenteIstanza.getIdIstanza(), attoreScriva);

            return getResponseSave(referenteIstanza, className, referenteIstanza != null ? "/referenti/id-istanza/" + referenteIstanza.getIdIstanza() : null);
        }
    }

    /**
     * @param referenteIstanza ReferenteIstanzaDTO
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response
     */
    @Override
    public Response updateReferente(ReferenteIstanzaDTO referenteIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, referenteIstanza);
        Response response = setAttoreRight(httpHeaders, referenteIstanza.getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }

        ErrorDTO error = istanzaService.validateReferenteDTO(referenteIstanza);
        if (null != error) {
            logError(className, error);
            return getResponseError(className, error);
        }

        if (StringUtils.isBlank(referenteIstanza.getGestAttoreUpd())) {
            referenteIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        }
        if (referenteIstanzaDAO.updateReferenteIstanza(referenteIstanza) != null) {
            updateIstanzaPraticaTimestampAttore(referenteIstanza.getIdIstanza(), attoreScriva);
            return getResponseSaveUpdate(referenteIstanzaDAO.loadReferenteIstanza(referenteIstanza.getIdReferenteIstanza()).get(0), className);
        }
        error = new ErrorDTO("500", "E100", "Errore inaspettato nella gestione della richiesta. Riprova a breve.", null, null);
        return getResponseError(className, error);
    }

    /**
     * @param uid             uidIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response deleteReferente(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uid);

        Long idIstanza = referenteIstanzaDAO.loadIdIstanzaByUID(uid);

        Response response = setAttoreRight(httpHeaders, idIstanza, Boolean.TRUE);
        if (response != null) {
            return response;
        }
        updateIstanzaPraticaTimestampAttore(idIstanza, attoreScriva);
        return getResponseDelete(referenteIstanzaDAO.deleteReferenteIstanza(uid), className);
    }

    /**
     * @param idReferenteIstanza idReferenteIstanza
     * @param securityContext    SecurityContext
     * @param httpHeaders        HttpHeaders
     * @param httpRequest        HttpServletRequest
     * @return Response
     */
    @Override
    public Response deleteReferenteById(Long idReferenteIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idReferenteIstanza);
        return getResponseDelete(referenteIstanzaDAO.deleteReferenteIstanzaById(idReferenteIstanza), className);
    }

    /**
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response createPDFIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        /*
        Response response = setAttoreRight(httpHeaders, idIstanza, Boolean.TRUE);
        if (response != null) {
            return response;
        }
        */

        List<IstanzaExtendedDTO> istanze = istanzaDAO.loadIstanza(idIstanza);
        if (CollectionUtils.isEmpty(istanze)) {
            ErrorDTO error = getErrorManager().getError("500", "E014", null, null, null);
            logError(className, error);
            return getResponseError(className, error);
        }
        IstanzaExtendedDTO istanza = istanze.get(0);
        String absolutePathTemplate = templateService.getTemplatePath(istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento());
        //String filename = StringUtils.isNotBlank(absolutePathTemplate) ? templateService.getTemplateFilename(istanze.get(0), TipologiaAllegatoEnum.MOD_IST.name(), absolutePathTemplate) : null;
        String filename = StringUtils.isNotBlank(absolutePathTemplate) ? templateService.getTemplateFilename(istanza, TipologiaAllegatoEnum.MOD_IST.name(), absolutePathTemplate) : null;
        if (StringUtils.isNotBlank(absolutePathTemplate) && StringUtils.isNotBlank(filename)) {
            String pathTemplate = absolutePathTemplate + File.separator + filename;
            try {
                byte[] out = templateUtil.getCompiledTemplatePDF(idIstanza, pathTemplate);
                String now = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = "Modulo_istanza_" + istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento() + "_" + istanza.getIdIstanza() + "_" + now;
                logEnd(className);
                return Response.ok(out, "application/pdf").header("Content-Disposition", "attachment; filename=\"" + fileName + "_" + idIstanza + ".pdf\"").build();
            } catch (Exception e) {
                logError(className, e);
                ErrorDTO error = getErrorManager().getError("500", "E014", null, null, null);
                return getResponseError(className, error);
            }
        }
        logError(className, "");
        ErrorDTO error = getErrorManager().getError("500", "E014", null, null, null);
        return getResponseError(className, error);
    }

    /**
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response readJsonData(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        List<IstanzaExtendedDTO> list = istanzaDAO.loadIstanza(idIstanza);
        if (null == list || list.isEmpty()) {
            return getResponseList(list, className);
        }
        JSONObject oJsonData = null;
        for (IstanzaExtendedDTO istanza : list) {
            try {
                String sJsonData = istanza.getJsonData();
                oJsonData = new JSONObject(sJsonData);
                if (oJsonData.has("QDR_ISTANZA")) {
                    oJsonData.getJSONObject("QDR_ISTANZA").put("QDR_ISTANZA", istanza.toJsonObj());
                }
            } catch (Exception e) {
                logError(className, e);
            }
        }
        logEnd(className);
        return Response.ok(oJsonData).build();

    }

    /**
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response updateJsonData(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza, Boolean.TRUE);
        if (response != null) {
            return response;
        }
        jsonDataManager.updateJsonData(idIstanza);
        logEnd(className);
        return Response.ok().build();
    }

    /**
     * @param uidIstanza      uidIstanza
     * @param idStatoIstanza  idStatoIstanza
     * @param gestAttoreUpd   gestAttoreUpd
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response updateStatoIstanza(String uidIstanza, Long idStatoIstanza, String gestAttoreUpd, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "uidIstanza [" + uidIstanza + "] - idStatoIstanza [" + idStatoIstanza + "]");
        IstanzaDTO istanza = istanzaDAO.findByUID(uidIstanza);
        if (istanza == null) {
            ErrorDTO error = getErrorManager().getError("404", "", "Errore nell'aggiornamento dell'elemento;  causa: elemento non trovato", null, null);
            return getResponseError(className, error);
        }

        Response response = setAttoreRight(httpHeaders, istanza.getIdIstanza(), Boolean.TRUE);
        /*
        if (response != null) {
            return response;
        }
        */

        List<StatoIstanzaAdempimentoDTO> statoIstanzaAdempimentoList = statoIstanzaAdempimentoDAO.loadStatoIstanzaAdempimentoByIdAdempimentoIdStatoIstanzaNewOld(istanza.getIdAdempimento(), istanza.getIdStatoIstanza(), idStatoIstanza);
        if (statoIstanzaAdempimentoList == null || statoIstanzaAdempimentoList.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }

        String attoreUpd = StringUtils.isNotBlank(attoreScriva.getCodiceFiscale()) ? attoreScriva.getCodiceFiscale() : gestAttoreUpd;
        Integer res = istanzaDAO.updateStatoIstanza(uidIstanza, idStatoIstanza, attoreUpd, null, istanzaService.saveStorico(idStatoIstanza));
        if (null == res) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        } else if (res < 1) {
            ErrorDTO error = getErrorManager().getError("404", "E100", "Errore nell'aggiornamento dell'elemento;  causa: elemento non trovato", null, null);
            return getResponseError(className, error);
        } else {
            List<IstanzaExtendedDTO> dto = istanzaDAO.loadIstanzaByUID(uidIstanza);
            if (null != dto && !dto.isEmpty()) {
                IstanzaStatoDTO istanzaStatoDTO = new IstanzaStatoDTO();
                istanzaStatoDTO.setIdIstanza(dto.get(0).getIdIstanza());
                istanzaStatoDTO.setIdStatoIstanza(idStatoIstanza);
                istanzaStatoDTO.setGestAttoreIns(attoreUpd);
                istanzaStatoDAO.saveIstanzaStato(istanzaStatoDTO);
                IstanzaExtendedDTO istanzaNew = istanzaService.updateJsonDataIstanza(dto.get(0));
                istanzaDAO.updateJsonDataIstanza(istanzaNew.getDTO());
            }
            logEnd(className);
            return Response.ok(dto).status(200).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
        }
    }

    /**
     * @param idIstanza       idIstanza
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response checkIstanza(Long idIstanza, String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - codAdempimento [" + codAdempimento + "]");
        ErrorDTO error = mandatoryInfoIstanzaManager.checkIstanza(codAdempimento, idIstanza);
        if (null != error) {
            return getResponseError(className, error);
        }
        logEnd(className);
        return Response.noContent().build();
    }

    /**
     * Generazione file pdf con elenco allegati associati all’istanza in corso.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response downloadPDFAllegati(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        /*
        if (response != null) {
            return response;
        }

         */

        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanzaAndCodTipologia(idIstanza, Constants.COD_TIPOLOGIA_ALLEGATI_ELENCO);
        if (allegatoIstanzaList != null && !allegatoIstanzaList.isEmpty()) {
            AllegatoIstanzaExtendedDTO allegatoIstanza = allegatoIstanzaList.get(0);
            File file = allegatiManager.getFileByUuid(allegatoIstanza.getUuidIndex());
            if (file != null) {
                logEnd(className);
                return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=\"" + allegatoIstanza.getNomeAllegato() + "\"").build();
            }
        }
        ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
        return getResponseError(className, error);
    }

    /**
     * Salva su index il modulo pdf dell'istanza firmato.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response uploadPDFModuloFirmatoIstanza(Long idIstanza, MultipartFormDataInput formDataInput, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - formDataInput [" + formDataInput + "]");
        Response response = setAttoreRight(httpHeaders, idIstanza);
        /*
        if (response != null) {
            return response;
        }
         */

        try {
            File file = formDataInput.getFormDataPart("file", File.class, null);
            String fileName = allegatiManager.getFileName(formDataInput, "file");
            Long idIstanzaAllegato = allegatiManager.uploadAllegato(idIstanza, attoreScriva, file, fileName, Constants.COD_CATEGORIA_ALLEGATI_SISTEMA, Constants.COD_TIPOLOGIA_ALLEGATI_MODULO_ISTANZA_FIRMATO);
            return getResponseList(idIstanzaAllegato != null ? allegatoIstanzaDAO.loadAllegatoIstanzaById(idIstanzaAllegato) : new ArrayList<>(), className);
        } catch (GenericException e) {
            logError(className, e);
            return Response.serverError().entity(e.getError()).status(500).build();
        } catch (Exception e) {
            logError(className, e);
            return getResponseError(className, getErrorManager().getError("500", "E100", null, null, null));
        }
    }

    /**
     * Create modulo delega response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response createModuloDelega(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }

        List<IstanzaExtendedDTO> istanze = istanzaDAO.loadIstanza(idIstanza);
        if (istanze != null && !istanze.isEmpty()) {
            IstanzaExtendedDTO istanza = istanze.get(0);

            String absolutePathTemplate = templateService.getTemplatePath(istanze.get(0).getAdempimento().getTipoAdempimento().getCodTipoAdempimento());
            String pathTemplate = null;
            if (StringUtils.isNotBlank(absolutePathTemplate)) {
                pathTemplate = absolutePathTemplate + File.separator + istanze.get(0).getAdempimento().getTipoAdempimento().getCodTipoAdempimento() + "_DEL_FIRMA.docx";
            }

            if (StringUtils.isNotBlank(pathTemplate)) {
                try {
                    byte[] out = templateUtil.getCompiledTemplatePDF(idIstanza, pathTemplate);
                    String now = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String fileName = "DEL_FIRMA" + "_"+ istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento() + "_" + istanza.getIdIstanza() + "_" + now;
                    logEnd(className);
                    return Response.ok(out, MediaType.APPLICATION_OCTET_STREAM_TYPE).header("Content-Disposition", "attachment; filename=\"" + fileName + ".pdf\"").build();
                } catch (Exception e) {
                    logError(className, e);
                }
                
            }
        }
        
        //NEW
        ErrorDTO error = getErrorManager().getError("500", "E077", null, null, null);
        setPlaceHolderValues(error, idIstanza);

		//ErrorDTO error = getErrorManager().getError("500", "E014", null, null, null);
		//logError(className, error);
        return Response.serverError().entity(error).status(500).build();
    }

    /**
     * Create doc or pdf istanza response.
     * Carica il docimento
     *
     * @param idIstanza            the id istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param outputFormat         the output format
     * @param archivia             salva su db
     * @param cancella             the cancella
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @return the response
     */
    @Override
    public Response createDOCOrPDFcIstanza(Long idIstanza, String codTipologiaAllegato, String outputFormat, Boolean archivia, Boolean cancella,
                                           SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "] - codTipologiaAllegato: [" + codTipologiaAllegato + "] -  outputFormat: [" + outputFormat + "]");
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        List<IstanzaExtendedDTO> istanze = istanzaDAO.loadIstanza(idIstanza); //recupera l'istanza
        if (CollectionUtils.isNotEmpty(istanze)) {
            IstanzaExtendedDTO istanza = istanze.get(0);
            String codTipologiaAllegatoExtract = StringUtils.isNotBlank(codTipologiaAllegato) ? codTipologiaAllegato : istanzaService.getCodTipologiaAllegato(idIstanza); //recupera il codice tipologia allegato
            if (StringUtils.isNotBlank(codTipologiaAllegatoExtract)) {
                String absolutePathTemplate = templateService.getTemplatePath(istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento()); //recupera il path del template
                String filename = StringUtils.isNotBlank(absolutePathTemplate) ? templateService.getTemplateFilename(istanza, codTipologiaAllegatoExtract, absolutePathTemplate) : null; //recupera il nome del file del template
                if (StringUtils.isNotBlank(absolutePathTemplate) && StringUtils.isNotBlank(filename)) {
                    String pathTemplate = absolutePathTemplate + File.separator + filename; //path completo del template del documento che si deve generare
                    try {
                        byte[] out = outputFormat.equalsIgnoreCase("pdf") ? templateUtil.getCompiledTemplatePDF(idIstanza, pathTemplate) : templateUtil.getCompiledTemplateDOC(idIstanza, pathTemplate); //genera documento
                        String now = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String fileName = codTipologiaAllegatoExtract + "_" + istanza.getCodIstanza() + "_" + now; // crea nome del file del documento che verrà scaricato
                        String heardType = outputFormat.equalsIgnoreCase("pdf") ? "application/pdf" : "application/msword";
                        String estensione = outputFormat.equalsIgnoreCase("pdf") ? Constants.EXT_PDF : Constants.EXT_DOC;
                        if (Boolean.TRUE.equals(archivia)) { // se flag archivia è true salva i metadata del documento su db
                            File file = new File(fileName);
                            FileUtils.writeByteArrayToFile(file, out);
                            istanzaService.archiviaAllegatiIstanza(istanza, file, fileName + estensione, codTipologiaAllegatoExtract, cancella, attoreScriva); // archivia il documento gestendo legami padre/figlio dei doc es elenco allegati (padre) - allegati all'istanza(figli)
                        }
                        logEnd(className);
                        return Response.ok(out, heardType).header("Content-Disposition", "attachment; filename=\"" + fileName + estensione + "\"").build();
                    } catch (Exception e) {
                        logError(className, e);
                    }
                }
            } else {
                return Response.noContent().build();
            }
        }
        ErrorDTO error = getErrorManager().getError("500", "E014", null, null, null);
        logError(className, error);
        return getResponseError(className, error);
    }

    /**
     * Create codice pratica response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response createCodicePratica(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza, Boolean.TRUE);
        try {
            return getResponse(istanzaService.createCodicePratica(idIstanza,httpHeaders, attoreScriva), className);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load istanza stato response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadIstanzaStato(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        try {
            return getResponse(istanzaService.loadIstanzaStato(idIstanza), className);
        } catch (Exception e) {
            logError(className, e);
            return getResponseError(className, getErrorManager().getError("500", "E100", null, null, null));
        } finally {
            logEnd(className);
        }
    }

    /**
     * Patch data pubblicazioned istanza response.
     *
     * @param idIstanza         the id istanza
     * @param dataPubblicazione the data pubblicazione
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response patchDataPubblicazionedIstanza(Long idIstanza, Date dataPubblicazione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        try {
            Response response = setAttoreRight(httpHeaders, idIstanza, Boolean.TRUE);
            if (response != null) {
                return response;
            }
            Response resp = getResponse(istanzaService.patchDataPubblicazioneIstanza(idIstanza, dataPubblicazione, attoreScriva), className);
            updateIstanzaPraticaTimestampAttore(idIstanza, attoreScriva);
            return resp;
        } catch (Exception e) {
            logError(className, e);
            return getResponseError(className, getErrorManager().getError("500", "E100", null, null, null));
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load istanza responsabili response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadIstanzaResponsabili(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        try {
            return getResponse(istanzaService.loadIstanzaResponsabili(idIstanza), className);
        } catch (Exception e) {
            logError(className, e);
            return getResponseError(className, getErrorManager().getError("500", "E100", null, null, null));
        } finally {
            logEnd(className);
        }
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
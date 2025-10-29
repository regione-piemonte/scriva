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


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import it.csi.scriva.scrivabesrv.business.be.OggettiIstanzaApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CatastoUbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoAreaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoLineaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoPuntoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaCompetenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaCompetenzaOggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaAreaProtettaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaCategoriaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaFiglioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaNatura2000DAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaVincoloAutorizzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipologiaOggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.OggettiIstanzaService;
import it.csi.scriva.scrivabesrv.business.be.service.ParchiService;
import it.csi.scriva.scrivabesrv.business.be.service.impl.IstanzaServiceImpl;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.GeoAreaOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.GeoLineaOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.GeoPuntoOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaUbicazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaUbicazioneGeoRefExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaUbicazioneGeometrieExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.StatoOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.InformazioniScrivaEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.IstanzaAttoreManager;
import it.csi.scriva.scrivabesrv.util.manager.JsonDataManager;
/**
 * The type Oggetti istanza api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OggettiIstanzaApiServiceImpl extends BaseApiServiceImpl implements OggettiIstanzaApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Geo area oggetto istanza dao.
     */
    @Autowired
    GeoAreaOggettoIstanzaDAO geoAreaOggettoIstanzaDAO;

    /**
     * The Geo punto oggetto istanza dao.
     */
    @Autowired
    GeoPuntoOggettoIstanzaDAO geoPuntoOggettoIstanzaDAO;

    /**
     * The Geo linea oggetto istanza dao.
     */
    @Autowired
    GeoLineaOggettoIstanzaDAO geoLineaOggettoIstanzaDAO;

    /**
     * The Oggetti istanza service.
     */
    @Autowired
    OggettiIstanzaService oggettiIstanzaService;

    /**
     * The Oggetto istanza dao.
     */
    @Autowired
    OggettoIstanzaDAO oggettoIstanzaDAO;

    /**
     * The Ubicazione oggetto istanza dao.
     */
    @Autowired
    UbicazioneOggettoIstanzaDAO ubicazioneOggettoIstanzaDAO;

    /**
     * The Adempimento config dao.
     */
    @Autowired
    AdempimentoConfigDAO adempimentoConfigDAO;

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Oggetto dao.
     */
    @Autowired
    OggettoDAO oggettoDAO;

    /**
     * The Ubicazione oggetto dao.
     */
    @Autowired
    UbicazioneOggettoDAO ubicazioneOggettoDAO;
    
    /**
     * The Catasto ubicazione oggetto istanza dao.
     */
    @Autowired
    CatastoUbicazioneOggettoIstanzaDAO catastoUbicazioneOggettoIstanzaDAO;
    
    /**
     * The oggetto istanza area protetta dao.
     */
    @Autowired
    OggettoIstanzaAreaProtettaDAO oggettoIstanzaAreaProtettaDAO;
    
    /**
     * The oggetto istanza vincolo autorizza dao.
     */
    @Autowired
    OggettoIstanzaVincoloAutorizzaDAO oggettoIstanzaVincoloAutorizzaDAO;
    
    /**
     * The oggetto istanza figlio dao.
     */
    @Autowired
    OggettoIstanzaFiglioDAO oggettoIstanzaFiglioDAO;
    
    /**
     * The oggetto istanza categoria dao.
     */
    @Autowired
    OggettoIstanzaCategoriaDAO oggettoIstanzaCategoriaDAO;
    
    /**
     * The oggetto istanza natura 2000 dao.
     */
    @Autowired
    OggettoIstanzaNatura2000DAO oggettoIstanzaNatura2000DAO;
    
    /**
     * The istanza competenza dao.
     */
    @Autowired
    IstanzaCompetenzaDAO istanzaCompetenzaDAO;
    
    /**
     * The istanza competenza oggetto dao.
     */
    @Autowired
    IstanzaCompetenzaOggettoDAO istanzaCompetenzaOggettoDAO;
    
    /**
     * The tipologia oggetto dao.
     */
    @Autowired
    TipologiaOggettoDAO tipologiaOggettoDAO;
    
//    /**
//     * The natura oggetto dao.
//     */
//    @Autowired
//    NaturaOggettoDAO naturaOggettoDAO;

    /**
     * The Istanza attore manager.
     */
    @Autowired
    IstanzaAttoreManager istanzaAttoreManager;
    
    /**
     * The Json Data manager.
     */
    @Autowired
    JsonDataManager jsonDataManager;

    /**
     * The Parchi service.
     */
    @Autowired
    ParchiService parchiService;
    
    /**
     * The Istanza service impl.
     */
    @Autowired
    IstanzaServiceImpl istanzaService;


    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadOggettiIstanza(String xRequestAuth, String xRequestId,
                                       Long idIstanza,
                                       SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        return getResponseList(idIstanza != null ? oggettiIstanzaService.loadOggettoIstanzaByIdIstanza(idIstanza) : oggettiIstanzaService.loadOggettiIstanza(), className);
    }

    /**
     * @param idOggettoIstanza idOggettoIstanza
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadOggettoIstanzaById(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggettoIstanza);
        List<OggettoIstanzaUbicazioneGeometrieExtendedDTO> oggettoIstanzaUbicazioneList = oggettiIstanzaService.loadOggettoIstanzaWithGeometriesById(idOggettoIstanza);
        Response response = setAttoreRight(httpHeaders, oggettoIstanzaUbicazioneList.get(0).getIdIstanza());
        if (response != null) {
            return response;
        }
        return getResponseList(oggettoIstanzaUbicazioneList, className);
    }

    /**
     * @param uidOggettoIstanza uidOggettoIstanza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadOggettoIstanzaByUID(String uidOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uidOggettoIstanza);
        List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList = oggettiIstanzaService.loadOggettoIstanzaByUID(uidOggettoIstanza);
        Response response = setAttoreRight(httpHeaders, oggettoIstanzaUbicazioneList.get(0).getIdIstanza());
        if (response != null) {
            return response;
        }
        return getResponseList(oggettoIstanzaUbicazioneList, className);
    }

    /**
     * @param oggettoIstanza  OggettoIstanzaUbicazioneExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response saveOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, oggettoIstanza);
        Date now = Calendar.getInstance().getTime();
        Response response = setAttoreRight(httpHeaders, oggettoIstanza.getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }

        ErrorDTO error = oggettiIstanzaService.validateDTO(oggettoIstanza, Boolean.FALSE);
        if (null != error) {
            return getResponseError(className, error);
        }

        Long idOggettoIstanza = oggettiIstanzaService.saveOggettoIstanza(oggettoIstanza, attoreScriva);
        if (idOggettoIstanza != null) {
            oggettiIstanzaService.copyLastInfoFromOggetto(idOggettoIstanza, oggettoIstanza.getIdOggetto(), (StringUtils.isBlank(oggettoIstanza.getGestAttoreIns()) ? attoreScriva.getCodiceFiscale() : oggettoIstanza.getGestAttoreIns()), now);
            updateIstanzaPraticaTimestampAttore(oggettoIstanza.getIdIstanza(), attoreScriva);
        }
        

        // Recupero record salvato : necessario per il gestUid
        List<OggettoIstanzaUbicazioneExtendedDTO> oggettiIstanzaNewList = idOggettoIstanza != null ? oggettiIstanzaService.loadOggettoIstanzaById(idOggettoIstanza) : null;
        return getResponseSaveUpdate(oggettiIstanzaNewList != null ? oggettiIstanzaNewList.get(0) : null, className);
    }

    /**
     * @param oggettoIstanza  OggettoIstanzaUbicazioneExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    //@Transactional
    public Response updateOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, oggettoIstanza);
        Response response = setAttoreRight(httpHeaders, oggettoIstanza.getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }
        ErrorDTO error = oggettiIstanzaService.validateDTO(oggettoIstanza, Boolean.TRUE);
        if (null != error) {
            return getResponseError(className, error);
        }

        Integer idOggettoIstanza = oggettiIstanzaService.updateOggettoIstanza(oggettoIstanza, attoreScriva);
        if (idOggettoIstanza != null) {
            updateIstanzaPraticaTimestampAttore(oggettoIstanza.getIdIstanza(), attoreScriva);
        }

        // Recupero record aggiornato : necessario per il recupero del gestUid
        List<OggettoIstanzaUbicazioneExtendedDTO> oggettiIstanzaNewList = oggettiIstanzaService.loadOggettoIstanzaByUID(oggettoIstanza.getGestUID());
        OggettoIstanzaUbicazioneExtendedDTO oggettiIstanzaUpdated = oggettiIstanzaNewList.get(0);
        //this.updateOggetto(oggettiIstanzaUpdated);
        logEnd(className);
        return Response.ok(oggettiIstanzaUpdated).status(200).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva.getProfiloAppIstanza()).build();
    }


    /**
     * @param uidOggettoIstanza uidOggettoIstanza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response deleteOggettoIstanza(String uidOggettoIstanza, String checkFigli, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uidOggettoIstanza);
        
        List<OggettoIstanzaExtendedDTO> oggettoIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaByUID(uidOggettoIstanza);
        
        if (oggettoIstanzaList == null || oggettoIstanzaList.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, "uidOggettoIstanza [" + uidOggettoIstanza + "] : Errore recupero OggettoIstanza");
            return getResponseError(className, error);
        }
        Response response = setAttoreRight(httpHeaders, oggettoIstanzaList.get(0).getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }

        // 1 --> QUERY PER VERIFICARE PRESENZA OGGETTI FIGLI SU TABELLA SCRIVA_R_OGGETTO_IST_FIGLIO IN JOIN CON SCRIVA_T_OGGETTO_ISTANZA
        List<OggettoIstanzaExtendedDTO> numFigliOggIst = oggettoIstanzaFiglioDAO.loadOggettiFigli(uidOggettoIstanza); 
        
        if(checkFigli != null && checkFigli.equals("true")) {
        	List<TipologiaOggettoExtendedDTO> tipoOggetto = tipologiaOggettoDAO.loadTipologiaOggetto(oggettoIstanzaList.get(0).getTipologiaOggetto().getIdTipologiaOggetto());
        	String desTipoOggetto = tipoOggetto.get(0).getDesTipologiaOggetto();
        	
        	
        	String desNaturaOggetto = tipoOggetto.get(0).getNaturaOggetto().getDesNaturaOggetto();
	        if (numFigliOggIst != null && !numFigliOggIst.isEmpty()) {
	        		        	
	        	ErrorDTO error =  getErrorManager().getError("400", "A047", "", null, null);
                error.setTitle(error.getTitle().replace("{PH_DES_TIPOLOGIA_OGGETTO}", desTipoOggetto));
                error.setTitle(error.getTitle().replace("{PH_NATURA_OGGETTO}", desNaturaOggetto));
                return getResponseError(className, error);
	        }
	        else {
	        	ErrorDTO error = getErrorManager().getError("400", "A048", "", null, null);
	            error.setTitle(error.getTitle().replace("{PH_DES_TIPOLOGIA_OGGETTO}", desTipoOggetto));
	            return getResponseError(className, error);
	        }
        }
        else {

	        // Cancellazione georeferenze
	        geoAreaOggettoIstanzaDAO.deleteGeoAreaOggettoIstanzaByUIDOggettoIstanza(uidOggettoIstanza);
	        geoLineaOggettoIstanzaDAO.deleteGeoLineaOggettoIstanzaByUIDOggettoIstanza(uidOggettoIstanza);
	        geoPuntoOggettoIstanzaDAO.deleteGeoPuntoOggettoIstanzaByUIDOggettoIstanza(uidOggettoIstanza);
	        
	        //2 --> AGGIUNGERE QUA LE NUOVE DELETE
	        Long idOggettoIstanza = oggettoIstanzaList.get(0).getIdOggettoIstanza();
	        Long idOggetto = oggettoIstanzaList.get(0).getOggetto().getIdOggetto();
	        Long idIstanza = oggettoIstanzaList.get(0).getIdIstanza();
	        catastoUbicazioneOggettoIstanzaDAO.deleteCatastoUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
	        
	        oggettoIstanzaAreaProtettaDAO.deleteOggettoIstanzaAreaProtettaByIdOggettoIstanza(idOggettoIstanza);
	        
	        oggettoIstanzaVincoloAutorizzaDAO.deleteOggettoIstanzaVincoloAutorizzaByIdOggettoIstanza(idOggettoIstanza);
	        
	       // oggettoIstanzaFiglioDAO.deleteOggettoIstanzaFiglioByIdOggIstPadre(idOggettoIstanza);//--->? Jira 1024
            oggettoIstanzaFiglioDAO.deleteOggettoIstanzaFiglioByIdOggIstFiglio(idOggettoIstanza);//--->?

	        
	        oggettoIstanzaCategoriaDAO.deleteOggettoIstanzaCategoria(idOggettoIstanza); //--->?
	        
	        oggettoIstanzaNatura2000DAO.deleteOggettoIstanzaNatura2000ByIdOggettoIstanza(idOggettoIstanza);

	
	        Integer resCancUbicazioni = ubicazioneOggettoIstanzaDAO.deleteUbicazioneOggettoIstanzaByUidOggettoIstanza(uidOggettoIstanza);
	        if (resCancUbicazioni == null) {
	            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
	            logError(className, "uidOggettoIstanza [" + uidOggettoIstanza + "] Errore durante la cancellazione delle ubicazioni");
	            return getResponseError(className, error);
	        } else {
	            

	            
	            // --> SI CANCELLANO LE AUTORITA' COMPETENTI COLLEGATE ALL'ISTANZA (ESEGUIRE CONTROLLO PER ESTRARRE LE AC DA CANCELLARE)
	            
	           List<IstanzaCompetenzaOggettoDTO> istCompOggList = istanzaCompetenzaOggettoDAO.loadIstanzaCompetenzaOggetti(idIstanza);
	
	           List<IstanzaCompetenzaOggettoDTO> istCompTerrToDel = (List<IstanzaCompetenzaOggettoDTO>) istCompOggList
	        		   .stream()
	        		   .filter(c -> Collections.frequency(istCompOggList, c.getIdCompetenzaTerritorio()) > 1)
	        		   .collect(Collectors.toList());
	        
	            for(IstanzaCompetenzaOggettoDTO istCompTerr : istCompTerrToDel) {
	            	istanzaCompetenzaDAO.deleteIstanzaCompetenzaByIdCompAndIdIst(istCompTerr.getIdCompetenzaTerritorio(), istCompTerr.getIdIstanza());
	            }
	            
	           
	            List<IstanzaCompetenzaOggettoDTO> istCompOggToDel = istCompOggList
	            		  .stream()
	            		  .filter(c -> c.getIdOggettoIstanza().equals(idOggettoIstanza))
	            		  .collect(Collectors.toList());
	            
	            for(IstanzaCompetenzaOggettoDTO istCompOgg : istCompOggToDel) {
	            	istanzaCompetenzaOggettoDAO.deleteIstanzaCompetenzaOggetto(istCompOgg.getIdOggettoIstanza());
	            }

	            
	            Integer resCancOggettoIstanza = oggettoIstanzaDAO.deleteOggettoIstanza(uidOggettoIstanza);
	            
	            // 3 --> CANCELLARE L'OGGETTO-ANAGRAFICA SOLO A DETERMINATE CONDIZIONI

	            List<OggettoIstanzaExtendedDTO> listOggIstByIdOggetto = oggettoIstanzaDAO.loadOggettoIstanzaByIdOggetto(idOggetto);
	            if(listOggIstByIdOggetto != null && (!(listOggIstByIdOggetto.size() > 1))) {
	            	ubicazioneOggettoDAO.deleteUbicazioneOggettoByIdOggetto(idOggetto);
	            	oggettoDAO.deleteOggettoById(idOggetto);
	            }
	            
	      
	            // 4 --> CANCELLARE I DATI TECNICI
	            
	            List<IstanzaExtendedDTO> istanza = istanzaDAO.loadIstanza(idIstanza);
	            String jsonData = istanza.get(0).getJsonData();
	            // jira 1024
	            //String value = jsonDataManager.searchValueFromJsonData(jsonData, "id_oggetto_istanza");
	            
	            /*
	             * Cancellare id oggetto istanza e tutto quello che si trova a quel livello
	            */

                // jira 1024
                // String jsonDataNew = jsonDataManager.removeObjectFromJson(jsonData, "id_oggetto_istanza", value);
                
                String  jsonDataNew = jsonDataManager.removeObjectFromJson(jsonData, "id_oggetto_istanza", idOggettoIstanza.toString());
                        //jsonDataNew = jsonDataManager.removeObjectFromJson(jsonData, "id_oggetto", idOggetto.toString());


	            
	            IstanzaExtendedDTO istanzaUpd = new IstanzaExtendedDTO();   
	            istanzaUpd = istanza.get(0); 
	            istanzaUpd.setJsonData(jsonDataNew); 

	            istanzaDAO.updateJsonDataIstanza(istanzaUpd);
	            	          
	            // 5 --> LA QUERY DEVE RESTITUIRE I PAGAMENTI GIA' EFFETTUATI
	            // ALL'UTENTE SI INVIA IL MESSAGGIO I00X
	            
	            
	            
	            if (resCancOggettoIstanza == null) {
	                ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
	                logError(className, "uidOggettoIstanza [" + uidOggettoIstanza + "] Errore durante la cancellazione dell'OggettoIstanza");
	                return getResponseError(className, error);
	            } else if (resCancOggettoIstanza < 1) {
	                ErrorDTO error = getErrorManager().getError("404", "", "Elemento non cancellato;  causa: elemento non trovato", null, null);
	                logError(className, "uidOggettoIstanza [" + uidOggettoIstanza + "] Errore durante la cancellazione dell'OggettoIstanza: elemento non trovato");
	                return getResponseError(className, error);
	            } else {
	                logEnd(className);
                    updateIstanzaPraticaTimestampAttore(idIstanza , attoreScriva);
	                return Response.noContent().build();
	            }
	        }
        }
    }

    /**
     * @param codAdempimento  codAdempimento
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response verificaGeoreferenziazione(String codAdempimento, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        List<AdempimentoConfigDTO> conf = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(codAdempimento, InformazioniScrivaEnum.ADEMPIMENTO.name(), Constants.CONFIG_KEY_IND_GEO_MODE);
        Boolean isGeoOk = Boolean.TRUE;
        List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> resp = new ArrayList<>();
        if (!conf.isEmpty()) {
            String valore = conf.get(0).getValore();
            logDebug(className, "Valore in configurazione : " + valore);
            switch (valore.toUpperCase()) {
                default:
                case "N":
                case "O":
                case "A":
                    //N (non richiesta) georeferenziazione opera non richiesta
                    //O (opzionale) georeferenziazione opera richiesta ma opzionale
                    //A (opzionale con avviso) georeferenziazione opera richiesta ma opzionale. In questo caso il sistema visualizza un messaggio per ricordare di eseguire la georeferenziazione (da decidere quando).
                    break;
                case "M":
                case "P":
                    //M (obbligatoria) georeferenziazione opera richiesta
                    //P (obbligatoria posticipabile) georeferenziazione opera posticipabile nell’iter di presentazione, ma da eseguire obbligatoriamente prima di presentare l’istanza

                    List<OggettoIstanzaDTO> list = oggettoIstanzaDAO.loadOggettoIstanzaSimpleByIdIstanza(idIstanza);
                    logDebug(className, "List size OggettoIstanza : " + list.size());
                    List<OggettoIstanzaDTO> geoInfoNull = list.stream().filter(dto -> StringUtils.isBlank(dto.getIndGeoStato())).collect(Collectors.toList());
                    logDebug(className, "List size geoInfoNull : " + geoInfoNull.size());
                    if (!geoInfoNull.isEmpty()) {
                        isGeoOk = Boolean.FALSE;
                    }
                    break;
            }
        }
        logEnd(className);
        if (Boolean.TRUE.equals(isGeoOk)) {
            return this.loadOggettoIstanzaCompleteByIdIstanza(idIstanza);
        }
        return Response.ok(resp).status(200).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }

    /**
     * Load oggetto istanza complete by id istanza response.
     *
     * @param idIstanza the id istanza
     * @return the response
     */
    private Response loadOggettoIstanzaCompleteByIdIstanza(Long idIstanza) {
        logBegin(className);
        List<OggettoIstanzaExtendedDTO> oggettiIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaByIdIstanza(idIstanza);
        if (null == oggettiIstanzaList) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, "idIstanza [" + idIstanza + "] : Errore nel recupero dell'istanza");
            return getResponseError(className, error);
        }
        logEnd(className);
        return Response.ok(getOggettoUbicazioneGeoRefList(oggettiIstanzaList)).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }

    /**
     * Update oggetto.
     *
     * @param oggettoIstanza the oggetto istanza
     */
    private void updateOggetto(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza) {
        Boolean updateOggetto = Boolean.FALSE;

        if (null != oggettoIstanza.getIdIstanza()) {
            List<IstanzaExtendedDTO> istanzeList = istanzaDAO.loadIstanza(oggettoIstanza.getIdIstanza());
            if (!istanzeList.isEmpty()) {
                StatoIstanzaDTO statoIstanza = istanzeList.get(0).getStatoIstanza();
                if (null != statoIstanza) {
                    if (!statoIstanza.getCodiceStatoIstanza().equalsIgnoreCase("BOZZA")) {
                        updateOggetto = Boolean.TRUE;
                    } else {
                        if (null != oggettoIstanza.getIdOggetto()) {
                            List<OggettoExtendedDTO> oggettiList = oggettoDAO.loadOggettoById(oggettoIstanza.getIdOggetto());
                            if (!oggettiList.isEmpty()) {
                                StatoOggettoDTO statoOggetto = oggettiList.get(0).getStatoOggetto();
                                if (null != statoOggetto && !statoOggetto.getCodStatoOggetto().equalsIgnoreCase("BOZZA")) {
                                    updateOggetto = Boolean.TRUE;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (Boolean.TRUE.equals(updateOggetto)) {
            // costruisce oggetto
            OggettoDTO oggetto = this.buildOggetto(oggettoIstanza);
            //aggiorna oggetto
            oggettoDAO.updateOggetto(oggetto);
            // costruisce ubicazioni
            List<UbicazioneOggettoDTO> ubicazioniOggetto = this.buildUbicazioneOggetto(oggettoIstanza);
            // aggiorna ubicazioni oggetto
            for (UbicazioneOggettoDTO ubicazioneOggetto : ubicazioniOggetto) {
                ubicazioneOggettoDAO.updateUbicazioneOggetto(ubicazioneOggetto);
            }
        }
    }

    /**
     * Build oggetto oggetto dto.
     *
     * @param oggettoIstanza the oggetto istanza
     * @return the oggetto dto
     */
    private OggettoDTO buildOggetto(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza) {
        OggettoDTO oggetto = new OggettoDTO();
        oggetto.setIdOggetto(oggettoIstanza.getIdOggetto());
        oggetto.setIdMasterdata(oggettoIstanza.getIdMasterdata());
        oggetto.setCoordinataX(oggettoIstanza.getCoordinataX());
        oggetto.setCoordinataY(oggettoIstanza.getCoordinataY());
        oggetto.setDenOggetto(oggettoIstanza.getDenOggetto());
        oggetto.setDesOggetto(oggettoIstanza.getDesOggetto());
        oggetto.setGestAttoreUpd(oggettoIstanza.getGestAttoreUpd());
        return oggetto;
    }

    /**
     * Build ubicazione oggetto list.
     *
     * @param oggettoIstanza the oggetto istanza
     * @return the list
     */
    private List<UbicazioneOggettoDTO> buildUbicazioneOggetto(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza) {
        List<UbicazioneOggettoDTO> ubicazioniOggetto = new ArrayList<>();
        List<UbicazioneOggettoIstanzaDTO> ubicazioniOggettoIstanza = oggettoIstanza.getUbicazioneOggettoIstanzaDTO();
        for (UbicazioneOggettoIstanzaDTO ubicazioneOggettoIstanza : ubicazioniOggettoIstanza) {
            UbicazioneOggettoDTO ubicazioneOggetto = new UbicazioneOggettoDTO();
            ubicazioneOggetto.setIdComune(ubicazioneOggettoIstanza.getIdComune());
            ubicazioneOggetto.setDenIndirizzo(ubicazioneOggettoIstanza.getDenIndirizzo());
            ubicazioneOggetto.setNumCivico(ubicazioneOggettoIstanza.getNumCivico());
            ubicazioneOggetto.setDesLocalita(ubicazioneOggettoIstanza.getDesLocalita());
            ubicazioneOggetto.setIndGeoProvenienza(ubicazioneOggettoIstanza.getIndGeoProvenienza());
            ubicazioneOggetto.setGestAttoreUpd(ubicazioneOggettoIstanza.getGestAttoreUpd());
            ubicazioniOggetto.add(ubicazioneOggetto);
        }
        return ubicazioniOggetto;
    }

    /**
     * Gets oggetto ubicazione geo ref list.
     *
     * @param oggettiIstanzaList the oggetti istanza list
     * @return the oggetto ubicazione geo ref list
     */
    private List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> getOggettoUbicazioneGeoRefList(List<OggettoIstanzaExtendedDTO> oggettiIstanzaList) {
        List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> oggettoIstanzaUbicazioneGeoRefList = new ArrayList<>();
        for (OggettoIstanzaExtendedDTO oggettoIstanza : oggettiIstanzaList) {
            OggettoIstanzaUbicazioneGeoRefExtendedDTO oggettoIstanzaUbicazioneGeoRef = new OggettoIstanzaUbicazioneGeoRefExtendedDTO();
            oggettoIstanzaUbicazioneGeoRef.setIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
            oggettoIstanzaUbicazioneGeoRef.setIdOggetto(oggettoIstanza.getOggetto().getIdOggetto());
            oggettoIstanzaUbicazioneGeoRef.setIdIstanza(oggettoIstanza.getIdIstanza());
            oggettoIstanzaUbicazioneGeoRef.setTipologiaOggetto(oggettoIstanza.getTipologiaOggetto());
            oggettoIstanzaUbicazioneGeoRef.setIndGeoStato(oggettoIstanza.getIndGeoStato());
            oggettoIstanzaUbicazioneGeoRef.setDenOggetto(oggettoIstanza.getDenOggetto());
            oggettoIstanzaUbicazioneGeoRef.setDesOggetto(oggettoIstanza.getDesOggetto());
            oggettoIstanzaUbicazioneGeoRef.setCoordinataX(oggettoIstanza.getCoordinataX());
            oggettoIstanzaUbicazioneGeoRef.setCoordinataY(oggettoIstanza.getCoordinataY());
            oggettoIstanzaUbicazioneGeoRef.setIdMasterdata(oggettoIstanza.getIdMasterdata());
            oggettoIstanzaUbicazioneGeoRef.setCodOggettoFonte(oggettoIstanza.getCodOggettoFonte());
            oggettoIstanzaUbicazioneGeoRef.setCodUtenza(oggettoIstanza.getCodUtenza());
            oggettoIstanzaUbicazioneGeoRef.setNoteAttoPrecedente(oggettoIstanza.getNoteAttoPrecedente());
            oggettoIstanzaUbicazioneGeoRef.setIdMasterdataOrigine(oggettoIstanza.getIdMasterdataOrigine());
            oggettoIstanzaUbicazioneGeoRef.setIndLivello(oggettoIstanza.getIndLivello());
            oggettoIstanzaUbicazioneGeoRef.setGestUID(oggettoIstanza.getGestUID());

            List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioniOggettoIstanzaList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
            oggettoIstanzaUbicazioneGeoRef.setUbicazioneOggettoIstanza(ubicazioniOggettoIstanzaList);

            List<GeoAreaOggettoIstanzaDTO> geoAreaOggettoIstanza = geoAreaOggettoIstanzaDAO.loadGeoAreaOggettoIstanzaByIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
            oggettoIstanzaUbicazioneGeoRef.setGeoAreaOggettoIstanza(geoAreaOggettoIstanza);

            List<GeoLineaOggettoIstanzaDTO> geoLineaOggettoIstanza = geoLineaOggettoIstanzaDAO.loadGeoLineaOggettoIstanzaByIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
            oggettoIstanzaUbicazioneGeoRef.setGeoLineaOggettoIstanza(geoLineaOggettoIstanza);

            List<GeoPuntoOggettoIstanzaDTO> geoPuntoOggettoIstanza = geoPuntoOggettoIstanzaDAO.loadGeoPuntoOggettoIstanzaByIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
            oggettoIstanzaUbicazioneGeoRef.setGeoPuntoOggettoIstanza(geoPuntoOggettoIstanza);

            oggettoIstanzaUbicazioneGeoRefList.add(oggettoIstanzaUbicazioneGeoRef);
        }
        return oggettoIstanzaUbicazioneGeoRefList;
    }

}
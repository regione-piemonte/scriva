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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import it.csi.scriva.scrivabesrv.business.be.IstanzaOsservazioni;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OsservazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoOsservazioneContatoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoOsservazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AllegatiService;
import it.csi.scriva.scrivabesrv.business.be.service.OsservazioneService;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoOsservazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.PaginationHeaderDTO;
import it.csi.scriva.scrivabesrv.dto.StatoOsservazioneContatoreDTO;
import it.csi.scriva.scrivabesrv.dto.StatoOsservazioneContatoreExtDTO;
import it.csi.scriva.scrivabesrv.dto.StatoOsservazioneDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.FasePubblicazioneEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.StatoOsservazioneEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.AllegatiManager;

/**
 * The type Istanza osservazioni api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IstanzaOsservazioniApiServiceImpl extends BaseApiServiceImpl implements IstanzaOsservazioni {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private OsservazioneDAO osservazioneDAO;
    
    @Autowired
    private IstanzaDAO istanzaDAO;

    @Autowired
    private OsservazioneService osservazioneService;

    @Autowired
    private StatoOsservazioneDAO statoOsservazioneDAO;

    @Autowired
    private StatoOsservazioneContatoreDAO statoOsservazioneContatoreDAO;

    @Autowired
    private AllegatiService allegatiService;

    @Autowired
    private AllegatiManager allegatiManager;

    @Autowired
    private AllegatoIstanzaDAO allegatoIstanzaDAO;

    /**
     * Load osservazioni response.
     *
     * @param xRequestAuth         the x request auth
     * @param xRequestId           the x request id
     * @param idIstanza            the id istanza
     * @param codStatoOsservazione the cod stato osservazione
     * @param offset               the offset
     * @param limit                the limit
     * @param sort                 the sort
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @return the response
     */
//    @Override
//    public Response loadOsservazioni(
//    	String xRequestAuth, String xRequestId, Long idIstanza, String codStatoOsservazione, 
//    	Long idOsservazione, String offset, String limit, String sort, SecurityContext securityContext, 
//    	HttpHeaders httpHeaders, HttpServletRequest httpRequest
//    ) {
//        logBeginInfo(className, "idIstanza: [" + idIstanza + "] - codStatoOsservazione : [" + codStatoOsservazione + "] - idOsservazione : [" + idOsservazione + "] - offset : [" + offset + "] - limit : [" + limit + "] - sort : [" + sort + "]");
//        try {
//            attoreScriva = getAttoreScriva(httpHeaders);
//        } catch (GenericException e) {
//            logError(className, e);
//            return getResponseError(className, e.getError());
//        }
//
//        List<OggettoOsservazioneExtendedDTO> listaOsservazioni = osservazioneService.loadOsservazioni(
//        	idIstanza, codStatoOsservazione, idOsservazione, attoreScriva, offset, limit, sort
//        );
//        
//        if (listaOsservazioni.isEmpty()) {
//            ErrorDTO error = getErrorManager().getError("404", "", "Elemento non trovato", null, null);
//            return getResponseError(className, error);
//        }
//        List<OggettoOsservazioneExtendedDTO> listaOsservazioniSize = 
//        	osservazioneService.loadOsservazioni(idIstanza, codStatoOsservazione, idOsservazione, attoreScriva, null, null, sort);
//        
//        List<StatoOsservazioneContatoreDTO> listStatoOsservazioneContatore = 
//        	statoOsservazioneContatoreDAO.loadStatoOsservazioniContatore(attoreScriva.getCodiceFiscale());
//        
//        StatoOsservazioneContatoreExtDTO statoOsservazioneContatoreExt = new StatoOsservazioneContatoreExtDTO();
//        statoOsservazioneContatoreExt.setStato(listStatoOsservazioneContatore);
//        
//        PaginationHeaderDTO paginationHeader = offset != null && limit != null ? getPaginationHeaderNew(
//        	listaOsservazioniSize, Integer.valueOf(offset), Integer.valueOf(limit)
//        	) : null;
//        
//        // gestito ordinamento quando arriva il parametro sort da FE den_oggetto
//        List<OggettoOsservazioneExtendedDTO> listaOsservazioniSort = sortList(sort, listaOsservazioni); 
//        
//        return Response.ok(listaOsservazioniSort).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, 
//        	attoreScriva != null ? attoreScriva.getProfiloAppIstanza() : null).header(
//        		"PaginationInfo", paginationHeader != null ? paginationHeader.getMap() : null
//        			).header("Counter", listStatoOsservazioneContatore != null ? statoOsservazioneContatoreExt.getMapTree() : null).build();
//    }
    
    @Override
    public Response loadOsservazioniSintesi(
    	String xRequestAuth, String xRequestId, Long idIstanza, String codStatoOsservazione, 
    	Long idOsservazione, String offset, String limit, String sort, SecurityContext securityContext, 
    	HttpHeaders httpHeaders, HttpServletRequest httpRequest
    ) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "] - codStatoOsservazione : [" + codStatoOsservazione + "] - idOsservazione : [" + idOsservazione + "] - offset : [" + offset + "] - limit : [" + limit + "] - sort : [" + sort + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, e.getError());
        }

        List<OggettoOsservazioneExtendedDTO> listaOsservazioni = osservazioneService.loadOsservazioniSintesi(
        	idIstanza, codStatoOsservazione, idOsservazione, attoreScriva, offset, limit, sort
        );
        
        if (listaOsservazioni.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("404", "", "Elemento non trovato", null, null);
            return getResponseError(className, error);
        }
        List<OggettoOsservazioneExtendedDTO> listaOsservazioniSize = 
        	osservazioneService.loadOsservazioniSintesi(idIstanza, codStatoOsservazione, idOsservazione, attoreScriva, null, null, sort);
        
        List<StatoOsservazioneContatoreDTO> listStatoOsservazioneContatore = 
        	statoOsservazioneContatoreDAO.loadStatoOsservazioniContatore(attoreScriva.getCodiceFiscale());
        
        StatoOsservazioneContatoreExtDTO statoOsservazioneContatoreExt = new StatoOsservazioneContatoreExtDTO();
        statoOsservazioneContatoreExt.setStato(listStatoOsservazioneContatore);
        
        PaginationHeaderDTO paginationHeader = offset != null && limit != null ? getPaginationHeaderNew(
        	listaOsservazioniSize, Integer.valueOf(offset), Integer.valueOf(limit)
        	) : null;
        
       
        return Response.ok(listaOsservazioni).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, 
        	attoreScriva != null ? attoreScriva.getProfiloAppIstanza() : null).header(
        		"PaginationInfo", paginationHeader != null ? paginationHeader.getMap() : null
        			).header("Counter", listStatoOsservazioneContatore != null ? statoOsservazioneContatoreExt.getMapTree() : null).build();
    }


    private List<OggettoOsservazioneExtendedDTO> sortList(String sort, List<OggettoOsservazioneExtendedDTO> listaOsservazioni) {
    	String sortField;
        char order = '-';
        if (sort != null && !sort.isEmpty()) {
        	sortField = sort.charAt(0)== order ? sort.substring(1) : sort;
        	Comparator<OggettoOsservazioneExtendedDTO> c = new Comparator<OggettoOsservazioneExtendedDTO>() {
                public int compare(OggettoOsservazioneExtendedDTO o1, OggettoOsservazioneExtendedDTO o2) {
                	if (sortField.equals("den_oggetto")) {
                		return o1.getIstanza().getOggettoIstanza().get(0).getDenOggetto().compareToIgnoreCase(o2.getIstanza().getOggettoIstanza().get(0).getDenOggetto());
                	}
                	return 0;
                }
            };
            if (sort.charAt(0)== order) {
            	Collections.sort(listaOsservazioni, c.reversed());
            }
            else {
            	Collections.sort(listaOsservazioni, c);
            }
        }
		return listaOsservazioni;
	}

	/**
     * Save osservazione response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param osservazione    the osservazione
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response saveOsservazione(String xRequestAuth, String xRequestId, OggettoOsservazioneExtendedDTO osservazione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, osservazione);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, e.getError());
        }

        //SCRIVA-941 aggiungere il controllo sullo stato dell'istanza
        List<IstanzaExtendedDTO> istanza = istanzaDAO.loadIstanza(osservazione.getIstanza().getIdIstanza());
        if (istanza.size()>0 && istanza.get(0).getDataFineOsservazioni().after(new java.sql.Timestamp(System.currentTimeMillis())))  {
        	if (istanza.get(0).getStatoIstanza().getDescrizioneStatoIstanza().equals(FasePubblicazioneEnum.CONCLUSA.getDescrizione())) {
        		ErrorDTO error = getErrorManager().getError("404", "", "Operazione non permessa pratica Conclusa", null, null);
            	logError(className, error);
                logEnd(className);
            	return Response.serverError().entity(error).status(404).build();
            }
        }
        else {
        	ErrorDTO error = getErrorManager().getError("404", "", "Operazione non permessa data osservazione scaduta", null, null);
        	logError(className, error);
            logEnd(className);
        	return Response.serverError().entity(error).status(404).build();
        }
        
        osservazione.setCfOsservazioneIns(attoreScriva.getCodiceFiscale());
        osservazione.setGestAttoreIns(attoreScriva.getCodiceFiscale());
        String statoOsservazionePrimoStep = "Bozza";
        List<StatoOsservazioneDTO> statoOsservazione = statoOsservazioneDAO.loadStatoOsservazioni(statoOsservazionePrimoStep);
        osservazione.setStatoOsservazione(statoOsservazione.get(0));
        Long id = osservazioneDAO.saveOsservazione(osservazione.getDTO());
        if (null == id) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        } else {
            OggettoOsservazioneExtendedDTO oggettoOsservazioneDTO = osservazioneDAO.loadOsservazioneById(id, null).get(0);
            URI uri = null;
            try {
                uri = new URI("/osservazioni/" + id);
            } catch (URISyntaxException e) {
                logError(className, e);
            }
            logEnd(className);
            return Response.ok(oggettoOsservazioneDTO).status(201).location(uri).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
        }
    }

    /**
     * Update osservazione response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param osservazione    the osservazione
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response updateOsservazione(String xRequestAuth, String xRequestId, OggettoOsservazioneExtendedDTO osservazione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, osservazione);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, e.getError());
        }
        //SCRIVA-892 - non si possono inviare osservazioni oltre scadenza
        if (osservazioneService.isDataOsservazioneExpired(osservazione.getIdIstanzaOsservazione(), attoreScriva)) {
        	ErrorDTO error = getErrorManager().getError("404", "", "Operazione non permesa - Data osservazione scaduta", null, null);
            logError(className, error);
            logEnd(className);
            return Response.serverError().entity(error).status(404).build(); 
		}
        
        //SCRIVA-905 osservazioni in stato INVIATE o PUBBLICATE, non devono poter essere cancellabili o modificabili
        List<OggettoOsservazioneExtendedDTO> osservazioneDBList = osservazioneDAO.loadOsservazioneById(osservazione.getIdIstanzaOsservazione(), attoreScriva);
        
        if (osservazioneDBList.get(0).getStatoOsservazione().getDesStatoOsservazione().equals(StatoOsservazioneEnum.PUBBLICATA.getDescrizione()) ||
        	osservazioneDBList.get(0).getStatoOsservazione().getDesStatoOsservazione().equals(StatoOsservazioneEnum.INVIATA.getDescrizione())) {
        	ErrorDTO error = getErrorManager().getError("404", "", "Osservazione non modificabile", null, null);
        	logError(className, error);
            logEnd(className);
        	return Response.serverError().entity(error).status(404).build();
        }
        
        osservazione.setCfOsservazioneIns(attoreScriva.getCodiceFiscale());
        osservazione.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        List<StatoOsservazioneDTO> statoOsservazione = statoOsservazioneDAO.loadStatoOsservazioni(osservazione.getStatoOsservazione().getCodStatoOsservazione());
        
        osservazione.getStatoOsservazione().setIdStatoOsservazione(statoOsservazione.get(0).getIdStatoOsservazione());
        Integer res = osservazioneDAO.updateOsservazione(osservazione.getDTO());
        return getResponseSaveUpdate(res != null && res >= 0 ? osservazioneDAO.loadOsservazioneById(osservazione.getIdIstanzaOsservazione(), null).get(0) : null, className);
    }

    /**
     * Delete osservazione response.
     *
     * @param xRequestAuth          the x request auth
     * @param xRequestId            the x request id
     * @param idIstanzaOsservazione the id istanza osservazione
     * @param securityContext       the security context
     * @param httpHeaders           the http headers
     * @param httpRequest           the http request
     * @return the response
     */
    @Override
    public Response deleteOsservazione(String xRequestAuth, String xRequestId, Long idIstanzaOsservazione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanzaOsservazione);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, e.getError());
        }
        
        //SCRIVA-905 osservazioni in stato INVIATE o PUBBLICATE, non devono poter essere cancellabili o modificabili 
        OggettoOsservazioneExtendedDTO oggettoOsservazioneDTO = 
        	osservazioneDAO.loadOsservazioneById(idIstanzaOsservazione, null).size()>0 ? osservazioneDAO.loadOsservazioneById(idIstanzaOsservazione, null).get(0):null;
        if (oggettoOsservazioneDTO!=null) {
        	if (oggettoOsservazioneDTO.getStatoOsservazione().getCodStatoOsservazione().equals(StatoOsservazioneEnum.PUBBLICATA.getDescrizione()) ||
        		oggettoOsservazioneDTO.getStatoOsservazione().getCodStatoOsservazione().equals(StatoOsservazioneEnum.INVIATA.getDescrizione())) {
            	ErrorDTO error = getErrorManager().getError("404", "", "Operazione non permessa ", null, null);
            	logError(className, error);
                logEnd(className);
            	return Response.serverError().entity(error).status(404).build();
            }
        }
        else {
        	ErrorDTO error = getErrorManager().getError("404", "", "Errore nella cancellazione dell'elemento; causa: elemento non trovato", null, null);
            logError(className, error);
            logEnd(className);
            return Response.serverError().entity(error).status(404).build();
        }

        //SCRIVA-850: snellita la logica di recupero degli allegati per permettere la loro cancellazione e dopo la cancellazione dell'osservazione  
        //List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatiService.loadAllegatiByIdIstanza(null, "", "", "", "", idIstanzaOsservazione, "", "", null, null, "", false, attoreScriva);
        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanza(
        		null, "", "", "", "", idIstanzaOsservazione, "", false, null, null, "", false, null
        );
        
        for (AllegatoIstanzaExtendedDTO allegatoIstanzaExtendedDTO : allegatoIstanzaList) {
            String uuidIndex = allegatoIstanzaExtendedDTO.getUuidIndex();

            // cancellazione file da index
            ErrorDTO error = allegatiManager.deleteContenutoByUuid(uuidIndex);
            if (error != null) {
                getResponseError(className, error);
            }

            //mettere update
            allegatoIstanzaDAO.updateIdAllegatoIstanzaPadre(uuidIndex);
            
            // In caso di esito positivo eliminazione del record in tabella
            Integer res = allegatoIstanzaDAO.deleteAllegatoIstanzaByUuidIndex(uuidIndex);
            if (res == null) {
                error = getErrorManager().getError("500", "E100", null, null, null);
                logError(className, error);
                logEnd(className);
                return Response.serverError().entity(error).status(500).build();
            } else if (res < 1) {
                error = getErrorManager().getError("404", "", "Errore nella cancellazione dell'elemento; causa: elemento non trovato", null, null);
                logError(className, error);
                logEnd(className);
                return Response.serverError().entity(error).status(404).build();
            } else {
                logEnd(className);
                //return Response.noContent().build();
            }
        }

        Integer res = osservazioneDAO.deleteOsservazioneByIdIstanzaOsservazione(idIstanzaOsservazione);
        if (res == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, error);
            logEnd(className);
            return Response.serverError().entity(error).status(500).build();
        } else if (res < 1) {
            ErrorDTO error = getErrorManager().getError("404", "", "Errore nella cancellazione dell'elemento; causa: elemento non trovato", null, null);
            logError(className, error);
            logEnd(className);
            return Response.serverError().entity(error).status(404).build();
        } else {
            logEnd(className);
            return Response.noContent().build();
        }
    }

}
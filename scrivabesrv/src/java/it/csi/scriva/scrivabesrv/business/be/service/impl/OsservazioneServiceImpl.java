/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaCompetenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaAreaProtettaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaNatura2000DAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OsservazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.OsservazioneService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaAreaProtettaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaAreaProtettaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000DTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000ExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoOsservazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.PubCompetenzaTerritorioDTO;
import it.csi.scriva.scrivabesrv.dto.PubNaturaGiuridicaDTO;
import it.csi.scriva.scrivabesrv.dto.PubOggettoIstanzaUbicazioneDTO;
import it.csi.scriva.scrivabesrv.dto.PubSoggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PubTipoSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.PubUbicazioneOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The type Tipi evento service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class OsservazioneServiceImpl extends BaseApiServiceImpl implements OsservazioneService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private OsservazioneDAO osservazioneDAO;

    @Autowired
    private OggettoIstanzaDAO oggettoIstanzaDAO;

    @Autowired
    private IstanzaCompetenzaDAO istanzaCompetenzaDAO;

    @Autowired
    private SoggettoIstanzaDAO soggettoIstanzaDAO;

    @Autowired
    private UbicazioneOggettoIstanzaDAO ubicazioneOggettoIstanzaDAO;
    
    @Autowired
    private OggettoIstanzaAreaProtettaDAO oggettoIstanzaAreaProtettaDAO;
    
    @Autowired
    private OggettoIstanzaNatura2000DAO oggettoIstanzaNatura2000DAO;
    
    

    /**
     * Load osservazioni list.
     *
     * @param idIstanza            the id istanza
     * @param codStatoOsservazione the cod stato osservazione
     * @param idOsservazione       the id osservazione
     * @param attoreScriva         the attore scriva
     * @param offset               the offset
     * @param limit                the limit
     * @param sort                 the sort
     * @return the list
     */
    @Override
    public List<OggettoOsservazioneExtendedDTO> loadOsservazioni(Long idIstanza, String codStatoOsservazione, Long idOsservazione, AttoreScriva attoreScriva, String offset, String limit, String sort) {
        logBegin(className);
        List<OggettoOsservazioneExtendedDTO> osservazioni = null;
        try {
            osservazioni = osservazioneDAO.loadOsservazioni(idIstanza, codStatoOsservazione, idOsservazione, attoreScriva, offset, limit, sort);
            if (StringUtils.isNotBlank(offset) || StringUtils.isNotBlank(limit)) {
                for (OggettoOsservazioneExtendedDTO oggettoOsservazioneExtendedDTO : osservazioni) {
                    Long istanza = oggettoOsservazioneExtendedDTO.getIstanza().getIdIstanza();
                    List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(istanza);
                    oggettoOsservazioneExtendedDTO.getIstanza().setCompetenzaTerritorio(mapIstanzaCompetenza(istanzaCompetenzaList));
                    oggettoOsservazioneExtendedDTO.getIstanza().setOggettoIstanza(mapSoggettoIstanza(istanza));
                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanzaByIdIstanza(istanza);
                    oggettoOsservazioneExtendedDTO.getIstanza().setSoggettoIstanza(mapSoggettoIstanza(soggettoIstanzaList));
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return osservazioni;
    }
    
    /**
     * Load osservazioni list.
     *
     * @param idIstanza            the id istanza
     * @param codStatoOsservazione the cod stato osservazione
     * @param idOsservazione       the id osservazione
     * @param attoreScriva         the attore scriva
     * @param offset               the offset
     * @param limit                the limit
     * @param sort                 the sort
     * @return the list
     */
    @Override
    public List<OggettoOsservazioneExtendedDTO> loadOsservazioniSintesi(Long idIstanza, String codStatoOsservazione, Long idOsservazione, AttoreScriva attoreScriva, String offset, String limit, String sort) {
        logBegin(className);
        List<OggettoOsservazioneExtendedDTO> osservazioni = null;
        try {
            osservazioni = osservazioneDAO.loadOsservazioniSintesi(idIstanza, codStatoOsservazione, idOsservazione, attoreScriva, offset, limit, sort);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return osservazioni;
    }


    /**
     * Map istanza competenza list.
     *
     * @param istanzaCompetenzaList the istanza competenza list
     * @return the list
     */
    private List<PubCompetenzaTerritorioDTO> mapIstanzaCompetenza(List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList) {
        logBegin(className);
        List<PubCompetenzaTerritorioDTO> pubCompetenzaTerritorioList = new ArrayList<>();
        for (IstanzaCompetenzaExtendedDTO istanzaCompetenzaExtendedDTO : istanzaCompetenzaList) {
            PubCompetenzaTerritorioDTO pubCompetenzaTerritorio = new PubCompetenzaTerritorioDTO();
            CompetenzaTerritorioExtendedDTO competenzaTerritorio = istanzaCompetenzaExtendedDTO.getCompetenzaTerritorio();
            if (competenzaTerritorio != null && istanzaCompetenzaExtendedDTO.getFlgAutoritaPrincipale() != null && Boolean.TRUE.equals(istanzaCompetenzaExtendedDTO.getFlgAutoritaPrincipale())) {
                pubCompetenzaTerritorio.setCapCompetenza(competenzaTerritorio.getCapCompetenza());
                pubCompetenzaTerritorio.setCodCompetenzaTerritorio(competenzaTerritorio.getCodCompetenzaTerritorio());
                pubCompetenzaTerritorio.setComuneCompetenza(competenzaTerritorio.getComuneCompetenza());
                pubCompetenzaTerritorio.setDataFineValidita(competenzaTerritorio.getDataFineValidita());
                pubCompetenzaTerritorio.setDesCompetenzaTerritorio(competenzaTerritorio.getDesCompetenzaTerritorio());
                pubCompetenzaTerritorio.setDesMailPec(competenzaTerritorio.getDesEmailPec());
                pubCompetenzaTerritorio.setIdCompetenzaTerritorio(competenzaTerritorio.getIdCompetenzaTerritorio());
                pubCompetenzaTerritorio.setIndirizzoCompetenza(competenzaTerritorio.getIndirizzoCompetenza());
                pubCompetenzaTerritorio.setNumCivicoIndirizzo(competenzaTerritorio.getNumCivicoIndirizzo());
                pubCompetenzaTerritorio.setOrario(competenzaTerritorio.getOrario());
                //pubCompetenzaTerritorio.setResponsabile(competenzaTerritorio.getResponsabile());
                pubCompetenzaTerritorio.setSitoWeb(competenzaTerritorio.getSitoWeb());
                pubCompetenzaTerritorio.setTipoCompetenza(competenzaTerritorio.getTipoCompetenza());
                pubCompetenzaTerritorio.setCodIpa(competenzaTerritorio.getCodIpa());
                pubCompetenzaTerritorioList.add(pubCompetenzaTerritorio);
            }
        }
        logEnd(className);
        return pubCompetenzaTerritorioList;
    }

    /**
     * Map soggetto istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    private List<PubOggettoIstanzaUbicazioneDTO> mapSoggettoIstanza(Long idIstanza) {
        logBegin(className);
        List<PubOggettoIstanzaUbicazioneDTO> pubOggettoIstanzaUbicazioneList = new ArrayList<>();
        List<OggettoIstanzaExtendedDTO> oggettoIstanzaExtendedList = oggettoIstanzaDAO.loadOggettoIstanzaByIdIstanza(idIstanza);
        for (OggettoIstanzaExtendedDTO oggettoIstanzaExtendedDTO : oggettoIstanzaExtendedList) {
            PubOggettoIstanzaUbicazioneDTO pubOggettoIstanzaUbicazioneDTO = new PubOggettoIstanzaUbicazioneDTO();
			/*
			pubOggettoIstanzaUbicazioneDTO.setCodScriva(oggettoIstanzaExtendedDTO.getCodScriva() != null
					? String.valueOf(oggettoIstanzaExtendedDTO.getCodScriva())
					: null);

			 */
            List<OggettoIstanzaNatura2000ExtendedDTO> sitiNatura2000 = oggettoIstanzaNatura2000DAO.loadOggettiIstanzaNatura2000(null, oggettoIstanzaExtendedDTO.getIdOggettoIstanza());
            List<OggettoIstanzaAreaProtettaExtendedDTO> areeProtette = oggettoIstanzaAreaProtettaDAO.loadOggettiIstanzaAreaProtetta(null, oggettoIstanzaExtendedDTO.getIdOggettoIstanza());
            
            pubOggettoIstanzaUbicazioneDTO.setCoordinataX(oggettoIstanzaExtendedDTO.getCoordinataX());
            pubOggettoIstanzaUbicazioneDTO.setCoordinataY(oggettoIstanzaExtendedDTO.getCoordinataY());
            pubOggettoIstanzaUbicazioneDTO.setDenOggetto(oggettoIstanzaExtendedDTO.getDenOggetto());
            pubOggettoIstanzaUbicazioneDTO.setDesOggetto(oggettoIstanzaExtendedDTO.getDesOggetto());
            pubOggettoIstanzaUbicazioneDTO.setIdOggettoIstanza(oggettoIstanzaExtendedDTO.getIdOggettoIstanza());
            pubOggettoIstanzaUbicazioneDTO.setTipologiaOggetto(oggettoIstanzaExtendedDTO.getTipologiaOggetto());
            pubOggettoIstanzaUbicazioneDTO.setUbicazioneOoggetto(mapPubUbicazioneOggettoIstanza(oggettoIstanzaExtendedDTO.getIdOggettoIstanza()));
            
            pubOggettoIstanzaUbicazioneDTO.setOggettoIstanzaAreaProtetta(areeProtette);
            pubOggettoIstanzaUbicazioneDTO.setOggettoIstanzaNatura2000(sitiNatura2000);
            
            pubOggettoIstanzaUbicazioneList.add(pubOggettoIstanzaUbicazioneDTO);
        }
        logEnd(className);
        return pubOggettoIstanzaUbicazioneList;
    }

    /**
     * Map pub ubicazione oggetto istanza list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    private List<PubUbicazioneOggettoIstanzaDTO> mapPubUbicazioneOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        List<PubUbicazioneOggettoIstanzaDTO> pubUbicazioneOggettoIstanzaList = new ArrayList<>();
        List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaExtendedList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
        for (UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanzaExtendedDTO : ubicazioneOggettoIstanzaExtendedList) {
            PubUbicazioneOggettoIstanzaDTO pubUbicazioneOggettoIstanzaDTO = new PubUbicazioneOggettoIstanzaDTO();
            pubUbicazioneOggettoIstanzaDTO.setComune(ubicazioneOggettoIstanzaExtendedDTO.getComune());
            pubUbicazioneOggettoIstanzaDTO.setDenIndirizzo(ubicazioneOggettoIstanzaExtendedDTO.getDenIndirizzo());
            pubUbicazioneOggettoIstanzaDTO.setDesLocalita(ubicazioneOggettoIstanzaExtendedDTO.getDesLocalita());
            pubUbicazioneOggettoIstanzaDTO.setIdOggettoIstanza(ubicazioneOggettoIstanzaExtendedDTO.getIdOggettoIstanza());
            pubUbicazioneOggettoIstanzaDTO.setNumCivico(ubicazioneOggettoIstanzaExtendedDTO.getNumCivico());
            pubUbicazioneOggettoIstanzaList.add(pubUbicazioneOggettoIstanzaDTO);
        }
        logEnd(className);
        return pubUbicazioneOggettoIstanzaList;
    }

    /**
     * Map soggetto istanza list.
     *
     * @param soggettoIstanzaExtendedList the soggetto istanza extended list
     * @return the list
     */
    private List<PubSoggettoIstanzaDTO> mapSoggettoIstanza(List<SoggettoIstanzaExtendedDTO> soggettoIstanzaExtendedList) {
        logBegin(className);
        List<PubSoggettoIstanzaDTO> pubSoggettoIstanzaList = new ArrayList<>();
        for (SoggettoIstanzaExtendedDTO soggettoIstanzaExtendedDTO : soggettoIstanzaExtendedList) {
            if (soggettoIstanzaExtendedDTO.getIdSoggettoPadre() == null) {
                PubSoggettoIstanzaDTO pubSoggettoIstanzaDTO = new PubSoggettoIstanzaDTO();
                pubSoggettoIstanzaDTO.setCapSedeLegale(soggettoIstanzaExtendedDTO.getCapSedeLegale());
                pubSoggettoIstanzaDTO.setCodiceFiscale(soggettoIstanzaExtendedDTO.getCfSoggetto());
                pubSoggettoIstanzaDTO.setCognome(soggettoIstanzaExtendedDTO.getCognome());
                pubSoggettoIstanzaDTO.setComuneSedeLegale(soggettoIstanzaExtendedDTO.getComuneSedeLegale());
                pubSoggettoIstanzaDTO.setDenominazione(soggettoIstanzaExtendedDTO.getDenSoggetto());
                pubSoggettoIstanzaDTO.setIdSoggettoIstanza(soggettoIstanzaExtendedDTO.getIdSoggettoIstanza());
                pubSoggettoIstanzaDTO.setIdSoggettoPadre(soggettoIstanzaExtendedDTO.getIdSoggettoPadre());
                if (soggettoIstanzaExtendedDTO.getTipoNaturaGiuridica() != null) {
                    PubNaturaGiuridicaDTO naturaGiuridica = new PubNaturaGiuridicaDTO();
                    naturaGiuridica.setCodNaturaGiuridica(soggettoIstanzaExtendedDTO.getTipoNaturaGiuridica().getCodiceTipoNaturaGiuridica());
                    naturaGiuridica.setDesNaturaGiuridica(soggettoIstanzaExtendedDTO.getTipoNaturaGiuridica().getDescrizioneTipoNaturaGiuridica());
                    pubSoggettoIstanzaDTO.setNaturaGiuridica(naturaGiuridica);
                }
                pubSoggettoIstanzaDTO.setNazioneSedeLegale(soggettoIstanzaExtendedDTO.getNazioneSedeLegale());
                pubSoggettoIstanzaDTO.setNome(soggettoIstanzaExtendedDTO.getNome());
                pubSoggettoIstanzaDTO.setPartitaIva(soggettoIstanzaExtendedDTO.getPartitaIvaSoggetto());
                if (pubSoggettoIstanzaDTO.getTipoSoggetto() != null) {
                    PubTipoSoggettoDTO tipoSoggetto = new PubTipoSoggettoDTO();
                    tipoSoggetto.setCodTipoSoggetto(pubSoggettoIstanzaDTO.getTipoSoggetto().getCodTipoSoggetto());
                    tipoSoggetto.setDesTipoSoggetto(pubSoggettoIstanzaDTO.getTipoSoggetto().getDesTipoSoggetto());
                    pubSoggettoIstanzaDTO.setTipoSoggetto(tipoSoggetto);
                }
                pubSoggettoIstanzaList.add(pubSoggettoIstanzaDTO);
            }
        }
        logEnd(className);
        return pubSoggettoIstanzaList;
    }
    
    /**
     * Restituisce true se la data scadenza osservazione e' scaduta.
     *
     * @param idIstanzaOsservazione
     * @param attoreScriva
     * @return the boolean
     */
    @Override
    public boolean isDataOsservazioneExpired(Long idIstanzaOsservazione, AttoreScriva attoreScriva) {
    	boolean isDataOsservazioneExpired = false;
    	List<OggettoOsservazioneExtendedDTO> oggettoOsservazioneList = 
    		osservazioneDAO.loadOsservazioneById(idIstanzaOsservazione, attoreScriva);
    	Date now = null;
    	Timestamp dataOsservazione=null;
        if (oggettoOsservazioneList!=null) {
        	now = Calendar.getInstance().getTime();
        	dataOsservazione =  oggettoOsservazioneList.get(0).getIstanza().getDataFineOsservazione();
        	if (dataOsservazione==null || now.after(dataOsservazione)) {
        		isDataOsservazioneExpired = true;
        	}
        }
    	return isDataOsservazioneExpired;
    }

}
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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilanteDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ProfiloAppDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAbilitazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AbilitazioniService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAbilitazioneDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ProfiloAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipoAbilitazioneEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Competenze territorio service.
 */
@Component
public class AbilitazioniServiceImpl extends BaseApiServiceImpl implements AbilitazioniService {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Istanza service.
     */
    @Autowired
    IstanzaService istanzaService;

    /**
     * The Compilante dao.
     */
    @Autowired
    CompilanteDAO compilanteDAO;

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Istanza attore dao.
     */
    @Autowired
    IstanzaAttoreDAO istanzaAttoreDAO;

    /**
     * The Profilo app dao.
     */
    @Autowired
    ProfiloAppDAO profiloAppDAO;

    /**
     * The Tipo abilitazione dao.
     */
    @Autowired
    TipoAbilitazioneDAO tipoAbilitazioneDAO;

    /**
     * Load abilitazioni by id istanza cf attore list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    @Override
    public List<TipoAbilitazioneDTO> loadAbilitazioniByIdIstanzaCFAttore(Long idIstanza, AttoreScriva attoreScriva) {
        logBeginInfo(className, idIstanza);
        List<TipoAbilitazioneDTO> tipoAbilitazionList = null;
        try {
            IstanzaExtendedDTO istanza = istanzaService.getIstanza(idIstanza);
            tipoAbilitazionList = istanza != null ? tipoAbilitazioneDAO.loadTipoAbilitazioneByAdempimentoIstanzaCfAttore(istanza.getAdempimento().getIdAdempimento(), idIstanza, attoreScriva.getCodiceFiscale()) : null;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return tipoAbilitazionList;
    }

    /**
     * Load abilitazioni revocabili for istanza by id istanza list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadAbilitazioniRevocabiliForIstanzaByIdIstanza(Long idIstanza, AttoreScriva attoreScriva) {
        logBeginInfo(className, idIstanza);
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList = null;
        try {
            istanzaAttoreList = idIstanza != null ? istanzaAttoreDAO.loadIstanzaAttoreRevocabiliByIdIstanza(idIstanza) : null;
            // SCRIVA-396 : escludere i compilanti con CF=CF ATTORE in linea, più in generale occorre escludere compilanti che sono anche richiedenti.
            istanzaAttoreList = istanzaAttoreList != null ?
                    istanzaAttoreList.stream()
                            .filter(attore ->
                                    !attore.getProfiloApp().getCodProfiloApp().equalsIgnoreCase(ProfiloAppEnum.RICHIEDENTE.name())
                                            && !attore.getCfAttore().equalsIgnoreCase(attoreScriva.getCodiceFiscale()))
                            .collect(Collectors.toList()) :
                    null;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return istanzaAttoreList;
    }

    /**
     * Load abilitazioni concesse for istanza by id istanza list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> loadAbilitazioniConcesseForIstanzaByIdIstanza(Long idIstanza, AttoreScriva attoreScriva) {
        logBeginInfo(className, idIstanza);
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList = null;
        try {
            istanzaAttoreList = idIstanza != null ? istanzaAttoreDAO.loadIstanzaAttoreByIdIstanzaCfAttoreAbilitanteDelegante(idIstanza, attoreScriva.getCodiceFiscale()) : null;
            istanzaAttoreList = istanzaAttoreList != null ?
                    istanzaAttoreList.stream()
                            .filter(attore -> attore.getTipoAbilitazione() != null)
                            .collect(Collectors.toList()) :
                    null;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return istanzaAttoreList;
    }

    /**
     * Save abilitazione list.
     *
     * @param istanzaAttore the istanza attore
     * @param attoreScriva  the attore scriva
     * @return the list
     * @throws GenericException the generic exception
     */
    @Override
    public List<IstanzaAttoreExtendedDTO> saveAbilitazione(IstanzaAttoreExtendedDTO istanzaAttore, AttoreScriva attoreScriva) throws GenericException {
        logBeginInfo(className, istanzaAttore);
        Long idIstanzaAttoreNew = null;
        try {
            if (!canAssignAbilitazione(istanzaAttore.getIdIstanza(), attoreScriva.getCodiceFiscale(), istanzaAttore.getTipoAbilitazione().getCodTipoAbilitazione())) {
                ErrorDTO error = getErrorManager().getError("500", "", "Attenzione: non autorizzato ad assegnare questa abilitazione.", null, null);
                logError(className, error);
                logEnd(className);
                throw new GenericException(error);
            }

            if (recordAlreadyExist(istanzaAttore.getIdIstanza(), istanzaAttore.getCfAttore(), istanzaAttore.getCfAbilitanteDelegante(), istanzaAttore.getTipoAbilitazione())) {
                ErrorDTO error = getErrorManager().getError("409", "E038", "Il soggetto risulta già essere abilitato a consultare o gestire l’istanza", null, null);
                logError(className, error);
                logEnd(className);
                throw new GenericException(error);
            }

            List<CompilanteDTO> compilanteList = compilanteDAO.loadCompilanteByCodiceFiscale(istanzaAttore.getCfAttore());
            istanzaAttore.setCompilante(compilanteList != null && !compilanteList.isEmpty() ? compilanteList.get(0) : null);
            istanzaAttore.setCfAbilitanteDelegante(attoreScriva.getCodiceFiscale());
            istanzaAttore.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            if (istanzaAttore.getProfiloApp() == null) {
                istanzaAttore.setProfiloApp(getProfiloApp(istanzaAttore.getTipoAbilitazione().getCodTipoAbilitazione()));
            }
            idIstanzaAttoreNew = istanzaAttoreDAO.saveIstanzaAttore(istanzaAttore.getDTO());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return idIstanzaAttoreNew != null ? istanzaAttoreDAO.loadIstanzaAttoreById(idIstanzaAttoreNew) : null;
    }

    /**
     * Update abilitazione list.
     *
     * @param istanzaAttore the istanza attore
     * @param attoreScriva  the attore scriva
     * @return the list
     * @throws GenericException the generic exception
     */
    @Override
    public IstanzaAttoreExtendedDTO updateAbilitazione(IstanzaAttoreExtendedDTO istanzaAttore, AttoreScriva attoreScriva) throws GenericException {
        logBeginInfo(className, istanzaAttore);
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList = null;
        IstanzaAttoreDTO istanzaAttoreDTO = istanzaAttore.getDTO();
        if (istanzaAttore.getTipoAbilitazione() != null) {
            // revoca abilitazione
            istanzaAttoreList = this.revocaAbilitazione(istanzaAttoreDTO, attoreScriva);
        } else {
            // revoca delega
            istanzaAttoreList = this.revocaDelega(istanzaAttoreDTO, attoreScriva);
        }
        IstanzaAttoreExtendedDTO istanzaAttoreNoRevocatoLast = null;
        for (IstanzaAttoreExtendedDTO istanzaAttoreNoRevocato : istanzaAttoreList) {
            if (
                    istanzaAttore.getTipoAbilitazione() == null || (TipoAbilitazioneEnum.CONS.name().equalsIgnoreCase(istanzaAttoreNoRevocato.getTipoAbilitazione().getCodTipoAbilitazione())
                            || TipoAbilitazioneEnum.GEST.name().equalsIgnoreCase(istanzaAttoreNoRevocato.getTipoAbilitazione().getCodTipoAbilitazione()))
            ) {
                // update data revoca
                Date date = new Date();
                Timestamp now = new Timestamp(date.getTime());
                logDebug(className, "Revoca istanzaAttore per idIstanza [" + istanzaAttoreDTO.getIdIstanza() + "] - cfAbilitanteDelegante [" + attoreScriva.getCodiceFiscale() + "] - cfAttore [" + istanzaAttoreDTO.getCfAttore() + ":\n" + istanzaAttoreNoRevocato);
        
                istanzaAttoreNoRevocato.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                istanzaAttoreNoRevocato.setDataRevoca(now);
                istanzaAttore = istanzaAttoreNoRevocato;
                istanzaAttoreDAO.updateIstanzaAttore(istanzaAttoreNoRevocato.getDTO());
                setDataRevocaAbilitazione(istanzaAttoreNoRevocato.getIdIstanza(), istanzaAttoreNoRevocato.getCfAttore(), now, attoreScriva);
        
            }
        
           if (istanzaAttoreNoRevocato.getCompilante() != null && istanzaAttoreNoRevocato.getCompilante().getIdCompilante() != null) 
           {
            istanzaAttoreNoRevocatoLast = istanzaAttoreNoRevocato;
           } 
        }

        if (istanzaAttore.getTipoAbilitazione() == null) {
            
            IstanzaAttoreDTO newIstanzaAttore = istanzaAttoreNoRevocatoLast.getDTO();
            newIstanzaAttore.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            newIstanzaAttore.setCfAttore(attoreScriva.getCodiceFiscale());
            
            List<CompilanteDTO> newCompilante = compilanteDAO.loadCompilanteByCodiceFiscale(attoreScriva.getCodiceFiscale());
            
            newIstanzaAttore.setIdCompilante(newCompilante.get(0).getIdCompilante());
            
            newIstanzaAttore.setIdProfiloApp(1L); //compilante
            IstanzaAttoreExtendedDTO istanzaAttoreJustNew = createNewIstanzaAttoreCompilante(newIstanzaAttore, istanzaAttoreDTO.getCfAttore());
            if (istanzaAttoreJustNew != null) {
                 Long idIstanza = istanzaAttoreJustNew.getIdIstanza();
                 Long idIstanzaAttore = istanzaAttoreJustNew.getIdIstanzaAttore();
                    if (idIstanza != null && idIstanzaAttore != null) {
                        IstanzaDTO istanza = istanzaDAO.findByPK(idIstanza);
                        if (istanza != null) {
                            istanzaAttoreDAO.updateIstanzaAttoreOwner(idIstanza, idIstanzaAttore);
                        }
                    }
            }
        }
        return istanzaAttore;
    }

    private List<IstanzaAttoreExtendedDTO> revocaAbilitazione(IstanzaAttoreDTO istanzaAttore, AttoreScriva attoreScriva) throws GenericException {
        logBeginInfo(className, istanzaAttore);
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList = null;
        try {
            // revoca abilitazione
            istanzaAttoreList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanzaAndCFAttoreCFAbilitanteTipoAbilitazioneProfiloApp(istanzaAttore.getIdIstanza(), istanzaAttore.getCfAttore(), attoreScriva.getCodiceFiscale(), istanzaAttore.getIdTipoAbilitazione(), istanzaAttore.getIdProfiloApp());
            logDebug(className, "Elenco istanzaAttore per idIstanza [" + istanzaAttore.getIdIstanza() + "] - cfAttore [" + istanzaAttore.getCfAttore() + "] - cfAbilitanteDelegante [" + attoreScriva.getCodiceFiscale() + "] - idTipoAbilitazione [" + istanzaAttore.getIdTipoAbilitazione() + "] - idProfiloApp [" + istanzaAttore.getIdProfiloApp() + "] :\n" + istanzaAttoreList);
            if (istanzaAttoreList.isEmpty()) {
                ErrorDTO error = getErrorManager().getError("500", "E026", "Attenzione: non sei autorizzato a revocare l’abilitazione.", null, null);
                logError(className, error);
                logEnd(className);
                throw new GenericException(error);
            }

            istanzaAttoreList = istanzaAttoreList.stream()
                    .filter(attore -> attore.getDataRevoca() == null && attore.getCfAttore().equalsIgnoreCase(istanzaAttore.getCfAttore()))
                    .collect(Collectors.toList());
            logDebug(className, "Elenco istanzaAttore NO REVOCA per idIstanza [" + istanzaAttore.getIdIstanza() + "] - cfAttore [" + istanzaAttore.getCfAttore() + "] - cfAbilitanteDelegante [" + attoreScriva.getCodiceFiscale() + "] - idTipoAbilitazione [" + istanzaAttore.getIdTipoAbilitazione() + "] - idProfiloApp [" + istanzaAttore.getIdProfiloApp() + "] :\n" + istanzaAttoreList);
            if (istanzaAttoreList.isEmpty()) {
                ErrorDTO error = getErrorManager().getError("500", "E025", "Attenzione: l’abilitazione risulta già revocata.", null, null);
                logError(className, error);
                logEnd(className);
                throw new GenericException(error);
            }
        } finally {
            logEnd(className);
        }
        return istanzaAttoreList;
    }

    private List<IstanzaAttoreExtendedDTO> revocaDelega(IstanzaAttoreDTO istanzaAttore, AttoreScriva attoreScriva) throws GenericException {
        logBeginInfo(className, istanzaAttore);
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList = null;
        try {
            //verifica che l'attoreScriva sia il RICHIEDENTE dell'istanza
            if (!hasIstanzaAttoreProfilo(istanzaAttore.getIdIstanza(), attoreScriva.getCodiceFiscale(), ProfiloAppEnum.RICHIEDENTE)) {
                ErrorDTO error = getErrorManager().getError("500", "E028", "Attenzione: non sei autorizzato a revocare la delega", null, null);
                logError(className, error);
                logEnd(className);
                throw new GenericException(error);
            }
            istanzaAttoreList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanzaAndCFAttore(istanzaAttore.getIdIstanza(), istanzaAttore.getCfAttore());
            logDebug(className, "Elenco istanzaAttore per idIstanza [" + istanzaAttore.getIdIstanza() + "] - cfAttore [" + istanzaAttore.getCfAttore() + "] - cfAbilitanteDelegante [" + attoreScriva.getCodiceFiscale() + "] - idTipoAbilitazione [" + istanzaAttore.getIdTipoAbilitazione() + "] - idProfiloApp [" + istanzaAttore.getIdProfiloApp() + "] :\n" + istanzaAttoreList);
            istanzaAttoreList = istanzaAttoreList.stream()
                    .filter(attore -> attore.getDataRevoca() == null
                            && attore.getTipoAbilitazione() == null
                            /*&& attore.getProfiloApp().getIdProfiloApp().equals(istanzaAttore.getIdProfiloApp())*/)
                    .collect(Collectors.toList());
            logDebug(className, "Elenco istanzaAttore NO REVOCATO per idIstanza [" + istanzaAttore.getIdIstanza() + "] - cfAttore [" + istanzaAttore.getCfAttore() + "] - cfAbilitanteDelegante [" + attoreScriva.getCodiceFiscale() + "] - idTipoAbilitazione [" + istanzaAttore.getIdTipoAbilitazione() + "] - idProfiloApp [" + istanzaAttore.getIdProfiloApp() + "] :\n" + istanzaAttoreList);
            if (istanzaAttoreList.isEmpty()) {
                ErrorDTO error = getErrorManager().getError("500", "E028", "Il soggetto delegato è già stato revocato", null, null);
                logError(className, error);
                logEnd(className);
                throw new GenericException(error);
            }
        } finally {
            logEnd(className);
        }
        return istanzaAttoreList;
    }

    private boolean hasIstanzaAttoreProfilo(Long idIstanza, String cfAttore, ProfiloAppEnum profiloApp) {
        logBeginInfo(className, "idIstanza : [" + idIstanza + "] - cfAttore : [" + cfAttore + "] - profiloApp : [" + profiloApp.name() + "]");
        boolean result = false;
        try {
            List<IstanzaAttoreExtendedDTO> istanzaAttoreList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanzaAndCFAttore(idIstanza, cfAttore);
            istanzaAttoreList = istanzaAttoreList != null ?
                    istanzaAttoreList.stream()
                            .filter(istAttore -> istAttore.getProfiloApp().getCodProfiloApp().equalsIgnoreCase(profiloApp.name()))
                            .collect(Collectors.toList()) :
                    new ArrayList<>();
            result = !istanzaAttoreList.isEmpty();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    private Integer setDataRevocaAbilitazione(Long idIstanza, String cfAttoreAbilitanteDelegante, Timestamp dataRevoca, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idIstanza : [" + idIstanza + "] - cfAttoreAbilitanteDelegante : [" + cfAttoreAbilitanteDelegante + "] - dataRevoca : [" + dataRevoca + "]");
        Integer result = null;

        List<IstanzaAttoreExtendedDTO> istanzaAttoreInLineaList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanzaCfAttoreAbilitanteDelegante(idIstanza, cfAttoreAbilitanteDelegante);
        logDebug(className, "Elenco istanzaAttore per idIstanza [" + idIstanza + "] e cfAbilitanteDelegante [" + cfAttoreAbilitanteDelegante + "] :\n" + istanzaAttoreInLineaList);

        if (istanzaAttoreInLineaList != null && !istanzaAttoreInLineaList.isEmpty()) {
            for (IstanzaAttoreExtendedDTO istanzaAttoreInLinea : istanzaAttoreInLineaList) {
                if (TipoAbilitazioneEnum.CONS.name().equalsIgnoreCase(istanzaAttoreInLinea.getTipoAbilitazione().getCodTipoAbilitazione())
                        || TipoAbilitazioneEnum.GEST.name().equalsIgnoreCase(istanzaAttoreInLinea.getTipoAbilitazione().getCodTipoAbilitazione())) {
                    istanzaAttoreInLinea.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                    istanzaAttoreInLinea.setDataRevoca(dataRevoca);
                    result = istanzaAttoreDAO.updateIstanzaAttore(istanzaAttoreInLinea.getDTO());
                    if (result != null && result > 0) {
                        setDataRevocaAbilitazione(idIstanza, istanzaAttoreInLinea.getCfAttore(), dataRevoca, attoreScriva);
                    }
                }
            }
        }
        return result;
    }

    private IstanzaAttoreExtendedDTO createNewIstanzaAttoreCompilante(IstanzaAttoreDTO istanzaAttore, String cfIstanzaAttoreRevocato) {
        logBeginInfo(className, "istanzaAttore : [" + istanzaAttore + "] - cfIstanzaAttoreRevocato : [" + cfIstanzaAttoreRevocato + "]");
        IstanzaAttoreExtendedDTO result = null;
        try {
            List<IstanzaAttoreExtendedDTO> istanzaAttoreList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanza(istanzaAttore.getIdIstanza());
            // verifica UNICO COMPILANTE CON DATA REVOCA NULLA
            istanzaAttoreList = istanzaAttoreList.stream()
                    .filter(istAttore -> istAttore.getProfiloApp().getCodProfiloApp().equalsIgnoreCase(ProfiloAppEnum.COMPILANTE.name())
                            && istAttore.getDataRevoca() != null
                            && !istAttore.getCfAttore().equalsIgnoreCase(cfIstanzaAttoreRevocato)
                    )
                    .collect(Collectors.toList());

            if (istanzaAttoreList.isEmpty()) {
                Long idNewIstanzaAttore = istanzaAttoreDAO.saveIstanzaAttore(istanzaAttore);
                istanzaAttoreList = idNewIstanzaAttore != null && idNewIstanzaAttore > 0 ? istanzaAttoreDAO.loadIstanzaAttoreById(idNewIstanzaAttore) : null;
                result = istanzaAttoreList != null && !istanzaAttoreList.isEmpty() ? istanzaAttoreList.get(0) : null;
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }

        return result;
    }

    private List<TipoAbilitazioneDTO> getTipoAbilitazioniByIdIstanzaCfAttore(Long idIstanza, String cfAttoreAbilitanteDelegante) {
        List<TipoAbilitazioneDTO> tipoAbilitazioneList = null;
        IstanzaDTO istanza = istanzaDAO.findByPK(idIstanza);
        if (istanza != null) {
            tipoAbilitazioneList = tipoAbilitazioneDAO.loadTipoAbilitazioneByAdempimentoIstanzaCfAttore(istanza.getIdAdempimento(), idIstanza, cfAttoreAbilitanteDelegante);
        }
        return tipoAbilitazioneList;
    }

    private boolean canAssignAbilitazione(Long idIstanza, String cfAttoreAbilitanteDelegante, String codAbilitazione) {
        logBeginInfo(className, "idIstanza : [" + idIstanza + "] - cfAttoreAbilitanteDelegante : [" + cfAttoreAbilitanteDelegante + "] - codAbilitazione : [" + codAbilitazione + "]");
        List<TipoAbilitazioneDTO> tipoAbilitazioneList = getTipoAbilitazioniByIdIstanzaCfAttore(idIstanza, cfAttoreAbilitanteDelegante);
        List<TipoAbilitazioneDTO> tipoAbilitazioneFilteredList = tipoAbilitazioneList != null ?
                tipoAbilitazioneList.stream()
                        .filter(tipoAbilitazione -> codAbilitazione.equals(tipoAbilitazione.getCodTipoAbilitazione()))
                        .collect(Collectors.toList()) :
                new ArrayList<>();
        logEnd(className);
        return tipoAbilitazioneFilteredList.isEmpty() ? Boolean.FALSE : Boolean.TRUE;
    }

    private ProfiloAppExtendedDTO getProfiloApp(String codTipoAbilitazione) {
        String codProfiloApp = null;
        if (TipoAbilitazioneEnum.CONS.name().equalsIgnoreCase(codTipoAbilitazione)) {
            codProfiloApp = ProfiloAppEnum.ABILITATO_CONSULTA.name();
        } else if (TipoAbilitazioneEnum.GEST.name().equalsIgnoreCase(codTipoAbilitazione)) {
            codProfiloApp = ProfiloAppEnum.ABILITATO_GESTIONE.name();
        }
        List<ProfiloAppExtendedDTO> profiloAppList = StringUtils.isNotBlank(codProfiloApp) ? profiloAppDAO.loadProfiloAppByCode(codProfiloApp) : null;

        return profiloAppList != null && !profiloAppList.isEmpty() ? profiloAppList.get(0) : null;
    }

    private boolean recordAlreadyExist(Long idIstanza, String cfAttoreAbilitato, String cfAttoreAbilitante, TipoAbilitazioneDTO tipoAbilitazione) {
        logBeginInfo(className, "idIstanza : [" + idIstanza + "] - cfAttoreAbilitato : [" + cfAttoreAbilitato + "] - cfAttoreAbilitante : [" + cfAttoreAbilitante + "] - tipoAbilitazione : [" + tipoAbilitazione + "]");
        boolean result = false;
        if (cfAttoreAbilitato.equalsIgnoreCase(cfAttoreAbilitante)) {
            logEnd(className);
            return true;
        }
        try {
            List<IstanzaAttoreExtendedDTO> istanzaAttoreList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanzaCFAttoreTipoAbilitazione(idIstanza, cfAttoreAbilitato, tipoAbilitazione.getIdTipoAbilitazione());
            List<IstanzaAttoreExtendedDTO> istanzaAttoreFilteredList = istanzaAttoreList != null ?
                    istanzaAttoreList.stream()
                            .filter(istanzaAttore -> istanzaAttore.getDataRevoca() == null)
                            .collect(Collectors.toList()) :
                    new ArrayList<>();
            result = !istanzaAttoreFilteredList.isEmpty();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

}
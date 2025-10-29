/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.manager;

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ComponenteAppDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.CompetenzeTerritorioService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaService;
import it.csi.scriva.scrivabesrv.business.be.service.TipiAdempimentoOggettiAppService;
import it.csi.scriva.scrivabesrv.dto.*;
import it.csi.scriva.scrivabesrv.dto.enumeration.AzioniBaseEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ProfiloAppEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Action role manager.
 */
@Component
public class ActionRoleManager {

    /**
     * The constant LOGGER.
     */
    protected static final Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".business");

    @Autowired
    private ComponenteAppDAO componenteAppDAO;

    @Autowired
    private CompetenzeTerritorioService competenzeTerritorioService;

    @Autowired
    private IstanzaService istanzaService;

    @Autowired
    private TipiAdempimentoOggettiAppService tipiAdempimentoOggettiAppService;
    
    @Autowired
    IstanzaDAO istanzaDao;

    /**
     * The Funzionario manager.
     */
    @Autowired
    FunzionarioManager funzionarioManager;


     /**
     * Gets right istanza.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return attoreScriva l'istanza di attoreScriva con i valori di canReadIstanza e canWriteIstanza impostati (modificato per evitare errore che si verificava col passaggio per riferimento )
     */
    /*
    public AttoreScriva getRightIstanza(Long idIstanza, AttoreScriva attoreScriva) {
        attoreScriva.setCanReadIstanza(Boolean.FALSE);
        attoreScriva.setCanWriteIstanza(Boolean.FALSE);
        LOGGER.info("Imposto i valori di default --> CanReadIstanza: FALSE, CanWriteIstanza: FALSE");
        
        List<Long> idIstanzaList = new ArrayList<Long>();
        idIstanzaList.add(idIstanza);
        Map<Long, String> istanzeIndModificabile = istanzaDao.loadIstanzaIndModificabile(idIstanzaList);
       
        
        String statoEditabilita =istanzeIndModificabile.get(idIstanza);
        
        // Se componente BO
        if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
        	LOGGER.info("Componente BO");
            attoreScriva.setProfiloAppIstanza(getProfiloAppFunzionario(attoreScriva));
            if (StringUtils.isNotBlank(attoreScriva.getProfiloAppIstanza()) && attoreScriva.getProfiloAppIstanza().equalsIgnoreCase(ProfiloAppEnum.READ_ALL.name())) {
                attoreScriva.setCanReadIstanza(Boolean.TRUE);
                LOGGER.info("CanReadIstanza: TRUE, ProfiloAppEnum: READ_ALL");
            } else {
                List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = this.getTipoAdempimentoOggettoApp(idIstanza, null, attoreScriva, Boolean.FALSE);
                // Se il funzionario ha diritti di amministrazione può effettuare modifiche in qualsiasi istante
                if (StringUtils.isNotBlank(attoreScriva.getProfiloAppIstanza()) && attoreScriva.getProfiloAppIstanza().equalsIgnoreCase(ProfiloAppEnum.ADMIN.name())) {
                    attoreScriva.setCanReadIstanza(Boolean.TRUE);
                    attoreScriva.setCanWriteIstanza(Boolean.TRUE);
                    LOGGER.info("CanReadIstanza: TRUE, CanWriteIstanza: TRUE, ProfiloAppEnum: ADMIN");
                } else {
                    AzioniBaseEnum azioniBaseEnum = tipoAdempimentoOggettoAppList != null && !tipoAdempimentoOggettoAppList.isEmpty() ? this.getProfiloApp(tipoAdempimentoOggettoAppList) : null;

                
                    if (azioniBaseEnum != null) {
                		if(statoEditabilita != null && !statoEditabilita.equals("NA")) { 
                			attoreScriva.setProfiloAppIstanza(azioniBaseEnum.name());// se ind_modificabile è diverso da NA (non editabile) prende il valore calcolato (se stato ediabilità è null c'è un errore di configurzione e nel dubbio inibisco la scrittura               			
                		}
                		else {// altrimenti prende il WRITE_LOCK per bloccarne la modifica
                			attoreScriva.setProfiloAppIstanza(AzioniBaseEnum.findByDescr("WRITE_LOCK").name());
                		}
                        if (AzioniBaseEnum.WRITE.name().equalsIgnoreCase(azioniBaseEnum.name())) {
                            attoreScriva.setCanReadIstanza(Boolean.TRUE);
                            attoreScriva.setCanWriteIstanza(Boolean.TRUE);
                            LOGGER.info("CanReadIstanza: TRUE, CanWriteIstanza: TRUE, AzioneBase: WRITE");
                        } else if (AzioniBaseEnum.READ_ONLY.name().equalsIgnoreCase(azioniBaseEnum.name())) {
                            attoreScriva.setCanReadIstanza(Boolean.TRUE);
                            attoreScriva.setCanWriteIstanza(Boolean.FALSE);
                            LOGGER.info("CanReadIstanza: TRUE, CanWriteIstanza: TRUE, AzioneBase: READ_ONLY");
                        }
                    }
                }
            }
        // Se componente FO    
        } else {
                        // estrae gli oggetti applicativi per l'istanza?!?
                        List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = this.getTipoAdempimentoOggettoApp(idIstanza, null, attoreScriva, Boolean.FALSE);
                        LOGGER.info("[ActionRoleManager::getRightIstanza] tipoAdempimentoOggettoAppList idIstanza [" + idIstanza + "]:\n" + tipoAdempimentoOggettoAppList);
                        
                        // se la lista non è vuota l'attore scriva può leggere l'istanza
                        if (tipoAdempimentoOggettoAppList != null && !tipoAdempimentoOggettoAppList.isEmpty()) {
                            attoreScriva.setCanReadIstanza(Boolean.TRUE);
                            LOGGER.info("CanReadIstanza: TRUE, CanWriteIstanza: TRUE, tipoAdempimentoOggettoAppList not NULL and not EMPTY");
                    
                            // estrae il record con lo stato_attore corrispondente ord_profilo_app (alias di scriva_d_gestione_attore.ordinamento_gestione_attore) minore
                            // se la lista è vuota ritorna READ_ONLY
                            AzioniBaseEnum azioniBaseEnum = this.getProfiloApp(tipoAdempimentoOggettoAppList);
                            LOGGER.info("[ActionRoleManager::getRightIstanza] azioniBaseEnum idIstanza [" + idIstanza + "]:\n" + azioniBaseEnum);
            
                            // setta la proprietà profiloAppIstanza con il valore della gestione attore ricavata dal record
                            attoreScriva.setProfiloAppIstanza(azioniBaseEnum.name());
            
                            // se il valore è WRITE o WRITE_LOCK or LOCK_QDR_SOGGETTO setta la proprietà canWriteIstanza a TRUE
                            //TODO: va implementato un nuovo modo di gestire il permesso di scrivere sulle API che consideri anche la possibilità che il permesso di scrittura sia limitato a quadri specifici o inibito per quadri specifici
                            if (AzioniBaseEnum.WRITE.name().equalsIgnoreCase(azioniBaseEnum.name()) || AzioniBaseEnum.WRITE_LOCK.name().equalsIgnoreCase(azioniBaseEnum.name()) || AzioniBaseEnum.LOCK_QDR_SOGGETTO.name().equalsIgnoreCase(azioniBaseEnum.name())) {
                                attoreScriva.setCanWriteIstanza(Boolean.TRUE);
                                LOGGER.info("CanWriteIstanza: TRUE, AzioneBase: WRITE or WRITE_LOCK or LOCK_QDR_SOGGETTO");
                            }
                           
                            // String azioniBase = this.getProfiloAppNew(tipoAdempimentoOggettoAppList);
                            // LOGGER.info("[ActionRoleManager::getRightIstanza] azioniBaseEnum idIstanza [" + idIstanza + "]:\n" + azioniBase);
                            // attoreScriva.setProfiloAppIstanza(azioniBase);
                            // if (StringUtils.isNotBlank(azioniBase) && azioniBase.contains(AzioniBaseEnum.WRITE.name())) {
                               // attoreScriva.setCanWriteIstanza(Boolean.TRUE);
                            //}
                        }

        }
        return attoreScriva;
    }
    
    */


    /**
     * Gets right istanza.
     * NUOVA IMPLEMENTAZIONE per SCRIVA 1378-1571
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return attoreScriva l'istanza di attoreScriva con i valori di canReadIstanza e canWriteIstanza impostati (modificato per evitare errore che si verificava col passaggio per riferimento )
     */

    public AttoreScriva getRightIstanza(Long idIstanza, AttoreScriva attoreScriva) {
        attoreScriva.setCanReadIstanza(Boolean.FALSE);
        attoreScriva.setCanWriteIstanza(Boolean.FALSE);
        LOGGER.info("Imposto i valori di default --> CanReadIstanza: FALSE, CanWriteIstanza: FALSE");
        
        List<Long> idIstanzaList = new ArrayList<Long>();
        idIstanzaList.add(idIstanza);

        
        // Se componente BO
        if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
        	LOGGER.info("Componente BO");
            // prende il profilo applicativo del funzionario e lo setta nell'oggetto AttoreScriva
            attoreScriva.setProfiloAppIstanza(getProfiloAppFunzionario(attoreScriva));
            // se il profilo applicativo è READ_ALL l'attore scriva può leggere l'istanza
            if (StringUtils.isNotBlank(attoreScriva.getProfiloAppIstanza()) && attoreScriva.getProfiloAppIstanza().equalsIgnoreCase(ProfiloAppEnum.READ_ALL.name())) {
                attoreScriva.setCanReadIstanza(Boolean.TRUE);
                LOGGER.info("CanReadIstanza: TRUE, ProfiloAppEnum: READ_ALL");
            } else {
                // estrae gli oggetti applicativi per l'istanza
                List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = this.getTipoAdempimentoOggettoApp(idIstanza, null, attoreScriva, Boolean.FALSE);
                LOGGER.info("[ActionRoleManager::getRightIstanza] tipoAdempimentoOggettoAppList idIstanza [" + idIstanza + "]:\n" + tipoAdempimentoOggettoAppList);
                // Se il funzionario ha diritti di amministrazione può effettuare modifiche in qualsiasi istante quindi canReadIstanza e canWriteIstanza sono TRUE
                if (StringUtils.isNotBlank(attoreScriva.getProfiloAppIstanza()) && attoreScriva.getProfiloAppIstanza().equalsIgnoreCase(ProfiloAppEnum.ADMIN.name())) {
                    attoreScriva.setCanReadIstanza(Boolean.TRUE);
                    attoreScriva.setCanWriteIstanza(Boolean.TRUE);
                    LOGGER.info("CanReadIstanza: TRUE, CanWriteIstanza: TRUE, ProfiloAppEnum: ADMIN");
                } else {

                     // NUOVA QUERY PER ESTRARRE LA GESTIONE ATTORE DI TUTTE LE ISTANZE: restituisce coppie idIstanza, gestioneAttore SCRIVA 1378-1571
                    // TODO eliminare il parametro codComponenteApp e renderlo statico visto che forse la query si userà solo per FO
                    List<Pair<Long, String>> idIstanzaGestioneAttore = istanzaDao.loadIstanzaGestioneAttoresBO(idIstanzaList,attoreScriva.getCodiceFiscale(),"BO");
                    
                    if (idIstanzaGestioneAttore != null && !idIstanzaGestioneAttore.isEmpty()) {
                        // RECUPERA IL VALORE DELLA GESTIONE ATTORE DALLA STRINGA DEL PAIR
                        String gestioneAttore = idIstanzaGestioneAttore.get(0).getRight();
                        LOGGER.info("Gestione attore: "+gestioneAttore);
                        if (gestioneAttore != null) {
                            // setto la proprietà profiloAppIstanza con il valore della gestione attore
                            attoreScriva.setProfiloAppIstanza(gestioneAttore);
                            boolean canWrite = false;
    
                            // cicla per intercettare eventuali gestioni attore multiple tipo es. "QDR_OGGETTO|QDR_ALLEGATI"
                            for (String azione : gestioneAttore.split("\\|")) {
                                azione = azione.trim(); //tolgo eventuali spazi
                                // setto canWrite a true se trovo un'azione scrivibile
                                if ("WRITE".equalsIgnoreCase(azione) || 
                                    "WRITE_LOCK".equalsIgnoreCase(azione) || // il WRITE_LOCK dava luogo a canWriteIstanza = true. L'ho trovato cosi' e l'ho lasciato, ma il motivo non è chiaro.
                                    azione.toUpperCase().startsWith("QDR_") ||
                                    azione.toUpperCase().startsWith("LOCK_QDR_")) {
                                    
                                    canWrite = true;
                                    break; // se trovi un'azione scrivibile, esci
                                }

                            }
                            attoreScriva.setCanWriteIstanza(canWrite);
                            attoreScriva.setCanReadIstanza(Boolean.TRUE);
                            LOGGER.info("CanReadIstanza: TRUE, CanWriteIstanza: " + String.valueOf(canWrite).toUpperCase());
                        }
                    }  else {
                        LOGGER.error("Gestione attore nullo o non trovata per l'istanza "+idIstanza);
                    }


                }
            }
        // Se componente FO    
        } 
        else 
        {
            LOGGER.info("Componente FO");
         
            // NUOVA QUERY PER ESTRARRE LA GESTIONE ATTORE DI TUTTE LE ISTANZE: restituisce coppie idIstanza, gestioneAttore SCRIVA 1378-1571
            //TODO eliminare il parametro codComponenteApp e renderlo statico visto che forse la query si userà solo per FO
            List<Pair<Long, String>> idIstanzaGestioneAttore = istanzaDao.loadIstanzaGestioneAttoresFO(idIstanzaList,attoreScriva.getCodiceFiscale(),"FO");

            if (idIstanzaGestioneAttore != null && !idIstanzaGestioneAttore.isEmpty()) 
            {
                // RECUPERA IL VALORE DELLA GESTIONE ATTORE DALLA STRINGA DEL PAIR
                String gestioneAttore = idIstanzaGestioneAttore.get(0).getRight();
                if (gestioneAttore != null) 
                {
                    // setto la proprietà profiloAppIstanza con il valore della gestione attore
                    attoreScriva.setProfiloAppIstanza(gestioneAttore);
                    boolean canWrite = false;

                    // cicla per intercettare eventuali gestioni attore multiple tipo es. "QDR_OGGETTO|QDR_ALLEGATI"
                    for (String azione : gestioneAttore.split("\\|")) 
                    {
                        azione = azione.trim(); //tolgo eventuali spazi
                        // setto canWrite a true se trovo un'azione scrivibile
                        if ("WRITE".equalsIgnoreCase(azione) || 
                            "WRITE_LOCK".equalsIgnoreCase(azione) || // il WRITE_LOCK dava luogo a canWriteIstanza = true. L'ho trovato cosi' e l'ho lasciato, ma il motivo non è chiaro.
                            azione.toUpperCase().startsWith("QDR_") ||
                            azione.toUpperCase().startsWith("LOCK_QDR_")) 
                        {
                            
                            canWrite = true;
                            break; // se trovi un'azione scrivibile, esci
                        }
                    }
                    attoreScriva.setCanWriteIstanza(canWrite);
                    attoreScriva.setCanReadIstanza(Boolean.TRUE);
                    LOGGER.info("CanReadIstanza: TRUE, CanWriteIstanza: " + String.valueOf(canWrite).toUpperCase());
                }
                
            }  
            else 
            {
                LOGGER.error("Gestione attore nullo o non trovata per l'istanza "+idIstanza);
            }
            
        }
        return attoreScriva;
    }
    
    
    /**
     * Gets right istanza.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     */
    public void getRightIstanza2(Long idIstanza, AttoreScriva attoreScriva) {
        attoreScriva.setCanReadIstanza(false);
        attoreScriva.setCanWriteIstanza(false);
        LOGGER.info("Imposto i valori di default --> CanReadIstanza: FALSE, CanWriteIstanza: FALSE");
        
        List<Long> idIstanzaList = new ArrayList<Long>();
        idIstanzaList.add(idIstanza);
        Map<Long, String> istanzeIndModificabile = istanzaDao.loadIstanzaIndModificabile(idIstanzaList);
      
        
        String statoEditabilita =istanzeIndModificabile.get(idIstanza);
        
        if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                LOGGER.info("Componente BO");
            attoreScriva.setProfiloAppIstanza(getProfiloAppFunzionario(attoreScriva));
            if (StringUtils.isNotBlank(attoreScriva.getProfiloAppIstanza()) && attoreScriva.getProfiloAppIstanza().equalsIgnoreCase(ProfiloAppEnum.READ_ALL.name())) {
                attoreScriva.setCanReadIstanza(true);
                LOGGER.info("CanReadIstanza: TRUE, ProfiloAppEnum: READ_ALL");
            } else {
                List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = this.getTipoAdempimentoOggettoApp(idIstanza, null, attoreScriva, false);
                // Se il funzionario ha diritti di amministrazione può effettuare modifiche in qualsiasi istante
                if (StringUtils.isNotBlank(attoreScriva.getProfiloAppIstanza()) && attoreScriva.getProfiloAppIstanza().equalsIgnoreCase(ProfiloAppEnum.ADMIN.name())) {
                    attoreScriva.setCanReadIstanza(true);
                    attoreScriva.setCanWriteIstanza(true);
                    LOGGER.info("CanReadIstanza: TRUE, CanWriteIstanza: TRUE, ProfiloAppEnum: ADMIN");
                } else {
                    AzioniBaseEnum azioniBaseEnum = tipoAdempimentoOggettoAppList != null && !tipoAdempimentoOggettoAppList.isEmpty() ? this.getProfiloApp(tipoAdempimentoOggettoAppList) : null;
                
                    if (azioniBaseEnum != null) {
                                if(statoEditabilita != null && !statoEditabilita.equals("NA")) {
                                        attoreScriva.setProfiloAppIstanza(azioniBaseEnum.name());// se ind_modificabile è diverso da NA (non editabile) prende il valore calcolato (se stato ediabilità è null c'è un errore di configurzione e nel dubbio inibisco la scrittura                                      
                                }
                                else {// altrimenti prende il WRITE_LOCK per bloccarne la modifica
                                        attoreScriva.setProfiloAppIstanza(AzioniBaseEnum.findByDescr("WRITE_LOCK").name());
                                }
                        if (AzioniBaseEnum.WRITE.name().equalsIgnoreCase(azioniBaseEnum.name())) {
                            attoreScriva.setCanReadIstanza(true);
                            attoreScriva.setCanWriteIstanza(true);
                            LOGGER.info("CanReadIstanza: TRUE, CanWriteIstanza: TRUE, AzioneBase: WRITE");
                        } else if (AzioniBaseEnum.READ_ONLY.name().equalsIgnoreCase(azioniBaseEnum.name())) {
                            attoreScriva.setCanReadIstanza(true);
                            attoreScriva.setCanWriteIstanza(true);
                            LOGGER.info("CanReadIstanza: TRUE, CanWriteIstanza: TRUE, AzioneBase: READ_ONLY");
                        }
                    }
                }
            }
        } else {
            List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = this.getTipoAdempimentoOggettoApp(idIstanza, null, attoreScriva, Boolean.FALSE);
            LOGGER.info("[ActionRoleManager::getRightIstanza] tipoAdempimentoOggettoAppList idIstanza [" + idIstanza + "]:\n" + tipoAdempimentoOggettoAppList);
            if (tipoAdempimentoOggettoAppList != null && !tipoAdempimentoOggettoAppList.isEmpty()) {
                attoreScriva.setCanReadIstanza(true);
                LOGGER.info("CanReadIstanza: TRUE, CanWriteIstanza: TRUE, tipoAdempimentoOggettoAppList not NULL and not EMPTY");
               /**/
                AzioniBaseEnum azioniBaseEnum = this.getProfiloApp(tipoAdempimentoOggettoAppList);
                LOGGER.info("[ActionRoleManager::getRightIstanza] azioniBaseEnum idIstanza [" + idIstanza + "]:\n" + azioniBaseEnum);
                attoreScriva.setProfiloAppIstanza(azioniBaseEnum.name());
                if (AzioniBaseEnum.WRITE.name().equalsIgnoreCase(azioniBaseEnum.name()) || AzioniBaseEnum.WRITE_LOCK.name().equalsIgnoreCase(azioniBaseEnum.name())) {
                    attoreScriva.setCanWriteIstanza(true);
                    LOGGER.info("CanWriteIstanza: TRUE, AzioneBase: WRITE or WRITE_LOCK");
                }
                /**/
               /* String azioniBase = this.getProfiloAppNew(tipoAdempimentoOggettoAppList);
                LOGGER.info("[ActionRoleManager::getRightIstanza] azioniBaseEnum idIstanza [" + idIstanza + "]:\n" + azioniBase);
                attoreScriva.setProfiloAppIstanza(azioniBase);
                if (StringUtils.isNotBlank(azioniBase) && azioniBase.contains(AzioniBaseEnum.WRITE.name())) {
                    attoreScriva.setCanWriteIstanza(Boolean.TRUE);
                }*/
            }
        }
    }
    

    /**
     * Can write istanza boolean.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the boolean
     */
    public Boolean canWriteIstanza(Long idIstanza, AttoreScriva attoreScriva) {
        Boolean retValue = Boolean.FALSE;
        AzioniBaseEnum azioniBaseEnum = this.getProfiloApp(idIstanza, null, attoreScriva);
        if (AzioniBaseEnum.WRITE.name().equalsIgnoreCase(azioniBaseEnum.name())) {
            retValue = Boolean.TRUE;
        }
        return retValue;
    }

    /**
     * Can read istanza boolean.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the boolean
     */
    public Boolean canReadIstanza(Long idIstanza, AttoreScriva attoreScriva) {
        Boolean retValue = Boolean.FALSE;
        List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = this.getTipoAdempimentoOggettoApp(idIstanza, null, attoreScriva, Boolean.FALSE);
        if (tipoAdempimentoOggettoAppList != null && !tipoAdempimentoOggettoAppList.isEmpty()) {
            retValue = Boolean.TRUE;
        }
        return retValue;
    }

    /**
     * Gets profilo app.
     *
     * @param idIstanza         the id istanza
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @param attoreScriva      the attore scriva
     * @return the profilo app
     */
    public AzioniBaseEnum getProfiloApp(Long idIstanza, String codTipoOggettoApp, AttoreScriva attoreScriva) {
        return getProfiloApp(this.getTipoAdempimentoOggettoApp(idIstanza, codTipoOggettoApp, attoreScriva, Boolean.FALSE));
    }

    /**
     * Gets profilo app.
     *
     * @param tipoAdempimentoOggettoAppList the tipo adempimento oggetto app list
     * @return the profilo app
     */
    public AzioniBaseEnum getProfiloApp(List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList) {
        // estrae il record con lo stato_attore corrispondente ord_profilo_app minore
        if (tipoAdempimentoOggettoAppList != null && !tipoAdempimentoOggettoAppList.isEmpty()) {
            TipoAdempimentoOggettoAppExtendedDTO tipoAdempimentoOggettoApp = tipoAdempimentoOggettoAppList
                    .stream()
                    .min(Comparator.comparing(TipoAdempimentoOggettoAppExtendedDTO::getOrdinamentoProfiloApp))
                    .orElse(new TipoAdempimentoOggettoAppExtendedDTO());

            return AzioniBaseEnum.findByDescr(tipoAdempimentoOggettoApp.getStatoAttore());
        }
        //se la lsita è vuota ritorna READ_ONLY
        return AzioniBaseEnum.READ_ONLY;
    }

    //TODO: da eliminare, metodo sperimentale per la gestione dell'attore gestione per modifiche richieste da SCRIVA-1378. 
    //Avendo deciso di cambiare approccio non è più necessario
/* 
    public String getProfiloAppNew(List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList) {
        if (tipoAdempimentoOggettoAppList != null && !tipoAdempimentoOggettoAppList.isEmpty()) {
            TipoAdempimentoOggettoAppExtendedDTO tipoAdempimentoOggettoApp = tipoAdempimentoOggettoAppList
                    .stream()
                    .min(Comparator.comparing(TipoAdempimentoOggettoAppExtendedDTO::getOrdinamentoProfiloApp))
                    .orElse(new TipoAdempimentoOggettoAppExtendedDTO());

            int minOrdinamento = tipoAdempimentoOggettoApp.getOrdinamentoProfiloApp();

            List<String> ordinamentoProfiloAppMinList =  tipoAdempimentoOggettoAppList
                    .stream()
                    .filter(t -> t.getOrdinamentoProfiloApp() == minOrdinamento)
                    .map(TipoAdempimentoOggettoAppExtendedDTO::getStatoAttore)
                    .distinct()
                    .collect(Collectors.toList());

            if(CollectionUtils.isNotEmpty(ordinamentoProfiloAppMinList)){
                return String.join("|", ordinamentoProfiloAppMinList);
            }
        }
        return AzioniBaseEnum.READ_ONLY.getDescrizione();
    }
*/

    /**
     * Gets tipo adempimento oggetto app.
     *
     * @param idIstanza             the id istanza
     * @param codTipoOggettoApp     the cod tipo oggetto app
     * @param attoreScriva          the attore scriva
     * @param filterGestoreProcesso the filter gestore processo
     * @return the tipo adempimento oggetto app
     */
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoApp(Long idIstanza, String codTipoOggettoApp, AttoreScriva attoreScriva, boolean filterGestoreProcesso) {
        if (Boolean.TRUE.equals(filterGestoreProcesso) && StringUtils.isNotBlank(getGestoreProcesso(idIstanza))) {
            return tipiAdempimentoOggettiAppService.getTipoAdempimentoOggettoAppForAzioniWithGestore(idIstanza, codTipoOggettoApp, attoreScriva);
        }
        return tipiAdempimentoOggettiAppService.getTipoAdempimentoOggettoAppForAzioni(idIstanza, codTipoOggettoApp, attoreScriva);
    }

    /**
     * Gets tipo adempimento oggetto app.
     *
     * @param idIstanzaList         the id istanza list
     * @param codTipoOggettoApp     the cod tipo oggetto app
     * @param attoreScriva          the attore scriva
     * @param filterGestoreProcesso the filter gestore processo
     * @return the tipo adempimento oggetto app
     */
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoApp(List<Long> idIstanzaList, String codTipoOggettoApp, AttoreScriva attoreScriva, boolean filterGestoreProcesso) {
        List<TipoAdempimentoOggettoAppExtendedDTO> result = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(idIstanzaList)) {
            // Estrazione associazione istanze, competenze, gestore_processo
            List<IstanzaCompetenzaGestore> istanzaCompetenzaGestoreList = getIstanzaCompetenzaGestoreList(idIstanzaList);

            // Estrazione oggetti applicativi per tutte le istanze
            List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = tipiAdempimentoOggettiAppService.getTipoAdempimentoOggettoAppForAzioni(idIstanzaList, codTipoOggettoApp, attoreScriva);

            // Estrapolare gli idIstanza che hanno un gestore processo
            List<Long> idIstanzaWithGestoreList = istanzaCompetenzaGestoreList.stream()
                    .filter(ist -> ist.getIdComponenteGestoreProcesso() != 0)
                    .map(IstanzaCompetenzaGestore::getIdIstanza)
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(idIstanzaWithGestoreList)) {
                // Estrazione oggetti applicativi di tutte le istanze che non hanno un gestore
                List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppForIstWithNOGestoreList = tipoAdempimentoOggettoAppList.stream()
                        .filter(t -> !idIstanzaWithGestoreList.contains(t.getIdIstanza()))
                        .collect(Collectors.toList());

                // Estrazione oggetti applicativi di tutte le istanze che hanno un gestore
                List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppForIstWithGestoreList = tipoAdempimentoOggettoAppList.stream()
                        .filter(t -> idIstanzaWithGestoreList.contains(t.getIdIstanza()))
                        .collect(Collectors.toList());
                tipoAdempimentoOggettoAppForIstWithGestoreList = tipiAdempimentoOggettiAppService.getOggettoAppNonPrevistoDaGestoreProcesso(tipoAdempimentoOggettoAppForIstWithGestoreList);

                // Aggiungere gli array ottenuti alla variabile in uscita
                result.addAll(tipoAdempimentoOggettoAppForIstWithNOGestoreList);
                result.addAll(tipoAdempimentoOggettoAppForIstWithGestoreList);
            } else {
                result.addAll(tipoAdempimentoOggettoAppList);
            }
        }
        return result;
    }

    /**
     * Gets istanza competenza gestore list.
     *
     * @param idIstanzaList the id istanza list
     * @return the istanza competenza gestore list
     */
    public List<IstanzaCompetenzaGestore> getIstanzaCompetenzaGestoreList(List<Long> idIstanzaList) {
        return componenteAppDAO.loadIstanzaCompetenzaGestore(idIstanzaList);
    }

    /**
     * Gets gestore processo.
     *
     * @param idIstanza the id istanza
     * @return the gestore processo
     */
    public String getGestoreProcesso(Long idIstanza) {
        if (idIstanza != null) {
            List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = competenzeTerritorioService.extractIstanzaCompetenzaList(idIstanza);
            if (CollectionUtils.isNotEmpty(istanzaCompetenzaList)) {
                IstanzaExtendedDTO istanza = istanzaService.getIstanza(idIstanza);
                ComponenteAppDTO componenteApp = componenteAppDAO.loadComponenteAppByIdAdempimentoCompTerritorio(istanza.getAdempimento().getIdAdempimento(), istanzaCompetenzaList.get(0).getCompetenzaTerritorio().getIdCompetenzaTerritorio());
                if (componenteApp != null) {
                    return componenteApp.getCodComponenteApp();
                }
            }
        }
        return null;
    }

    /**
     * Gets profilo app funzionario.
     *
     * @param attoreScriva the attore scriva
     * @return the profilo app funzionario
     */
    public String getProfiloAppFunzionario(AttoreScriva attoreScriva) {
        ProfiloAppEnum profiloApp = null;
        if (attoreScriva != null) {
            try {
                FunzionarioAutorizzatoDTO funzionarioAutorizzato = funzionarioManager.getFunzionarioAutorizzatoByCF(attoreScriva.getCodiceFiscale());
                if (funzionarioAutorizzato != null && !funzionarioAutorizzato.getFunzionarioProfilo().isEmpty()) {
                    List<FunzionarioProfiloExtendedDTO> funzionarioProfiloAdmin = funzionarioAutorizzato.getFunzionarioProfilo().stream()
                            .filter(funzProfilo -> funzProfilo.getProfiloApp().getCodProfiloApp().equalsIgnoreCase(ProfiloAppEnum.ADMIN.name()))
                            .collect(Collectors.toList());
                    if (!funzionarioProfiloAdmin.isEmpty()) {
                        profiloApp = ProfiloAppEnum.ADMIN;
                    } else {
                        List<FunzionarioProfiloExtendedDTO> funzionarioProfiloReadAll = funzionarioAutorizzato.getFunzionarioProfilo().stream()
                                .filter(funzProfilo -> funzProfilo.getProfiloApp().getCodProfiloApp().equalsIgnoreCase(ProfiloAppEnum.READ_ALL.name()))
                                .collect(Collectors.toList());
                        profiloApp = !funzionarioProfiloReadAll.isEmpty() ? ProfiloAppEnum.READ_ALL : null;
                    }

                }
            } catch (Exception e) {
                LOGGER.error("[ActionRoleManager::setProfiloAppFunzionario] attoreScriva :\n" + attoreScriva + "\n", e);
            }
        }
        return profiloApp != null ? profiloApp.name() : null;
    }

}
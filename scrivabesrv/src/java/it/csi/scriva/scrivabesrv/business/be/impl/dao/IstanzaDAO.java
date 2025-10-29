/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao;

import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.PraticaCollegataDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
/**
 * The interface Istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzaDAO {

    /**
     * Load istanze list.
     *
     * @return List<IstanzaExtendedDTO>                 list
     */
    List<IstanzaExtendedDTO> loadIstanze();

    /**
     * Load istanza list.
     *
     * @param idIstanza the id istanza
     * @return List<IstanzaExtendedDTO>                 list
     */
    List<IstanzaExtendedDTO> loadIstanza(Long idIstanza);

    /**
     * Load istanza list.
     *
     * @param codIstanza the cod istanza
     * @return the list
     */
    List<IstanzaExtendedDTO> loadIstanza(String codIstanza);

    /**
     * Load istanza by uid list.
     *
     * @param uidIstanza uidIstanza
     * @return List<IstanzaExtendedDTO>                 list
     */
    List<IstanzaExtendedDTO> loadIstanzaByUID(String uidIstanza);

    /**
     * Load istanza by id oggetto istanza list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    List<IstanzaExtendedDTO> loadIstanzaByIdOggettoIstanza(Long idOggettoIstanza);

    /**
     * Load istanze by id compilante list.
     *
     * @param idCompilante idCompilante
     * @return List<IstanzaExtendedDTO>                 list
     */
    List<IstanzaExtendedDTO> loadIstanzeByIdCompilante(Long idCompilante);

    /**
     * Load istanze by cf compilante list.
     *
     * @param cfCompilante codice fiscale compilante
     * @return List<IstanzaExtendedDTO>                 list
     */
    List<IstanzaExtendedDTO> loadIstanzeByCfCompilante(String cfCompilante);

    /**
     * Save istanza long.
     *
     * @param dto                   IstanzaDTO
     * @param codiceTipoAdempimento codiceTipoAdempimento
     * @param codiceAdempimento     the codice adempimento
     * @param saveStorico           the save storico
     * @return id record salvato
     */
    Long saveIstanza(IstanzaDTO dto, String codiceTipoAdempimento, String codiceAdempimento, boolean saveStorico, String componenteApplicativa);

    /**
     * Update istanza integer.
     *
     * @param dto            IstanzaDTO
     * @param saveStorico    the save storico
     * @param flgCreaPratica the flg crea pratica
     * @return numero record aggiornati
     */
    Integer updateIstanza(IstanzaDTO dto, boolean saveStorico, boolean flgCreaPratica);

    /**
     * Update json data istanza integer.
     *
     * @param dto IstanzaDTO
     * @return numero record aggiornati
     */
    Integer updateJsonDataIstanza(IstanzaDTO dto);

    /**
     * Update stato istanza integer.
     *
     * @param uidIstanza     uidIstanza
     * @param idStatoIstanza idStatoIstanza
     * @param attoreUpd      attoreUpd
     * @param jsonData       the json data
     * @param saveStorico    the save storico
     * @return numero record aggiornati
     */
    Integer updateStatoIstanza(String uidIstanza, Long idStatoIstanza, String attoreUpd, String jsonData, boolean saveStorico);

    /**
     * Delete istanza integer.
     *
     * @param uid uidIstanza
     * @return numero record cancellati
     */
    Integer deleteIstanza(String uid);

    /**
     * Delete istanza by id integer.
     *
     * @param id idIstanza
     * @return numero record cancellati
     */
    Integer deleteIstanzaById(Long id);

    /**
     * Find by uid istanza dto.
     *
     * @param uidIstanza uidIstanza
     * @return IstanzaDTO istanza dto
     */
    IstanzaDTO findByUID(String uidIstanza);

    /**
     * Find by pk istanza dto.
     *
     * @param idIstanza idIstanza
     * @return IstanzaDTO istanza dto
     */
    IstanzaDTO findByPK(Long idIstanza);

    /**
     * Find by id oggetto istanza dto.
     *
     * @param idOggetto the id oggetto
     * @return the istanza dto
     */
    IstanzaDTO findByIdOggetto(Long idOggetto);

    /**
     * Update uuid index integer.
     *
     * @param idIstanza idIstanza
     * @param uuidIndex uuidIndex
     * @return numero record aggiornati
     */
    Integer updateUuidIndex(Long idIstanza, String uuidIndex);

    /**
     * Update istanza attore integer.
     *
     * @param idIstanza            idIstanza
     * @param idIstanzaAttore      idIstanzaAttore
     * @param idIstanzaAttoreOwner the id istanza attore owner
     * @param attoreUpd            attoreUpd
     * @return numero record aggiornati
     */
    Integer updateIstanzaAttore(Long idIstanza, Long idIstanzaAttore, Long idIstanzaAttoreOwner, String attoreUpd);

    /**
     * Load pratiche collegate oggetto list.
     *
     * @param idOggetti         the id oggetti
     * @param annoPresentazione the anno presentazione
     * @return the list
     */
    List<PraticaCollegataDTO> loadPraticheCollegateOggetto(List<Long> idOggetti, Integer annoPresentazione);

    /**
     * Load codice pratica string.
     *
     * @param idIstanza             the id istanza
     * @param codiceTipoAdempimento the codice tipo adempimento
     * @param codiceAdempimento     the codice adempimento
     * @return the string
     */
    String loadCodicePratica(Long idIstanza, String codiceTipoAdempimento, String codiceAdempimento);

    /**
     * Update data pubblicazione integer.
     *
     * @param idIstanza         the id istanza
     * @param dataPubblicazione the data pubblicazione
     * @param attoreUpd         the attore upd
     * @param idIstanzaAttore   the id istanza attore
     * @param idFunzionario     the id funzionario
     * @param saveStorico       the save storico
     * @return the integer
     */
    Integer updateDataPubblicazione(Long idIstanza, Date dataPubblicazione, String attoreUpd, Long idIstanzaAttore, Long idFunzionario, boolean saveStorico);

    /**
     * Update data atto from provvedimento finale integer.
     *
     * @param idIstanza       the id istanza
     * @param attoreUpd       the attore upd
     * @param idIstanzaAttore the id istanza attore
     * @param idFunzionario   the id funzionario
     * @param saveStorico     the save storico
     * @return the integer
     */
    Integer updateDataConclProcedimentoFromProvvedimentoFinale(Long idIstanza, String attoreUpd, Long idIstanzaAttore, Long idFunzionario, boolean saveStorico);
    
    /**
     * Load istanza ind modificabile.
     *
     * @param idIstanze the id istanze
     * @return the list
     */
    Map<Long, String> loadIstanzaIndModificabile(List<Long> idIstanze);

 
    /**
     * Updates the timestamp of an instance of a practice for a specific actor.
     * 
     * @param idIstanza The ID of the instance.
     * @param ComponenteApp The application component.
     * @param idIstanzaAttore The ID of the instance actor.
     * @param idFunzionario The ID of the employee.
     * @param saveStorico Indicates whether to save the historical data.
     * @return The updated instance.
     */
    Integer updateIstanzaPraticaTimestampAttore(Long idIstanza, String ComponenteApp, Long idIstanzaAttore, Long idFunzionario, boolean saveStorico);


    /**
     * Loads a list of pairs containing the ID and name of the "Istanza Gestione Attore" 
     * based on the provided list of instance IDs, actor's fiscal code, and application component code.
     *
     * @param idIstanzaList the list of instance IDs to filter the results
     * @param cfAttore the fiscal code of the actor to filter the results
     * @param codComponenteApp the code of the application component to filter the results
     * @return a list of pairs where each pair contains an integer ID and a string name of the "Istanza Gestione Attore"
     */
    List<Pair<Long, String>> loadIstanzaGestioneAttoresFO(List<Long> idIstanzaList, String cfAttore, String codComponenteApp);

        /**
     * Loads a list of pairs containing the ID and name of the "Istanza Gestione Attore" 
     * based on the provided list of instance IDs, actor's fiscal code, and application component code.
     *
     * @param idIstanzaList the list of instance IDs to filter the results
     * @param cfAttore the fiscal code of the actor to filter the results
     * @param codComponenteApp the code of the application component to filter the results
     * @return a list of pairs where each pair contains an integer ID and a string name of the "Istanza Gestione Attore"
     */
    List<Pair<Long, String>> loadIstanzaGestioneAttoresBO(List<Long> idIstanzaList, String cfAttore, String codComponenteApp);

}
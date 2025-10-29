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

import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreExtendedDTO;

import java.util.List;

/**
 * The interface Istanza attore dao.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzaAttoreDAO {

    /**
     * Load istanze attori list.
     *
     * @return List<IstanzaAttoreExtendedDTO>             list
     */
    List<IstanzaAttoreExtendedDTO> loadIstanzeAttori();

    /**
     * Load istanza attore by id list.
     *
     * @param idIstanzaAttore idIstanzaAttore
     * @return List<IstanzaAttoreExtendedDTO>             list
     */
    List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreById(Long idIstanzaAttore);


    /**
     * Load istanza attore by id istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<IstanzaAttoreExtendedDTO>             list
     */
    List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanza(Long idIstanza);

    /**
     * Load istanza attore by cf attore list.
     *
     * @param cfAttore codice fiscale attore
     * @return List<IstanzaAttoreExtendedDTO>             list
     */
    List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByCFAttore(String cfAttore);

    /**
     * Load istanza attore by id istanza and cf attore list.
     *
     * @param idIstanza idIstanza
     * @param cfAttore  codice fiscale attore
     * @return List<IstanzaAttoreExtendedDTO>             list
     */
    List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaAndCFAttore(Long idIstanza, String cfAttore);

    /**
     * Load istanza attore by id istanza and cf attore list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreRevocabiliByIdIstanza(Long idIstanza);

    /**
     * Load istanza attore by id istanza and cf attore cf abilitante list.
     *
     * @param idIstanza          the id istanza
     * @param cfAttore           the cf attore
     * @param idTipoAbilitazione the id tipo abilitazione
     * @return the list
     */
    List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaCFAttoreTipoAbilitazione(Long idIstanza, String cfAttore, Long idTipoAbilitazione);

    /**
     * Load istanza attore by id istanza and cf attore cf abilitante tipo abilitazione profilo app list.
     *
     * @param idIstanza          the id istanza
     * @param cfAttore           the cf attore
     * @param cfAttoreAbilitante the cf attore abilitante
     * @param idTipoAbilitazione the id tipo abilitazione
     * @param idProfiloApp       the id profilo app
     * @return the list
     */
    List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaAndCFAttoreCFAbilitanteTipoAbilitazioneProfiloApp(Long idIstanza, String cfAttore, String cfAttoreAbilitante, Long idTipoAbilitazione, Long idProfiloApp);

    /**
     * Load istanza attore by id istanza cf attore cod comp app cod prof app list.
     *
     * @param idIstanza        idIstanza
     * @param cfAttore         codice fiscale attore
     * @param codComponenteApp codice componente apploadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfApp
     * @param codProfiloApp    codice profilo app
     * @return List<IstanzaAttoreExtendedDTO>            list
     */
    List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfApp(Long idIstanza, String cfAttore, String codComponenteApp, String codProfiloApp);

/**
     * Load istanza attore by id istanza cf attore cod comp app cod prof app list.
     *
     * @param idIstanza        idIstanza
     * @param cfAttore         codice fiscale attore
     * @param codComponenteApp codice componente apploadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfApp
     * @return List<IstanzaAttoreExtendedDTO>            list
     **/
     List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfApp(Long idIstanza, String cfAttore, String codComponenteApp);
    
     /**
     * Load istanza attore by id istanza cf attore abilitante delegante list.
     *
     * @param idIstanza the id istanza
     * @param cfAttore  the cf attore
     * @return the list
     */
    List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaCfAttoreAbilitanteDelegante(Long idIstanza, String cfAttore);

    /**
     * Save istanza attore long.
     *
     * @param istanzaAttore istanzaAttore
     * @return id record inserito
     */
    Long saveIstanzaAttore(IstanzaAttoreDTO istanzaAttore);

    /**
     * Update istanza attore integer.
     *
     * @param istanzaAttore istanzaAttore
     * @return numero record aggiornati
     */
    Integer updateIstanzaAttore(IstanzaAttoreDTO istanzaAttore);


    /**
     * Update istanza attore integer.
     *
     * @param idCompilante the id compilante
     * @param cfAttore     the cf attore
     * @param cfAttoreUpd  the cf attore upd
     * @return numero record aggiornati
     */
    Integer updateIdCompilanteByCFAttore(Long idCompilante, String cfAttore, String cfAttoreUpd);

    /**
     * Delete istanza attore integer.
     *
     * @param idIstanzaAttore idIstanzaAttore
     * @return numero record eliminati
     */
    Integer deleteIstanzaAttore(Long idIstanzaAttore);


    /**
     * Delete istanza attore by id istanza cf attore cod prof app integer.
     *
     * @param idIstanza     idIstanza
     * @param cfAttore      codice fiscale attore
     * @param codProfiloApp codice profilo app
     * @return numero record eliminati
     */
    Integer deleteIstanzaAttoreByIdIstanzaCFAttoreCodProfApp(Long idIstanza, String cfAttore, String codProfiloApp);

    /**
     * Delete istanza attore by uid istanza cf attore cod prof app integer.
     *
     * @param uidIstanza    uidIstanza
     * @param cfAttore      codice fiscale attore
     * @param codProfiloApp codice profilo app
     * @return numero record eliminati
     */
    Integer deleteIstanzaAttoreByUidIstanzaCFAttoreCodProfApp(String uidIstanza, String cfAttore, String codProfiloApp);

    /**
     * Gets istanza attore by uid.
     *
     * @param uid the uid
     * @return the istanza attore by uid
     */
    IstanzaAttoreDTO getIstanzaAttoreByUid(String uid);

    /**
     * Delete ref istanza attore by id istanza cf attore cod prof app integer.
     *
     * @param idIstanza     the id istanza
     * @param cfAttore      the cf attore
     * @param codProfiloApp the cod profilo app
     * @return the integer
     */
    Integer deleteRefIstanzaAttoreByIdIstanzaCFAttoreCodProfApp(Long idIstanza, String cfAttore, String codProfiloApp);

    /**
     * Update istanza attore by id istanza cf attore cod prof app integer.
     *
     * @param idIstanza     the id istanza
     * @param cfAttore      the cf attore
     * @param codProfiloApp the cod profilo app
     * @param cfAttoreNew   the cf attore new
     * @param cfAttoreUpd   the cf attore upd
     * @return the integer
     */
    Integer updateIstanzaAttoreByIdIstanzaCFAttoreCodProfApp(Long idIstanza, String cfAttore, String codProfiloApp, String cfAttoreNew, String cfAttoreUpd);

    /**
     * @param idIstanza
     * @param idIstanzaAttore
     * @return
     */
    Integer updateIstanzaAttoreOwner(Long idIstanza, Long idIstanzaAttore);

	List<IstanzaAttoreExtendedDTO> loadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfAppForSoggIst(Long idIstanza,
			String cfAttore, String codComponenteApp, String codProfiloApp);


}
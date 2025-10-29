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

import it.csi.scriva.scrivabesrv.business.be.exception.DuplicateRecordException;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaExtendedDTO;

import java.util.List;

/**
 * The interface Istanza competenza dao.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzaCompetenzaDAO {

    /**
     * Load istanze competenza list.
     *
     * @return List<IstanzaCompetenzaExtendedDTO>     list
     */
    List<IstanzaCompetenzaExtendedDTO> loadIstanzeCompetenza();

    /**
     * Load istanza competenza by id istanza competenza list.
     *
     * @param idIstanza idIstanza
     * @return List<IstanzaCompetenzaExtendedDTO>     list
     */
    List<IstanzaCompetenzaExtendedDTO> loadIstanzaCompetenzaByIdIstanza(Long idIstanza);

    /**
     * Load istanza competenza by id istanza competenza list.
     *
     * @param idIstanza              idIstanza
     * @param idCompetenzaTerritorio idCompetenzaTerritorio
     * @return List<IstanzaCompetenzaExtendedDTO>     list
     */
    List<IstanzaCompetenzaExtendedDTO> loadIstanzaCompetenzaByIdIstanza(Long idIstanza, Long idCompetenzaTerritorio);
    
    List<IstanzaCompetenzaExtendedDTO> loadIstanzaCompetenzaByIdIstanzaClean(Long idIstanza);


    /**
     * Load competenza territorio secondarie by id adempimento id istanza list.
     *
     * @param idAdempimento         idAdempimento
     * @param idIstanza             idIstanza
     * @param livelliCompetenzaList the livelli competenza list
     * @return List<Long>      idCompetenzeTerritorio
     */
    List<Long> loadCompetenzaTerritorioSecondarieByIdAdempimentoIdIstanza(Long idAdempimento, Long idIstanza, List<String> livelliCompetenzaList);

    /**
     * Load competenza territorio principali by id adempimento id istanza list.
     *
     * @param idAdempimento the id adempimento
     * @param idIstanza     the id istanza
     * @return the list
     */
    List<Long> loadCompetenzaTerritorioPrincipaliByIdAdempimentoIdIstanza(Long idAdempimento, Long idIstanza);

    /**
     * Load competenza territorio verifica by id adempimento id istanza list.
     *
     * @param idAdempimento the id adempimento
     * @param idIstanza     the id istanza
     * @return the list
     */
    List<Long> loadCompetenzaTerritorioVerificaByIdAdempimentoIdIstanza(Long idAdempimento, Long idIstanza);

    /**
     * Save istanza competenza long.
     *
     * @param istanzaCompetenza istanzaCompetenza
     * @return id record salvato
     */
    Long saveIstanzaCompetenza(IstanzaCompetenzaDTO istanzaCompetenza) throws DuplicateRecordException;

    /**
     * Update istanza competenza integer.
     *
     * @param istanzaCompetenza istanzaCompetenza
     * @return numero record aggiornati
     */
    Integer updateIstanzaCompetenza(IstanzaCompetenzaDTO istanzaCompetenza);

    /**
     * Delete istanza competenza integer.
     *
     * @param uid uid
     * @return numero record cancellati
     */
    Integer deleteIstanzaCompetenza(String uid);
    
    /**
     * Delete istanza competenza by identificativi competenza integer.
     * 
     * @param idIstanza idIstanza
     * @param idCompetenzaTerritorio idCompetenzaTerritorio
     * @return numero record cancellati
     */
    Integer deleteIstanzaCompetenzaByIdCompAndIdIst(Long idIstanza, Long idCompetenzaTerritorio);

	/**
	*
	* @param idIstanza
	* @param indAssegnataDaSistema
	* @return
	*
	*/
	List<IstanzaCompetenzaExtendedDTO> loadIstanzaCompetenzaByIdIstanza(Long idIstanza, String indAssegnataDaSistema);

	/**
	*
	* @param idIstanza
	* @return
	*
	*/
	List<String>  loadLivelliCompetenzaCategorieProgettuali(Long idIstanza);

    
}
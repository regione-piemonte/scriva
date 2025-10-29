/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service;

import it.csi.scriva.scrivabesrv.business.be.exception.DuplicateRecordException;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaExtendedDTO;

import java.util.List;

/**
 * The interface Competenze territorio service.
 *
 * @author CSI PIEMONTE
 */
public interface CompetenzeTerritorioService {

    /**
     * Load competenze territorio list.
     *
     * @param attoreScriva the attore scriva
     * @return list list
     */
    List<CompetenzaTerritorioExtendedDTO> loadCompetenzeTerritorio(AttoreScriva attoreScriva);

    /**
     * Load ambito list.
     *
     * @param idAdempimento the id adempimento
     * @param attoreScriva  the attore scriva
     * @return the list
     */
    List<CompetenzaTerritorioExtendedDTO> loadCompetenzeTerritorio(Long idAdempimento, AttoreScriva attoreScriva);

    /**
     * Load ambito list.
     *
     * @param idCompetenzaTerritorio the id competenza territorio
     * @param attoreScriva           the attore scriva
     * @return the list
     */
    List<CompetenzaTerritorioExtendedDTO> loadCompetenzeTerritorioById(Long idCompetenzaTerritorio, AttoreScriva attoreScriva);

    /**
     * Load istanza competenze territorio list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    List<IstanzaCompetenzaExtendedDTO> loadIstanzaCompetenzeTerritorio(Long idIstanza, AttoreScriva attoreScriva);
    
    /**
     * Load competenza territorio responsabile.
     *
     * @param idCompetenzaTerritorioResponsabile the id competenza territorio responsabile
     * @param attoreScriva           the attore scriva
     * @return the list
     */
    CompetenzaTerritorioResponsabileDTO loadCompetenzeTerritorioByIdCompResp(Long idCompetenzaTerritorioResponsabile, AttoreScriva attoreScriva);

    /**
     * Save istanza competenza territorio list.
     *
     * @param istanzaCompetenza the istanza competenza
     * @param attoreScriva      the attore scriva
     * @return the list
     * @throws DuplicateRecordException the duplicate record exception
     */
    IstanzaCompetenzaExtendedDTO saveIstanzaCompetenzaTerritorio(IstanzaCompetenzaExtendedDTO istanzaCompetenza, AttoreScriva attoreScriva) throws DuplicateRecordException;

    /**
     * Update istanza competenza territorio istanza competenza extended dto.
     *
     * @param istanzaCompetenza the istanza competenza
     * @param attoreScriva      the attore scriva
     * @return the istanza competenza extended dto
     */
    IstanzaCompetenzaExtendedDTO updateIstanzaCompetenzaTerritorio(IstanzaCompetenzaExtendedDTO istanzaCompetenza, AttoreScriva attoreScriva);

    /**
     * Verifica competenze territorio by id istanza list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     * @throws GenericException the generic exception
     */
    List<IstanzaCompetenzaExtendedDTO> verificaCompetenzeTerritorioByIdIstanza(Long idIstanza, AttoreScriva attoreScriva) throws GenericException;

    /**
     * Calculate istanza competenza territorio secondarie list.
     *
     * @param idIstanza         the id istanza
     * @param livelliCompetenza the livelli competenza
     * @param attoreScriva      the attore scriva
     * @return the list
     * @throws GenericException the generic exception
     */
    List<IstanzaCompetenzaExtendedDTO> calculateIstanzaCompetenzaTerritorioSecondarie(Long idIstanza, String livelliCompetenza, AttoreScriva attoreScriva) throws GenericException;

    /**
     * Delete istanza competenza territorio secondarie int.
     *
     * @param idIstanza the id istanza
     * @return the int
     */
    int deleteIstanzaCompetenzaTerritorioSecondarie(Long idIstanza);
    
	/**
	 * Delete istanza competenza territorio categorie progettuali.
	 *
	 * @param idIstanza the id istanza
	 * @return the int
	 */
	int deleteIstanzaCompetenzaTerritorioCategorieProgettuali(Long idIstanza);


    /**
     * Gets istanza competenza principale list.
     *
     * @param istanzaCompetenzaList the istanza competenza list
     * @return the istanza competenza principale list
     */
    List<IstanzaCompetenzaExtendedDTO> getIstanzaCompetenzaPrincipaleList(List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList);

    /**
     * Gets istanza competenza assegnata bo list.
     *
     * @param istanzaCompetenzaList the istanza competenza list
     * @return the istanza competenza assegnata bo list
     */
    List<IstanzaCompetenzaExtendedDTO> getIstanzaCompetenzaAssegnataBOList(List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList);

    /**
     * Define istanza competenza list list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<IstanzaCompetenzaExtendedDTO> extractIstanzaCompetenzaList(Long idIstanza);

	/**
	 * Gestione AC.
	 *
	 * @param idIstanza the id istanza
	 * @param ac3 the ac 3
	 * @param tipoSelezione the tipo selezione
	 * @param attoreScriva the attore scriva
	 * @return the list
	 * @throws GenericException 
	 */
	List<IstanzaCompetenzaExtendedDTO> gestioneAC(Long idIstanza, Boolean ac3, String tipoSelezione,
			AttoreScriva attoreScriva) throws GenericException;



}
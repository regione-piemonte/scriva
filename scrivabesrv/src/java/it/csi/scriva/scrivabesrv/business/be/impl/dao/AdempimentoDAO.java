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

import it.csi.scriva.scrivabesrv.dto.AdempimentoDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;

import java.util.List;

/**
 * The interface Adempimento dao.
 *
 * @author CSI PIEMONTE
 */
public interface AdempimentoDAO {

    /**
     * Load adempimenti attivi list.
     *
     * @return List<AdempimentoExtendedDTO>       list
     */
    List<AdempimentoExtendedDTO> loadAdempimentiAttivi();


    /**
     * Load adempimenti attivi list.
     *
     * @param idAmbito           the id ambito
     * @param codAmbito          the cod ambito
     * @param idTipoAdempimento  the id tipo adempimento
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAdempimento     the cod adempimento
     * @param idCompilante       the id compilante
     * @param codComponenteApp   the cod componente app
     * @return the list
     */
    List<AdempimentoExtendedDTO> loadAdempimenti(Long idAmbito, String codAmbito, Long idTipoAdempimento, String codTipoAdempimento, String codAdempimento, Long idCompilante, String codComponenteApp);

    /**
     * Load adempimento by cf funzionario list.
     *
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoByCfFunzionario(String cfFunzionario);

    /**
     * Load adempimento by cf funzionario list.
     *
     * @param attoreScriva       the attore scriva
     * @param idAmbito           the id ambito
     * @param codAmbito          the cod ambito
     * @param idTipoAdempimento  the id tipo adempimento
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAdempimento     the cod adempimento
     * @param idCompilante       the id compilante
     * @return the list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoByCfFunzionario(AttoreScriva attoreScriva, Long idAmbito, String codAmbito, Long idTipoAdempimento, String codTipoAdempimento, String codAdempimento, Long idCompilante);

    /**
     * Load adempimento by id list.
     *
     * @param idAdempimento idAdempimento
     * @return List<AdempimentoExtendedDTO>       list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoById(Long idAdempimento);

    /**
     * Load adempimento by code list.
     *
     * @param codAdempimento codAdempimento
     * @return List<AdempimentoExtendedDTO>       list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoByCode(String codAdempimento);

    /**
     * Load adempimento by id tipo adempimento list.
     *
     * @param idTipoAdempimento idTipoAdempimento
     * @return List<AdempimentoExtendedDTO>       list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoByIdTipoAdempimento(Long idTipoAdempimento);

    /**
     * Load adempimento by id tipo adempimento list.
     *
     * @param idTipoAdempimento the id tipo adempimento
     * @param cfFunzionario     the cf funzionario
     * @return the list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoByIdTipoAdempimentoCfFunzionario(Long idTipoAdempimento, String cfFunzionario);

    /**
     * Load adempimento by code tipo adempimento list.
     *
     * @param codTipoAdempimento codTipoAdempimento
     * @return List<AdempimentoExtendedDTO>       list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoByCodeTipoAdempimento(String codTipoAdempimento);

    /**
     * Load adempimento by code tipo adempimento cf funzionario list.
     *
     * @param codTipoAdempimento the cod tipo adempimento
     * @param cfFunzionario      the cf funzionario
     * @return the list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoByCodeTipoAdempimentoCfFunzionario(String codTipoAdempimento, String cfFunzionario);

    /**
     * Load adempimento by id ambito list.
     *
     * @param idAmbito idAmbito
     * @return List<AdempimentoExtendedDTO>       list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoByIdAmbito(Long idAmbito);

    /**
     * Load adempimento by id ambito cf funzionario list.
     *
     * @param idAmbito      the id ambito
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoByIdAmbitoCfFunzionario(Long idAmbito, String cfFunzionario);

    /**
     * Load adempimento by code ambito list.
     *
     * @param codAmbito codAmbito
     * @return List<AdempimentoExtendedDTO>       list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoByCodeAmbito(String codAmbito);

    /**
     * Load adempimento by code ambito cf funzionario list.
     *
     * @param codAmbito     the cod ambito
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    List<AdempimentoExtendedDTO> loadAdempimentoByCodeAmbitoCfFunzionario(String codAmbito, String cfFunzionario);

    /**
     * Find by pk adempimento dto.
     *
     * @param idAdempimento idAdempimento
     * @return TipoAdempimentoDTO adempimento dto
     */
    AdempimentoDTO findByPK(Long idAdempimento);

    /**
     * Save adempimento long.
     *
     * @param adempimentoDTO AdempimentoDTO
     * @return id record salvato
     */
    Long saveAdempimento(AdempimentoDTO adempimentoDTO);

    /**
     * Update adempimento integer.
     *
     * @param adempimentoDTO AdempimentoDTO
     * @return numero record aggiornati
     */
    Integer updateAdempimento(AdempimentoDTO adempimentoDTO);

    /**
     * Delete adempimento integer.
     *
     * @param idAdempimento idAdempimento
     * @return numero record cancellati
     */
    Integer deleteAdempimento(Long idAdempimento);

}
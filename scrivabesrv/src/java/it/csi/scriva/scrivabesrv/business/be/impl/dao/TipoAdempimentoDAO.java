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

import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;

import java.util.List;

/**
 * The interface Tipo adempimento dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoAdempimentoDAO {

    /**
     * Load tipi adempimento attivi list.
     *
     * @return List<TipoAdempimentoExtendedDTO>      list
     */
    List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoAttivi();

    /**
     * Load tipi adempimento list.
     *
     * @param idAmbito           the id ambito
     * @param idCompilante       the id compilante
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAmbito          the cod ambito
     * @return the list
     */
    List<TipoAdempimentoExtendedDTO> loadTipiAdempimento(Long idAmbito, Long idCompilante, String codTipoAdempimento, String codAmbito, Boolean flgAttivo);

    /**
     * Load tipi adempimento by cf funzionario list.
     *
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByCfFunzionario(String cfFunzionario);

    /**
     * Load tipi adempimento by cf funzionario list.
     *
     * @param cfFunzionario      the cf funzionario
     * @param idAmbito           the id ambito
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAmbito          the cod ambito
     * @return the list
     */
    List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByCfFunzionario(String cfFunzionario, Long idAmbito, String codTipoAdempimento, String codAmbito, Boolean flgAttivo);

    /**
     * Load tipi adempimento by id ambito list.
     *
     * @param idAmbito idAmbito
     * @return List<TipoAdempimentoExtendedDTO>      list
     */
    List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByIdAmbito(Long idAmbito);

    /**
     * Load tipi adempimento by id ambito cf funzionario list.
     *
     * @param cfFunzionario the cf funzionario
     * @param idAmbito      the id ambito
     * @return the list
     */
    List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByIdAmbitoCfFunzionario(String cfFunzionario, Long idAmbito);

    /**
     * Load tipi adempimento by code ambito list.
     *
     * @param codAmbito codAmbito
     * @return List<TipoAdempimentoExtendedDTO>      list
     */
    List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByCodeAmbito(String codAmbito);

    /**
     * Load tipo adempimento list.
     *
     * @param idTipoAdempimento idTipoAdempimento
     * @return List<TipoAdempimentoExtendedDTO>      list
     */
    List<TipoAdempimentoExtendedDTO> loadTipoAdempimento(Long idTipoAdempimento);

    /**
     * Load tipo adempimento by code list.
     *
     * @param codTipoAdempimento codTipoAdempimento
     * @return List<TipoAdempimentoExtendedDTO>      list
     */
    List<TipoAdempimentoExtendedDTO> loadTipoAdempimentoByCode(String codTipoAdempimento);

    /**
     * Save tipo adempimento long.
     *
     * @param tipoAdempimentoDTO TipoAdempimentoDTO
     * @return id record salvato
     */
    Long saveTipoAdempimento(TipoAdempimentoDTO tipoAdempimentoDTO);

    /**
     * Update tipo adempimento integer.
     *
     * @param tipoAdempimentoDTO TipoAdempimentoDTO
     * @return numero record aggiornati
     */
    Integer updateTipoAdempimento(TipoAdempimentoDTO tipoAdempimentoDTO);

    /**
     * Delete tipo adempimento integer.
     *
     * @param idTipoAdempimento idTipoAdempimento
     * @return numero record cancellati
     */
    Integer deleteTipoAdempimento(Long idTipoAdempimento);

    /**
     * Find by pk tipo adempimento dto.
     *
     * @param idTipoAdempimento idTipoAdempimento
     * @return TipoAdempimentoDTO tipo adempimento dto
     */
    TipoAdempimentoDTO findByPK(Long idTipoAdempimento);

    /**
     * Load tipi adempimento attivi by compilante list.
     *
     * @param idCompilante idCompilante
     * @return List<TipoAdempimentoExtendedDTO>      list
     */
    List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoAttiviByCompilante(Long idCompilante);


    /**
     * Update uuid index integer.
     *
     * @param idTipoAdempimento idTipoAdempimento
     * @param uuidIndex         uuidIndex
     * @return numero record aggiornati
     */
    Integer updateUuidIndex(Long idTipoAdempimento, String uuidIndex);
}
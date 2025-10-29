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

import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;

import java.util.List;

/**
 * The interface Ambito dao.
 *
 * @author CSI PIEMONTE
 */
public interface AmbitoDAO {

    /**
     * Load ambiti attivi list.
     *
     * @return List<AmbitoDTO>     list
     */
    List<AmbitoDTO> loadAmbitiAttivi();

    /**
     * Load ambiti attivi string.
     *
     * @return the string
     */
    String loadJsonAmbitiAttivi();

    /**
     * Load ambito list.
     *
     * @param idAmbito  the id ambito
     * @param codAmbito the cod ambito
     * @return the list
     */
    List<AmbitoDTO> loadAmbito(Long idAmbito, String codAmbito);

    /**
     * Load ambito by id list.
     *
     * @param idAmbito idAmbito
     * @return List<AmbitoDTO>     list
     */
    List<AmbitoDTO> loadAmbitoById(Long idAmbito);

    /**
     * Load ambito by id list list.
     *
     * @param idAmbitoList the id ambito list
     * @return the list
     */
    List<AmbitoDTO> loadAmbitoByIdList(List<Long> idAmbitoList);

    /**
     * Load ambito by code list.
     *
     * @param codAmbito codice ambito
     * @return List<AmbitoDTO>     list
     */
    List<AmbitoDTO> loadAmbitoByCode(String codAmbito);

    /**
     * Load ambito by cf funzionario list.
     *
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    List<AmbitoDTO> loadAmbitoByCfFunzionario(String cfFunzionario);


    /**
     * Save ambito long.
     *
     * @param ambito AmbitoDTO
     * @return id record inserito
     */
    Long saveAmbito(AmbitoDTO ambito);

    /**
     * Update ambito integer.
     *
     * @param ambito AmbitoDTO
     * @return numero record aggiornati
     */
    Integer updateAmbito(AmbitoDTO ambito);

    /**
     * Delete ambito.
     *
     * @param idAmbito idAmbito
     */
    void deleteAmbito(Long idAmbito);

}
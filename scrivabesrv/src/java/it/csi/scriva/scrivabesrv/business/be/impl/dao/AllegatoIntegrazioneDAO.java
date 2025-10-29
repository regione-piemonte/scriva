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

import it.csi.scriva.scrivabesrv.dto.AllegatoIntegrazioneDTO;

import java.util.Date;
import java.util.List;

/**
 * The interface Allegato integrazione dao.
 *
 * @author CSI PIEMONTE
 */
public interface AllegatoIntegrazioneDAO {

    /**
     * Load allegato integrazione list.
     *
     * @param idIntegrazioneIstanza the id integrazione istanza
     * @return the list
     */
    List<AllegatoIntegrazioneDTO> loadAllegatoIntegrazione(Long idIntegrazioneIstanza);


    /**
     * Saveallegato integrazione compilante long.
     *
     * @param dto     the dto
     * @param dataIns the data ins
     * @return the long
     */
    Long saveAllegatoIntegrazione(AllegatoIntegrazioneDTO dto, Date dataIns);


    /**
     * Delete allegato integrazione integer.
     *
     * @param idIntegrazioneIstanza the id integrazione istanza
     * @return the integer
     */
    Integer deleteAllegatoIntegrazione(Long idIntegrazioneIstanza);

    /**
     * Delete allegato integrazione by id allegato integer.
     *
     * @param uuidIndex the uuid index
     * @return the integer
     */
    Integer deleteAllegatoIntegrazioneByUuidIndexAllegato(String uuidIndex);

}
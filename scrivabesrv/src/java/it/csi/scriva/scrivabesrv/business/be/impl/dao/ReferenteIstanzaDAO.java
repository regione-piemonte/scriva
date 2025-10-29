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

import it.csi.scriva.scrivabesrv.dto.ReferenteIstanzaDTO;

import java.util.List;

/**
 * The interface Referente istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface ReferenteIstanzaDAO {

    /**
     * Load referenti istanze list.
     *
     * @return List<ReferenteIstanzaDTO> list
     */
    List<ReferenteIstanzaDTO> loadReferentiIstanze();

    /**
     * Load referente istanza list.
     *
     * @param idReferenteIstanza idReferenteIstanza
     * @return List<ReferenteIstanzaDTO> list
     */
    List<ReferenteIstanzaDTO> loadReferenteIstanza(Long idReferenteIstanza);

    /**
     * Load referenti istanza by id istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<ReferenteIstanzaDTO> list
     */
    List<ReferenteIstanzaDTO> loadReferentiIstanzaByIdIstanza(Long idIstanza);

    /**
     * Load referenti istanza by code istanza list.
     *
     * @param codeIstanza codeIstanza
     * @return List<ReferenteIstanzaDTO> list
     */
    List<ReferenteIstanzaDTO> loadReferentiIstanzaByCodeIstanza(String codeIstanza);

    /**
     * Save referente istanza long.
     *
     * @param dto ReferenteIstanzaDTO
     * @return id record salvato
     */
    Long saveReferenteIstanza(ReferenteIstanzaDTO dto);

    /**
     * Update referente istanza integer.
     *
     * @param dto ReferenteIstanzaDTO
     * @return numero record aggiornati
     */
    Integer updateReferenteIstanza(ReferenteIstanzaDTO dto);

    /**
     * Delete referente istanza integer.
     *
     * @param uid uid
     * @return numero record cancellati
     */
    Integer deleteReferenteIstanza(String uid);

    /**
     * Delete referente istanza by id integer.
     *
     * @param idReferenteIstanza idReferenteIstanza
     * @return numero record cancellati
     */
    Integer deleteReferenteIstanzaById(Long idReferenteIstanza);

    /**
     * Retrieves the ID of an instance based on the provided unique identifier (UID).
     *
     * @param uid the unique identifier of the instance
     * @return the ID of the instance corresponding to the given UID
     */
    Long loadIdIstanzaByUID(String uid);
}
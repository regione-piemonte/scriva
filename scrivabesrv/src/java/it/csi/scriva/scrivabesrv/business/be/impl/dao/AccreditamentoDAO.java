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

import it.csi.scriva.scrivabesrv.dto.AccreditamentoDTO;

import java.util.List;

/**
 * The interface Accreditamento dao.
 *
 * @author CSI PIEMONTE
 */
public interface AccreditamentoDAO {

    /**
     * Load accreditamento list.
     *
     * @param uid uid
     * @param otp otp
     * @return List<AccreditamentoDTO>  list
     */
    List<AccreditamentoDTO> loadAccreditamento(String uid, String otp);

    /**
     * Load accreditamento by pk accreditamento dto.
     *
     * @param pk idRichiestaAccredito
     * @return AccreditamentoDTO accreditamento dto
     */
    AccreditamentoDTO loadAccreditamentoByPK(Long pk);

    /**
     * Load accreditamento by uid accreditamento dto.
     *
     * @param uid the uid
     * @return the accreditamento dto
     */
    AccreditamentoDTO loadAccreditamentoByUid(String uid);

    /**
     * Save accreditamento long.
     *
     * @param dto AccreditamentoDTO
     * @return id_richiesta_accredito long
     */
    Long saveAccreditamento(AccreditamentoDTO dto);

    /**
     * Update accreditamento integer.
     *
     * @param dto AccreditamentoDTO
     * @return numero record aggiornati
     */
    Integer updateAccreditamento(AccreditamentoDTO dto);

    /**
     * Delete by cf.
     *
     * @param cf codice fiscale
     */
    void deleteByCF(String cf);
}
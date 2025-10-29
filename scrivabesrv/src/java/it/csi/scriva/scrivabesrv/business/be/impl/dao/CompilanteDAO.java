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

import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;

import java.util.List;

/**
 * The interface Compilante dao.
 *
 * @author CSI PIEMONTE
 */
public interface CompilanteDAO {

    /**
     * Load compilanti list.
     *
     * @return List<CompilanteDTO> list
     */
    List<CompilanteDTO> loadCompilanti();

    /**
     * Load compilante by codice fiscale list.
     *
     * @param codiceFiscale codiceFiscale
     * @return List<CompilanteDTO> list
     */
    List<CompilanteDTO> loadCompilanteByCodiceFiscale(String codiceFiscale);

    /**
     * Save compilante long.
     *
     * @param dto CompilanteDTO
     * @return id record inserito
     */
    Long saveCompilante(CompilanteDTO dto);

    /**
     * Update compilante integer.
     *
     * @param dto CompilanteDTO
     * @return numero record aggiornati
     */
    Integer updateCompilante(CompilanteDTO dto);

    /**
     * Load compilante list.
     *
     * @param idCompilante idCompilante
     * @return List<CompilanteDTO> list
     */
    List<CompilanteDTO> loadCompilante(Long idCompilante);

    /**
     * Delete compilante integer.
     *
     * @param uid uid
     * @return numero record cancallati
     */
    Integer deleteCompilante(String uid);

}
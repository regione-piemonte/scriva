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

import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaDTO;
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaExtendedDTO;

import java.util.List;

/**
 * The interface Compilante preferenza dao.
 *
 * @author CSI PIEMONTE
 */
public interface CompilantePreferenzaDAO {

    /**
     * Load compilanti preferenze list.
     *
     * @return List<CompilantePreferenzaExtendedDTO> list
     */
    List<CompilantePreferenzaExtendedDTO> loadCompilantiPreferenze();

    /**
     * Load compilante preferenza list.
     *
     * @param idPreferenza idPreferenza
     * @return List<CompilantePreferenzaExtendedDTO> list
     */
    List<CompilantePreferenzaExtendedDTO> loadCompilantePreferenza(Long idPreferenza);

    /**
     * Load compilante preferenze by compilante list.
     *
     * @param idCompilante idCompilante
     * @return List<CompilantePreferenzaExtendedDTO> list
     */
    List<CompilantePreferenzaExtendedDTO> loadCompilantePreferenzeByCompilante(Long idCompilante);

    /**
     * Load compilante preferenze by codice fiscale list.
     *
     * @param codiceFiscale codiceFiscale
     * @return List<CompilantePreferenzaExtendedDTO> list
     */
    List<CompilantePreferenzaExtendedDTO> loadCompilantePreferenzeByCodiceFiscale(String codiceFiscale);

    /**
     * Load compilante preferenze by tipo adempimento list.
     *
     * @param idCompilante      idCompilante
     * @param idTipoAdempimento idTipoAdempimento
     * @return List<CompilantePreferenzaExtendedDTO> list
     */
    List<CompilantePreferenzaExtendedDTO> loadCompilantePreferenzeByTipoAdempimento(Long idCompilante, Long idTipoAdempimento);

    /**
     * Save compilante preferenza long.
     *
     * @param dto CompilantePreferenzaDTO
     * @return id record inserito
     */
    Long saveCompilantePreferenza(CompilantePreferenzaDTO dto);

    /**
     * Delete compilante preferenza integer.
     *
     * @param idPreferenza idPreferenza
     * @return numero record cancellati
     */
    Integer deleteCompilantePreferenza(Long idPreferenza);
}
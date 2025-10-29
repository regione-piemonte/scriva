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

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.IntegrazioneIstanzaExtendedDTO;

import java.util.List;

/**
 * The interface Integrazione istanza service.
 *
 * @author CSI PIEMONTE
 */
public interface IntegrazioneIstanzaService {

    /**
     * Load integrazioni istanza list.
     *
     * @param idIstanza           the id istanza
     * @param codTipoIntegrazione the cod tipo integrazione
     * @return the list
     */
    List<IntegrazioneIstanzaExtendedDTO> loadIntegrazioniIstanzaByIdIstanza(Long idIstanza, String codTipoIntegrazione);

    /**
     * Load integrazioni istanza by id list.
     *
     * @param idIntegrazioneIstanza the id istanza
     * @return the list
     */
    List<IntegrazioneIstanzaExtendedDTO> loadIntegrazioniIstanzaById(Long idIntegrazioneIstanza);

    /**
     * Save integrazione istanza long.
     *
     * @param integrazioneIstanza the integrazione istanza
     * @param attoreScriva        the attore scriva
     * @return the long
     */
    Long saveIntegrazioneIstanza(IntegrazioneIstanzaExtendedDTO integrazioneIstanza, AttoreScriva attoreScriva);

    /**
     * Update integrazione istanza integer.
     *
     * @param integrazioneIstanza the integrazione istanza
     * @param attoreScriva        the attore scriva
     * @return the integer
     */
    Integer updateIntegrazioneIstanza(IntegrazioneIstanzaExtendedDTO integrazioneIstanza, AttoreScriva attoreScriva);

}
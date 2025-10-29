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

import it.csi.scriva.scrivabesrv.dto.IntegrazioneIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IntegrazioneIstanzaExtendedDTO;

import java.util.Date;
import java.util.List;

/**
 * The interface Integrazione istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface IntegrazioneIstanzaDAO {

    /**
     * Load integrazioni istanza list.
     *
     * @param idIstanza             the id istanza
     * @param idIntegrazioneIstanza the id integrazione istanza
     * @param codTipoIntegrazione   the cod tipo integrazione
     * @return the list
     */
    List<IntegrazioneIstanzaExtendedDTO> loadIntegrazioniIstanza(Long idIstanza, Long idIntegrazioneIstanza, String codTipoIntegrazione);

    /**
     * Load integrazioni istanza no inviata list.
     *
     * @param idIstanza           the id istanza
     * @param codTipoIntegrazione the cod tipo integrazione
     * @return the list
     */
    List<IntegrazioneIstanzaExtendedDTO> loadIntegrazioniIstanzaNoInviata(Long idIstanza, String codTipoIntegrazione);

    /**
     * Save integrazione istanza long.
     *
     * @param integrazioneIstanza the integrazione istanza
     * @param dataIns             the data ins
     * @return the long
     */
    Long saveIntegrazioneIstanza(IntegrazioneIstanzaDTO integrazioneIstanza, Date dataIns);

    /**
     * Update integrazione istanza integer.
     *
     * @param integrazioneIstanza the integrazione istanza
     * @param now                 the now
     * @return the integer
     */
    Integer updateIntegrazioneIstanza(IntegrazioneIstanzaDTO integrazioneIstanza, Date now);

    /**
     * Delete integrazione istanza integer.
     *
     * @param idIstanza             the id istanza
     * @param idIntegrazioneIstanza the id integrazione istanza
     * @param gestUid               the gest uid
     * @return the integer
     */
    Integer deleteIntegrazioneIstanza(Long idIstanza, Long idIntegrazioneIstanza, String gestUid);


    /**
     * Update data integrazione protocollo allegati integer.
     *
     * @param integrazioneIstanzaDTO the integrazione istanza dto
     * @param dataIntegrazione       the data integrazione
     * @return the integer
     */
    Integer updateDataIntegrazioneProtocolloAllegati(IntegrazioneIstanzaDTO integrazioneIstanzaDTO, Date dataIntegrazione);

}
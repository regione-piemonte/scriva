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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAbilitazioneDTO;

import java.util.List;

/**
 * The interface Abilitazioni service.
 *
 * @author CSI PIEMONTE
 */
public interface AbilitazioniService {

    /**
     * Load abilitazioni by id istanza cf attore list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    List<TipoAbilitazioneDTO> loadAbilitazioniByIdIstanzaCFAttore(Long idIstanza, AttoreScriva attoreScriva);

    /**
     * Load abilitazioni revocabili for istanza by id istanza list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    List<IstanzaAttoreExtendedDTO> loadAbilitazioniRevocabiliForIstanzaByIdIstanza(Long idIstanza, AttoreScriva attoreScriva);

    /**
     * Load abilitazioni concesse for istanza by id istanza list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    List<IstanzaAttoreExtendedDTO> loadAbilitazioniConcesseForIstanzaByIdIstanza(Long idIstanza, AttoreScriva attoreScriva);

    /**
     * Save abilitazione list.
     *
     * @param istanzaAttore the istanza attore
     * @param attoreScriva  the attore scriva
     * @return the list
     * @throws GenericException the generic exception
     */
    List<IstanzaAttoreExtendedDTO> saveAbilitazione(IstanzaAttoreExtendedDTO istanzaAttore, AttoreScriva attoreScriva) throws GenericException;

    /**
     * Update abilitazione list.
     *
     * @param istanzaAttore the istanza attore
     * @param attoreScriva  the attore scriva
     * @return the list
     * @throws GenericException the generic exception
     */
    IstanzaAttoreExtendedDTO updateAbilitazione(IstanzaAttoreExtendedDTO istanzaAttore, AttoreScriva attoreScriva) throws GenericException;

}
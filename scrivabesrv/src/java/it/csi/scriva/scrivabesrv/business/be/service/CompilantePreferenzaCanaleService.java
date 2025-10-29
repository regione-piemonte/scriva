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
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaCanaleExtendedDTO;

import java.util.List;

/**
 * The interface Compilante preferenza canale service.
 *
 * @author CSI PIEMONTE
 */
public interface CompilantePreferenzaCanaleService {

    /**
     * Load compilante preferenze by codice fiscale list.
     *
     * @param cfCompilante the cf compilante
     * @return the list
     */
    List<CompilantePreferenzaCanaleExtendedDTO> loadCompilantePreferenzeByCodiceFiscale(String cfCompilante);

    /**
     * Load all compilante preferenze by codice fiscale list.
     *
     * @param cfCompilante the cf compilante
     * @return the list
     */
    List<CompilantePreferenzaCanaleExtendedDTO> loadAllCompilantePreferenzeByCodiceFiscale(String cfCompilante);

    /**
     * Load all compilante preferenze list by codice fiscale list.
     *
     * @param cfCompilante      the cf compilante
     * @param codCanaleNotifica the cod canale notifica
     * @return the list
     */
    Boolean checkCompilantePreferenzeCanale(String cfCompilante, String codCanaleNotifica);

    /**
     * Upsert compilante preferenza canale integer.
     *
     * @param compilantePreferenzaCanaleList the compilante preferenza canale list
     * @param attoreScriva                   the attore scriva
     * @return the integer
     */
    Integer upsertCompilantePreferenzaCanale(List<CompilantePreferenzaCanaleExtendedDTO> compilantePreferenzaCanaleList, AttoreScriva attoreScriva);

}
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

import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaCanaleDTO;
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaCanaleExtendedDTO;

import java.util.List;

/**
 * The interface Compilante preferenza canale dao.
 *
 * @author CSI PIEMONTE
 */
public interface CompilantePreferenzaCanaleDAO {

    /**
     * Load compilanti preferenze canale list.
     *
     * @return the list
     */
    List<CompilantePreferenzaCanaleExtendedDTO> loadCompilantiPreferenzeCanale();

    /**
     * Load compilante preferenza canale list.
     *
     * @param idCompilantePreferenzaCanale the id compilante preferenza canale
     * @return the list
     */
    List<CompilantePreferenzaCanaleExtendedDTO> loadCompilantePreferenzaCanale(Long idCompilantePreferenzaCanale);

    /**
     * Load compilante preferenze by id compilante list.
     *
     * @param idCompilante the id compilante
     * @param flgDefault   the flg default
     * @return the list
     */
    List<CompilantePreferenzaCanaleExtendedDTO> loadCompilantePreferenzeCanaleByIdCompilante(Long idCompilante, Boolean flgDefault);

    /**
     * Load compilante preferenze by codice fiscale list.
     *
     * @param cfCompilante the codice fiscale
     * @param flgDefault   the flg default
     * @return the list
     */
    List<CompilantePreferenzaCanaleExtendedDTO> loadCompilantePreferenzeCanaleByCodiceFiscale(String cfCompilante, Boolean flgDefault);

    /**
     * Load compilante preferenze canale by codice fiscale with union list.
     *
     * @param flgDefault the flg default
     * @return the list
     */
    List<CompilantePreferenzaCanaleExtendedDTO> loadPreferenzeCanale(Boolean flgDefault);

    /**
     * Upsert compilante preferenza integer.
     *
     * @param compilantePreferenzaCanale the compilante preferenza canale
     * @return the integer
     */
    Integer upsertCompilantePreferenzaCanale(CompilantePreferenzaCanaleDTO compilantePreferenzaCanale);

}
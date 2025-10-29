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

import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;

import java.util.List;

/**
 * The interface Compilanti service.
 *
 * @author CSI PIEMONTE
 */
public interface CompilantiService {

    /**
     * Load compilanti list.
     *
     * @return the list
     */
    List<CompilanteDTO> loadCompilanti();

    /**
     * Load compilanti list.
     *
     * @param codiceFiscale the codice fiscale
     * @return the list
     */
    List<CompilanteDTO> loadCompilanti(String codiceFiscale);

    /**
     * Updatec compilante integer.
     *
     * @param compilante the compilante
     * @return the integer
     */
    Integer updateCompilante(CompilanteDTO compilante);

}
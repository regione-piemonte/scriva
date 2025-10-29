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

import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;

import java.util.List;

/**
 * The interface Ambiti service.
 *
 * @author CSI PIEMONTE
 */
public interface AmbitiService {

    /**
     * Load ambito list.
     *
     * @param idAmbito  the id ambito
     * @param codAmbito the cod ambito
     * @return the list
     */
    List<AmbitoDTO> loadAmbiti(Long idAmbito, String codAmbito);

    /**
     * Load json ambiti string.
     *
     * @param idAmbito  the id ambito
     * @param codAmbito the cod ambito
     * @return the string
     */
    String loadJsonAmbiti(Long idAmbito, String codAmbito);

    /**
     * Load ambito list.
     *
     * @param idAmbito the id ambito
     * @return the list
     */
    List<AmbitoDTO> loadAmbito(Long idAmbito);

}
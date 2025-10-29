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

import it.csi.scriva.scrivabesrv.dto.FunzionarioCompetenzaExtendedDTO;

import java.util.List;

/**
 * The interface Funzionario competenza dao.
 *
 * @author CSI PIEMONTE
 */
public interface FunzionarioCompetenzaDAO {

    /**
     * Load funzionari competenza list.
     *
     * @return the list
     */
    List<FunzionarioCompetenzaExtendedDTO> loadFunzionariCompetenza();

    /**
     * Load funzionario competenza by id funzionario list.
     *
     * @param idFunzionario the id funzionario
     * @return the list
     */
    List<FunzionarioCompetenzaExtendedDTO> loadFunzionarioCompetenzaByIdFunzionario(Long idFunzionario);

    /**
     * Load funzionario competenza by id competenza list.
     *
     * @param idCompetenza the id competenza
     * @return the list
     */
    List<FunzionarioCompetenzaExtendedDTO> loadFunzionarioCompetenzaByIdCompetenza(Long idCompetenza);

    /**
     * Load funzionario competenza by id funzionario competenza list.
     *
     * @param idFunzionario the id funzionario
     * @param idCompetenza  the id competenza
     * @return the list
     */
    List<FunzionarioCompetenzaExtendedDTO> loadFunzionarioCompetenzaByIdFunzionarioCompetenza(Long idFunzionario, Long idCompetenza);

    /**
     * Load funzionario competenza by cf list.
     *
     * @param codiceFiscaleFunzionario the codice fiscale funzionario
     * @return the list
     */
    List<FunzionarioCompetenzaExtendedDTO> loadFunzionarioCompetenzaByCf(String codiceFiscaleFunzionario);

}
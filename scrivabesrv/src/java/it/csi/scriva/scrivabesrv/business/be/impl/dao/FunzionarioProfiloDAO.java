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

import it.csi.scriva.scrivabesrv.dto.FunzionarioProfiloExtendedDTO;

import java.util.List;

/**
 * The interface Funzionario profilo dao.
 *
 * @author CSI PIEMONTE
 */
public interface FunzionarioProfiloDAO {

    /**
     * Load funzionari profilo list.
     *
     * @return the list
     */
    List<FunzionarioProfiloExtendedDTO> loadFunzionariProfilo();

    /**
     * Load funzionario profilo by id funzionario list.
     *
     * @param idFunzionario the id funzionario
     * @return the list
     */
    List<FunzionarioProfiloExtendedDTO> loadFunzionarioProfiloByIdFunzionario(Long idFunzionario);

    /**
     * Load funzionario profilo by id profilo list.
     *
     * @param idProfilo the id profilo
     * @return the list
     */
    List<FunzionarioProfiloExtendedDTO> loadFunzionarioProfiloByIdProfilo(Long idProfilo);

    /**
     * Load funzionario profilo by id funzionario profilo list.
     *
     * @param idFunzionario the id funzionario
     * @param idProfilo     the id profilo
     * @return the list
     */
    List<FunzionarioProfiloExtendedDTO> loadFunzionarioProfiloByIdFunzionarioProfilo(Long idFunzionario, Long idProfilo);

    /**
     * Load funzionario profilo by cf list.
     *
     * @param codiceFiscaleFunzionario the codice fiscale funzionario
     * @return the list
     */
    List<FunzionarioProfiloExtendedDTO> loadFunzionarioProfiloByCf(String codiceFiscaleFunzionario);
}
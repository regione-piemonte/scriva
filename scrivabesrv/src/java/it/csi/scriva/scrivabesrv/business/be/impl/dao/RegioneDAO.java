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

import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;

import java.util.List;

/**
 * The interface Regione dao.
 *
 * @author CSI PIEMONTE
 */
public interface RegioneDAO {

    /**
     * Load regioni list.
     *
     * @return the list
     */
    List<RegioneExtendedDTO> loadRegioni();

    /**
     * Load regioni list.
     *
     * @param codIstatRegione the cod istat regione
     * @param codIstatNazione the cod istat nazione
     * @return the list
     */
    List<RegioneExtendedDTO> loadRegioni(String codIstatRegione, String codIstatNazione);

    /**
     * Load regioni attive list.
     *
     * @return the list
     */
    List<RegioneExtendedDTO> loadRegioniAttive();

    /**
     * Load regione by id list.
     *
     * @param idRegione the id regione
     * @return the list
     */
    List<RegioneExtendedDTO> loadRegioneById(Long idRegione);

    /**
     * Load regione by cod istat list.
     *
     * @param codIstatRegione the cod istat regione
     * @return the list
     */
    List<RegioneExtendedDTO> loadRegioneByCodIstat(String codIstatRegione);

    /**
     * Load regione by denominazione list.
     *
     * @param denomRegione the denom regione
     * @return the list
     */
    List<RegioneExtendedDTO> loadRegioneByDenominazione(String denomRegione);

    /**
     * Load regione by id nazione list.
     *
     * @param idNazione the id nazione
     * @return the list
     */
    List<RegioneExtendedDTO> loadRegioneByIdNazione(Long idNazione);

    /**
     * Load regione by cod istat nazione list.
     *
     * @param codIstatNazione the cod istat nazione
     * @return the list
     */
    List<RegioneExtendedDTO> loadRegioneByCodIstatNazione(String codIstatNazione);

    /**
     * Load regione by denom nazione list.
     *
     * @param denomNazione the denom nazione
     * @return the list
     */
    List<RegioneExtendedDTO> loadRegioneByDenomNazione(Long denomNazione);

}
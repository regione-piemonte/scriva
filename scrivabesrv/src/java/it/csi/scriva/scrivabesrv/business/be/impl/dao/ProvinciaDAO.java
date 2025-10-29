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

import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;

import java.util.List;

/**
 * The interface Provincia dao.
 *
 * @author CSI PIEMONTE
 */
public interface ProvinciaDAO {

    /**
     * Load province list.
     *
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvince();

    /**
     * Load province list.
     *
     * @param codIstatProvincia the cod istat provincia
     * @param codIstatRegione   the cod istat regione
     * @param idAdempimento     the id adempimento
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvince(String codIstatProvincia, String codIstatRegione, Long idAdempimento);

    /**
     * Load province attive list.
     *
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvinceAttive();

    /**
     * Load provincia by id list.
     *
     * @param idProvincia the id provincia
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvinciaById(Long idProvincia);

    /**
     * Load provincia by cod istat list.
     *
     * @param codIstatProvincia the cod istat provincia
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvinciaByCodIstat(String codIstatProvincia);

    /**
     * Load provincia by denominazione list.
     *
     * @param denomProvincia the denom provincia
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvinciaByDenominazione(String denomProvincia);

    /**
     * Load provincia by id regione list.
     *
     * @param idRegione the id regione
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvinciaByIdRegione(Long idRegione);

    /**
     * Load provincia by cod istat regione list.
     *
     * @param codIstatRegione the cod istat regione
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvinciaByCodIstatRegione(String codIstatRegione);

    /**
     * Load provincia by denom regione list.
     *
     * @param denomRegione the denom regione
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvinciaByDenomRegione(Long denomRegione);

    /**
     * Load provincia by id adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    List<ProvinciaExtendedDTO> loadProvinciaByIdAdempimento(Long idAdempimento);
}
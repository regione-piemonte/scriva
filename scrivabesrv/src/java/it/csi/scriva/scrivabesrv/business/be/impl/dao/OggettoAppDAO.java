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

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.OggettoAppExtendedDTO;

import java.util.List;

/**
 * The interface Oggetto app dao.
 *
 * @author CSI PIEMONTE
 */
public interface OggettoAppDAO {

    /**
     * Load oggetti app list.
     *
     * @return List<OggettoAppExtendedDTO>  list
     */
    List<OggettoAppExtendedDTO> loadOggettiApp();

    /**
     * Load oggetto app by id list.
     *
     * @param idOggettoApp idOggettoApp
     * @return List<OggettoAppExtendedDTO>  list
     */
    List<OggettoAppExtendedDTO> loadOggettoAppById(Long idOggettoApp);

    /**
     * Load oggetto app by code list.
     *
     * @param codOggettoApp codOggettoApp
     * @return List<OggettoAppExtendedDTO>  list
     */
    List<OggettoAppExtendedDTO> loadOggettoAppByCode(String codOggettoApp);

    /**
     * Load oggetto app by id istanza id istanza and attore list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    List<OggettoAppExtendedDTO> loadOggettoAppByIdIstanzaIdIstanzaAndAttore(Long idIstanza, AttoreScriva attoreScriva);

}
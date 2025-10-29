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

import it.csi.scriva.scrivabesrv.dto.MappaFonteEsternaDTO;

import java.util.List;

/**
 * The interface Mappa fonte esterna dao.
 *
 * @author CSI PIEMONTE
 */
public interface MappaFonteEsternaDAO {

    /**
     * Load mappe fonte esterna list.
     *
     * @return the list
     */
    List<MappaFonteEsternaDTO> loadMappeFonteEsterna();

    /**
     * Load mappa fonte esterna by id list.
     *
     * @param idMappaFonteEsterna the id mappa fonte esterna
     * @return the list
     */
    List<MappaFonteEsternaDTO> loadMappaFonteEsternaById(Long idMappaFonteEsterna);

    /**
     * Load mappa fonte esterna by cod masterdata fonte list.
     *
     * @param codMasterdata the cod masterdata
     * @param codFonte      the cod fonte
     * @return the list
     */
    List<MappaFonteEsternaDTO> loadMappaFonteEsternaByCodMasterdataFonte(String codMasterdata, String infoFonte, String codFonte);

}
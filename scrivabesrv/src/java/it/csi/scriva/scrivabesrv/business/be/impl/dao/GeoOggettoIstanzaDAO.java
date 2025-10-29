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

import it.csi.scriva.scrivabesrv.dto.GeoOggettoIstanzaDTO;

import java.util.List;

/**
 * The interface Geo oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface GeoOggettoIstanzaDAO {

    /**
     * Gets oggetti.
     *
     * @param idVirtuale       idVirtuale
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<GeoOggettoIstanzaDTO>   oggetti
     */
    List<GeoOggettoIstanzaDTO> getOggetti(Long idVirtuale, Long idOggettoIstanza);

    /**
     * Gets oggetti.
     *
     * @param idVirtuale           the id virtuale
     * @param idOggettoIstanzaList the id oggetto istanza list
     * @return the oggetti
     */
    List<GeoOggettoIstanzaDTO> getOggetti(Long idVirtuale, List<Long> idOggettoIstanzaList);

    /**
     * Gets oggetti.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<GeoOggettoIstanzaDTO>   oggetti
     */
    List<GeoOggettoIstanzaDTO> getOggetti( Long idOggettoIstanza);

    /**
     * Gets next id geometrico.
     *
     * @return sequence successiva per idGeometrico
     */
    Long getNextIdGeometrico();

    /**
     * Delete oggetto istanza integer.
     *
     * @param idGeometrico idGeometrico
     * @return numero record cancellati
     */
    Integer deleteOggettoIstanza(Long idGeometrico);

    /**
     * Load coordinate geo aree by oggetto istanza list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    List<String> loadCoordinateGeoAreeByOggettoIstanza(Long idOggettoIstanza);

    /**
     * Load gml geometry by id oggetto istanza string.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the string
     */
    String loadGMLGeometryByIdOggettoIstanza(Long idOggettoIstanza);

}
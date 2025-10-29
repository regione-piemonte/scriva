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

import it.csi.scriva.scrivabesrv.dto.GeoLineaOggettoIstanzaDTO;

import java.util.Date;
import java.util.List;

/**
 * The interface Geo linea oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface GeoLineaOggettoIstanzaDAO {

    /**
     * Load geo linee oggetti istanza list.
     *
     * @return List<GeoLineaOggettoIstanzaDTO> list
     */
    List<GeoLineaOggettoIstanzaDTO> loadGeoLineeOggettiIstanza();

    /**
     * Load geo linea oggetto istanza list.
     *
     * @param idGeoLineaOggettoIstanza idGeoLineaOggettoIstanza
     * @return List<GeoLineaOggettoIstanzaDTO> list
     */
    List<GeoLineaOggettoIstanzaDTO> loadGeoLineaOggettoIstanza(Long idGeoLineaOggettoIstanza);

    /**
     * Load geo linea oggetto istanza by id oggetto istanza list.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<GeoLineaOggettoIstanzaDTO> list
     */
    List<GeoLineaOggettoIstanzaDTO> loadGeoLineaOggettoIstanzaByIdOggettoIstanza(Long idOggettoIstanza);

    /**
     * Load geo linea oggetto istanza by id geometrico list.
     *
     * @param idGeometrico idGeometrico
     * @return List<GeoLineaOggettoIstanzaDTO> list
     */
    List<GeoLineaOggettoIstanzaDTO> loadGeoLineaOggettoIstanzaByIdGeometrico(Long idGeometrico);

    /**
     * Save geo linea oggetto istanza long.
     *
     * @param dto GeoLineaOggettoIstanzaDTO
     * @return id record inserito
     */
    Long saveGeoLineaOggettoIstanza(GeoLineaOggettoIstanzaDTO dto);

    /**
     * Update geo linea oggetto istanza integer.
     *
     * @param dto GeoLineaOggettoIstanzaDTO
     * @return numero record aggiornati
     */
    Integer updateGeoLineaOggettoIstanza(GeoLineaOggettoIstanzaDTO dto);

    /**
     * Delete geo linea oggetto istanza.
     *
     * @param gestUID gestUID
     */
    void deleteGeoLineaOggettoIstanza(String gestUID);

    /**
     * Save copy geo linea oggetto istanza long.
     *
     * @param idOggettoIstanzaNew    idOggettoIstanzaNew
     * @param idOggettoIstanzaToCopy idOggettoIstanzaToCopy
     * @param gestDataIns            gestDataIns
     * @param gestAttoreIns          gestAttoreIns
     * @return id record inserito
     */
    Long saveCopyGeoLineaOggettoIstanza(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns);

    /**
     * Delete geo linea oggetto istanza by uid oggetto istanza integer.
     *
     * @param uidOggettoIstanza uidOggettoIstanza
     * @return numero record cancellati
     */
    Integer deleteGeoLineaOggettoIstanzaByUIDOggettoIstanza(String uidOggettoIstanza);

}
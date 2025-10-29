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

import it.csi.scriva.scrivabesrv.dto.GeoAreaOggettoIstanzaDTO;

import java.util.Date;
import java.util.List;

/**
 * The interface Geo area oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface GeoAreaOggettoIstanzaDAO {

    /**
     * Load geo aree oggetti istanza list.
     *
     * @return List<GeoAreaOggettoIstanzaDTO>  list
     */
    List<GeoAreaOggettoIstanzaDTO> loadGeoAreeOggettiIstanza();

    /**
     * Load geo area oggetto istanza list.
     *
     * @param idGeoAreaOggettoIstanza idGeoAreaOggettoIstanza
     * @return List<GeoAreaOggettoIstanzaDTO>  list
     */
    List<GeoAreaOggettoIstanzaDTO> loadGeoAreaOggettoIstanza(Long idGeoAreaOggettoIstanza);

    /**
     * Load geo area oggetto istanza by id oggetto istanza list.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<GeoAreaOggettoIstanzaDTO>  list
     */
    List<GeoAreaOggettoIstanzaDTO> loadGeoAreaOggettoIstanzaByIdOggettoIstanza(Long idOggettoIstanza);

    /**
     * Load geo area oggetto istanza by id geometrico list.
     *
     * @param idGeometrico idGeometrico
     * @return List<GeoAreaOggettoIstanzaDTO>  list
     */
    List<GeoAreaOggettoIstanzaDTO> loadGeoAreaOggettoIstanzaByIdGeometrico(Long idGeometrico);

    /**
     * Save geo area oggetto istanza long.
     *
     * @param dto GeoAreaOggettoIstanzaDTO
     * @return id record salvato
     */
    Long saveGeoAreaOggettoIstanza(GeoAreaOggettoIstanzaDTO dto);

    /**
     * Update geo area oggetto istanza integer.
     *
     * @param dto GeoAreaOggettoIstanzaDTO
     * @return numero record aggiornati
     */
    Integer updateGeoAreaOggettoIstanza(GeoAreaOggettoIstanzaDTO dto);

    /**
     * Delete geo area oggetto istanza.
     *
     * @param gestUID gestUID
     */
    void deleteGeoAreaOggettoIstanza(String gestUID);

    /**
     * Delete geo area oggetto istanza by uid oggetto istanza integer.
     *
     * @param uidOggettoIstanza uidOggettoIstanza
     * @return numero record cancellati
     */
    Integer deleteGeoAreaOggettoIstanzaByUIDOggettoIstanza(String uidOggettoIstanza);

    /**
     * Save copy geo area oggetto istanza long.
     *
     * @param idOggettoIstanzaNew    idOggettoIstanzaNew
     * @param idOggettoIstanzaToCopy idOggettoIstanzaToCopy
     * @param gestDataIns            gestDataIns
     * @param gestAttoreIns          gestAttoreIns
     * @return id record inserito
     */
    Long saveCopyGeoAreaOggettoIstanza(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns);

    /**
     * Load coordinate geo aree list by oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    List<String> loadCoordinateGeoAreeByOggettoIstanza(Long idOggettoIstanza);

    /**
     * Load GMLGeometry by oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the geometry string
     */
    String loadGMLGeometryByIdOggettoIstanza(Long idOggettoIstanza);

	/**
	 * @param idOggettoIstanza
	 * @return
	 */
	List<String> loadCoordinateGeoAreeByOggettoIstanzaForComuni(Long idOggettoIstanza);

}
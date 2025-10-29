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

import it.csi.scriva.scrivabesrv.dto.GeoPuntoOggettoIstanzaDTO;

import java.util.Date;
import java.util.List;

/**
 * The interface Geo punto oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface GeoPuntoOggettoIstanzaDAO {

    /**
     * Load geo punti oggetti istanza list.
     *
     * @return List<GeoPuntoOggettoIstanzaDTO> list
     */
    List<GeoPuntoOggettoIstanzaDTO> loadGeoPuntiOggettiIstanza();

    /**
     * Load geo punto oggetto istanza list.
     *
     * @param idGeoPuntoOggettoIstanza idGeoPuntoOggettoIstanza
     * @return List<GeoPuntoOggettoIstanzaDTO> list
     */
    List<GeoPuntoOggettoIstanzaDTO> loadGeoPuntoOggettoIstanza(Long idGeoPuntoOggettoIstanza);

    /**
     * Load geo punto oggetto istanza by id oggetto istanza list.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<GeoPuntoOggettoIstanzaDTO> list
     */
    List<GeoPuntoOggettoIstanzaDTO> loadGeoPuntoOggettoIstanzaByIdOggettoIstanza(Long idOggettoIstanza);

    /**
     * Load geo punto oggetto istanza by id geometrico list.
     *
     * @param idGeometrico idGeometrico
     * @return List<GeoPuntoOggettoIstanzaDTO> list
     */
    List<GeoPuntoOggettoIstanzaDTO> loadGeoPuntoOggettoIstanzaByIdGeometrico(Long idGeometrico);

    /**
     * Save geo punto oggetto istanza long.
     *
     * @param dto GeoPuntoOggettoIstanzaDTO
     * @return id record inserito
     */
    Long saveGeoPuntoOggettoIstanza(GeoPuntoOggettoIstanzaDTO dto);

    /**
     * Update geo punto oggetto istanza integer.
     *
     * @param dto GeoPuntoOggettoIstanzaDTO
     * @return numero record aggiornati
     */
    Integer updateGeoPuntoOggettoIstanza(GeoPuntoOggettoIstanzaDTO dto);

    /**
     * Delete geo punto oggetto istanza.
     *
     * @param gestUID gestUID
     */
    void deleteGeoPuntoOggettoIstanza(String gestUID);

    /**
     * Save copy geo punto oggetto istanza long.
     *
     * @param idOggettoIstanzaNew    idOggettoIstanzaNew
     * @param idOggettoIstanzaToCopy idOggettoIstanzaToCopy
     * @param gestDataIns            gestDataIns
     * @param gestAttoreIns          gestAttoreIns
     * @return id record inserito
     */
    Long saveCopyGeoPuntoOggettoIstanza(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns);

    /**
     * Delete geo punto oggetto istanza by uid oggetto istanza integer.
     *
     * @param uidOggettoIstanza uidOggettoIstanza
     * @return numero record cancellati
     */
    Integer deleteGeoPuntoOggettoIstanzaByUIDOggettoIstanza(String uidOggettoIstanza);
}
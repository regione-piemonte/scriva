/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service;

import java.util.List;

/**
 * The interface Geo service.
 *
 * @author CSI PIEMONTE
 */
public interface GeoService {

    /**
     * Load coordinate geo aree by oggetto istanza list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    List<String> loadCoordinateGeoAreeByOggettoIstanza(Long idOggettoIstanza);

    /**
     * Load coordinates geo aree by oggetto istanza as string.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the geometry string
     */
    String loadGMLGeometryByIdOggettoIstanza(Long idOggettoIstanza);

}
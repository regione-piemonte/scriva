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

import it.csi.scriva.scrivabesrv.dto.GeoPointConverterDTO;

import java.util.List;

/**
 * The interface GeoPointConverter dao.
 *
 * @author CSI PIEMONTE
 */
public interface GeoPointConverterDAO {

    /**
     * Convert GeoPointConverterDTO list.
     *
     * @param source the source
     * @return GeoPointConverterDTO point ocnverted
     */
    List<GeoPointConverterDTO> convert(GeoPointConverterDTO source);

    /**
     * Convert multipoint to polygon string.
     *
     * @param geometry the geometry
     * @return the string
     */
    String convertGeometryToPolygon(String geometry);

}
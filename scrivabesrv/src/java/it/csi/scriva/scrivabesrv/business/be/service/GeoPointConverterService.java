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

import it.csi.scriva.scrivabesrv.dto.GeoPointConverterDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * The interface Geo Point Converter service.
 *
 * @author CSI PIEMONTE
 */
public interface GeoPointConverterService {

    /**
     * Convert epsg point
     *
     * @return the point list
     */
    List<GeoPointConverterDTO> convert(Integer sourceEPSG, Integer targetEPSG, BigDecimal lat, BigDecimal lon);

}
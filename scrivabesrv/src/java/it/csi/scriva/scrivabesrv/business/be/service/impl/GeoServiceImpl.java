/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoAreaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Geo service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class GeoServiceImpl extends BaseServiceImpl implements GeoService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private GeoAreaOggettoIstanzaDAO geoAreaOggettoIstanzaDAO;

    @Autowired
    private GeoOggettoIstanzaDAO geoOggettoIstanzaDAO;

    /**
     * Load coordinate geo aree by oggetto istanza list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    @Override
    public List<String> loadCoordinateGeoAreeByOggettoIstanza(Long idOggettoIstanza) {
        logBeginInfo(className, idOggettoIstanza);
        //return geoOggettoIstanzaDAO.loadCoordinateGeoAreeByOggettoIstanza(idOggettoIstanza);
        return geoAreaOggettoIstanzaDAO.loadCoordinateGeoAreeByOggettoIstanza(idOggettoIstanza);
    }

    /**
     * Load coordinates geo aree by oggetto istanza as string.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the geometry string
     */
    @Override
    public String loadGMLGeometryByIdOggettoIstanza(Long idOggettoIstanza) {
        logBeginInfo(className, idOggettoIstanza);
        return geoOggettoIstanzaDAO.loadGMLGeometryByIdOggettoIstanza(idOggettoIstanza);
        //return geoAreaOggettoIstanzaDAO.loadGMLGeometryByIdOggettoIstanza(idOggettoIstanza);
    }

}
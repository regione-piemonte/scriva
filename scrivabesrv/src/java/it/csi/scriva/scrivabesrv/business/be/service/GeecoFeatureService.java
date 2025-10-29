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

import java.util.Map;

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Feature;
import it.csi.scriva.scrivabesrv.dto.GeecoFeature;

/**
 * The interface Geeco feature service.
 *
 * @author CSI PIEMONTE
 */
public interface GeecoFeatureService {

    /**
     * Insert feature for editing layer feature.
     *
     * @param geecoFeature the geeco feature
     * @return the feature
     */
    Map<String, Object> insertFeatureForEditingLayer(GeecoFeature geecoFeature);

    /**
     * Insert feature for editing layer old feature.
     *
     * @param geecoFeature the geeco feature
     * @return the feature
     */
    Feature insertFeatureForEditingLayerOLD(GeecoFeature geecoFeature);

      /**
     * Update feature for editing layer old feature.
     *
     * @param geecoFeature the geeco feature
     * @return the feature
     */

    Feature updateFeatureForEditingLayerOLD(GeecoFeature geecoFeature);

    /**
     * Update feature for editing layer feature.
     *
     * @param geecoFeature the geeco feature
     * @return the feature
     */
    Map<String, Object> updateFeatureForEditingLayer(GeecoFeature geecoFeature) throws GenericException;

    /**
     * Delete feature for editing layer feature.
     *
     * @param geecoFeature the geeco feature
     */
    void deleteFeatureForEditingLayer(GeecoFeature geecoFeature);

    /**
     * Insert geometry.
     *
     * @param geecoFeature     the geeco feature
     * @param idOggettoIstanza the id oggetto istanza
     */
    void insertGeometry(GeecoFeature geecoFeature, Long idOggettoIstanza);

    /**
     * Update geometry.
     *
     * @param geecoFeature     the geeco feature
     * @param idOggettoIstanza the id oggetto istanza
     */
    void updateGeometry(GeecoFeature geecoFeature, Long idOggettoIstanza);
}
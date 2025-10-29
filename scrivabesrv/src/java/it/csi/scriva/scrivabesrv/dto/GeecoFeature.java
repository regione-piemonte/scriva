/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Feature;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Geeco feature.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeecoFeature implements Serializable {

    private String idEditingSession;

    private Long idVirtuale;

    private Long idGeometrico;

    private Feature feature;

    /**
     * Gets id editing session.
     *
     * @return the id editing session
     */
    public String getIdEditingSession() {
        return idEditingSession;
    }

    /**
     * Sets id editing session.
     *
     * @param idEditingSession the id editing session
     */
    public void setIdEditingSession(String idEditingSession) {
        this.idEditingSession = idEditingSession;
    }

    /**
     * Gets id virtuale.
     *
     * @return the id virtuale
     */
    public Long getIdVirtuale() {
        return idVirtuale;
    }

    /**
     * Sets id virtuale.
     *
     * @param idVirtuale the id virtuale
     */
    public void setIdVirtuale(Long idVirtuale) {
        this.idVirtuale = idVirtuale;
    }

    /**
     * Gets id geometrico.
     *
     * @return the id geometrico
     */
    public Long getIdGeometrico() {
        return idGeometrico;
    }

    /**
     * Sets id geometrico.
     *
     * @param idGeometrico the id geometrico
     */
    public void setIdGeometrico(Long idGeometrico) {
        this.idGeometrico = idGeometrico;
    }

    /**
     * Gets feature.
     *
     * @return the feature
     */
    public Feature getFeature() {
        return feature;
    }

    /**
     * Sets feature.
     *
     * @param feature the feature
     */
    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeecoFeature that = (GeecoFeature) o;
        return Objects.equals(idEditingSession, that.idEditingSession) && Objects.equals(idVirtuale, that.idVirtuale) && Objects.equals(idGeometrico, that.idGeometrico) && Objects.equals(feature, that.feature);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idEditingSession, idVirtuale, idGeometrico, feature);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "GeecoFeature {\n" +
                "         idEditingSession:'" + idEditingSession + "'" +
                ",\n         idVirtuale:" + idVirtuale +
                ",\n         idGeometrico:" + idGeometrico +
                ",\n         feature:" + feature +
                "}\n";
    }

}
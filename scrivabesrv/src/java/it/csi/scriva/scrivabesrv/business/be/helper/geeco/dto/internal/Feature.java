/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/**
 * Feature class
 *
 * @author CSI PIEMONTE
 */
public class Feature implements Serializable{
    private static final long serialVersionUID = 736193242460261354L;

    private String type;
    private Geometry geometry;
    private HashMap<String, Object> properties;
    private Long id;

    /**
     * Instantiates a new Feature.
     */
    public Feature() {
        this.properties = new HashMap<String, Object>();
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Gets geometry.
     *
     * @return the geometry
     */
    public Geometry getGeometry() {
        return this.geometry;
    }

    /**
     * Sets geometry.
     *
     * @param geometry the geometry
     */
    public void setGeometry(final Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * Gets properties.
     *
     * @return the properties
     */
    public HashMap<String, Object> getProperties() {
        return this.properties;
    }

    /**
     * Sets properties.
     *
     * @param properties the properties
     */
    public void setProperties(final HashMap<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * equals
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Feature feature = (Feature) o;
        return Objects.equals(type, feature.type) && Objects.equals(geometry, feature.geometry) && Objects.equals(properties, feature.properties) && Objects.equals(id, feature.id);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, geometry, properties, id);
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Feature {");
        sb.append("         type:'").append(type).append("'");
        sb.append(",         geometry:").append(geometry);
        sb.append(",         properties:").append(properties);
        sb.append(",         id:").append(id);
        sb.append("}");
        return sb.toString();
    }
}
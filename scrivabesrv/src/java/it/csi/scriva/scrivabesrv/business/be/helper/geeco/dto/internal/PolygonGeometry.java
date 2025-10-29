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
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * PolygonGeometry class
 *
 * @author CSI PIEMONTE
 */
public class PolygonGeometry extends Geometry implements Serializable {
    private static final long serialVersionUID = 155967147343193073L;
    private List<List<List<BigDecimal>>> coordinates;

    /**
     * Instantiates a new Polygon geometry.
     */
    public PolygonGeometry() {
        super.setType("Polygon");
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public List<List<List<BigDecimal>>> getCoordinates() {
        return this.coordinates;
    }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(final List<List<List<BigDecimal>>> coordinates) {
        this.coordinates = coordinates;
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
        PolygonGeometry that = (PolygonGeometry) o;
        return Objects.equals(coordinates, that.coordinates);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PolygonGeometry {");
        sb.append("         coordinates:").append(coordinates);
        sb.append("}");
        return sb.toString();
    }
}
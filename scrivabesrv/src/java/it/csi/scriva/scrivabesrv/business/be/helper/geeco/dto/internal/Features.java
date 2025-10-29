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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Features class
 *
 * @author CSI PIEMONTE
 */
public class Features implements Serializable{
    private static final long serialVersionUID = 8954173122337485311L;
    private String type;
    private List<Feature> features;

    /**
     * Instantiates a new Features.
     */
    public Features() {
        this.type = "FeatureCollection";
        this.features = new ArrayList<Feature>();
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
     * Gets features.
     *
     * @return the features
     */
    public List<Feature> getFeatures() {
        return this.features;
    }

    /**
     * Sets features list.
     *
     * @param features the features
     */
    public void setFeaturesList(final List<Feature> features) {
        this.features = features;
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
        Features features1 = (Features) o;
        return Objects.equals(type, features1.type) && Objects.equals(features, features1.features);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, features);
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Features {");
        sb.append("         type:'").append(type).append("'");
        sb.append(",         features:").append(features);
        sb.append("}");
        return sb.toString();
    }
}
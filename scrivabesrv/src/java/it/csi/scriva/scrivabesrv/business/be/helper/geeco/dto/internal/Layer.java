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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Layer class
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(Include.NON_NULL)
public class Layer implements Serializable {
    private static final long serialVersionUID = 8164101060227233658L;

    private boolean canInsertNewFeatures;
    private boolean canDeleteFeatures;
    private String defaultStyles;
    private Map<String, Object> defaultValues;

    private ArrayList<AttributeSchema> schemas;

    private Features features;

    private String alias;
    private String idLayer;


    /**
     * Is can insert new features boolean.
     *
     * @return the boolean
     */
    public boolean isCanInsertNewFeatures() {
        return this.canInsertNewFeatures;
    }

    /**
     * Sets can insert new features.
     *
     * @param canInsertNewFeatures the can insert new features
     */
    public void setCanInsertNewFeatures(final boolean canInsertNewFeatures) {
        this.canInsertNewFeatures = canInsertNewFeatures;
    }

    /**
     * Is can delete features boolean.
     *
     * @return the boolean
     */
    public boolean isCanDeleteFeatures() {
        return this.canDeleteFeatures;
    }

    /**
     * Sets can delete features.
     *
     * @param canDeleteFeatures the can delete features
     */
    public void setCanDeleteFeatures(final boolean canDeleteFeatures) {
        this.canDeleteFeatures = canDeleteFeatures;
    }

    /**
     * Gets schemas.
     *
     * @return the schemas
     */
    public ArrayList<AttributeSchema> getSchemas() {
        return this.schemas;
    }

    /**
     * Sets schemas.
     *
     * @param schemas the schemas
     */
    public void setSchemas(final ArrayList<AttributeSchema> schemas) {
        this.schemas = schemas;
    }

    /**
     * Gets features.
     *
     * @return the features
     */
    public Features getFeatures() {
        return this.features;
    }

    /**
     * Sets features.
     *
     * @param features the features
     */
    public void setFeatures(final Features features) {
        this.features = features;
    }

    /**
     * Gets id layer.
     *
     * @return the id layer
     */
    public String getIdLayer() {
        return this.idLayer;
    }

    /**
     * Sets id layer.
     *
     * @param idLayer the id layer
     */
    public void setIdLayer(final String idLayer) {
        this.idLayer = idLayer;
    }

    /**
     * Gets default values.
     *
     * @return the default values
     */
    public Map<String, Object> getDefaultValues() {
        return this.defaultValues;
    }

    /**
     * Sets default values.
     *
     * @param defaultValues the default values
     */
    public void setDefaultValues(final Map<String, Object> defaultValues) {
        this.defaultValues = defaultValues;
    }

    /**
     * Gets default styles.
     *
     * @return the default styles
     */
    public String getDefaultStyles() {
        return this.defaultStyles;
    }

    /**
     * Sets default styles.
     *
     * @param defaultStyles the default styles
     */
    public void setDefaultStyles(final String defaultStyles) {
        this.defaultStyles = defaultStyles;
    }

    /**
     * Gets alias.
     *
     * @return the alias
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * Sets alias.
     *
     * @param alias the alias
     */
    public void setAlias(final String alias) {
        this.alias = alias;
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
        Layer layer = (Layer) o;
        return canInsertNewFeatures == layer.canInsertNewFeatures && canDeleteFeatures == layer.canDeleteFeatures && Objects.equals(defaultStyles, layer.defaultStyles) && Objects.equals(defaultValues, layer.defaultValues) && Objects.equals(schemas, layer.schemas) && Objects.equals(features, layer.features) && Objects.equals(alias, layer.alias) && Objects.equals(idLayer, layer.idLayer);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(canInsertNewFeatures, canDeleteFeatures, defaultStyles, defaultValues, schemas, features, alias, idLayer);
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Layer {");
        sb.append("         canInsertNewFeatures:").append(canInsertNewFeatures);
        sb.append(",         canDeleteFeatures:").append(canDeleteFeatures);
        sb.append(",         defaultStyles:'").append(defaultStyles).append("'");
        sb.append(",         defaultValues:").append(defaultValues);
        sb.append(",         schemas:").append(schemas);
        sb.append(",         features:").append(features);
        sb.append(",         alias:'").append(alias).append("'");
        sb.append(",         idLayer:'").append(idLayer).append("'");
        sb.append("}");
        return sb.toString();
    }
}
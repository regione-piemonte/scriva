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
import java.util.Objects;

/**
 * AttributeSchema class
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(Include.NON_NULL)
public class AttributeSchema implements Serializable
{
    private static final long serialVersionUID = 2237493414731534006L;

    private String name;
    private String alias;
    private boolean required;
    private String type;
    private ArrayList<String> choices;
    private String geomType;
    private String numericType;
    private int maxLength;
    private boolean readonly;

    /**
     * Gets max length.
     *
     * @return the max length
     */
    public int getMaxLength() {
        return this.maxLength;
    }

    /**
     * Sets max length.
     *
     * @param maxLength the max length
     */
    public void setMaxLength(final int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * Is readonly boolean.
     *
     * @return the boolean
     */
    public boolean isReadonly() {
        return this.readonly;
    }

    /**
     * Sets readonly.
     *
     * @param readonly the readonly
     */
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(final String name) {
        this.name = name;
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
     * Is required boolean.
     *
     * @return the boolean
     */
    public boolean isRequired() {
        return this.required;
    }

    /**
     * Sets required.
     *
     * @param required the required
     */
    public void setRequired(final boolean required) {
        this.required = required;
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
     * Gets choices.
     *
     * @return the choices
     */
    public ArrayList<String> getChoices() {
        return this.choices;
    }

    /**
     * Sets choices.
     *
     * @param choices the choices
     */
    public void setChoices(final ArrayList<String> choices) {
        this.choices = choices;
    }

    /**
     * Gets geom type.
     *
     * @return the geom type
     */
    public String getGeomType() {
        return this.geomType;
    }

    /**
     * Sets geom type.
     *
     * @param geomType the geom type
     */
    public void setGeomType(final String geomType) {
        this.geomType = geomType;
    }

    /**
     * Gets numeric type.
     *
     * @return the numeric type
     */
    public String getNumericType() {
        return this.numericType;
    }

    /**
     * Sets numeric type.
     *
     * @param numericType the numeric type
     */
    public void setNumericType(final String numericType) {
        this.numericType = numericType;
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
        AttributeSchema that = (AttributeSchema) o;
        return required == that.required && maxLength == that.maxLength && readonly == that.readonly && Objects.equals(name, that.name) && Objects.equals(alias, that.alias) && Objects.equals(type, that.type) && Objects.equals(choices, that.choices) && Objects.equals(geomType, that.geomType) && Objects.equals(numericType, that.numericType);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, alias, required, type, choices, geomType, numericType, maxLength, readonly);
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AttributeSchema {");
        sb.append("         name:'").append(name).append("'");
        sb.append(",         alias:'").append(alias).append("'");
        sb.append(",         required:").append(required);
        sb.append(",         type:'").append(type).append("'");
        sb.append(",         choices:").append(choices);
        sb.append(",         geomType:'").append(geomType).append("'");
        sb.append(",         numericType:'").append(numericType).append("'");
        sb.append(",         maxLength:").append(maxLength);
        sb.append(",         readonly:").append(readonly);
        sb.append("}");
        return sb.toString();
    }
}
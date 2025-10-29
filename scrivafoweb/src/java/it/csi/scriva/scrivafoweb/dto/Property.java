/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * The type Property.
 */
public class Property {
    // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled]

    private String name = null;
    private String value = null;
    private String source = null;


    /**
     * Gets name.
     *
     * @return the name
     */
    @JsonProperty("name")

    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets value.
     *
     * @return the value
     */
    @JsonProperty("value")

    public String getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }


    /**
     * Gets source.
     *
     * @return the source
     */
    @JsonProperty("source")

    public String getSource() {
        return source;
    }

    /**
     * Sets source.
     *
     * @param source the source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(name, property.name) && Objects.equals(value, property.value) && Objects.equals(source, property.source);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, value, source);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "Property {\n" +
                "         name:'" + name + "'" +
                ",\n         value:'" + value + "'" +
                ",\n         source:'" + source + "'" +
                "}\n";
    }
}
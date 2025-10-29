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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Search header dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchHeaderDTO implements Serializable {

    private String codice;

    private String label;

    private Long ordinamento;

    /**
     * Gets codice.
     *
     * @return the codice
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Sets codice.
     *
     * @param codice the codice
     */
    public void setCodice(String codice) {
        this.codice = codice;
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label.
     *
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets ordinamento.
     *
     * @return the ordinamento
     */
    public Long getOrdinamento() {
        return ordinamento;
    }

    /**
     * Sets ordinamento.
     *
     * @param ordinamento the ordinamento
     */
    public void setOrdinamento(Long ordinamento) {
        this.ordinamento = ordinamento;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchHeaderDTO that = (SearchHeaderDTO) o;
        return Objects.equals(codice, that.codice) && Objects.equals(label, that.label) && Objects.equals(ordinamento, that.ordinamento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codice, label, ordinamento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchHeaderDTO {\n");
        sb.append("         codice:'").append(codice).append("'");
        sb.append(",\n         label:'").append(label).append("'");
        sb.append(",\n         ordinamento:").append(ordinamento);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * To json string string.
     *
     * @return string string
     */
    @JsonIgnore
    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Pub stato istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubStatoIstanzaDTO implements Serializable {

    @JsonProperty("cod_stato_istanza")
    private String codStatoIstanza;

    @JsonProperty("des_stato_istanza")
    private String desStatoIstanza;

    @JsonProperty("label_stato")
    private String labelStato;

    /**
     * Gets cod stato istanza.
     *
     * @return the cod stato istanza
     */
    public String getCodStatoIstanza() {
        return codStatoIstanza;
    }

    /**
     * Sets cod stato istanza.
     *
     * @param codStatoIstanza the cod stato istanza
     */
    public void setCodStatoIstanza(String codStatoIstanza) {
        this.codStatoIstanza = codStatoIstanza;
    }

    /**
     * Gets des stato istanza.
     *
     * @return the des stato istanza
     */
    public String getDesStatoIstanza() {
        return desStatoIstanza;
    }

    /**
     * Sets des stato istanza.
     *
     * @param desStatoIstanza the des stato istanza
     */
    public void setDesStatoIstanza(String desStatoIstanza) {
        this.desStatoIstanza = desStatoIstanza;
    }


    /**
     * Gets label stato.
     *
     * @return the label stato
     */
    public String getLabelStato() {
        return labelStato;
    }

    /**
     * Sets label stato.
     *
     * @param labelStato the label stato
     */
    public void setLabelStato(String labelStato) {
        this.labelStato = labelStato;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubStatoIstanzaDTO that = (PubStatoIstanzaDTO) o;
        return Objects.equals(codStatoIstanza, that.codStatoIstanza) && Objects.equals(desStatoIstanza, that.desStatoIstanza) && Objects.equals(labelStato, that.labelStato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codStatoIstanza, desStatoIstanza, labelStato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "PubStatoIstanzaDTO {\n" +
                "         codStatoIstanza:'" + codStatoIstanza + "'" +
                ",\n         desStatoIstanza:'" + desStatoIstanza + "'" +
                ",\n         labelStato:'" + labelStato + "'" +
                "}\n";
    }
}
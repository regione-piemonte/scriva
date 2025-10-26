/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Pub natura giuridica dto.
 *
 * @author CSI PIEMONTE
 */
public class PubNaturaGiuridicaDTO implements Serializable {

    @JsonProperty("cod_natura_giuridica")
    private String codNaturaGiuridica;

    @JsonProperty("des_natura_giuridica")
    private String desNaturaGiuridica;

    /**
     * Gets cod natura giuridica.
     *
     * @return the cod natura giuridica
     */
    public String getCodNaturaGiuridica() {
        return codNaturaGiuridica;
    }

    /**
     * Sets cod natura giuridica.
     *
     * @param codNaturaGiuridica the cod natura giuridica
     */
    public void setCodNaturaGiuridica(String codNaturaGiuridica) {
        this.codNaturaGiuridica = codNaturaGiuridica;
    }

    /**
     * Gets des natura giuridica.
     *
     * @return the des natura giuridica
     */
    public String getDesNaturaGiuridica() {
        return desNaturaGiuridica;
    }

    /**
     * Sets des natura giuridica.
     *
     * @param desNaturaGiuridica the des natura giuridica
     */
    public void setDesNaturaGiuridica(String desNaturaGiuridica) {
        this.desNaturaGiuridica = desNaturaGiuridica;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubNaturaGiuridicaDTO that = (PubNaturaGiuridicaDTO) o;
        return Objects.equals(codNaturaGiuridica, that.codNaturaGiuridica) && Objects.equals(desNaturaGiuridica, that.desNaturaGiuridica);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codNaturaGiuridica, desNaturaGiuridica);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PubNaturaGiuridicaDTO {\n");
        sb.append("         codNaturaGiuridica:'").append(codNaturaGiuridica).append("'");
        sb.append(",\n         desNaturaGiuridica:'").append(desNaturaGiuridica).append("'");
        sb.append("}\n");
        return sb.toString();
    }

}
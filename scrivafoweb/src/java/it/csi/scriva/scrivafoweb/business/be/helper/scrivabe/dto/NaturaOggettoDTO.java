/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class NaturaOggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_natura_oggetto")
    private Long idNaturaOggetto;

    @JsonProperty("cod_natura_oggetto")
    private String codNaturaOggetto;

    @JsonProperty("des_natura_oggetto")
    private String desNaturaOggetto;

    public Long getIdNaturaOggetto() {
        return idNaturaOggetto;
    }

    public void setIdNaturaOggetto(Long idNaturaOggetto) {
        this.idNaturaOggetto = idNaturaOggetto;
    }

    public String getCodNaturaOggetto() {
        return codNaturaOggetto;
    }

    public void setCodNaturaOggetto(String codNaturaOggetto) {
        this.codNaturaOggetto = codNaturaOggetto;
    }

    public String getDesNaturaOggetto() {
        return desNaturaOggetto;
    }

    public void setDesNaturaOggetto(String desNaturaOggetto) {
        this.desNaturaOggetto = desNaturaOggetto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codNaturaOggetto, desNaturaOggetto, idNaturaOggetto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NaturaOggettoDTO other = (NaturaOggettoDTO) obj;
        return Objects.equals(codNaturaOggetto, other.codNaturaOggetto) && Objects.equals(desNaturaOggetto, other.desNaturaOggetto) && Objects.equals(idNaturaOggetto, other.idNaturaOggetto);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NaturaOggettoDTO {\n    idNaturaOggetto: ").append(idNaturaOggetto).append("\n    codNaturaOggetto: ").append(codNaturaOggetto).append("\n    desNaturaOggetto: ").append(desNaturaOggetto).append("\n}");
        return builder.toString();
    }

}
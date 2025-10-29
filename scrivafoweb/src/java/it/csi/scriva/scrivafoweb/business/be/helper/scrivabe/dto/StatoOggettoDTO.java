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

public class StatoOggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_stato_oggetto")
    private Long idStatoOggetto;

    @JsonProperty("cod_stato_oggetto")
    private String codStatoOggetto;

    @JsonProperty("des_stato_oggetto")
    private String desStatoOggetto;

    public Long getIdStatoOggetto() {
        return idStatoOggetto;
    }

    public void setIdStatoOggetto(Long idStatoOggetto) {
        this.idStatoOggetto = idStatoOggetto;
    }

    public String getCodStatoOggetto() {
        return codStatoOggetto;
    }

    public void setCodStatoOggetto(String codStatoOggetto) {
        this.codStatoOggetto = codStatoOggetto;
    }

    public String getDesStatoOggetto() {
        return desStatoOggetto;
    }

    public void setDesStatoOggetto(String desStatoOggetto) {
        this.desStatoOggetto = desStatoOggetto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codStatoOggetto, desStatoOggetto, idStatoOggetto);
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
        StatoOggettoDTO other = (StatoOggettoDTO) obj;
        return Objects.equals(codStatoOggetto, other.codStatoOggetto) && Objects.equals(desStatoOggetto, other.desStatoOggetto) && Objects.equals(idStatoOggetto, other.idStatoOggetto);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StatoOggettoDTO {\n    idStatoOggetto: ").append(idStatoOggetto).append("\n    codStatoOggetto: ").append(codStatoOggetto).append("\n    desStatoOggetto: ").append(desStatoOggetto).append("\n}");
        return builder.toString();
    }

}
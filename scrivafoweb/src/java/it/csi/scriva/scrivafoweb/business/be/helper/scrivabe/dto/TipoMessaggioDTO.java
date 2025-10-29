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

public class TipoMessaggioDTO implements Serializable {

    @JsonProperty("id_tipo_messaggio")
    private Long idTipoMessaggio;

    @JsonProperty("cod_tipo_messaggio")
    private String codTipoMessaggio;

    @JsonProperty("des_tipo_messaggio")
    private String desTipoMessaggio;

    public Long getIdTipoMessaggio() {
        return idTipoMessaggio;
    }

    public void setIdTipoMessaggio(Long idTipoMessaggio) {
        this.idTipoMessaggio = idTipoMessaggio;
    }

    public String getCodTipoMessaggio() {
        return codTipoMessaggio;
    }

    public void setCodTipoMessaggio(String codTipoMessaggio) {
        this.codTipoMessaggio = codTipoMessaggio;
    }

    public String getDesTipoMessaggio() {
        return desTipoMessaggio;
    }

    public void setDesTipoMessaggio(String desTipoMessaggio) {
        this.desTipoMessaggio = desTipoMessaggio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codTipoMessaggio, desTipoMessaggio, idTipoMessaggio);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipoMessaggioDTO other = (TipoMessaggioDTO) obj;
        return Objects.equals(codTipoMessaggio, other.codTipoMessaggio)
                && Objects.equals(desTipoMessaggio, other.desTipoMessaggio)
                && Objects.equals(idTipoMessaggio, other.idTipoMessaggio);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TipoMessaggioDTO [idTipoMessaggio=").append(idTipoMessaggio).append("\n  codTipoMessaggio=")
                .append(codTipoMessaggio).append("\n  desTipoMessaggio=").append(desTipoMessaggio).append("]");
        return builder.toString();
    }
}
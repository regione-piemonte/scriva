/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class TipoSoggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_tipo_soggetto")
    private Long idTipoSoggetto;

    @JsonProperty("cod_tipo_soggetto")
    private String codiceTipoSoggetto;

    @JsonProperty("des_tipo_soggetto")
    private String descrizioneTipoSoggetto;

    public Long getIdTipoSoggetto() {
        return idTipoSoggetto;
    }

    public void setIdTipoSoggetto(Long idTipoSoggetto) {
        this.idTipoSoggetto = idTipoSoggetto;
    }

    public String getCodiceTipoSoggetto() {
        return codiceTipoSoggetto;
    }

    public void setCodiceTipoSoggetto(String codiceTipoSoggetto) {
        this.codiceTipoSoggetto = codiceTipoSoggetto;
    }

    public String getDescrizioneTipoSoggetto() {
        return descrizioneTipoSoggetto;
    }

    public void setDescrizioneTipoSoggetto(String descrizioneTipoSoggetto) {
        this.descrizioneTipoSoggetto = descrizioneTipoSoggetto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceTipoSoggetto, descrizioneTipoSoggetto, idTipoSoggetto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipoSoggettoDTO other = (TipoSoggettoDTO) obj;
        return Objects.equals(codiceTipoSoggetto, other.codiceTipoSoggetto)
                && Objects.equals(descrizioneTipoSoggetto, other.descrizioneTipoSoggetto)
                && Objects.equals(idTipoSoggetto, other.idTipoSoggetto);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class TipoSoggettoDTO {\n");
        sb.append("    idTipoSoggetto: ").append(toIndentedString(String.valueOf(idTipoSoggetto))).append("\n");
        sb.append("    codiceTipoSoggetto: ").append(toIndentedString(codiceTipoSoggetto)).append("\n");
        sb.append("    descrizioneTipoSoggetto: ").append(toIndentedString(descrizioneTipoSoggetto)).append("\n");

        sb.append("}");

        return sb.toString();
    }
}
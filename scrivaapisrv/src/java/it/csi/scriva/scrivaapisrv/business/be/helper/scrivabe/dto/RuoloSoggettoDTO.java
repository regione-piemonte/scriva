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

public class RuoloSoggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_ruolo_soggetto")
    private Long idRuoloSoggetto;

    @JsonProperty("cod_ruolo_soggetto")
    private String codiceRuoloSoggetto;

    @JsonProperty("des_ruolo_soggetto")
    private String descrizioneRuoloSoggetto;

    public Long getIdRuoloSoggetto() {
        return idRuoloSoggetto;
    }

    public void setIdRuoloSoggetto(Long idRuoloSoggetto) {
        this.idRuoloSoggetto = idRuoloSoggetto;
    }

    public String getCodiceRuoloSoggetto() {
        return codiceRuoloSoggetto;
    }

    public void setCodiceRuoloSoggetto(String codiceRuoloSoggetto) {
        this.codiceRuoloSoggetto = codiceRuoloSoggetto;
    }

    public String getDescrizioneRuoloSoggetto() {
        return descrizioneRuoloSoggetto;
    }

    public void setDescrizioneRuoloSoggetto(String descrizioneRuoloSoggetto) {
        this.descrizioneRuoloSoggetto = descrizioneRuoloSoggetto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceRuoloSoggetto, descrizioneRuoloSoggetto, idRuoloSoggetto);
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
        RuoloSoggettoDTO other = (RuoloSoggettoDTO) obj;
        return Objects.equals(codiceRuoloSoggetto, other.codiceRuoloSoggetto)
                && Objects.equals(descrizioneRuoloSoggetto, other.descrizioneRuoloSoggetto)
                && Objects.equals(idRuoloSoggetto, other.idRuoloSoggetto);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RuoloSoggettoDTO\n[idRuoloSoggetto=").append(idRuoloSoggetto)
                .append("\n codiceRuoloSoggetto=").append(codiceRuoloSoggetto)
                .append("\n descrizioneRuoloSoggetto=").append(descrizioneRuoloSoggetto).append("]");
        return builder.toString();
    }
}
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompilantePreferenzaExtendedDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_preferenza")
    private Long idPreferenza;

    @JsonProperty("compilante")
    private CompilanteDTO compilante;

    @JsonProperty("tipo_adempimento")
    private TipoAdempimentoExtendedDTO tipoAdempimento;

    public Long getIdPreferenza() {
        return idPreferenza;
    }

    public void setIdPreferenza(Long idPreferenza) {
        this.idPreferenza = idPreferenza;
    }

    public CompilanteDTO getCompilante() {
        return compilante;
    }

    public void setCompilante(CompilanteDTO compilante) {
        this.compilante = compilante;
    }

    public TipoAdempimentoExtendedDTO getTipoAdempimento() {
        return tipoAdempimento;
    }

    public void setTipoAdempimento(TipoAdempimentoExtendedDTO tipoAdempimento) {
        this.tipoAdempimento = tipoAdempimento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(compilante, idPreferenza, tipoAdempimento);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompilantePreferenzaExtendedDTO other = (CompilantePreferenzaExtendedDTO) obj;
        return Objects.equals(compilante, other.compilante) && Objects.equals(idPreferenza, other.idPreferenza) && Objects.equals(tipoAdempimento, other.tipoAdempimento);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CompilantePreferenzaExtendedDTO [idPreferenza=").append(idPreferenza).append("\n  compilante=").append(compilante).append("\n  tipoAdempimento=").append(tipoAdempimento).append("]");
        return builder.toString();
    }


}
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
 * The type Compilante preferenza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompilantePreferenzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_preferenza")
    private Long idPreferenza;

    @JsonProperty("id_compilante")
    private Long idCompilante;

    @JsonProperty("id_tipo_adempimento")
    private Long idTipoAdempimento;

    /**
     * Gets id preferenza.
     *
     * @return idPreferenza id preferenza
     */
    public Long getIdPreferenza() {
        return idPreferenza;
    }

    /**
     * Sets id preferenza.
     *
     * @param idPreferenza idPreferenza
     */
    public void setIdPreferenza(Long idPreferenza) {
        this.idPreferenza = idPreferenza;
    }

    /**
     * Gets id compilante.
     *
     * @return idCompilante id compilante
     */
    public Long getIdCompilante() {
        return idCompilante;
    }

    /**
     * Sets id compilante.
     *
     * @param idCompilante idCompilante
     */
    public void setIdCompilante(Long idCompilante) {
        this.idCompilante = idCompilante;
    }

    /**
     * Gets id tipo adempimento.
     *
     * @return idAdempimento id tipo adempimento
     */
    public Long getIdTipoAdempimento() {
        return idTipoAdempimento;
    }

    /**
     * Sets id tipo adempimento.
     *
     * @param idTipoAdempimento idAdempimento
     */
    public void setIdTipoAdempimento(Long idTipoAdempimento) {
        this.idTipoAdempimento = idTipoAdempimento;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCompilante, idPreferenza, idTipoAdempimento);
    }

    /**
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompilantePreferenzaDTO other = (CompilantePreferenzaDTO) obj;
        return Objects.equals(idCompilante, other.idCompilante) && Objects.equals(idPreferenza, other.idPreferenza) && Objects.equals(idTipoAdempimento, other.idTipoAdempimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CompilantePreferenzaDTO [idPreferenza=").append(idPreferenza).append("\n  idCompilante=").append(idCompilante).append("\n  idAdempimento=").append(idTipoAdempimento).append("]");
        return builder.toString();
    }

}
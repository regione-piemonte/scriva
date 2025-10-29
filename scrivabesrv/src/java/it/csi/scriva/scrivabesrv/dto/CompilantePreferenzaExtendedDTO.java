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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Compilante preferenza extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompilantePreferenzaExtendedDTO extends CompilantePreferenzaDTO implements Serializable {

    @JsonProperty("compilante")
    private CompilanteDTO compilante;

    @JsonProperty("tipo_adempimento")
    private TipoAdempimentoExtendedDTO tipoAdempimento;

    /**
     * Gets compilante.
     *
     * @return the compilante
     */
    public CompilanteDTO getCompilante() {
        return compilante;
    }

    /**
     * Sets compilante.
     *
     * @param compilante the compilante
     */
    public void setCompilante(CompilanteDTO compilante) {
        this.compilante = compilante;
    }

    /**
     * Gets tipo adempimento.
     *
     * @return the tipo adempimento
     */
    public TipoAdempimentoExtendedDTO getTipoAdempimento() {
        return tipoAdempimento;
    }

    /**
     * Sets tipo adempimento.
     *
     * @param tipoAdempimento the tipo adempimento
     */
    public void setTipoAdempimento(TipoAdempimentoExtendedDTO tipoAdempimento) {
        this.tipoAdempimento = tipoAdempimento;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompilantePreferenzaExtendedDTO that = (CompilantePreferenzaExtendedDTO) o;
        return Objects.equals(compilante, that.compilante) && Objects.equals(tipoAdempimento, that.tipoAdempimento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), compilante, tipoAdempimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "CompilantePreferenzaExtendedDTO {\n" +
                super.toString() +
                "         compilante:" + compilante +
                ",\n         tipoAdempimento:" + tipoAdempimento +
                "}\n";
    }

    /**
     * Gets dto.
     *
     * @return CompilantePreferenzaDTO dto
     */
    @JsonIgnore
    public CompilantePreferenzaDTO getDTO() {
        CompilantePreferenzaDTO dto = new CompilantePreferenzaDTO();
        if (null != this.getCompilante()) {
            dto.setIdCompilante(this.getCompilante().getIdCompilante());
        }
        dto.setIdPreferenza(this.getIdPreferenza());
        if (null != this.getTipoAdempimento()) {
            dto.setIdTipoAdempimento(this.getTipoAdempimento().getIdTipoAdempimento());
        }
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        return dto;
    }

}
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
 * AdempimentoExtendedDTO
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempimentoExtendedDTO extends AdempimentoDTO implements Serializable {

    @JsonProperty("tipo_adempimento")
    private TipoAdempimentoExtendedDTO tipoAdempimento;

    /**
     * Gets tipo adempimento.
     *
     * @return tipoAdempimento tipo adempimento
     */
    public TipoAdempimentoExtendedDTO getTipoAdempimento() {
        return tipoAdempimento;
    }

    /**
     * Sets tipo adempimento.
     *
     * @param tipoAdempimento tipoAdempimento
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        AdempimentoExtendedDTO that = (AdempimentoExtendedDTO) o;
        return Objects.equals(tipoAdempimento, that.tipoAdempimento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoAdempimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdempimentoExtendedDTO {");
        sb.append(super.toString());
        sb.append("         tipoAdempimento:").append(tipoAdempimento);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return AdempimentoDTO dto
     */
    @JsonIgnore
    public AdempimentoDTO getDTO() {
        AdempimentoDTO dto = new AdempimentoDTO();
        dto.setIdAdempimento(this.getIdAdempimento());
        if (null != this.getTipoAdempimento()) {
            dto.setIdTipoAdempimento(this.getTipoAdempimento().getIdTipoAdempimento());
        }
        dto.setDesAdempimento(this.getDesAdempimento());
        dto.setDesEstesaAdempimento(this.getDesEstesaAdempimento());
        dto.setCodAdempimento(this.getCodAdempimento());
        dto.setOrdinamentoAdempimento(this.getOrdinamentoAdempimento());
        dto.setFlgAttivo(this.getFlgAttivo());

        return dto;
    }
}
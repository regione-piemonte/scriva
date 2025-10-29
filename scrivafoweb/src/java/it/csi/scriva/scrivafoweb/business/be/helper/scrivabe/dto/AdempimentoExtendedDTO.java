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

/**
 * AdempimentoExtendedDTO
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoExtendedDTO extends AdempimentoDTO implements Serializable {

    @JsonProperty("tipo_adempimento")
    private TipoAdempimentoExtendedDTO tipoAdempimento;

    /**
     * @return tipoAdempimento
     */
    public TipoAdempimentoExtendedDTO getTipoAdempimento() {
        return tipoAdempimento;
    }

    /**
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

}
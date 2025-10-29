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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * TipoAdempimentoExtendedDTO
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoAdempimentoExtendedDTO extends TipoAdempimentoDTO implements Serializable {

    @JsonProperty("ambito")
    private AmbitoDTO ambito;

    private Long preferito;

    /**
     * @return ambito
     */
    public AmbitoDTO getAmbito() {
        return ambito;
    }

    /**
     * @param ambito ambito
     */
    public void setAmbito(AmbitoDTO ambito) {
        this.ambito = ambito;
    }

    /**
     * @return preferito
     */
    public Long getPreferito() {
        return preferito;
    }

    /**
     * @param preferito preferito
     */
    public void setPreferito(Long preferito) {
        this.preferito = preferito;
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
        TipoAdempimentoExtendedDTO that = (TipoAdempimentoExtendedDTO) o;
        return Objects.equals(ambito, that.ambito) && Objects.equals(preferito, that.preferito);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ambito, preferito);
    }



    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoAdempimentoExtendedDTO {");
        sb.append(super.toString());
        sb.append("         ambito:").append(ambito);
        sb.append(",         preferito:").append(preferito);
        sb.append("}");
        return sb.toString();
    }

    /**
     * @return TipoAdempimentoDTO
     */
    @JsonIgnore
    public TipoAdempimentoDTO getDTO() {
        TipoAdempimentoDTO dto = new TipoAdempimentoDTO();
        dto.setIdTipoAdempimento(this.getIdTipoAdempimento());
        if (null != this.getAmbito()) {
            dto.setIdAmbito(this.getAmbito().getIdAmbito());
        }
        dto.setDesTipoAdempimento(this.getDesTipoAdempimento());
        dto.setCodTipoAdempimento(this.getCodTipoAdempimento());
        dto.setOrdinamentoTipoAdempimento(this.getOrdinamentoTipoAdempimento());
        dto.setFlgAttivo(this.getFlgAttivo());

        return dto;
    }

}
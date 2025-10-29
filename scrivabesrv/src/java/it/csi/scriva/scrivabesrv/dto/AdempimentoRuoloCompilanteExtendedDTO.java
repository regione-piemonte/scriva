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
 * The type Adempimento ruolo compilante extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempimentoRuoloCompilanteExtendedDTO extends AdempimentoRuoloCompilanteDTO implements Serializable {

    @JsonProperty("adempimento")
    private AdempimentoExtendedDTO adempimento;

    @JsonProperty("ruolo_compilante")
    private RuoloCompilanteDTO ruoloCompilante;

    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
    }

    public RuoloCompilanteDTO getRuoloCompilante() {
        return ruoloCompilante;
    }

    public void setRuoloCompilante(RuoloCompilanteDTO ruoloCompilante) {
        this.ruoloCompilante = ruoloCompilante;
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
        AdempimentoRuoloCompilanteExtendedDTO that = (AdempimentoRuoloCompilanteExtendedDTO) o;
        return Objects.equals(adempimento, that.adempimento) && Objects.equals(ruoloCompilante, that.ruoloCompilante);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), adempimento, ruoloCompilante);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "AdempimentoRuoloCompilanteExtendedDTO {\n" +
                super.toString() +
                "         adempimento:" + adempimento +
                ",\n         ruoloCompilante:" + ruoloCompilante +
                "}\n";
    }

    /**
     * @return AdempimentoRuoloCompilanteDTO
     */
    @JsonIgnore
    public AdempimentoRuoloCompilanteDTO getDTO() {
        AdempimentoRuoloCompilanteDTO dto = new AdempimentoRuoloCompilanteDTO();
        if (null != this.getRuoloCompilante()) {
            dto.setIdRuoloCompilante(this.getRuoloCompilante().getIdRuoloCompilante());
        }
        if (null != this.getAdempimento()) {
            dto.setIdAdempimento(this.getAdempimento().getIdTipoAdempimento());
        }
        dto.setFlgPopolaRichiedente(this.getFlgPopolaRichiedente());
        dto.setFlgModuloDelega(this.getFlgModuloDelega());
        dto.setFlgModuloProcura(this.getFlgModuloProcura());
        dto.setFlgRuoloDefault(this.getFlgRuoloDefault());
        return dto;
    }

}
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
 * The type Template extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateExtendedDTO extends TemplateDTO implements Serializable {

    @JsonProperty("adempimento")
    private AdempimentoExtendedDTO adempimento;

    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
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
        TemplateExtendedDTO that = (TemplateExtendedDTO) o;
        return Objects.equals(adempimento, that.adempimento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), adempimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TemplateExtendedDTO {\n" +
                super.toString() +
                "         adempimento:" + adempimento +
                "}\n";
    }

    /**
     * @return TemplateDTO
     */
    @JsonIgnore
    public TemplateDTO getDTO() {
        TemplateDTO dto = new TemplateDTO();
        dto.setIdTemplate(this.getIdTemplate());
        if (null != this.adempimento) {
            dto.setIdAdempimento(this.adempimento.getIdTipoAdempimento());
        }
        dto.setCodTemplate(this.getCodTemplate());
        dto.setDesTemplate(this.getDesTemplate());
        dto.setDataInizioValidita(this.getDataInizioValidita());
        dto.setDataCessazione(this.getDataCessazione());
        dto.setJsonConfguraTemplate(this.getJsonConfguraTemplate());
        return dto;
    }

}
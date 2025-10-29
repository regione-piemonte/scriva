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
 * The type Template quadro extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateQuadroExtendedDTO extends TemplateQuadroDTO implements Serializable {

    @JsonProperty("template")
    private TemplateExtendedDTO template;

    @JsonProperty("quadro")
    private QuadroExtendedDTO quadro;

    public TemplateExtendedDTO getTemplate() {
        return template;
    }

    public void setTemplate(TemplateExtendedDTO template) {
        this.template = template;
    }

    public QuadroExtendedDTO getQuadro() {
        return quadro;
    }

    public void setQuadro(QuadroExtendedDTO quadro) {
        this.quadro = quadro;
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
        TemplateQuadroExtendedDTO that = (TemplateQuadroExtendedDTO) o;
        return Objects.equals(template, that.template) && Objects.equals(quadro, that.quadro);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), template, quadro);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TemplateQuadroExtendedDTO {\n" +
                super.toString() +
                "         template:" + template +
                ",\n         quadro:" + quadro +
                "}\n";
    }

    /**
     * @return TemplateQuadroDTO
     */
    @JsonIgnore
    public TemplateQuadroDTO getDTO() {
        TemplateQuadroDTO dto = new TemplateQuadroDTO();
        dto.setIdTemplateQuadro(this.getIdTemplateQuadro());
        if (null != this.template) {
            dto.setIdTemplate(this.template.getIdTemplate());
        }
        if (null != this.quadro) {
            dto.setIdQuadro(this.quadro.getIdQuadro());
        }
        dto.setOrdinamentoTemplateQuadro(this.getOrdinamentoTemplateQuadro());
        dto.setIndVisibile(this.getIndVisibile());
        dto.setJsonVestizioneQuadro(this.getJsonVestizioneQuadro());
        return dto;
    }

}
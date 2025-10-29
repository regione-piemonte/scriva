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
 * The type Istanza template quadro extended dto.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaTemplateQuadroExtendedDTO extends IstanzaTemplateQuadroDTO implements Serializable {

    @JsonProperty("template_quadro")
    private TemplateQuadroExtendedDTO templateQuadro;

    /**
     * Gets template quadro.
     *
     * @return the template quadro
     */
    public TemplateQuadroExtendedDTO getTemplateQuadro() {
        return templateQuadro;
    }

    /**
     * Sets template quadro.
     *
     * @param templateQuadro the template quadro
     */
    public void setTemplateQuadro(TemplateQuadroExtendedDTO templateQuadro) {
        this.templateQuadro = templateQuadro;
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
        IstanzaTemplateQuadroExtendedDTO that = (IstanzaTemplateQuadroExtendedDTO) o;
        return Objects.equals(templateQuadro, that.templateQuadro);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), templateQuadro);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IstanzaTemplateQuadroExtendedDTO {\n" +
                super.toString() +
                "         templateQuadro:" + templateQuadro +
                "}\n";
    }

}
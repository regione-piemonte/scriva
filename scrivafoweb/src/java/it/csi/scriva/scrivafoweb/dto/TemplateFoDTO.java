/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TemplateExtendedDTO;

import java.util.List;
import java.util.Objects;

/**
 * The type Template fo dto.
 */
public class TemplateFoDTO extends TemplateExtendedDTO {

    /**
     * The Quadri.
     */
    @JsonProperty("quadri")
    List<QuadroFoDTO> quadri;

    public List<QuadroFoDTO> getQuadri() {
        return quadri;
    }

    public void setQuadri(List<QuadroFoDTO> quadri) {
        this.quadri = quadri;
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
        TemplateFoDTO that = (TemplateFoDTO) o;
        return Objects.equals(quadri, that.quadri);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), quadri);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TemplateFoDTO {\n" +
                super.toString() +
                "         quadri:" + quadri +
                "}\n";
    }
}
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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;

import java.util.Objects;

/**
 * The type Istanza template fo dto.
 */
public class IstanzaTemplateFoDTO {

    @JsonProperty("istanza")
    private IstanzaExtendedDTO istanza;

    @JsonProperty("template")
    private TemplateFoDTO template;

    /**
     * Gets istanza.
     *
     * @return the istanza
     */
    public IstanzaExtendedDTO getIstanza() {
        return istanza;
    }

    /**
     * Sets istanza.
     *
     * @param istanza the istanza
     */
    public void setIstanza(IstanzaExtendedDTO istanza) {
        this.istanza = istanza;
    }

    /**
     * Gets template.
     *
     * @return the template
     */
    public TemplateFoDTO getTemplate() {
        return template;
    }

    /**
     * Sets template.
     *
     * @param template the template
     */
    public void setTemplate(TemplateFoDTO template) {
        this.template = template;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IstanzaTemplateFoDTO that = (IstanzaTemplateFoDTO) o;
        return Objects.equals(istanza, that.istanza) && Objects.equals(template, that.template);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(istanza, template);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IstanzaTemplateFoDTO {\n" +
                "         istanza:" + istanza +
                ",\n         template:" + template +
                "}\n";
    }
}
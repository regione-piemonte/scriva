/*

========================LICENSE_START=================================
Copyright (C) 2025 Regione Piemonte
SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
SPDX-License-Identifier: EUPL-1.2
=========================LICENSE_END==================================
*/
package it.csi.scriva.scrivaapisrv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Istanza template fo dto.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaTemplateFoDTO implements Serializable {

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

    @Override
    public int hashCode() {
        return Objects.hash(istanza, template);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IstanzaTemplateFoDTO other = (IstanzaTemplateFoDTO) obj;
        return Objects.equals(istanza, other.istanza) && Objects.equals(template, other.template);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IstanzaTemplateFoDTO [istanza=").append(istanza).append("\n  template=").append(template).append("]");
        return builder.toString();
    }

}
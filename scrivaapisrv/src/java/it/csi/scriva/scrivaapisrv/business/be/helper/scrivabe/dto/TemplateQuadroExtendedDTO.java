/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class TemplateQuadroExtendedDTO extends BaseDTO implements Serializable {
    @JsonProperty("id_template_quadro")
    private Long idTemplateQuadro;

    @JsonProperty("template")
    private TemplateExtendedDTO template;

    @JsonProperty("quadro")
    private QuadroExtendedDTO quadro;

    @JsonProperty("ordinamento_template_quadro")
    private Integer ordinamentoTemplateQuadro;

    public Long getIdTemplateQuadro() {
        return idTemplateQuadro;
    }

    public void setIdTemplateQuadro(Long idTemplateQuadro) {
        this.idTemplateQuadro = idTemplateQuadro;
    }

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

    public Integer getOrdinamentoTemplateQuadro() {
        return ordinamentoTemplateQuadro;
    }

    public void setOrdinamentoTemplateQuadro(Integer ordinamentoTemplateQuadro) {
        this.ordinamentoTemplateQuadro = ordinamentoTemplateQuadro;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTemplateQuadro, ordinamentoTemplateQuadro, quadro, template);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        TemplateQuadroExtendedDTO other = (TemplateQuadroExtendedDTO) obj;
        return Objects.equals(idTemplateQuadro, other.idTemplateQuadro) && Objects.equals(ordinamentoTemplateQuadro, other.ordinamentoTemplateQuadro) && Objects.equals(quadro, other.quadro) && Objects.equals(template, other.template);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TemplateQuadroExtendedDTO {\n    idTemplateQuadro: ").append(idTemplateQuadro).append("\n    template: ").append(template).append("\n    quadro: ").append(quadro).append("\n    ordinamentoTemplateQuadro: ").append(ordinamentoTemplateQuadro).append("\n}");
        return builder.toString();
    }
}
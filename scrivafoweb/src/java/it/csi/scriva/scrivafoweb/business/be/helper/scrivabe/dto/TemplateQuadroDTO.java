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
 * The type Template quadro dto.
 *
 * @author CSI PIEMONTE
 */
public class TemplateQuadroDTO implements Serializable {

    @JsonProperty("id_template_quadro")
    private Long idTemplateQuadro;

    @JsonProperty("id_template")
    private Long idTemplate;

    @JsonProperty("id_quadro")
    private Long idQuadro;

    @JsonProperty("ordinamento_template_quadro")
    private Integer ordinamentoTemplateQuadro;

    @JsonProperty("ind_visibile")
    private String indVisibile;

    @JsonProperty("json_vestizione_quadro")
    private String jsonVestizioneQuadro;

    /**
     * Gets id template quadro.
     *
     * @return the id template quadro
     */
    public Long getIdTemplateQuadro() {
        return idTemplateQuadro;
    }

    /**
     * Sets id template quadro.
     *
     * @param idTemplateQuadro the id template quadro
     */
    public void setIdTemplateQuadro(Long idTemplateQuadro) {
        this.idTemplateQuadro = idTemplateQuadro;
    }

    /**
     * Gets id template.
     *
     * @return the id template
     */
    public Long getIdTemplate() {
        return idTemplate;
    }

    /**
     * Sets id template.
     *
     * @param idTemplate the id template
     */
    public void setIdTemplate(Long idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * Gets id quadro.
     *
     * @return the id quadro
     */
    public Long getIdQuadro() {
        return idQuadro;
    }

    /**
     * Sets id quadro.
     *
     * @param idQuadro the id quadro
     */
    public void setIdQuadro(Long idQuadro) {
        this.idQuadro = idQuadro;
    }

    /**
     * Gets ordinamento template quadro.
     *
     * @return the ordinamento template quadro
     */
    public Integer getOrdinamentoTemplateQuadro() {
        return ordinamentoTemplateQuadro;
    }

    /**
     * Sets ordinamento template quadro.
     *
     * @param ordinamentoTemplateQuadro the ordinamento template quadro
     */
    public void setOrdinamentoTemplateQuadro(Integer ordinamentoTemplateQuadro) {
        this.ordinamentoTemplateQuadro = ordinamentoTemplateQuadro;
    }

    /**
     * Gets ind visibile.
     *
     * @return the ind visibile
     */
    public String getIndVisibile() {
        return indVisibile;
    }

    /**
     * Sets ind visibile.
     *
     * @param indVisibile the ind visibile
     */
    public void setIndVisibile(String indVisibile) {
        this.indVisibile = indVisibile;
    }

    /**
     * Gets json vestizione quadro.
     *
     * @return the json vestizione quadro
     */
    public String getJsonVestizioneQuadro() {
        return jsonVestizioneQuadro;
    }

    /**
     * Sets json vestizione quadro.
     *
     * @param jsonVestizioneQuadro the json vestizione quadro
     */
    public void setJsonVestizioneQuadro(String jsonVestizioneQuadro) {
        this.jsonVestizioneQuadro = jsonVestizioneQuadro;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateQuadroDTO that = (TemplateQuadroDTO) o;
        return Objects.equals(idTemplateQuadro, that.idTemplateQuadro) && Objects.equals(idTemplate, that.idTemplate) && Objects.equals(idQuadro, that.idQuadro) && Objects.equals(ordinamentoTemplateQuadro, that.ordinamentoTemplateQuadro) && Objects.equals(indVisibile, that.indVisibile) && Objects.equals(jsonVestizioneQuadro, that.jsonVestizioneQuadro);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTemplateQuadro, idTemplate, idQuadro, ordinamentoTemplateQuadro, indVisibile, jsonVestizioneQuadro);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TemplateQuadroDTO {\n" +
                "         idTemplateQuadro:" + idTemplateQuadro +
                ",\n         idTemplate:" + idTemplate +
                ",\n         idQuadro:" + idQuadro +
                ",\n         ordinamentoTemplateQuadro:" + ordinamentoTemplateQuadro +
                ",\n         indVisibile:'" + indVisibile + "'" +
                ",\n         jsonVestizioneQuadro:'" + jsonVestizioneQuadro + "'" +
                "}\n";
    }
}
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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.QuadroExtendedDTO;

import java.util.Objects;

/**
 * The type Quadro fo dto.
 */
public class QuadroFoDTO extends QuadroExtendedDTO {

    @JsonProperty("id_template_quadro")
    private Long idTemplateQuadro;

    @JsonProperty("ordinamento_template_quadro")
    private Integer ordinamentoTemplateQuadro;

    @JsonProperty("json_data_quadro")
    private String jsonDataQuadro;

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
     * Gets json data quadro.
     *
     * @return the json data quadro
     */
    public String getJsonDataQuadro() {
        return jsonDataQuadro;
    }

    /**
     * Sets json data quadro.
     *
     * @param jsonDataQuadro the json data quadro
     */
    public void setJsonDataQuadro(String jsonDataQuadro) {
        this.jsonDataQuadro = jsonDataQuadro;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        QuadroFoDTO that = (QuadroFoDTO) o;
        return Objects.equals(idTemplateQuadro, that.idTemplateQuadro) && Objects.equals(ordinamentoTemplateQuadro, that.ordinamentoTemplateQuadro) && Objects.equals(jsonDataQuadro, that.jsonDataQuadro) && Objects.equals(jsonVestizioneQuadro, that.jsonVestizioneQuadro);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idTemplateQuadro, ordinamentoTemplateQuadro, jsonDataQuadro, jsonVestizioneQuadro);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "QuadroFoDTO {\n" +
                "         idTemplateQuadro:" + idTemplateQuadro +
                ",\n         ordinamentoTemplateQuadro:" + ordinamentoTemplateQuadro +
                ",\n         jsonDataQuadro:'" + jsonDataQuadro + "'" +
                ",\n         jsonVestizioneQuadro:'" + jsonVestizioneQuadro + "'" +
                "}\n";
    }
}
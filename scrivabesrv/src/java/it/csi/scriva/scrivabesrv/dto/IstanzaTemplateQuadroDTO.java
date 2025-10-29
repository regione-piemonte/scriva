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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Istanza template quadro dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IstanzaTemplateQuadroDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_template_quadro")
    private Long idTemplateQuadro;

    @JsonProperty("json_data_quadro")
    private String jsonDataQuadro;

    @JsonProperty("cod_tipo_quadro")
    private String codTipoQuadro;

    /**
     * Gets id istanza.
     *
     * @return the id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza the id istanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

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
     * Gets cod tipo quadro.
     *
     * @return the cod tipo quadro
     */
    public String getCodTipoQuadro() {
        return codTipoQuadro;
    }

    /**
     * Sets cod tipo quadro.
     *
     * @param codTipoQuadro the cod tipo quadro
     */
    public void setCodTipoQuadro(String codTipoQuadro) {
        this.codTipoQuadro = codTipoQuadro;
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
        IstanzaTemplateQuadroDTO that = (IstanzaTemplateQuadroDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idTemplateQuadro, that.idTemplateQuadro) && Objects.equals(jsonDataQuadro, that.jsonDataQuadro) && Objects.equals(codTipoQuadro, that.codTipoQuadro);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIstanza, idTemplateQuadro, jsonDataQuadro, codTipoQuadro);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IstanzaTemplateQuadroDTO {\n" +
                "         idIstanza:" + idIstanza +
                ",\n         idTemplateQuadro:" + idTemplateQuadro +
                ",\n         jsonDataQuadro:'" + jsonDataQuadro + "'" +
                ",\n         codTipoQuadro:'" + codTipoQuadro + "'" +
                "}\n";
    }
}
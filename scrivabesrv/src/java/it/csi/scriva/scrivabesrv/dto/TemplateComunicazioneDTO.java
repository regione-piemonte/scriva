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
 * The type Template comunicazione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateComunicazioneDTO implements Serializable {

    @JsonProperty("id_template")
    private Long idTemplate;

    @JsonProperty("cod_template")
    private String codTemplate;

    @JsonProperty("des_template")
    private String desTemplate;

    @JsonProperty("des_oggetto")
    private String desOggetto;

    @JsonProperty("des_corpo")
    private String desCorpo;

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
     * Gets cod template.
     *
     * @return the cod template
     */
    public String getCodTemplate() {
        return codTemplate;
    }

    /**
     * Sets cod template.
     *
     * @param codTemplate the cod template
     */
    public void setCodTemplate(String codTemplate) {
        this.codTemplate = codTemplate;
    }

    /**
     * Gets des template.
     *
     * @return the des template
     */
    public String getDesTemplate() {
        return desTemplate;
    }

    /**
     * Sets des template.
     *
     * @param desTemplate the des template
     */
    public void setDesTemplate(String desTemplate) {
        this.desTemplate = desTemplate;
    }

    /**
     * Gets des oggetto.
     *
     * @return the des oggetto
     */
    public String getDesOggetto() {
        return desOggetto;
    }

    /**
     * Sets des oggetto.
     *
     * @param desOggetto the des oggetto
     */
    public void setDesOggetto(String desOggetto) {
        this.desOggetto = desOggetto;
    }

    /**
     * Gets des corpo.
     *
     * @return the des corpo
     */
    public String getDesCorpo() {
        return desCorpo;
    }

    /**
     * Sets des corpo.
     *
     * @param desCorpo the des corpo
     */
    public void setDesCorpo(String desCorpo) {
        this.desCorpo = desCorpo;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TemplateComunicazioneDTO that = (TemplateComunicazioneDTO) o;
        return Objects.equals(idTemplate, that.idTemplate) && Objects.equals(codTemplate, that.codTemplate) && Objects.equals(desTemplate, that.desTemplate) && Objects.equals(desOggetto, that.desOggetto) && Objects.equals(desCorpo, that.desCorpo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTemplate, codTemplate, desTemplate, desOggetto, desCorpo);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TemplateComunicazioneDTO {");
        sb.append("         idTemplate:").append(idTemplate);
        sb.append(",         codTemplate:'").append(codTemplate).append("'");
        sb.append(",         desTemplate:'").append(desTemplate).append("'");
        sb.append(",         desOggetto:'").append(desOggetto).append("'");
        sb.append(",         desCorpo:'").append(desCorpo).append("'");
        sb.append("}");
        return sb.toString();
    }
}
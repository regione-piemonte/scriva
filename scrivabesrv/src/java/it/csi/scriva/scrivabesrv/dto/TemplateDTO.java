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
import java.util.Date;
import java.util.Objects;

/**
 * The type Template dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateDTO implements Serializable {

    @JsonProperty("id_template")
    private Long idTemplate;

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("cod_template")
    private String codTemplate;

    @JsonProperty("des_template")
    private String desTemplate;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_cessazione")
    private Date dataCessazione;

    @JsonIgnore
    private Boolean flgAttivo;

    @JsonProperty("json_configura_template")
    private String jsonConfguraTemplate;

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
     * Gets id adempimento.
     *
     * @return the id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento the id adempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
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
     * Gets data inizio validita.
     *
     * @return the data inizio validita
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets data inizio validita.
     *
     * @param dataInizioValidita the data inizio validita
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * Gets data cessazione.
     *
     * @return the data cessazione
     */
    public Date getDataCessazione() {
        return dataCessazione;
    }

    /**
     * Sets data cessazione.
     *
     * @param dataCessazione the data cessazione
     */
    public void setDataCessazione(Date dataCessazione) {
        this.dataCessazione = dataCessazione;
    }

    /**
     * Gets flg attivo.
     *
     * @return the flg attivo
     */
    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    /**
     * Sets flg attivo.
     *
     * @param flgAttivo the flg attivo
     */
    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    /**
     * Gets json confgura template.
     *
     * @return the json confgura template
     */
    public String getJsonConfguraTemplate() {
        return jsonConfguraTemplate;
    }

    /**
     * Sets json confgura template.
     *
     * @param jsonConfguraTemplate the json confgura template
     */
    public void setJsonConfguraTemplate(String jsonConfguraTemplate) {
        this.jsonConfguraTemplate = jsonConfguraTemplate;
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
        TemplateDTO that = (TemplateDTO) o;
        return Objects.equals(idTemplate, that.idTemplate) && Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(codTemplate, that.codTemplate) && Objects.equals(desTemplate, that.desTemplate) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataCessazione, that.dataCessazione) && Objects.equals(flgAttivo, that.flgAttivo) && Objects.equals(jsonConfguraTemplate, that.jsonConfguraTemplate);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTemplate, idAdempimento, codTemplate, desTemplate, dataInizioValidita, dataCessazione, flgAttivo, jsonConfguraTemplate);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TemplateDTO {\n" +
                "         idTemplate:" + idTemplate +
                ",\n         idAdempimento:" + idAdempimento +
                ",\n         codTemplate:'" + codTemplate + "'" +
                ",\n         desTemplate:'" + desTemplate + "'" +
                ",\n         dataInizioValidita:" + dataInizioValidita +
                ",\n         dataCessazione:" + dataCessazione +
                ",\n         flgAttivo:" + flgAttivo +
                ",\n         jsonConfguraTemplate:'" + jsonConfguraTemplate + "'" +
                "}\n";
    }

}
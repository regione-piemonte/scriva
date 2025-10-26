/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TemplateExtendedDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_template")
    private Long idTemplate;

    @JsonProperty("adempimento")
    private AdempimentoExtendedDTO adempimento;

    @JsonProperty("cod_template")
    private String codTemplate;

    @JsonProperty("des_template")
    private String desTemplate;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_cessazione")
    private Date dataCessazione;

    @JsonProperty("pdf_template")
    private String pdfTemplate;

    @JsonIgnore
    private Boolean flgAttivo;

    @JsonProperty("json_configura_template")
    private String jsonConfguraTemplate;

    public Long getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Long idTemplate) {
        this.idTemplate = idTemplate;
    }

    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
    }

    public String getCodTemplate() {
        return codTemplate;
    }

    public void setCodTemplate(String codTemplate) {
        this.codTemplate = codTemplate;
    }

    public String getDesTemplate() {
        return desTemplate;
    }

    public void setDesTemplate(String desTemplate) {
        this.desTemplate = desTemplate;
    }

    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    public Date getDataCessazione() {
        return dataCessazione;
    }

    public void setDataCessazione(Date dataCessazione) {
        this.dataCessazione = dataCessazione;
    }

    public String getPdfTemplate() {
        return pdfTemplate;
    }

    public void setPdfTemplate(String pdfTemplate) {
        this.pdfTemplate = pdfTemplate;
    }

    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    public String getJsonConfguraTemplate() {
        return jsonConfguraTemplate;
    }

    public void setJsonConfguraTemplate(String jsonConfguraTemplate) {
        this.jsonConfguraTemplate = jsonConfguraTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        TemplateExtendedDTO that = (TemplateExtendedDTO) o;
        return Objects.equals(idTemplate, that.idTemplate) && Objects.equals(adempimento, that.adempimento) && Objects.equals(codTemplate, that.codTemplate) && Objects.equals(desTemplate, that.desTemplate) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataCessazione, that.dataCessazione) && Objects.equals(pdfTemplate, that.pdfTemplate) && Objects.equals(flgAttivo, that.flgAttivo) && Objects.equals(jsonConfguraTemplate, that.jsonConfguraTemplate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idTemplate, adempimento, codTemplate, desTemplate, dataInizioValidita, dataCessazione, pdfTemplate, flgAttivo, jsonConfguraTemplate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TemplateExtendedDTO {");
        sb.append(super.toString());
        sb.append("         idTemplate:").append(idTemplate);
        sb.append(",         adempimento:").append(adempimento);
        sb.append(",         codTemplate:'").append(codTemplate).append("'");
        sb.append(",         desTemplate:'").append(desTemplate).append("'");
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataCessazione:").append(dataCessazione);
        sb.append(",         pdfTemplate:'").append(pdfTemplate).append("'");
        sb.append(",         flgAttivo:").append(flgAttivo);
        sb.append(",         jsonConfguraTemplate:'").append(jsonConfguraTemplate).append("'");
        sb.append("}");
        return sb.toString();
    }
}
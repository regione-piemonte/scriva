/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoExtendedDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The type Template fo dto.
 *
 * @author CSI PIEMONTE
 */
public class TemplateFoDTO implements Serializable {

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

    @JsonIgnore
    private Boolean flgAttivo;

    @JsonProperty("json_configura_template")
    private String jsonConfguraTemplate;

    /**
     * The Quadri.
     */
    @JsonProperty("quadri")
    List<QuadroFoDTO> quadri;

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
     * Gets adempimento.
     *
     * @return the adempimento
     */
    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    /**
     * Sets adempimento.
     *
     * @param adempimento the adempimento
     */
    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
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
     * Gets quadri.
     *
     * @return the quadri
     */
    public List<QuadroFoDTO> getQuadri() {
        return quadri;
    }

    /**
     * Sets quadri.
     *
     * @param quadri the quadri
     */
    public void setQuadri(List<QuadroFoDTO> quadri) {
        this.quadri = quadri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TemplateFoDTO that = (TemplateFoDTO) o;
        return Objects.equals(idTemplate, that.idTemplate) && Objects.equals(adempimento, that.adempimento) && Objects.equals(codTemplate, that.codTemplate) && Objects.equals(desTemplate, that.desTemplate) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataCessazione, that.dataCessazione) && Objects.equals(flgAttivo, that.flgAttivo) && Objects.equals(jsonConfguraTemplate, that.jsonConfguraTemplate) && Objects.equals(quadri, that.quadri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTemplate, adempimento, codTemplate, desTemplate, dataInizioValidita, dataCessazione, flgAttivo, jsonConfguraTemplate, quadri);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TemplateFoDTO {");
        sb.append("         idTemplate:").append(idTemplate);
        sb.append(",         adempimento:").append(adempimento);
        sb.append(",         codTemplate:'").append(codTemplate).append("'");
        sb.append(",         desTemplate:'").append(desTemplate).append("'");
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataCessazione:").append(dataCessazione);
        sb.append(",         flgAttivo:").append(flgAttivo);
        sb.append(",         jsonConfguraTemplate:'").append(jsonConfguraTemplate).append("'");
        sb.append(",         quadri:").append(quadri);
        sb.append("}");
        return sb.toString();
    }
}
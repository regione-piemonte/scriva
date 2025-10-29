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

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Search dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchDTO implements Serializable {

    @JsonProperty("componente_app")
    private String componenteApp;

    @JsonProperty("cf_compilante")
    private String cfCompilante;

    @JsonProperty("cf_soggetto")
    private String cfSoggetto;

    @JsonProperty("stato_istanza")
    private String statoIstanza;

    @JsonProperty("simple_search")
    private String simpleSearchMultipleFields;

    @JsonProperty("tipo_adempimento")
    private String tipoAdempimento;

    @JsonProperty("adempimento")
    private String adempimento;

    @JsonProperty("data_inserimento_from")
    private Date dataInserimentoFrom;

    @JsonProperty("data_inserimento_to")
    private Date dataInserimentTo;

    @JsonProperty("data_presentazione_from")
    private Date dataPresentazioneFrom;

    @JsonProperty("data_presentazione_to")
    private Date dataPresentazionTo;

    @JsonProperty("provincia_oggetto_istanza")
    private String provinciaOggettoIstanza;

    @JsonProperty("comune_oggetto_istanza")
    private String comuneOggettoIstanza;

    @JsonProperty("id_tipo_evento")
    private Long idTipoEvento;

    @JsonProperty("data_evento_from")
    private Date dataEventoFrom;

    @JsonProperty("data_evento_to")
    private Date dataEventoTo;

    @JsonProperty("des_stato_sintesi_pagamento")
    private String desStatoSintesiPagamento;

    @JsonProperty("tipo_pratiche")
    private String tipoPratiche;
    
    @JsonProperty("label_stato")
    private String labelStato;
    

    /**
     * Gets componente app.
     *
     * @return the componente app
     */
    public String getComponenteApp() {
        return componenteApp;
    }

    /**
     * Sets componente app.
     *
     * @param componenteApp the componente app
     */
    public void setComponenteApp(String componenteApp) {
        this.componenteApp = componenteApp;
    }

    /**
     * Gets cf compilante.
     *
     * @return the cf compilante
     */
    public String getCfCompilante() {
        return cfCompilante;
    }

    /**
     * Sets cf compilante.
     *
     * @param cfCompilante the cf compilante
     */
    public void setCfCompilante(String cfCompilante) {
        this.cfCompilante = cfCompilante;
    }

    /**
     * Gets cf soggetto.
     *
     * @return the cf soggetto
     */
    public String getCfSoggetto() {
        return cfSoggetto;
    }

    /**
     * Sets cf soggetto.
     *
     * @param cfSoggetto the cf soggetto
     */
    public void setCfSoggetto(String cfSoggetto) {
        this.cfSoggetto = cfSoggetto;
    }

    /**
     * Gets stato istanza.
     *
     * @return the stato istanza
     */
    public String getStatoIstanza() {
        return statoIstanza;
    }

    /**
     * Sets stato istanza.
     *
     * @param statoIstanza the stato istanza
     */
    public void setStatoIstanza(String statoIstanza) {
        this.statoIstanza = statoIstanza;
    }

    /**
     * Gets simple search multiple fields.
     *
     * @return the simple search multiple fields
     */
    public String getSimpleSearchMultipleFields() {
        return simpleSearchMultipleFields;
    }

    /**
     * Sets simple search multiple fields.
     *
     * @param simpleSearchMultipleFields the simple search multiple fields
     */
    public void setSimpleSearchMultipleFields(String simpleSearchMultipleFields) {
        this.simpleSearchMultipleFields = simpleSearchMultipleFields;
    }

    /**
     * Gets tipo adempimento.
     *
     * @return the tipo adempimento
     */
    public String getTipoAdempimento() {
        return tipoAdempimento;
    }

    /**
     * Sets tipo adempimento.
     *
     * @param tipoAdempimento the tipo adempimento
     */
    public void setTipoAdempimento(String tipoAdempimento) {
        this.tipoAdempimento = tipoAdempimento;
    }

    /**
     * Gets adempimento.
     *
     * @return the adempimento
     */
    public String getAdempimento() {
        return adempimento;
    }

    /**
     * Sets adempimento.
     *
     * @param adempimento the adempimento
     */
    public void setAdempimento(String adempimento) {
        this.adempimento = adempimento;
    }

    /**
     * Gets data inserimento from.
     *
     * @return the data inserimento from
     */
    public Date getDataInserimentoFrom() {
        return dataInserimentoFrom;
    }

    /**
     * Sets data inserimento from.
     *
     * @param dataInserimentoFrom the data inserimento from
     */
    public void setDataInserimentoFrom(Date dataInserimentoFrom) {
        this.dataInserimentoFrom = dataInserimentoFrom;
    }

    /**
     * Gets data inseriment to.
     *
     * @return the data inseriment to
     */
    public Date getDataInserimentTo() {
        return dataInserimentTo;
    }

    /**
     * Sets data inseriment to.
     *
     * @param dataInserimentTo the data inseriment to
     */
    public void setDataInserimentTo(Date dataInserimentTo) {
        this.dataInserimentTo = dataInserimentTo;
    }

    /**
     * Gets data presentazione from.
     *
     * @return the data presentazione from
     */
    public Date getDataPresentazioneFrom() {
        return dataPresentazioneFrom;
    }

    /**
     * Sets data presentazione from.
     *
     * @param dataPresentazioneFrom the data presentazione from
     */
    public void setDataPresentazioneFrom(Date dataPresentazioneFrom) {
        this.dataPresentazioneFrom = dataPresentazioneFrom;
    }

    /**
     * Gets data presentazion to.
     *
     * @return the data presentazion to
     */
    public Date getDataPresentazionTo() {
        return dataPresentazionTo;
    }

    /**
     * Sets data presentazion to.
     *
     * @param dataPresentazionTo the data presentazion to
     */
    public void setDataPresentazionTo(Date dataPresentazionTo) {
        this.dataPresentazionTo = dataPresentazionTo;
    }

    /**
     * Gets provincia oggetto istanza.
     *
     * @return the provincia oggetto istanza
     */
    public String getProvinciaOggettoIstanza() {
        return provinciaOggettoIstanza;
    }

    /**
     * Sets provincia oggetto istanza.
     *
     * @param provinciaOggettoIstanza the provincia oggetto istanza
     */
    public void setProvinciaOggettoIstanza(String provinciaOggettoIstanza) {
        this.provinciaOggettoIstanza = provinciaOggettoIstanza;
    }

    /**
     * Gets comune oggetto istanza.
     *
     * @return the comune oggetto istanza
     */
    public String getComuneOggettoIstanza() {
        return comuneOggettoIstanza;
    }

    /**
     * Sets comune oggetto istanza.
     *
     * @param comuneOggettoIstanza the comune oggetto istanza
     */
    public void setComuneOggettoIstanza(String comuneOggettoIstanza) {
        this.comuneOggettoIstanza = comuneOggettoIstanza;
    }

    /**
     * Gets id tipo evento.
     *
     * @return the id tipo evento
     */
    public Long getIdTipoEvento() {
        return idTipoEvento;
    }

    /**
     * Sets id tipo evento.
     *
     * @param idTipoEvento the id tipo evento
     */
    public void setIdTipoEvento(Long idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    /**
     * Gets data evento from.
     *
     * @return the data evento from
     */
    public Date getDataEventoFrom() {
        return dataEventoFrom;
    }

    /**
     * Sets data evento from.
     *
     * @param dataEventoFrom the data evento from
     */
    public void setDataEventoFrom(Date dataEventoFrom) {
        this.dataEventoFrom = dataEventoFrom;
    }

    /**
     * Gets data evento to.
     *
     * @return the data evento to
     */
    public Date getDataEventoTo() {
        return dataEventoTo;
    }

    /**
     * Sets data evento to.
     *
     * @param dataEventoTo the data evento to
     */
    public void setDataEventoTo(Date dataEventoTo) {
        this.dataEventoTo = dataEventoTo;
    }

    /**
     * Gets des stato sintesi pagamento.
     *
     * @return the des stato sintesi pagamento
     */
    public String getDesStatoSintesiPagamento() {
        return desStatoSintesiPagamento;
    }

    /**
     * Sets des stato sintesi pagamento.
     *
     * @param desStatoSintesiPagamento the des stato sintesi pagamento
     */
    public void setDesStatoSintesiPagamento(String desStatoSintesiPagamento) {
        this.desStatoSintesiPagamento = desStatoSintesiPagamento;
    }

    /**
     * Gets tipo pratiche.
     *
     * @return the tipo pratiche
     */
    public String getTipoPratiche() {
        return tipoPratiche;
    }

    /**
     * Sets tipo pratiche.
     *
     * @param tipoPratiche the tipo pratiche
     */
    public void setTipoPratiche(String tipoPratiche) {
        this.tipoPratiche = tipoPratiche;
    }

    /**
	 * @return the labelStato
	 */
	public String getLabelStato() {
		return labelStato;
	}

	/**
	 * @param labelStato the labelStato to set
	 */
	public void setLabelStato(String labelStato) {
		this.labelStato = labelStato;
	}
	
	
    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchDTO searchDTO = (SearchDTO) o;
        return Objects.equals(componenteApp, searchDTO.componenteApp) && Objects.equals(cfCompilante, searchDTO.cfCompilante) && Objects.equals(cfSoggetto, searchDTO.cfSoggetto) && Objects.equals(statoIstanza, searchDTO.statoIstanza) && Objects.equals(simpleSearchMultipleFields, searchDTO.simpleSearchMultipleFields) && Objects.equals(tipoAdempimento, searchDTO.tipoAdempimento) && Objects.equals(adempimento, searchDTO.adempimento) && Objects.equals(dataInserimentoFrom, searchDTO.dataInserimentoFrom) && Objects.equals(dataInserimentTo, searchDTO.dataInserimentTo) && Objects.equals(dataPresentazioneFrom, searchDTO.dataPresentazioneFrom) && Objects.equals(dataPresentazionTo, searchDTO.dataPresentazionTo) && Objects.equals(provinciaOggettoIstanza, searchDTO.provinciaOggettoIstanza) && Objects.equals(comuneOggettoIstanza, searchDTO.comuneOggettoIstanza) && Objects.equals(idTipoEvento, searchDTO.idTipoEvento) && Objects.equals(dataEventoFrom, searchDTO.dataEventoFrom) && Objects.equals(dataEventoTo, searchDTO.dataEventoTo) && Objects.equals(desStatoSintesiPagamento, searchDTO.desStatoSintesiPagamento) && Objects.equals(tipoPratiche, searchDTO.tipoPratiche) && Objects.equals(labelStato, searchDTO.labelStato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(componenteApp, cfCompilante, cfSoggetto, statoIstanza, simpleSearchMultipleFields, tipoAdempimento, adempimento, dataInserimentoFrom, dataInserimentTo, dataPresentazioneFrom, dataPresentazionTo, provinciaOggettoIstanza, comuneOggettoIstanza, idTipoEvento, dataEventoFrom, dataEventoTo, desStatoSintesiPagamento, tipoPratiche, labelStato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchDTO {\n");
        sb.append("         componenteApp:'").append(componenteApp).append("'");
        sb.append(",\n         cfCompilante:'").append(cfCompilante).append("'");
        sb.append(",\n         cfSoggetto:'").append(cfSoggetto).append("'");
        sb.append(",\n         statoIstanza:'").append(statoIstanza).append("'");
        sb.append(",\n         simpleSearchMultipleFields:'").append(simpleSearchMultipleFields).append("'");
        sb.append(",\n         tipoAdempimento:'").append(tipoAdempimento).append("'");
        sb.append(",\n         adempimento:'").append(adempimento).append("'");
        sb.append(",\n         dataInserimentoFrom:").append(dataInserimentoFrom);
        sb.append(",\n         dataInserimentTo:").append(dataInserimentTo);
        sb.append(",\n         dataPresentazioneFrom:").append(dataPresentazioneFrom);
        sb.append(",\n         dataPresentazionTo:").append(dataPresentazionTo);
        sb.append(",\n         provinciaOggettoIstanza:'").append(provinciaOggettoIstanza).append("'");
        sb.append(",\n         comuneOggettoIstanza:'").append(comuneOggettoIstanza).append("'");
        sb.append(",\n         idTipoEvento:").append(idTipoEvento);
        sb.append(",\n         dataEventoFrom:").append(dataEventoFrom);
        sb.append(",\n         dataEventoTo:").append(dataEventoTo);
        sb.append(",\n         desStatoSintesiPagamento:'").append(desStatoSintesiPagamento).append("'");
        sb.append(",\n         tipoPratiche:'").append(tipoPratiche).append("'");
        sb.append(",\n         labelStato:'").append(labelStato).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}
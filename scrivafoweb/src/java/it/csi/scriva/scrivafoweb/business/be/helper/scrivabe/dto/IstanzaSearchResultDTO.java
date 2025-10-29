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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * The type Istanza search result dto.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaSearchResultDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("gestuid_istanza")
    private String gestUIDIstanza;

    @JsonProperty("cod_istanza")
    private String codIstanza;

    @JsonProperty("cod_ambito")
    private String codAdmbito;

    @JsonProperty("cod_adempimento")
    private String codAdempimento;

    @JsonProperty("des_adempimento")
    private String desAdempimento;

    @JsonProperty("den_soggetto")
    private String denSoggetto;

    @JsonProperty("den_oggetto")
    private String denOggetto;

    @JsonProperty("comune")
    private String comune;

    @JsonProperty("des_stato_istanza")
    private String desStatoIstanza;

    @JsonProperty("cod_stato_istanza")
    private String codiceStatoIstanza;

    @JsonProperty("des_estesa_stato_istanza")
    private String desEstesaStatoIstanza;

    @JsonProperty("label_stato")
    private String labelStato;

    @JsonProperty("data_inserimento_istanza")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataInserimentoIstanza;

    @JsonProperty("data_modifica_istanza")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataModificaIstanza;

    @JsonProperty("data_inserimento_pratica")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataInserimentoPratica;

    @JsonProperty("data_modifica_pratica")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataModificaPratica;

    @JsonProperty("cod_pratica")
    private String codPratica;

    @JsonProperty("attore_gestione_istanza")
    private String attoreGestioneIstanza;

    @JsonProperty("attore_modifica_fo")
    private String attoreModificaFO;

    @JsonProperty("attore_modifica_bo")
    private String attoreModificaBO;

    @JsonProperty("des_stato_sintesi_pagamento")
    private String desStatoSintesiPagamento;

    @JsonProperty("tipi_adempimento_ogg_app")
    private List<TipoAdempimentoOggettoAppExtendedDTO> tipiAdempimentoOggettoApp;

    @JsonProperty("profili_app")
    private List<ProfiloOggettoAppExtendedDTO> profiloOggettoAppList;

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
     * Gets gest uid istanza.
     *
     * @return the gest uid istanza
     */
    public String getGestUIDIstanza() {
        return gestUIDIstanza;
    }

    /**
     * Sets gest uid istanza.
     *
     * @param gestUIDIstanza the gest uid istanza
     */
    public void setGestUIDIstanza(String gestUIDIstanza) {
        this.gestUIDIstanza = gestUIDIstanza;
    }

    /**
     * Gets cod istanza.
     *
     * @return the cod istanza
     */
    public String getCodIstanza() {
        return codIstanza;
    }

    /**
     * Sets cod istanza.
     *
     * @param codIstanza the cod istanza
     */
    public void setCodIstanza(String codIstanza) {
        this.codIstanza = codIstanza;
    }

    /**
     * Gets cod admbito.
     *
     * @return the cod admbito
     */
    public String getCodAdmbito() {
        return codAdmbito;
    }

    /**
     * Sets cod admbito.
     *
     * @param codAdmbito the cod admbito
     */
    public void setCodAdmbito(String codAdmbito) {
        this.codAdmbito = codAdmbito;
    }

    /**
     * Gets cod adempimento.
     *
     * @return the cod adempimento
     */
    public String getCodAdempimento() {
        return codAdempimento;
    }

    /**
     * Sets cod adempimento.
     *
     * @param codAdempimento the cod adempimento
     */
    public void setCodAdempimento(String codAdempimento) {
        this.codAdempimento = codAdempimento;
    }

    /**
     * Gets des adempimento.
     *
     * @return the des adempimento
     */
    public String getDesAdempimento() {
        return desAdempimento;
    }

    /**
     * Sets des adempimento.
     *
     * @param desAdempimento the des adempimento
     */
    public void setDesAdempimento(String desAdempimento) {
        this.desAdempimento = desAdempimento;
    }

    /**
     * Gets den soggetto.
     *
     * @return the den soggetto
     */
    public String getDenSoggetto() {
        return denSoggetto;
    }

    /**
     * Sets den soggetto.
     *
     * @param denSoggetto the den soggetto
     */
    public void setDenSoggetto(String denSoggetto) {
        this.denSoggetto = denSoggetto;
    }

    /**
     * Gets den oggetto.
     *
     * @return the den oggetto
     */
    public String getDenOggetto() {
        return denOggetto;
    }

    /**
     * Sets den oggetto.
     *
     * @param denOggetto the den oggetto
     */
    public void setDenOggetto(String denOggetto) {
        this.denOggetto = denOggetto;
    }

    /**
     * Gets comune.
     *
     * @return the comune
     */
    public String getComune() {
        return comune;
    }

    /**
     * Sets comune.
     *
     * @param comune the comune
     */
    public void setComune(String comune) {
        this.comune = comune;
    }

    /**
     * Gets des stato istanza.
     *
     * @return the des stato istanza
     */
    public String getDesStatoIstanza() {
        return desStatoIstanza;
    }

    /**
     * Sets des stato istanza.
     *
     * @param desStatoIstanza the des stato istanza
     */
    public void setDesStatoIstanza(String desStatoIstanza) {
        this.desStatoIstanza = desStatoIstanza;
    }

    /**
     * Gets codice stato istanza.
     *
     * @return the codice stato istanza
     */
    public String getCodiceStatoIstanza() {
        return codiceStatoIstanza;
    }

    /**
     * Sets codice stato istanza.
     *
     * @param codiceStatoIstanza the codice stato istanza
     */
    public void setCodiceStatoIstanza(String codiceStatoIstanza) {
        this.codiceStatoIstanza = codiceStatoIstanza;
    }

    /**
     * Gets des estesa stato istanza.
     *
     * @return the des estesa stato istanza
     */
    public String getDesEstesaStatoIstanza() {
        return desEstesaStatoIstanza;
    }

    /**
     * Sets des estesa stato istanza.
     *
     * @param desEstesaStatoIstanza the des estesa stato istanza
     */
    public void setDesEstesaStatoIstanza(String desEstesaStatoIstanza) {
        this.desEstesaStatoIstanza = desEstesaStatoIstanza;
    }

    /**
     * Gets label stato.
     *
     * @return the label stato
     */
    public String getLabelStato() {
        return labelStato;
    }

    /**
     * Sets label stato.
     *
     * @param labelStato the label stato
     */
    public void setLabelStato(String labelStato) {
        this.labelStato = labelStato;
    }

    /**
     * Gets data inserimento istanza.
     *
     * @return the data inserimento istanza
     */
    public Timestamp getDataInserimentoIstanza() {
        return dataInserimentoIstanza;
    }

    /**
     * Sets data inserimento istanza.
     *
     * @param dataInserimentoIstanza the data inserimento istanza
     */
    public void setDataInserimentoIstanza(Timestamp dataInserimentoIstanza) {
        this.dataInserimentoIstanza = dataInserimentoIstanza;
    }

    /**
     * Gets data modifica istanza.
     *
     * @return the data modifica istanza
     */
    public Timestamp getDataModificaIstanza() {
        return dataModificaIstanza;
    }

    /**
     * Sets data modifica istanza.
     *
     * @param dataModificaIstanza the data modifica istanza
     */
    public void setDataModificaIstanza(Timestamp dataModificaIstanza) {
        this.dataModificaIstanza = dataModificaIstanza;
    }

    /**
     * Gets data inserimento pratica.
     *
     * @return the data inserimento pratica
     */
    public Timestamp getDataInserimentoPratica() {
        return dataInserimentoPratica;
    }

    /**
     * Sets data inserimento pratica.
     *
     * @param dataInserimentoPratica the data inserimento pratica
     */
    public void setDataInserimentoPratica(Timestamp dataInserimentoPratica) {
        this.dataInserimentoPratica = dataInserimentoPratica;
    }

    /**
     * Gets data modifica pratica.
     *
     * @return the data modifica pratica
     */
    public Timestamp getDataModificaPratica() {
        return dataModificaPratica;
    }

    /**
     * Sets data modifica pratica.
     *
     * @param dataModificaPratica the data modifica pratica
     */
    public void setDataModificaPratica(Timestamp dataModificaPratica) {
        this.dataModificaPratica = dataModificaPratica;
    }

    /**
     * Gets cod pratica.
     *
     * @return the cod pratica
     */
    public String getCodPratica() {
        return codPratica;
    }

    /**
     * Sets cod pratica.
     *
     * @param codPratica the cod pratica
     */
    public void setCodPratica(String codPratica) {
        this.codPratica = codPratica;
    }

    /**
     * Gets attore gestione istanza.
     *
     * @return the attore gestione istanza
     */
    public String getAttoreGestioneIstanza() {
        return attoreGestioneIstanza;
    }

    /**
     * Sets attore gestione istanza.
     *
     * @param attoreGestioneIstanza the attore gestione istanza
     */
    public void setAttoreGestioneIstanza(String attoreGestioneIstanza) {
        this.attoreGestioneIstanza = attoreGestioneIstanza;
    }

    /**
     * Gets attore modifica fo.
     *
     * @return the attore modifica fo
     */
    public String getAttoreModificaFO() {
        return attoreModificaFO;
    }

    /**
     * Sets attore modifica fo.
     *
     * @param attoreModificaFO the attore modifica fo
     */
    public void setAttoreModificaFO(String attoreModificaFO) {
        this.attoreModificaFO = attoreModificaFO;
    }

    /**
     * Gets attore modifica bo.
     *
     * @return the attore modifica bo
     */
    public String getAttoreModificaBO() {
        return attoreModificaBO;
    }

    /**
     * Sets attore modifica bo.
     *
     * @param attoreModificaBO the attore modifica bo
     */
    public void setAttoreModificaBO(String attoreModificaBO) {
        this.attoreModificaBO = attoreModificaBO;
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
     * Gets tipi adempimento oggetto app.
     *
     * @return the tipi adempimento oggetto app
     */
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipiAdempimentoOggettoApp() {
        return tipiAdempimentoOggettoApp;
    }

    /**
     * Sets tipi adempimento oggetto app.
     *
     * @param tipiAdempimentoOggettoApp the tipi adempimento oggetto app
     */
    public void setTipiAdempimentoOggettoApp(List<TipoAdempimentoOggettoAppExtendedDTO> tipiAdempimentoOggettoApp) {
        this.tipiAdempimentoOggettoApp = tipiAdempimentoOggettoApp;
    }

    /**
     * Gets profilo oggetto app list.
     *
     * @return the profilo oggetto app list
     */
    public List<ProfiloOggettoAppExtendedDTO> getProfiloOggettoAppList() {
        return profiloOggettoAppList;
    }

    /**
     * Sets profilo oggetto app list.
     *
     * @param profiloOggettoAppList the profilo oggetto app list
     */
    public void setProfiloOggettoAppList(List<ProfiloOggettoAppExtendedDTO> profiloOggettoAppList) {
        this.profiloOggettoAppList = profiloOggettoAppList;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IstanzaSearchResultDTO that = (IstanzaSearchResultDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(gestUIDIstanza, that.gestUIDIstanza) && Objects.equals(codIstanza, that.codIstanza) && Objects.equals(codAdmbito, that.codAdmbito) && Objects.equals(codAdempimento, that.codAdempimento) && Objects.equals(desAdempimento, that.desAdempimento) && Objects.equals(denSoggetto, that.denSoggetto) && Objects.equals(denOggetto, that.denOggetto) && Objects.equals(comune, that.comune) && Objects.equals(desStatoIstanza, that.desStatoIstanza) && Objects.equals(codiceStatoIstanza, that.codiceStatoIstanza) && Objects.equals(desEstesaStatoIstanza, that.desEstesaStatoIstanza) && Objects.equals(labelStato, that.labelStato) && Objects.equals(dataInserimentoIstanza, that.dataInserimentoIstanza) && Objects.equals(dataModificaIstanza, that.dataModificaIstanza) && Objects.equals(dataInserimentoPratica, that.dataInserimentoPratica) && Objects.equals(dataModificaPratica, that.dataModificaPratica) && Objects.equals(codPratica, that.codPratica) && Objects.equals(attoreGestioneIstanza, that.attoreGestioneIstanza) && Objects.equals(attoreModificaFO, that.attoreModificaFO) && Objects.equals(attoreModificaBO, that.attoreModificaBO) && Objects.equals(desStatoSintesiPagamento, that.desStatoSintesiPagamento) && Objects.equals(tipiAdempimentoOggettoApp, that.tipiAdempimentoOggettoApp) && Objects.equals(profiloOggettoAppList, that.profiloOggettoAppList);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, gestUIDIstanza, codIstanza, codAdmbito, codAdempimento, desAdempimento, denSoggetto, denOggetto, comune, desStatoIstanza, codiceStatoIstanza, desEstesaStatoIstanza, labelStato, dataInserimentoIstanza, dataModificaIstanza, dataInserimentoPratica, dataModificaPratica, codPratica, attoreGestioneIstanza, attoreModificaFO, attoreModificaBO, desStatoSintesiPagamento, tipiAdempimentoOggettoApp, profiloOggettoAppList);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IstanzaSearchResultDTO {\n" +
                "         idIstanza:" + idIstanza +
                ",\n         gestUIDIstanza:'" + gestUIDIstanza + "'" +
                ",\n         codIstanza:'" + codIstanza + "'" +
                ",\n         codAdmbito:'" + codAdmbito + "'" +
                ",\n         codAdempimento:'" + codAdempimento + "'" +
                ",\n         desAdempimento:'" + desAdempimento + "'" +
                ",\n         denSoggetto:'" + denSoggetto + "'" +
                ",\n         denOggetto:'" + denOggetto + "'" +
                ",\n         comune:'" + comune + "'" +
                ",\n         desStatoIstanza:'" + desStatoIstanza + "'" +
                ",\n         codiceStatoIstanza:'" + codiceStatoIstanza + "'" +
                ",\n         desEstesaStatoIstanza:'" + desEstesaStatoIstanza + "'" +
                ",\n         labelStato:'" + labelStato + "'" +
                ",\n         dataInserimentoIstanza:" + dataInserimentoIstanza +
                ",\n         dataModificaIstanza:" + dataModificaIstanza +
                ",\n         dataInserimentoPratica:" + dataInserimentoPratica +
                ",\n         dataModificaPratica:" + dataModificaPratica +
                ",\n         codPratica:'" + codPratica + "'" +
                ",\n         attoreGestioneIstanza:'" + attoreGestioneIstanza + "'" +
                ",\n         attoreModificaFO:'" + attoreModificaFO + "'" +
                ",\n         attoreModificaBO:'" + attoreModificaBO + "'" +
                ",\n         desStatoSintesiPagamento:'" + desStatoSintesiPagamento + "'" +
                ",\n         tipiAdempimentoOggettoApp:" + tipiAdempimentoOggettoApp +
                ",\n         profiloOggettoAppList:" + profiloOggettoAppList +
                "}\n";
    }
}
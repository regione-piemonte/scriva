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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Istanza dto.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_stato_istanza")
    private Long idStatoIstanza;

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("cod_istanza")
    private String codIstanza;

    @JsonProperty("data_inserimento_istanza")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataInserimentoIstanza;

    @JsonProperty("data_modifica_istanza")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataModificaIstanza;

    @JsonProperty("json_data")
    private String jsonData;

    @JsonProperty("uuid_index")
    private String uuidIndex;

    @JsonProperty("cod_pratica")
    private String codPratica;

    //@JsonProperty(value = "id_template", access = JsonProperty.Access.WRITE_ONLY)
    @JsonProperty(value = "id_template")
    private Long idTemplate;

    @JsonIgnore
    private Long idIstanzaAttoreOwner;

    @JsonProperty("data_inserimento_pratica")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataInserimentoPratica;

    @JsonProperty("data_modifica_pratica")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataModificaPratica;

    @JsonProperty("des_stato_sintesi_pagamento")
    private String desStatoSintesiPagamento;

    @JsonIgnore
    private Long idIstanzaAttore;

    @JsonIgnore
    private Long idFunzionario;

    @JsonProperty("data_pubblicazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataPubblicazione;

    @JsonProperty("num_protocollo_istanza")
    private String numProtocolloIstanza;

    @JsonProperty("data_protocollo_istanza")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataProtocolloIstanza;

    @JsonProperty("data_inizio_osservazioni")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataInizioOsservazioni;

    @JsonProperty("data_fine_osservazioni")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataFineOsservazioni;

    @JsonProperty("data_conclusione_procedimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataConclusioneProcedimento;

    @JsonProperty("id_esito_procedimento")
    private Long idEsitoProcedimento;

    @JsonProperty("data_scadenza_procedimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataScadenzaProcedimento;

    @JsonProperty("anno_registro")
    private Integer annoRegistro;

    @JsonProperty("id_stato_proced_statale")
    private Long idStatoProcedStatale;

    @JsonProperty("id_esito_proced_statale")
    private Long idEsitoProcedStatale;   

    /**
     * Gets id istanza.
     *
     * @return idIstanza id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza idIstanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets id stato istanza.
     *
     * @return idStatoIstanza id stato istanza
     */
    public Long getIdStatoIstanza() {
        return idStatoIstanza;
    }

    /**
     * Sets id stato istanza.
     *
     * @param idStatoIstanza idStatoIstanza
     */
    public void setIdStatoIstanza(Long idStatoIstanza) {
        this.idStatoIstanza = idStatoIstanza;
    }

    /**
     * Gets id adempimento.
     *
     * @return idAdempimento id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento idAdempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * Gets cod istanza.
     *
     * @return codIstanza cod istanza
     */
    public String getCodIstanza() {
        return codIstanza;
    }

    /**
     * Sets cod istanza.
     *
     * @param codIstanza codIstanza
     */
    public void setCodIstanza(String codIstanza) {
        this.codIstanza = codIstanza;
    }

    /**
     * Gets data inserimento istanza.
     *
     * @return dataInserimentoIstanza data inserimento istanza
     */
    public Timestamp getDataInserimentoIstanza() {
        return dataInserimentoIstanza;
    }

    /**
     * Sets data inserimento istanza.
     *
     * @param dataInserimentoIstanza dataInserimentoIstanza
     */
    public void setDataInserimentoIstanza(Timestamp dataInserimentoIstanza) {
        this.dataInserimentoIstanza = dataInserimentoIstanza;
    }

    /**
     * Gets data modifica istanza.
     *
     * @return dataModificaIstanza data modifica istanza
     */
    public Timestamp getDataModificaIstanza() {
        return dataModificaIstanza;
    }

    /**
     * Sets data modifica istanza.
     *
     * @param dataModificaIstanza dataModificaIstanza
     */
    public void setDataModificaIstanza(Timestamp dataModificaIstanza) {
        this.dataModificaIstanza = dataModificaIstanza;
    }

    /**
     * Gets json data.
     *
     * @return jsonData json data
     */
    public String getJsonData() {
        return jsonData;
    }

    /**
     * Sets json data.
     *
     * @param jsonData jsonData
     */
    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    /**
     * Gets uuid index.
     *
     * @return uuidIndex uuid index
     */
    public String getUuidIndex() {
        return uuidIndex;
    }

    /**
     * Sets uuid index.
     *
     * @param uuidIndex uuidIndex
     */
    public void setUuidIndex(String uuidIndex) {
        this.uuidIndex = uuidIndex;
    }

    /**
     * Gets cod pratica.
     *
     * @return codPratica cod pratica
     */
    public String getCodPratica() {
        return codPratica;
    }

    /**
     * Sets cod pratica.
     *
     * @param codPratica codPratica
     */
    public void setCodPratica(String codPratica) {
        this.codPratica = codPratica;
    }

    /**
     * Gets id template.
     *
     * @return idTemplate id template
     */
    @JsonIgnore
    public Long getIdTemplate() {
        return idTemplate;
    }

    /**
     * Sets id template.
     *
     * @param idTemplate idTemplate
     */
    @JsonProperty("id_template")
    public void setIdTemplate(Long idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * Gets id istanza attore owner.
     *
     * @return idIstanzaAttoreOwner id istanza attore owner
     */
    public Long getIdIstanzaAttoreOwner() {
        return idIstanzaAttoreOwner;
    }

    /**
     * Sets id istanza attore owner.
     *
     * @param idIstanzaAttoreOwner idIstanzaAttoreOwner
     */
    public void setIdIstanzaAttoreOwner(Long idIstanzaAttoreOwner) {
        this.idIstanzaAttoreOwner = idIstanzaAttoreOwner;
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
     * Gets id istanza attore.
     *
     * @return the id istanza attore
     */
    public Long getIdIstanzaAttore() {
        return idIstanzaAttore;
    }

    /**
     * Sets id istanza attore.
     *
     * @param idIstanzaAttore the id istanza attore
     */
    public void setIdIstanzaAttore(Long idIstanzaAttore) {
        this.idIstanzaAttore = idIstanzaAttore;
    }

    /**
     * Gets id funzionario.
     *
     * @return the id funzionario
     */
    public Long getIdFunzionario() {
        return idFunzionario;
    }

    /**
     * Sets id funzionario.
     *
     * @param idFunzionario the id funzionario
     */
    public void setIdFunzionario(Long idFunzionario) {
        this.idFunzionario = idFunzionario;
    }

    /**
     * Gets data pubblicazione.
     *
     * @return the data pubblicazione
     */
    public Timestamp getDataPubblicazione() {
        return dataPubblicazione;
    }

    /**
     * Sets data pubblicazione.
     *
     * @param dataPubblicazione the data pubblicazione
     */
    public void setDataPubblicazione(Timestamp dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    /**
     * Gets num protocollo istanza.
     *
     * @return the num protocollo istanza
     */
    public String getNumProtocolloIstanza() {
        return numProtocolloIstanza;
    }

    /**
     * Sets num protocollo istanza.
     *
     * @param numProtocolloIstanza the num protocollo istanza
     */
    public void setNumProtocolloIstanza(String numProtocolloIstanza) {
        this.numProtocolloIstanza = numProtocolloIstanza;
    }

    /**
     * Gets data protocollo istanza.
     *
     * @return the data protocollo istanza
     */
    public Timestamp getDataProtocolloIstanza() {
        return dataProtocolloIstanza;
    }

    /**
     * Sets data protocollo istanza.
     *
     * @param dataProtocolloIstanza the data protocollo istanza
     */
    public void setDataProtocolloIstanza(Timestamp dataProtocolloIstanza) {
        this.dataProtocolloIstanza = dataProtocolloIstanza;
    }

    /**
     * Gets data inizio osservazioni.
     *
     * @return the data inizio osservazioni
     */
    public Timestamp getDataInizioOsservazioni() {
        return dataInizioOsservazioni;
    }

    /**
     * Sets data inizio osservazioni.
     *
     * @param dataInizioOsservazioni the data inizio osservazioni
     */
    public void setDataInizioOsservazioni(Timestamp dataInizioOsservazioni) {
        this.dataInizioOsservazioni = dataInizioOsservazioni;
    }

    /**
     * Gets data fine osservazioni.
     *
     * @return the data fine osservazioni
     */
    public Timestamp getDataFineOsservazioni() {
        return dataFineOsservazioni;
    }

    /**
     * Sets data fine osservazioni.
     *
     * @param dataFineOsservazioni the data fine osservazioni
     */
    public void setDataFineOsservazioni(Timestamp dataFineOsservazioni) {
        this.dataFineOsservazioni = dataFineOsservazioni;
    }

    /**
     * Gets data conclusione procedimento.
     *
     * @return the data conclusione procedimento
     */
    public Timestamp getDataConclusioneProcedimento() {
        return dataConclusioneProcedimento;
    }

    /**
     * Sets data conclusione procedimento.
     *
     * @param dataConclusioneProcedimento the data conclusione procedimento
     */
    public void setDataConclusioneProcedimento(Timestamp dataConclusioneProcedimento) {
        this.dataConclusioneProcedimento = dataConclusioneProcedimento;
    }

    /**
     * Gets id esito procedimento.
     *
     * @return the id esito procedimento
     */
    public Long getIdEsitoProcedimento() {
        return idEsitoProcedimento;
    }

    /**
     * Sets id esito procedimento.
     *
     * @param idEsitoProcedimento the id esito procedimento
     */
    public void setIdEsitoProcedimento(Long idEsitoProcedimento) {
        this.idEsitoProcedimento = idEsitoProcedimento;
    }

    /**
     * Gets data scadenza procedimento.
     *
     * @return the data scadenza procedimento
     */
    public Timestamp getDataScadenzaProcedimento() {
        return dataScadenzaProcedimento;
    }

    /**
     * Sets data scadenza procedimento.
     *
     * @param dataScadenzaProcedimento the data scadenza procedimento
     */
    public void setDataScadenzaProcedimento(Timestamp dataScadenzaProcedimento) {
        this.dataScadenzaProcedimento = dataScadenzaProcedimento;
    }

    /**
     * Gets anno registro.
     *
     * @return the anno registro
     */
    public Integer getAnnoRegistro() {
        return annoRegistro;
    }

    /**
     * Sets anno registro.
     *
     * @param annoRegistro the anno registro
     */
    public void setAnnoRegistro(Integer annoRegistro) {
        this.annoRegistro = annoRegistro;
    }

    /**
     * Gets id stato proced statale.
     *
     * @return the id stato proced statale
     */
    public Long getIdStatoProcedStatale() {
        return idStatoProcedStatale;
    }

    /**
     * Sets id stato proced statale.
     *
     * @param idStatoProcedStatale the id stato proced statale
     */
    public void setIdStatoProcedStatale(Long idStatoProcedStatale) {
        this.idStatoProcedStatale = idStatoProcedStatale;
    }

    /**
     * Gets id esito proced statale.
     *
     * @return the id esito proced statale
     */
    public Long getIdEsitoProcedStatale() {
        return idEsitoProcedStatale;
    }

    /**
     * Sets id esito proced statale.
     *
     * @param idEsitoProcedStatale the id esito proced statale
     */
    public void setIdEsitoProcedStatale(Long idEsitoProcedStatale) {
        this.idEsitoProcedStatale = idEsitoProcedStatale;
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
        IstanzaDTO that = (IstanzaDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idStatoIstanza, that.idStatoIstanza) && Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(codIstanza, that.codIstanza) && Objects.equals(dataInserimentoIstanza, that.dataInserimentoIstanza) && Objects.equals(dataModificaIstanza, that.dataModificaIstanza) && Objects.equals(jsonData, that.jsonData) && Objects.equals(uuidIndex, that.uuidIndex) && Objects.equals(codPratica, that.codPratica) && Objects.equals(idTemplate, that.idTemplate) && Objects.equals(idIstanzaAttoreOwner, that.idIstanzaAttoreOwner) && Objects.equals(dataInserimentoPratica, that.dataInserimentoPratica) && Objects.equals(dataModificaPratica, that.dataModificaPratica) && Objects.equals(desStatoSintesiPagamento, that.desStatoSintesiPagamento) && Objects.equals(idIstanzaAttore, that.idIstanzaAttore) && Objects.equals(idFunzionario, that.idFunzionario) && Objects.equals(dataPubblicazione, that.dataPubblicazione) && Objects.equals(numProtocolloIstanza, that.numProtocolloIstanza) && Objects.equals(dataProtocolloIstanza, that.dataProtocolloIstanza) && Objects.equals(dataInizioOsservazioni, that.dataInizioOsservazioni) && Objects.equals(dataFineOsservazioni, that.dataFineOsservazioni) && Objects.equals(dataConclusioneProcedimento, that.dataConclusioneProcedimento) && Objects.equals(idEsitoProcedimento, that.idEsitoProcedimento) && Objects.equals(dataScadenzaProcedimento, that.dataScadenzaProcedimento) && Objects.equals(annoRegistro, that.annoRegistro) && Objects.equals(idStatoProcedStatale, that.idStatoProcedStatale) && Objects.equals(idEsitoProcedStatale, that.idEsitoProcedStatale);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIstanza, idStatoIstanza, idAdempimento, codIstanza, dataInserimentoIstanza, dataModificaIstanza, jsonData, uuidIndex, codPratica, idTemplate, idIstanzaAttoreOwner, dataInserimentoPratica, dataModificaPratica, desStatoSintesiPagamento, idIstanzaAttore, idFunzionario, dataPubblicazione, numProtocolloIstanza, dataProtocolloIstanza, dataInizioOsservazioni, dataFineOsservazioni, dataConclusioneProcedimento, idEsitoProcedimento, dataScadenzaProcedimento, annoRegistro, idStatoProcedStatale, idEsitoProcedStatale);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IstanzaDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idIstanza:" + idIstanza +
                ",\n         idStatoIstanza:" + idStatoIstanza +
                ",\n         idAdempimento:" + idAdempimento +
                ",\n         codIstanza:'" + codIstanza + "'" +
                ",\n         dataInserimentoIstanza:" + dataInserimentoIstanza +
                ",\n         dataModificaIstanza:" + dataModificaIstanza +
                ",\n         jsonData:'" + jsonData + "'" +
                ",\n         uuidIndex:'" + uuidIndex + "'" +
                ",\n         codPratica:'" + codPratica + "'" +
                ",\n         idTemplate:" + idTemplate +
                ",\n         idIstanzaAttoreOwner:" + idIstanzaAttoreOwner +
                ",\n         dataInserimentoPratica:" + dataInserimentoPratica +
                ",\n         dataModificaPratica:" + dataModificaPratica +
                ",\n         desStatoSintesiPagamento:'" + desStatoSintesiPagamento + "'" +
                ",\n         idIstanzaAttore:" + idIstanzaAttore +
                ",\n         idFunzionario:" + idFunzionario +
                ",\n         dataPubblicazione:" + dataPubblicazione +
                ",\n         numProtocolloIstanza:'" + numProtocolloIstanza + "'" +
                ",\n         dataProtocolloIstanza:" + dataProtocolloIstanza +
                ",\n         dataInizioOsservazioni:" + dataInizioOsservazioni +
                ",\n         dataFineOsservazioni:" + dataFineOsservazioni +
                ",\n         dataConclusioneProcedimento:" + dataConclusioneProcedimento +
                ",\n         idEsitoProcedimento:" + idEsitoProcedimento +
                ",\n         dataScadenzaProcedimento:" + dataScadenzaProcedimento +
                ",\n         annoRegistro:" + annoRegistro +
                ",\n         idEsitoProcedStatale:" + idEsitoProcedStatale +
                ",\n         idStatoProcedStatale:" + idStatoProcedStatale +
                "}\n";
    }

}
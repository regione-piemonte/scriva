/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.XML;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * The type P pay rt out dto.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PPayRtOutDTO {

    private String identificativoPagamento;

    private String codiceEsito;

    private String descrizioneEsito;

    private String descrizioneStatoPagamento;

    private String iuvOriginario;

    private String iuvEffettivo;

    private String identificativoStatoPagamento;

    private String ricevutaPdf;

    private String rtXml;

    /**
     * Gets rt xml.
     *
     * @return the rt xml
     */
    public String getRtXml() {
        return rtXml;
    }

    /**
     * Sets rt xml.
     *
     * @param rtXml the rt xml
     */
    public void setRtXml(String rtXml) {
        this.rtXml = rtXml;
    }

    /**
     * Gets codice esito.
     *
     * @return the codice esito
     */
    public String getCodiceEsito() {
        return codiceEsito;
    }

    /**
     * Sets codice esito.
     *
     * @param codiceEsito the codice esito
     */
    public void setCodiceEsito(String codiceEsito) {
        this.codiceEsito = codiceEsito;
    }

    /**
     * Gets descrizione esito.
     *
     * @return the descrizione esito
     */
    public String getDescrizioneEsito() {
        return descrizioneEsito;
    }

    /**
     * Sets descrizione esito.
     *
     * @param descrizioneEsito the descrizione esito
     */
    public void setDescrizioneEsito(String descrizioneEsito) {
        this.descrizioneEsito = descrizioneEsito;
    }

    /**
     * Gets identificativo pagamento.
     *
     * @return the identificativo pagamento
     */
    public String getIdentificativoPagamento() {
        return identificativoPagamento;
    }

    /**
     * Sets identificativo pagamento.
     *
     * @param identificativoPagamento the identificativo pagamento
     */
    public void setIdentificativoPagamento(String identificativoPagamento) {
        this.identificativoPagamento = identificativoPagamento;
    }

    /**
     * Gets identificativo stato pagamento.
     *
     * @return the identificativo stato pagamento
     */
    public String getIdentificativoStatoPagamento() {
        return identificativoStatoPagamento;
    }

    /**
     * Sets identificativo stato pagamento.
     *
     * @param identificativoStatoPagamento the identificativo stato pagamento
     */
    public void setIdentificativoStatoPagamento(String identificativoStatoPagamento) {
        this.identificativoStatoPagamento = identificativoStatoPagamento;
    }

    /**
     * Gets iuv originario.
     *
     * @return the iuv originario
     */
    public String getIuvOriginario() {
        return iuvOriginario;
    }

    /**
     * Sets iuv originario.
     *
     * @param iuvOriginario the iuv originario
     */
    public void setIuvOriginario(String iuvOriginario) {
        this.iuvOriginario = iuvOriginario;
    }

    /**
     * Gets iuv effettivo.
     *
     * @return the iuv effettivo
     */
    public String getIuvEffettivo() {
        return iuvEffettivo;
    }

    /**
     * Gets descrizione stato pagamento.
     *
     * @return the descrizione stato pagamento
     */
    public String getDescrizioneStatoPagamento() {
        return descrizioneStatoPagamento;
    }

    /**
     * Sets descrizione stato pagamento.
     *
     * @param descrizioneStatoPagamento the descrizione stato pagamento
     */
    public void setDescrizioneStatoPagamento(String descrizioneStatoPagamento) {
        this.descrizioneStatoPagamento = descrizioneStatoPagamento;
    }

    /**
     * Gets ricevuta pdf.
     *
     * @return the ricevuta pdf
     */
    public String getRicevutaPdf() {
        return ricevutaPdf;
    }

    /**
     * Sets ricevuta pdf.
     *
     * @param ricevutaPdf the ricevuta pdf
     */
    public void setRicevutaPdf(String ricevutaPdf) {
        this.ricevutaPdf = ricevutaPdf;
    }

    /**
     * Sets iuv effettivo.
     *
     * @param iuvEffettivo the iuv effettivo
     */
    public void setIuvEffettivo(String iuvEffettivo) {
        this.iuvEffettivo = iuvEffettivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PPayRtOutDTO that = (PPayRtOutDTO) o;
        return Objects.equals(rtXml, that.rtXml) && Objects.equals(codiceEsito, that.codiceEsito) && Objects.equals(descrizioneEsito, that.descrizioneEsito) && Objects.equals(identificativoPagamento, that.identificativoPagamento) && Objects.equals(identificativoStatoPagamento, that.identificativoStatoPagamento) && Objects.equals(iuvOriginario, that.iuvOriginario) && Objects.equals(iuvEffettivo, that.iuvEffettivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rtXml, codiceEsito, descrizioneEsito, identificativoPagamento, identificativoStatoPagamento, iuvOriginario, iuvEffettivo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PPayRtOutDTO {");
        sb.append("         rt_xml:'").append(rtXml).append("'");
        sb.append(",         codiceEsito:'").append(codiceEsito).append("'");
        sb.append(",         descrizioneEsito:'").append(descrizioneEsito).append("'");
        sb.append(",         identificativoPagamento:'").append(identificativoPagamento).append("'");
        sb.append(",         identificativoStatoPagamento:'").append(identificativoStatoPagamento).append("'");
        sb.append(",         iuvOriginario:'").append(iuvOriginario).append("'");
        sb.append(",         iuvEffettivo:'").append(iuvEffettivo).append("'");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Get rt xml json json object.
     *
     * @return the json object
     */
    @JsonIgnore
    public JSONObject getRtXmlJson() {
        JSONObject jsonXML = null;
        if (StringUtils.isNotBlank(rtXml)) {
            byte[] rtXmlBytes = Base64.getDecoder().decode(rtXml);
            String rtXmlDecoded = new String(rtXmlBytes, StandardCharsets.UTF_8);
            this.setRtXml(rtXmlDecoded);
            jsonXML = XML.toJSONObject(rtXmlDecoded);
        }
        return jsonXML;
    }

    /**
     * Get rt pdf byte [ ].
     *
     * @return the byte [ ]
     */
    @JsonIgnore
    public byte[] getRtPdf() {
        byte[] rtPdf = null;
        if (StringUtils.isNotBlank(ricevutaPdf)) {
            rtPdf = Base64.getDecoder().decode(ricevutaPdf);
        }
        return rtPdf;
    }
}
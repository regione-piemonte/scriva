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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * PaymentDataResult
 */
public class PaymentDataResult {
    private BigDecimal importoPagatoTotale = null;

    private String enteBeneficiario = null;

    private String codiceFiscaleEnteBeneficiario = null;

    private String tipologiaVersamento = null;

    private BigDecimal importoPagato = null;

    private OneOfPaymentDataResultDatiMultibeneficiario datiMultibeneficiario = null;

    private String nomeECognomeRagioneSociale = null;

    private String codiceFiscalePIva = null;

    private String codiceAvviso = null;

    private String identificativoUnicoVersamento = null;

    private String numeroTransazione = null;

    private String prestatoreDiServiziDiPagamento = null;

    private OffsetDateTime dataEOraOperazione = null;

    private OffsetDateTime dataEsitoPagamento = null;

    private String identificativoUnicoRiscossione = null;

    private String infoAggiuntive = null;

    private OneOfPaymentDataResultResult result = null;

    public PaymentDataResult importoPagatoTotale(BigDecimal importoPagatoTotale) {
        this.importoPagatoTotale = importoPagatoTotale;
        return this;
    }

    /**
     * Get importoPagatoTotale
     *
     * @return importoPagatoTotale
     **/
    public BigDecimal getImportoPagatoTotale() {
        return importoPagatoTotale;
    }

    public void setImportoPagatoTotale(BigDecimal importoPagatoTotale) {
        this.importoPagatoTotale = importoPagatoTotale;
    }

    public PaymentDataResult enteBeneficiario(String enteBeneficiario) {
        this.enteBeneficiario = enteBeneficiario;
        return this;
    }

    /**
     * Get enteBeneficiario
     *
     * @return enteBeneficiario
     **/
    public String getEnteBeneficiario() {
        return enteBeneficiario;
    }

    public void setEnteBeneficiario(String enteBeneficiario) {
        this.enteBeneficiario = enteBeneficiario;
    }

    public PaymentDataResult codiceFiscaleEnteBeneficiario(String codiceFiscaleEnteBeneficiario) {
        this.codiceFiscaleEnteBeneficiario = codiceFiscaleEnteBeneficiario;
        return this;
    }

    /**
     * Get codiceFiscaleEnteBeneficiario
     *
     * @return codiceFiscaleEnteBeneficiario
     **/
    public String getCodiceFiscaleEnteBeneficiario() {
        return codiceFiscaleEnteBeneficiario;
    }

    public void setCodiceFiscaleEnteBeneficiario(String codiceFiscaleEnteBeneficiario) {
        this.codiceFiscaleEnteBeneficiario = codiceFiscaleEnteBeneficiario;
    }

    public PaymentDataResult tipologiaVersamento(String tipologiaVersamento) {
        this.tipologiaVersamento = tipologiaVersamento;
        return this;
    }

    /**
     * Get tipologiaVersamento
     *
     * @return tipologiaVersamento
     **/
    public String getTipologiaVersamento() {
        return tipologiaVersamento;
    }

    public void setTipologiaVersamento(String tipologiaVersamento) {
        this.tipologiaVersamento = tipologiaVersamento;
    }

    public PaymentDataResult importoPagato(BigDecimal importoPagato) {
        this.importoPagato = importoPagato;
        return this;
    }

    /**
     * Get importoPagato
     *
     * @return importoPagato
     **/
    public BigDecimal getImportoPagato() {
        return importoPagato;
    }

    public void setImportoPagato(BigDecimal importoPagato) {
        this.importoPagato = importoPagato;
    }

    public PaymentDataResult datiMultibeneficiario(OneOfPaymentDataResultDatiMultibeneficiario datiMultibeneficiario) {
        this.datiMultibeneficiario = datiMultibeneficiario;
        return this;
    }

    /**
     * Get datiMultibeneficiario
     *
     * @return datiMultibeneficiario
     **/
    public OneOfPaymentDataResultDatiMultibeneficiario getDatiMultibeneficiario() {
        return datiMultibeneficiario;
    }

    public void setDatiMultibeneficiario(OneOfPaymentDataResultDatiMultibeneficiario datiMultibeneficiario) {
        this.datiMultibeneficiario = datiMultibeneficiario;
    }

    public PaymentDataResult nomeECognomeRagioneSociale(String nomeECognomeRagioneSociale) {
        this.nomeECognomeRagioneSociale = nomeECognomeRagioneSociale;
        return this;
    }

    /**
     * Get nomeECognomeRagioneSociale
     *
     * @return nomeECognomeRagioneSociale
     **/
    public String getNomeECognomeRagioneSociale() {
        return nomeECognomeRagioneSociale;
    }

    public void setNomeECognomeRagioneSociale(String nomeECognomeRagioneSociale) {
        this.nomeECognomeRagioneSociale = nomeECognomeRagioneSociale;
    }

    public PaymentDataResult codiceFiscalePIva(String codiceFiscalePIva) {
        this.codiceFiscalePIva = codiceFiscalePIva;
        return this;
    }

    /**
     * Get codiceFiscalePIva
     *
     * @return codiceFiscalePIva
     **/
    public String getCodiceFiscalePIva() {
        return codiceFiscalePIva;
    }

    public void setCodiceFiscalePIva(String codiceFiscalePIva) {
        this.codiceFiscalePIva = codiceFiscalePIva;
    }

    public PaymentDataResult codiceAvviso(String codiceAvviso) {
        this.codiceAvviso = codiceAvviso;
        return this;
    }

    /**
     * Get codiceAvviso
     *
     * @return codiceAvviso
     **/
    public String getCodiceAvviso() {
        return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {
        this.codiceAvviso = codiceAvviso;
    }

    public PaymentDataResult identificativoUnicoVersamento(String identificativoUnicoVersamento) {
        this.identificativoUnicoVersamento = identificativoUnicoVersamento;
        return this;
    }

    /**
     * Get identificativoUnicoVersamento
     *
     * @return identificativoUnicoVersamento
     **/
    public String getIdentificativoUnicoVersamento() {
        return identificativoUnicoVersamento;
    }

    public void setIdentificativoUnicoVersamento(String identificativoUnicoVersamento) {
        this.identificativoUnicoVersamento = identificativoUnicoVersamento;
    }

    public PaymentDataResult numeroTransazione(String numeroTransazione) {
        this.numeroTransazione = numeroTransazione;
        return this;
    }

    /**
     * Get numeroTransazione
     *
     * @return numeroTransazione
     **/
    public String getNumeroTransazione() {
        return numeroTransazione;
    }

    public void setNumeroTransazione(String numeroTransazione) {
        this.numeroTransazione = numeroTransazione;
    }

    public PaymentDataResult prestatoreDiServiziDiPagamento(String prestatoreDiServiziDiPagamento) {
        this.prestatoreDiServiziDiPagamento = prestatoreDiServiziDiPagamento;
        return this;
    }

    /**
     * Get prestatoreDiServiziDiPagamento
     *
     * @return prestatoreDiServiziDiPagamento
     **/
    public String getPrestatoreDiServiziDiPagamento() {
        return prestatoreDiServiziDiPagamento;
    }

    public void setPrestatoreDiServiziDiPagamento(String prestatoreDiServiziDiPagamento) {
        this.prestatoreDiServiziDiPagamento = prestatoreDiServiziDiPagamento;
    }

    public PaymentDataResult dataEOraOperazione(OffsetDateTime dataEOraOperazione) {
        this.dataEOraOperazione = dataEOraOperazione;
        return this;
    }

    /**
     * Get dataEOraOperazione
     *
     * @return dataEOraOperazione
     **/
    public OffsetDateTime getDataEOraOperazione() {
        return dataEOraOperazione;
    }

    public void setDataEOraOperazione(OffsetDateTime dataEOraOperazione) {
        this.dataEOraOperazione = dataEOraOperazione;
    }

    public PaymentDataResult dataEsitoPagamento(OffsetDateTime dataEsitoPagamento) {
        this.dataEsitoPagamento = dataEsitoPagamento;
        return this;
    }

    /**
     * Get dataEsitoPagamento
     *
     * @return dataEsitoPagamento
     **/
    public OffsetDateTime getDataEsitoPagamento() {
        return dataEsitoPagamento;
    }

    public void setDataEsitoPagamento(OffsetDateTime dataEsitoPagamento) {
        this.dataEsitoPagamento = dataEsitoPagamento;
    }

    public PaymentDataResult identificativoUnicoRiscossione(String identificativoUnicoRiscossione) {
        this.identificativoUnicoRiscossione = identificativoUnicoRiscossione;
        return this;
    }

    /**
     * Get identificativoUnicoRiscossione
     *
     * @return identificativoUnicoRiscossione
     **/
    public String getIdentificativoUnicoRiscossione() {
        return identificativoUnicoRiscossione;
    }

    public void setIdentificativoUnicoRiscossione(String identificativoUnicoRiscossione) {
        this.identificativoUnicoRiscossione = identificativoUnicoRiscossione;
    }

    public PaymentDataResult infoAggiuntive(String infoAggiuntive) {
        this.infoAggiuntive = infoAggiuntive;
        return this;
    }

    /**
     * Get infoAggiuntive
     *
     * @return infoAggiuntive
     **/
    public String getInfoAggiuntive() {
        return infoAggiuntive;
    }

    public void setInfoAggiuntive(String infoAggiuntive) {
        this.infoAggiuntive = infoAggiuntive;
    }

    public PaymentDataResult result(OneOfPaymentDataResultResult result) {
        this.result = result;
        return this;
    }

    /**
     * Get result
     *
     * @return result
     **/
    public OneOfPaymentDataResultResult getResult() {
        return result;
    }

    public void setResult(OneOfPaymentDataResultResult result) {
        this.result = result;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentDataResult paymentDataResult = (PaymentDataResult) o;
        return Objects.equals(this.importoPagatoTotale, paymentDataResult.importoPagatoTotale) &&
                Objects.equals(this.enteBeneficiario, paymentDataResult.enteBeneficiario) &&
                Objects.equals(this.codiceFiscaleEnteBeneficiario, paymentDataResult.codiceFiscaleEnteBeneficiario) &&
                Objects.equals(this.tipologiaVersamento, paymentDataResult.tipologiaVersamento) &&
                Objects.equals(this.importoPagato, paymentDataResult.importoPagato) &&
                Objects.equals(this.datiMultibeneficiario, paymentDataResult.datiMultibeneficiario) &&
                Objects.equals(this.nomeECognomeRagioneSociale, paymentDataResult.nomeECognomeRagioneSociale) &&
                Objects.equals(this.codiceFiscalePIva, paymentDataResult.codiceFiscalePIva) &&
                Objects.equals(this.codiceAvviso, paymentDataResult.codiceAvviso) &&
                Objects.equals(this.identificativoUnicoVersamento, paymentDataResult.identificativoUnicoVersamento) &&
                Objects.equals(this.numeroTransazione, paymentDataResult.numeroTransazione) &&
                Objects.equals(this.prestatoreDiServiziDiPagamento, paymentDataResult.prestatoreDiServiziDiPagamento) &&
                Objects.equals(this.dataEOraOperazione, paymentDataResult.dataEOraOperazione) &&
                Objects.equals(this.dataEsitoPagamento, paymentDataResult.dataEsitoPagamento) &&
                Objects.equals(this.identificativoUnicoRiscossione, paymentDataResult.identificativoUnicoRiscossione) &&
                Objects.equals(this.infoAggiuntive, paymentDataResult.infoAggiuntive) &&
                Objects.equals(this.result, paymentDataResult.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(importoPagatoTotale, enteBeneficiario, codiceFiscaleEnteBeneficiario, tipologiaVersamento, importoPagato, datiMultibeneficiario, nomeECognomeRagioneSociale, codiceFiscalePIva, codiceAvviso, identificativoUnicoVersamento, numeroTransazione, prestatoreDiServiziDiPagamento, dataEOraOperazione, dataEsitoPagamento, identificativoUnicoRiscossione, infoAggiuntive, result);
    }


    @Override
    public String toString() {
        return "class PaymentDataResult {\n" +
                "    importoPagatoTotale: " + toIndentedString(importoPagatoTotale) + "\n" +
                "    enteBeneficiario: " + toIndentedString(enteBeneficiario) + "\n" +
                "    codiceFiscaleEnteBeneficiario: " + toIndentedString(codiceFiscaleEnteBeneficiario) + "\n" +
                "    tipologiaVersamento: " + toIndentedString(tipologiaVersamento) + "\n" +
                "    importoPagato: " + toIndentedString(importoPagato) + "\n" +
                "    datiMultibeneficiario: " + toIndentedString(datiMultibeneficiario) + "\n" +
                "    nomeECognomeRagioneSociale: " + toIndentedString(nomeECognomeRagioneSociale) + "\n" +
                "    codiceFiscalePIva: " + toIndentedString(codiceFiscalePIva) + "\n" +
                "    codiceAvviso: " + toIndentedString(codiceAvviso) + "\n" +
                "    identificativoUnicoVersamento: " + toIndentedString(identificativoUnicoVersamento) + "\n" +
                "    numeroTransazione: " + toIndentedString(numeroTransazione) + "\n" +
                "    prestatoreDiServiziDiPagamento: " + toIndentedString(prestatoreDiServiziDiPagamento) + "\n" +
                "    dataEOraOperazione: " + toIndentedString(dataEOraOperazione) + "\n" +
                "    dataEsitoPagamento: " + toIndentedString(dataEsitoPagamento) + "\n" +
                "    identificativoUnicoRiscossione: " + toIndentedString(identificativoUnicoRiscossione) + "\n" +
                "    infoAggiuntive: " + toIndentedString(infoAggiuntive) + "\n" +
                "    result: " + toIndentedString(result) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
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
import java.util.Objects;

/**
 * PaymentComponentMultipayee
 */
public class PaymentComponentMultipayee {
    private String codiceFiscaleEnte = null;

    private String codiceVersamento = null;

    private Integer annoAccertamento = null;

    private String causale = null;

    private String datiSpecificiRiscossione = null;

    private BigDecimal importo = null;

    private String numeroAccertamento = null;

    private Integer progressivo = null;

    public PaymentComponentMultipayee codiceFiscaleEnte(String codiceFiscaleEnte) {
        this.codiceFiscaleEnte = codiceFiscaleEnte;
        return this;
    }

    /**
     * Get codiceFiscaleEnte
     *
     * @return codiceFiscaleEnte
     **/
    public String getCodiceFiscaleEnte() {
        return codiceFiscaleEnte;
    }

    public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {
        this.codiceFiscaleEnte = codiceFiscaleEnte;
    }

    public PaymentComponentMultipayee codiceVersamento(String codiceVersamento) {
        this.codiceVersamento = codiceVersamento;
        return this;
    }

    /**
     * Get codiceVersamento
     *
     * @return codiceVersamento
     **/
    public String getCodiceVersamento() {
        return codiceVersamento;
    }

    public void setCodiceVersamento(String codiceVersamento) {
        this.codiceVersamento = codiceVersamento;
    }

    public PaymentComponentMultipayee annoAccertamento(Integer annoAccertamento) {
        this.annoAccertamento = annoAccertamento;
        return this;
    }

    /**
     * Get annoAccertamento
     *
     * @return annoAccertamento
     **/
    public Integer getAnnoAccertamento() {
        return annoAccertamento;
    }

    public void setAnnoAccertamento(Integer annoAccertamento) {
        this.annoAccertamento = annoAccertamento;
    }

    public PaymentComponentMultipayee causale(String causale) {
        this.causale = causale;
        return this;
    }

    /**
     * Get causale
     *
     * @return causale
     **/
    public String getCausale() {
        return causale;
    }

    public void setCausale(String causale) {
        this.causale = causale;
    }

    public PaymentComponentMultipayee datiSpecificiRiscossione(String datiSpecificiRiscossione) {
        this.datiSpecificiRiscossione = datiSpecificiRiscossione;
        return this;
    }

    /**
     * Get datiSpecificiRiscossione
     *
     * @return datiSpecificiRiscossione
     **/
    public String getDatiSpecificiRiscossione() {
        return datiSpecificiRiscossione;
    }

    public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {
        this.datiSpecificiRiscossione = datiSpecificiRiscossione;
    }

    public PaymentComponentMultipayee importo(BigDecimal importo) {
        this.importo = importo;
        return this;
    }

    /**
     * Get importo
     *
     * @return importo
     **/
    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public PaymentComponentMultipayee numeroAccertamento(String numeroAccertamento) {
        this.numeroAccertamento = numeroAccertamento;
        return this;
    }

    /**
     * Get numeroAccertamento
     *
     * @return numeroAccertamento
     **/
    public String getNumeroAccertamento() {
        return numeroAccertamento;
    }

    public void setNumeroAccertamento(String numeroAccertamento) {
        this.numeroAccertamento = numeroAccertamento;
    }

    public PaymentComponentMultipayee progressivo(Integer progressivo) {
        this.progressivo = progressivo;
        return this;
    }

    /**
     * Get progressivo
     *
     * @return progressivo
     **/
    public Integer getProgressivo() {
        return progressivo;
    }

    public void setProgressivo(Integer progressivo) {
        this.progressivo = progressivo;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentComponentMultipayee paymentComponentMultipayee = (PaymentComponentMultipayee) o;
        return Objects.equals(this.codiceFiscaleEnte, paymentComponentMultipayee.codiceFiscaleEnte) &&
                Objects.equals(this.codiceVersamento, paymentComponentMultipayee.codiceVersamento) &&
                Objects.equals(this.annoAccertamento, paymentComponentMultipayee.annoAccertamento) &&
                Objects.equals(this.causale, paymentComponentMultipayee.causale) &&
                Objects.equals(this.datiSpecificiRiscossione, paymentComponentMultipayee.datiSpecificiRiscossione) &&
                Objects.equals(this.importo, paymentComponentMultipayee.importo) &&
                Objects.equals(this.numeroAccertamento, paymentComponentMultipayee.numeroAccertamento) &&
                Objects.equals(this.progressivo, paymentComponentMultipayee.progressivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceFiscaleEnte, codiceVersamento, annoAccertamento, causale, datiSpecificiRiscossione, importo, numeroAccertamento, progressivo);
    }


    @Override
    public String toString() {
        return "class PaymentComponentMultipayee {\n" +
                "    codiceFiscaleEnte: " + toIndentedString(codiceFiscaleEnte) + "\n" +
                "    codiceVersamento: " + toIndentedString(codiceVersamento) + "\n" +
                "    annoAccertamento: " + toIndentedString(annoAccertamento) + "\n" +
                "    causale: " + toIndentedString(causale) + "\n" +
                "    datiSpecificiRiscossione: " + toIndentedString(datiSpecificiRiscossione) + "\n" +
                "    importo: " + toIndentedString(importo) + "\n" +
                "    numeroAccertamento: " + toIndentedString(numeroAccertamento) + "\n" +
                "    progressivo: " + toIndentedString(progressivo) + "\n" +
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
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * PaymentStampTaxReference
 */
public class PaymentStampTaxReference {
    private String codiceEsito = null;

    private String descrizioneEsito = null;

    private List<String> elencoIuvMarcaBollo = null;

    private String iuvDocumento = null;

    private String paymentUrl = null;

    public PaymentStampTaxReference codiceEsito(String codiceEsito) {
        this.codiceEsito = codiceEsito;
        return this;
    }

    /**
     * Get codiceEsito
     *
     * @return codiceEsito
     **/
    public String getCodiceEsito() {
        return codiceEsito;
    }

    public void setCodiceEsito(String codiceEsito) {
        this.codiceEsito = codiceEsito;
    }

    public PaymentStampTaxReference descrizioneEsito(String descrizioneEsito) {
        this.descrizioneEsito = descrizioneEsito;
        return this;
    }

    /**
     * Get descrizioneEsito
     *
     * @return descrizioneEsito
     **/
    public String getDescrizioneEsito() {
        return descrizioneEsito;
    }

    public void setDescrizioneEsito(String descrizioneEsito) {
        this.descrizioneEsito = descrizioneEsito;
    }

    public PaymentStampTaxReference elencoIuvMarcaBollo(List<String> elencoIuvMarcaBollo) {
        this.elencoIuvMarcaBollo = elencoIuvMarcaBollo;
        return this;
    }

    public PaymentStampTaxReference addElencoIuvMarcaBolloItem(String elencoIuvMarcaBolloItem) {
        if (this.elencoIuvMarcaBollo == null) {
            this.elencoIuvMarcaBollo = new ArrayList<String>();
        }
        this.elencoIuvMarcaBollo.add(elencoIuvMarcaBolloItem);
        return this;
    }

    /**
     * Get elencoIuvMarcaBollo
     *
     * @return elencoIuvMarcaBollo
     **/
    public List<String> getElencoIuvMarcaBollo() {
        return elencoIuvMarcaBollo;
    }

    public void setElencoIuvMarcaBollo(List<String> elencoIuvMarcaBollo) {
        this.elencoIuvMarcaBollo = elencoIuvMarcaBollo;
    }

    public PaymentStampTaxReference iuvDocumento(String iuvDocumento) {
        this.iuvDocumento = iuvDocumento;
        return this;
    }

    /**
     * Get iuvDocumento
     *
     * @return iuvDocumento
     **/
    public String getIuvDocumento() {
        return iuvDocumento;
    }

    public void setIuvDocumento(String iuvDocumento) {
        this.iuvDocumento = iuvDocumento;
    }

    public PaymentStampTaxReference paymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
        return this;
    }

    /**
     * Get paymentUrl
     *
     * @return paymentUrl
     **/
    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentStampTaxReference paymentStampTaxReference = (PaymentStampTaxReference) o;
        return Objects.equals(this.codiceEsito, paymentStampTaxReference.codiceEsito) &&
                Objects.equals(this.descrizioneEsito, paymentStampTaxReference.descrizioneEsito) &&
                Objects.equals(this.elencoIuvMarcaBollo, paymentStampTaxReference.elencoIuvMarcaBollo) &&
                Objects.equals(this.iuvDocumento, paymentStampTaxReference.iuvDocumento) &&
                Objects.equals(this.paymentUrl, paymentStampTaxReference.paymentUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceEsito, descrizioneEsito, elencoIuvMarcaBollo, iuvDocumento, paymentUrl);
    }


    @Override
    public String toString() {
        return "class PaymentStampTaxReference {\n" +
                "    codiceEsito: " + toIndentedString(codiceEsito) + "\n" +
                "    descrizioneEsito: " + toIndentedString(descrizioneEsito) + "\n" +
                "    elencoIuvMarcaBollo: " + toIndentedString(elencoIuvMarcaBollo) + "\n" +
                "    iuvDocumento: " + toIndentedString(iuvDocumento) + "\n" +
                "    paymentUrl: " + toIndentedString(paymentUrl) + "\n" +
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
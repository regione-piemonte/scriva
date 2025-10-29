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

import java.util.Objects;

/**
 * PaymentReferences
 */
public class PaymentReferences {
    private String codiceEsito = null;

    private String descrizioneEsito = null;

    private String iuv = null;

    private String paymentUrl = null;

    public PaymentReferences codiceEsito(String codiceEsito) {
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

    public PaymentReferences descrizioneEsito(String descrizioneEsito) {
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

    public PaymentReferences iuv(String iuv) {
        this.iuv = iuv;
        return this;
    }

    /**
     * Get iuv
     *
     * @return iuv
     **/
    public String getIuv() {
        return iuv;
    }

    public void setIuv(String iuv) {
        this.iuv = iuv;
    }

    public PaymentReferences paymentUrl(String paymentUrl) {
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
        PaymentReferences paymentReferences = (PaymentReferences) o;
        return Objects.equals(this.codiceEsito, paymentReferences.codiceEsito) &&
                Objects.equals(this.descrizioneEsito, paymentReferences.descrizioneEsito) &&
                Objects.equals(this.iuv, paymentReferences.iuv) &&
                Objects.equals(this.paymentUrl, paymentReferences.paymentUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceEsito, descrizioneEsito, iuv, paymentUrl);
    }


    @Override
    public String toString() {
        return "class PaymentReferences {\n" +
                "    codiceEsito: " + toIndentedString(codiceEsito) + "\n" +
                "    descrizioneEsito: " + toIndentedString(descrizioneEsito) + "\n" +
                "    iuv: " + toIndentedString(iuv) + "\n" +
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
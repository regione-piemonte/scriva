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
 * PaymentMultipayeeReferences
 */
public class PaymentMultipayeeReferences {
    private String codiceAvviso = null;

    private String codiceEsito = null;

    private String descrizioneEsito = null;

    private String identificativoPagamento = null;

    private String iuv = null;

    public PaymentMultipayeeReferences codiceAvviso(String codiceAvviso) {
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

    public PaymentMultipayeeReferences codiceEsito(String codiceEsito) {
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

    public PaymentMultipayeeReferences descrizioneEsito(String descrizioneEsito) {
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

    public PaymentMultipayeeReferences identificativoPagamento(String identificativoPagamento) {
        this.identificativoPagamento = identificativoPagamento;
        return this;
    }

    /**
     * Get identificativoPagamento
     *
     * @return identificativoPagamento
     **/
    public String getIdentificativoPagamento() {
        return identificativoPagamento;
    }

    public void setIdentificativoPagamento(String identificativoPagamento) {
        this.identificativoPagamento = identificativoPagamento;
    }

    public PaymentMultipayeeReferences iuv(String iuv) {
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


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentMultipayeeReferences paymentMultipayeeReferences = (PaymentMultipayeeReferences) o;
        return Objects.equals(this.codiceAvviso, paymentMultipayeeReferences.codiceAvviso) &&
                Objects.equals(this.codiceEsito, paymentMultipayeeReferences.codiceEsito) &&
                Objects.equals(this.descrizioneEsito, paymentMultipayeeReferences.descrizioneEsito) &&
                Objects.equals(this.identificativoPagamento, paymentMultipayeeReferences.identificativoPagamento) &&
                Objects.equals(this.iuv, paymentMultipayeeReferences.iuv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceAvviso, codiceEsito, descrizioneEsito, identificativoPagamento, iuv);
    }


    @Override
    public String toString() {
        return "class PaymentMultipayeeReferences {\n" +
                "    codiceAvviso: " + toIndentedString(codiceAvviso) + "\n" +
                "    codiceEsito: " + toIndentedString(codiceEsito) + "\n" +
                "    descrizioneEsito: " + toIndentedString(descrizioneEsito) + "\n" +
                "    identificativoPagamento: " + toIndentedString(identificativoPagamento) + "\n" +
                "    iuv: " + toIndentedString(iuv) + "\n" +
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
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

import java.util.Arrays;
import java.util.Objects;

/**
 * PaymentNotice
 */
public class PaymentNotice {
    private byte[] paymentnotice = null;

    private OneOfPaymentNoticeResult result = null;

    public PaymentNotice paymentnotice(byte[] paymentnotice) {
        this.paymentnotice = paymentnotice;
        return this;
    }

    /**
     * Return pdf encoded base64 of payment notice
     *
     * @return paymentnotice
     **/
    public byte[] getPaymentnotice() {
        return paymentnotice;
    }

    public void setPaymentnotice(byte[] paymentnotice) {
        this.paymentnotice = paymentnotice;
    }

    public PaymentNotice result(OneOfPaymentNoticeResult result) {
        this.result = result;
        return this;
    }

    /**
     * Get result
     *
     * @return result
     **/
    public OneOfPaymentNoticeResult getResult() {
        return result;
    }

    public void setResult(OneOfPaymentNoticeResult result) {
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
        PaymentNotice paymentNotice = (PaymentNotice) o;
        return Arrays.equals(this.paymentnotice, paymentNotice.paymentnotice) &&
                Objects.equals(this.result, paymentNotice.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(paymentnotice), result);
    }


    @Override
    public String toString() {
        return "class PaymentNotice {\n" +
                "    paymentnotice: " + toIndentedString(paymentnotice) + "\n" +
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
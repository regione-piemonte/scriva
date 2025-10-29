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
 * PaymentDataMultipayeeResult
 */
public class PaymentDataMultipayeeResult implements OneOfPaymentDataResultDatiMultibeneficiario {
  private String enteBeneficiario = null;

  private String codiceFiscaleEnteBeneficiario = null;

  private String tipologiaVersamento = null;

  private BigDecimal importoPagato = null;

  public PaymentDataMultipayeeResult enteBeneficiario(String enteBeneficiario) {
    this.enteBeneficiario = enteBeneficiario;
    return this;
  }

   /**
   * Get enteBeneficiario
   * @return enteBeneficiario
  **/
  public String getEnteBeneficiario() {
    return enteBeneficiario;
  }

  public void setEnteBeneficiario(String enteBeneficiario) {
    this.enteBeneficiario = enteBeneficiario;
  }

  public PaymentDataMultipayeeResult codiceFiscaleEnteBeneficiario(String codiceFiscaleEnteBeneficiario) {
    this.codiceFiscaleEnteBeneficiario = codiceFiscaleEnteBeneficiario;
    return this;
  }

   /**
   * Get codiceFiscaleEnteBeneficiario
   * @return codiceFiscaleEnteBeneficiario
  **/
  public String getCodiceFiscaleEnteBeneficiario() {
    return codiceFiscaleEnteBeneficiario;
  }

  public void setCodiceFiscaleEnteBeneficiario(String codiceFiscaleEnteBeneficiario) {
    this.codiceFiscaleEnteBeneficiario = codiceFiscaleEnteBeneficiario;
  }

  public PaymentDataMultipayeeResult tipologiaVersamento(String tipologiaVersamento) {
    this.tipologiaVersamento = tipologiaVersamento;
    return this;
  }

   /**
   * Get tipologiaVersamento
   * @return tipologiaVersamento
  **/
  public String getTipologiaVersamento() {
    return tipologiaVersamento;
  }

  public void setTipologiaVersamento(String tipologiaVersamento) {
    this.tipologiaVersamento = tipologiaVersamento;
  }

  public PaymentDataMultipayeeResult importoPagato(BigDecimal importoPagato) {
    this.importoPagato = importoPagato;
    return this;
  }

   /**
   * Get importoPagato
   * @return importoPagato
  **/
  public BigDecimal getImportoPagato() {
    return importoPagato;
  }

  public void setImportoPagato(BigDecimal importoPagato) {
    this.importoPagato = importoPagato;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentDataMultipayeeResult paymentDataMultipayeeResult = (PaymentDataMultipayeeResult) o;
    return Objects.equals(this.enteBeneficiario, paymentDataMultipayeeResult.enteBeneficiario) &&
        Objects.equals(this.codiceFiscaleEnteBeneficiario, paymentDataMultipayeeResult.codiceFiscaleEnteBeneficiario) &&
        Objects.equals(this.tipologiaVersamento, paymentDataMultipayeeResult.tipologiaVersamento) &&
        Objects.equals(this.importoPagato, paymentDataMultipayeeResult.importoPagato);
  }

  @Override
  public int hashCode() {
    return Objects.hash(enteBeneficiario, codiceFiscaleEnteBeneficiario, tipologiaVersamento, importoPagato);
  }


  @Override
  public String toString() {
    return "class PaymentDataMultipayeeResult {\n" +
            "    enteBeneficiario: " + toIndentedString(enteBeneficiario) + "\n" +
            "    codiceFiscaleEnteBeneficiario: " + toIndentedString(codiceFiscaleEnteBeneficiario) + "\n" +
            "    tipologiaVersamento: " + toIndentedString(tipologiaVersamento) + "\n" +
            "    importoPagato: " + toIndentedString(importoPagato) + "\n" +
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
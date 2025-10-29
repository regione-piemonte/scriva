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
 * DebtPositionPaymentDataComponentiImporto
 */
public class DebtPositionPaymentDataComponentiImporto {
  private BigDecimal importo = null;

  private String causaleDescrittiva = null;

  private BigDecimal annoAccertamento = null;

  private String numeroAccertamento = null;

  public DebtPositionPaymentDataComponentiImporto importo(BigDecimal importo) {
    this.importo = importo;
    return this;
  }

   /**
   * Get importo
   * @return importo
  **/
  public BigDecimal getImporto() {
    return importo;
  }

  public void setImporto(BigDecimal importo) {
    this.importo = importo;
  }

  public DebtPositionPaymentDataComponentiImporto causaleDescrittiva(String causaleDescrittiva) {
    this.causaleDescrittiva = causaleDescrittiva;
    return this;
  }

   /**
   * Get causaleDescrittiva
   * @return causaleDescrittiva
  **/
  public String getCausaleDescrittiva() {
    return causaleDescrittiva;
  }

  public void setCausaleDescrittiva(String causaleDescrittiva) {
    this.causaleDescrittiva = causaleDescrittiva;
  }

  public DebtPositionPaymentDataComponentiImporto annoAccertamento(BigDecimal annoAccertamento) {
    this.annoAccertamento = annoAccertamento;
    return this;
  }

   /**
   * Get annoAccertamento
   * @return annoAccertamento
  **/
  public BigDecimal getAnnoAccertamento() {
    return annoAccertamento;
  }

  public void setAnnoAccertamento(BigDecimal annoAccertamento) {
    this.annoAccertamento = annoAccertamento;
  }

  public DebtPositionPaymentDataComponentiImporto numeroAccertamento(String numeroAccertamento) {
    this.numeroAccertamento = numeroAccertamento;
    return this;
  }

   /**
   * Get numeroAccertamento
   * @return numeroAccertamento
  **/
  public String getNumeroAccertamento() {
    return numeroAccertamento;
  }

  public void setNumeroAccertamento(String numeroAccertamento) {
    this.numeroAccertamento = numeroAccertamento;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DebtPositionPaymentDataComponentiImporto debtPositionPaymentDataComponentiImporto = (DebtPositionPaymentDataComponentiImporto) o;
    return Objects.equals(this.importo, debtPositionPaymentDataComponentiImporto.importo) &&
        Objects.equals(this.causaleDescrittiva, debtPositionPaymentDataComponentiImporto.causaleDescrittiva) &&
        Objects.equals(this.annoAccertamento, debtPositionPaymentDataComponentiImporto.annoAccertamento) &&
        Objects.equals(this.numeroAccertamento, debtPositionPaymentDataComponentiImporto.numeroAccertamento);
  }

  @Override
  public int hashCode() {
    return Objects.hash(importo, causaleDescrittiva, annoAccertamento, numeroAccertamento);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DebtPositionPaymentDataComponentiImporto {\n");

    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    causaleDescrittiva: ").append(toIndentedString(causaleDescrittiva)).append("\n");
    sb.append("    annoAccertamento: ").append(toIndentedString(annoAccertamento)).append("\n");
    sb.append("    numeroAccertamento: ").append(toIndentedString(numeroAccertamento)).append("\n");
    sb.append("}");
    return sb.toString();
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
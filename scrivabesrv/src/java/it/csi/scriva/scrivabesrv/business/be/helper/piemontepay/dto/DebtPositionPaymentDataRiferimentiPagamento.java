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
 * DebtPositionPaymentDataRiferimentiPagamento
 */
public class DebtPositionPaymentDataRiferimentiPagamento {
  private String nome = null;

  private String valore = null;

  public DebtPositionPaymentDataRiferimentiPagamento nome(String nome) {
    this.nome = nome;
    return this;
  }

   /**
   * Get nome
   * @return nome
  **/
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public DebtPositionPaymentDataRiferimentiPagamento valore(String valore) {
    this.valore = valore;
    return this;
  }

   /**
   * Get valore
   * @return valore
  **/
  public String getValore() {
    return valore;
  }

  public void setValore(String valore) {
    this.valore = valore;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DebtPositionPaymentDataRiferimentiPagamento debtPositionPaymentDataRiferimentiPagamento = (DebtPositionPaymentDataRiferimentiPagamento) o;
    return Objects.equals(this.nome, debtPositionPaymentDataRiferimentiPagamento.nome) &&
        Objects.equals(this.valore, debtPositionPaymentDataRiferimentiPagamento.valore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, valore);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DebtPositionPaymentDataRiferimentiPagamento {\n");

    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    valore: ").append(toIndentedString(valore)).append("\n");
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
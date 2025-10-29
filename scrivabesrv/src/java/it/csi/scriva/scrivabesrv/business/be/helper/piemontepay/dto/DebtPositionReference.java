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
 * DebtPositionReference
 */
public class DebtPositionReference {
  private String codiceAvviso = null;

  private String codiceEsito = null;

  private String descrizioneEsito = null;

  private String identificativoPagamento = null;

  private String iuv = null;

  public DebtPositionReference codiceAvviso(String codiceAvviso) {
    this.codiceAvviso = codiceAvviso;
    return this;
  }

   /**
   * Get codiceAvviso
   * @return codiceAvviso
  **/
  public String getCodiceAvviso() {
    return codiceAvviso;
  }

  public void setCodiceAvviso(String codiceAvviso) {
    this.codiceAvviso = codiceAvviso;
  }

  public DebtPositionReference codiceEsito(String codiceEsito) {
    this.codiceEsito = codiceEsito;
    return this;
  }

   /**
   * Get codiceEsito
   * @return codiceEsito
  **/
  public String getCodiceEsito() {
    return codiceEsito;
  }

  public void setCodiceEsito(String codiceEsito) {
    this.codiceEsito = codiceEsito;
  }

  public DebtPositionReference descrizioneEsito(String descrizioneEsito) {
    this.descrizioneEsito = descrizioneEsito;
    return this;
  }

   /**
   * Get descrizioneEsito
   * @return descrizioneEsito
  **/
  public String getDescrizioneEsito() {
    return descrizioneEsito;
  }

  public void setDescrizioneEsito(String descrizioneEsito) {
    this.descrizioneEsito = descrizioneEsito;
  }

  public DebtPositionReference identificativoPagamento(String identificativoPagamento) {
    this.identificativoPagamento = identificativoPagamento;
    return this;
  }

   /**
   * Get identificativoPagamento
   * @return identificativoPagamento
  **/
  public String getIdentificativoPagamento() {
    return identificativoPagamento;
  }

  public void setIdentificativoPagamento(String identificativoPagamento) {
    this.identificativoPagamento = identificativoPagamento;
  }

  public DebtPositionReference iuv(String iuv) {
    this.iuv = iuv;
    return this;
  }

   /**
   * Get iuv
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
    DebtPositionReference debtPositionReference = (DebtPositionReference) o;
    return Objects.equals(this.codiceAvviso, debtPositionReference.codiceAvviso) &&
        Objects.equals(this.codiceEsito, debtPositionReference.codiceEsito) &&
        Objects.equals(this.descrizioneEsito, debtPositionReference.descrizioneEsito) &&
        Objects.equals(this.identificativoPagamento, debtPositionReference.identificativoPagamento) &&
        Objects.equals(this.iuv, debtPositionReference.iuv);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceAvviso, codiceEsito, descrizioneEsito, identificativoPagamento, iuv);
  }


  @Override
  public String toString() {
    return "class DebtPositionReference {\n" +
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
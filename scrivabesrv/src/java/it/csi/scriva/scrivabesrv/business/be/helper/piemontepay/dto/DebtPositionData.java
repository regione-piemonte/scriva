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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DebtPositionData
 */
public class DebtPositionData {
  private String causale = null;

  private String codiceFiscalePartitaIVAPagatore = null;

  private String cognome = null;

  private List<PaymentComponent> componentiPagamento = null;

  private OffsetDateTime dataFineValidita = null;

  private OffsetDateTime dataInizioValidita = null;

  private OffsetDateTime dataScadenza = null;

  private String email = null;

  private String identificativoPagamento = null;

  private BigDecimal importo = null;

  private String nome = null;

  private String notePerIlPagatore = null;

  private String ragioneSociale = null;

  private Boolean requiresCostUpdate = false;

  public DebtPositionData causale(String causale) {
    this.causale = causale;
    return this;
  }

   /**
   * Get causale
   * @return causale
  **/
  public String getCausale() {
    return causale;
  }

  public void setCausale(String causale) {
    this.causale = causale;
  }

  public DebtPositionData codiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {
    this.codiceFiscalePartitaIVAPagatore = codiceFiscalePartitaIVAPagatore;
    return this;
  }

   /**
   * Get codiceFiscalePartitaIVAPagatore
   * @return codiceFiscalePartitaIVAPagatore
  **/
  public String getCodiceFiscalePartitaIVAPagatore() {
    return codiceFiscalePartitaIVAPagatore;
  }

  public void setCodiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {
    this.codiceFiscalePartitaIVAPagatore = codiceFiscalePartitaIVAPagatore;
  }

  public DebtPositionData cognome(String cognome) {
    this.cognome = cognome;
    return this;
  }

   /**
   * Get cognome
   * @return cognome
  **/
  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public DebtPositionData componentiPagamento(List<PaymentComponent> componentiPagamento) {
    this.componentiPagamento = componentiPagamento;
    return this;
  }

  public DebtPositionData addComponentiPagamentoItem(PaymentComponent componentiPagamentoItem) {
    if (this.componentiPagamento == null) {
      this.componentiPagamento = new ArrayList<PaymentComponent>();
    }
    this.componentiPagamento.add(componentiPagamentoItem);
    return this;
  }

   /**
   * Get componentiPagamento
   * @return componentiPagamento
  **/
  public List<PaymentComponent> getComponentiPagamento() {
    return componentiPagamento;
  }

  public void setComponentiPagamento(List<PaymentComponent> componentiPagamento) {
    this.componentiPagamento = componentiPagamento;
  }

  public DebtPositionData dataFineValidita(OffsetDateTime dataFineValidita) {
    this.dataFineValidita = dataFineValidita;
    return this;
  }

   /**
   * Get dataFineValidita
   * @return dataFineValidita
  **/
  public OffsetDateTime getDataFineValidita() {
    return dataFineValidita;
  }

  public void setDataFineValidita(OffsetDateTime dataFineValidita) {
    this.dataFineValidita = dataFineValidita;
  }

  public DebtPositionData dataInizioValidita(OffsetDateTime dataInizioValidita) {
    this.dataInizioValidita = dataInizioValidita;
    return this;
  }

   /**
   * Get dataInizioValidita
   * @return dataInizioValidita
  **/
  public OffsetDateTime getDataInizioValidita() {
    return dataInizioValidita;
  }

  public void setDataInizioValidita(OffsetDateTime dataInizioValidita) {
    this.dataInizioValidita = dataInizioValidita;
  }

  public DebtPositionData dataScadenza(OffsetDateTime dataScadenza) {
    this.dataScadenza = dataScadenza;
    return this;
  }

   /**
   * Get dataScadenza
   * @return dataScadenza
  **/
  public OffsetDateTime getDataScadenza() {
    return dataScadenza;
  }

  public void setDataScadenza(OffsetDateTime dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  public DebtPositionData email(String email) {
    this.email = email;
    return this;
  }

   /**
   * Get email
   * @return email
  **/
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public DebtPositionData identificativoPagamento(String identificativoPagamento) {
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

  public DebtPositionData importo(BigDecimal importo) {
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

  public DebtPositionData nome(String nome) {
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

  public DebtPositionData notePerIlPagatore(String notePerIlPagatore) {
    this.notePerIlPagatore = notePerIlPagatore;
    return this;
  }

   /**
   * Get notePerIlPagatore
   * @return notePerIlPagatore
  **/
  public String getNotePerIlPagatore() {
    return notePerIlPagatore;
  }

  public void setNotePerIlPagatore(String notePerIlPagatore) {
    this.notePerIlPagatore = notePerIlPagatore;
  }

  public DebtPositionData ragioneSociale(String ragioneSociale) {
    this.ragioneSociale = ragioneSociale;
    return this;
  }

   /**
   * Get ragioneSociale
   * @return ragioneSociale
  **/
  public String getRagioneSociale() {
    return ragioneSociale;
  }

  public void setRagioneSociale(String ragioneSociale) {
    this.ragioneSociale = ragioneSociale;
  }

  public DebtPositionData requiresCostUpdate(Boolean requiresCostUpdate) {
    this.requiresCostUpdate = requiresCostUpdate;
    return this;
  }

   /**
   * Get requiresCostUpdate
   * @return requiresCostUpdate
  **/
  public Boolean isRequiresCostUpdate() {
    return requiresCostUpdate;
  }

  public void setRequiresCostUpdate(Boolean requiresCostUpdate) {
    this.requiresCostUpdate = requiresCostUpdate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DebtPositionData debtPositionData = (DebtPositionData) o;
    return Objects.equals(this.causale, debtPositionData.causale) &&
        Objects.equals(this.codiceFiscalePartitaIVAPagatore, debtPositionData.codiceFiscalePartitaIVAPagatore) &&
        Objects.equals(this.cognome, debtPositionData.cognome) &&
        Objects.equals(this.componentiPagamento, debtPositionData.componentiPagamento) &&
        Objects.equals(this.dataFineValidita, debtPositionData.dataFineValidita) &&
        Objects.equals(this.dataInizioValidita, debtPositionData.dataInizioValidita) &&
        Objects.equals(this.dataScadenza, debtPositionData.dataScadenza) &&
        Objects.equals(this.email, debtPositionData.email) &&
        Objects.equals(this.identificativoPagamento, debtPositionData.identificativoPagamento) &&
        Objects.equals(this.importo, debtPositionData.importo) &&
        Objects.equals(this.nome, debtPositionData.nome) &&
        Objects.equals(this.notePerIlPagatore, debtPositionData.notePerIlPagatore) &&
        Objects.equals(this.ragioneSociale, debtPositionData.ragioneSociale) &&
        Objects.equals(this.requiresCostUpdate, debtPositionData.requiresCostUpdate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(causale, codiceFiscalePartitaIVAPagatore, cognome, componentiPagamento, dataFineValidita, dataInizioValidita, dataScadenza, email, identificativoPagamento, importo, nome, notePerIlPagatore, ragioneSociale, requiresCostUpdate);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DebtPositionData {\n");

    sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
    sb.append("    codiceFiscalePartitaIVAPagatore: ").append(toIndentedString(codiceFiscalePartitaIVAPagatore)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    componentiPagamento: ").append(toIndentedString(componentiPagamento)).append("\n");
    sb.append("    dataFineValidita: ").append(toIndentedString(dataFineValidita)).append("\n");
    sb.append("    dataInizioValidita: ").append(toIndentedString(dataInizioValidita)).append("\n");
    sb.append("    dataScadenza: ").append(toIndentedString(dataScadenza)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    identificativoPagamento: ").append(toIndentedString(identificativoPagamento)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    notePerIlPagatore: ").append(toIndentedString(notePerIlPagatore)).append("\n");
    sb.append("    ragioneSociale: ").append(toIndentedString(ragioneSociale)).append("\n");
    sb.append("    requiresCostUpdate: ").append(toIndentedString(requiresCostUpdate)).append("\n");
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
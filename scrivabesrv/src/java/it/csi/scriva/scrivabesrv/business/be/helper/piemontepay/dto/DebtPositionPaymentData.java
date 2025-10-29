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
 * DebtPositionPaymentData
 */
public class DebtPositionPaymentData {
  private OffsetDateTime dataScadenza = null;

  private OffsetDateTime dataInizioValidita = null;

  private OffsetDateTime dataFineValidita = null;

  private BigDecimal importo = null;

  private List<DebtPositionPaymentDataComponentiImporto> componentiImporto = null;

  private String causale = null;

  private String motivazione = null;

  private List<DebtPositionPaymentDataRiferimentiPagamento> riferimentiPagamento = null;

  private String nome = null;

  private String cognome = null;

  private String ragioneSociale = null;

  private String email = null;

  private String codiceFiscalePartitaIVAPagatore = null;

  private String identificativoPagamento = null;

  private Boolean requiresCostUpdate = false;

  public DebtPositionPaymentData dataScadenza(OffsetDateTime dataScadenza) {
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

  public DebtPositionPaymentData dataInizioValidita(OffsetDateTime dataInizioValidita) {
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

  public DebtPositionPaymentData dataFineValidita(OffsetDateTime dataFineValidita) {
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

  public DebtPositionPaymentData importo(BigDecimal importo) {
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

  public DebtPositionPaymentData componentiImporto(List<DebtPositionPaymentDataComponentiImporto> componentiImporto) {
    this.componentiImporto = componentiImporto;
    return this;
  }

  public DebtPositionPaymentData addComponentiImportoItem(DebtPositionPaymentDataComponentiImporto componentiImportoItem) {
    if (this.componentiImporto == null) {
      this.componentiImporto = new ArrayList<DebtPositionPaymentDataComponentiImporto>();
    }
    this.componentiImporto.add(componentiImportoItem);
    return this;
  }

   /**
   * Get componentiImporto
   * @return componentiImporto
  **/
  public List<DebtPositionPaymentDataComponentiImporto> getComponentiImporto() {
    return componentiImporto;
  }

  public void setComponentiImporto(List<DebtPositionPaymentDataComponentiImporto> componentiImporto) {
    this.componentiImporto = componentiImporto;
  }

  public DebtPositionPaymentData causale(String causale) {
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

  public DebtPositionPaymentData motivazione(String motivazione) {
    this.motivazione = motivazione;
    return this;
  }

   /**
   * Get motivazione
   * @return motivazione
  **/
  public String getMotivazione() {
    return motivazione;
  }

  public void setMotivazione(String motivazione) {
    this.motivazione = motivazione;
  }

  public DebtPositionPaymentData riferimentiPagamento(List<DebtPositionPaymentDataRiferimentiPagamento> riferimentiPagamento) {
    this.riferimentiPagamento = riferimentiPagamento;
    return this;
  }

  public DebtPositionPaymentData addRiferimentiPagamentoItem(DebtPositionPaymentDataRiferimentiPagamento riferimentiPagamentoItem) {
    if (this.riferimentiPagamento == null) {
      this.riferimentiPagamento = new ArrayList<DebtPositionPaymentDataRiferimentiPagamento>();
    }
    this.riferimentiPagamento.add(riferimentiPagamentoItem);
    return this;
  }

   /**
   * Get riferimentiPagamento
   * @return riferimentiPagamento
  **/
  public List<DebtPositionPaymentDataRiferimentiPagamento> getRiferimentiPagamento() {
    return riferimentiPagamento;
  }

  public void setRiferimentiPagamento(List<DebtPositionPaymentDataRiferimentiPagamento> riferimentiPagamento) {
    this.riferimentiPagamento = riferimentiPagamento;
  }

  public DebtPositionPaymentData nome(String nome) {
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

  public DebtPositionPaymentData cognome(String cognome) {
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

  public DebtPositionPaymentData ragioneSociale(String ragioneSociale) {
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

  public DebtPositionPaymentData email(String email) {
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

  public DebtPositionPaymentData codiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {
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

  public DebtPositionPaymentData identificativoPagamento(String identificativoPagamento) {
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

  public DebtPositionPaymentData requiresCostUpdate(Boolean requiresCostUpdate) {
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
    DebtPositionPaymentData debtPositionPaymentData = (DebtPositionPaymentData) o;
    return Objects.equals(this.dataScadenza, debtPositionPaymentData.dataScadenza) &&
        Objects.equals(this.dataInizioValidita, debtPositionPaymentData.dataInizioValidita) &&
        Objects.equals(this.dataFineValidita, debtPositionPaymentData.dataFineValidita) &&
        Objects.equals(this.importo, debtPositionPaymentData.importo) &&
        Objects.equals(this.componentiImporto, debtPositionPaymentData.componentiImporto) &&
        Objects.equals(this.causale, debtPositionPaymentData.causale) &&
        Objects.equals(this.motivazione, debtPositionPaymentData.motivazione) &&
        Objects.equals(this.riferimentiPagamento, debtPositionPaymentData.riferimentiPagamento) &&
        Objects.equals(this.nome, debtPositionPaymentData.nome) &&
        Objects.equals(this.cognome, debtPositionPaymentData.cognome) &&
        Objects.equals(this.ragioneSociale, debtPositionPaymentData.ragioneSociale) &&
        Objects.equals(this.email, debtPositionPaymentData.email) &&
        Objects.equals(this.codiceFiscalePartitaIVAPagatore, debtPositionPaymentData.codiceFiscalePartitaIVAPagatore) &&
        Objects.equals(this.identificativoPagamento, debtPositionPaymentData.identificativoPagamento) &&
        Objects.equals(this.requiresCostUpdate, debtPositionPaymentData.requiresCostUpdate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataScadenza, dataInizioValidita, dataFineValidita, importo, componentiImporto, causale, motivazione, riferimentiPagamento, nome, cognome, ragioneSociale, email, codiceFiscalePartitaIVAPagatore, identificativoPagamento, requiresCostUpdate);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DebtPositionPaymentData {\n");

    sb.append("    dataScadenza: ").append(toIndentedString(dataScadenza)).append("\n");
    sb.append("    dataInizioValidita: ").append(toIndentedString(dataInizioValidita)).append("\n");
    sb.append("    dataFineValidita: ").append(toIndentedString(dataFineValidita)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    componentiImporto: ").append(toIndentedString(componentiImporto)).append("\n");
    sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
    sb.append("    motivazione: ").append(toIndentedString(motivazione)).append("\n");
    sb.append("    riferimentiPagamento: ").append(toIndentedString(riferimentiPagamento)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    ragioneSociale: ").append(toIndentedString(ragioneSociale)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    codiceFiscalePartitaIVAPagatore: ").append(toIndentedString(codiceFiscalePartitaIVAPagatore)).append("\n");
    sb.append("    identificativoPagamento: ").append(toIndentedString(identificativoPagamento)).append("\n");
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
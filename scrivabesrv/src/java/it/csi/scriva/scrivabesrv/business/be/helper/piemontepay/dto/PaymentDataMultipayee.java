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
 * PaymentDataMultipayee
 */
public class PaymentDataMultipayee {
    private String causale = null;

    private String codiceFiscalePartitaIVAPagatore = null;

    private String cognome = null;

    private List<PaymentComponent> componentiPagamentoEntePrimario = null;

    private List<PaymentComponentMultipayee> componentiPagamentoEntiSecondari = null;

    private OffsetDateTime dataFineValidita = null;

    private OffsetDateTime dataInizioValidita = null;

    private OffsetDateTime dataScadenza = null;

    private String email = null;

    private String identificativoPagamento = null;

    private BigDecimal importoTotaleEntePrimario = null;

    private BigDecimal importoTotaleEntiSecondari = null;

    private BigDecimal importoTotale = null;

    private String nome = null;

    private String notePerIlPagatore = null;

    private String ragioneSociale = null;

    private Boolean requiresCostUpdate = false;

    public PaymentDataMultipayee causale(String causale) {
        this.causale = causale;
        return this;
    }

    /**
     * Get causale
     *
     * @return causale
     **/
    public String getCausale() {
        return causale;
    }

    public void setCausale(String causale) {
        this.causale = causale;
    }

    public PaymentDataMultipayee codiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {
        this.codiceFiscalePartitaIVAPagatore = codiceFiscalePartitaIVAPagatore;
        return this;
    }

    /**
     * Get codiceFiscalePartitaIVAPagatore
     *
     * @return codiceFiscalePartitaIVAPagatore
     **/
    public String getCodiceFiscalePartitaIVAPagatore() {
        return codiceFiscalePartitaIVAPagatore;
    }

    public void setCodiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {
        this.codiceFiscalePartitaIVAPagatore = codiceFiscalePartitaIVAPagatore;
    }

    public PaymentDataMultipayee cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    /**
     * Get cognome
     *
     * @return cognome
     **/
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public PaymentDataMultipayee componentiPagamentoEntePrimario(List<PaymentComponent> componentiPagamentoEntePrimario) {
        this.componentiPagamentoEntePrimario = componentiPagamentoEntePrimario;
        return this;
    }

    public PaymentDataMultipayee addComponentiPagamentoEntePrimarioItem(PaymentComponent componentiPagamentoEntePrimarioItem) {
        if (this.componentiPagamentoEntePrimario == null) {
            this.componentiPagamentoEntePrimario = new ArrayList<PaymentComponent>();
        }
        this.componentiPagamentoEntePrimario.add(componentiPagamentoEntePrimarioItem);
        return this;
    }

    /**
     * Get componentiPagamentoEntePrimario
     *
     * @return componentiPagamentoEntePrimario
     **/
    public List<PaymentComponent> getComponentiPagamentoEntePrimario() {
        return componentiPagamentoEntePrimario;
    }

    public void setComponentiPagamentoEntePrimario(List<PaymentComponent> componentiPagamentoEntePrimario) {
        this.componentiPagamentoEntePrimario = componentiPagamentoEntePrimario;
    }

    public PaymentDataMultipayee componentiPagamentoEntiSecondari(List<PaymentComponentMultipayee> componentiPagamentoEntiSecondari) {
        this.componentiPagamentoEntiSecondari = componentiPagamentoEntiSecondari;
        return this;
    }

    public PaymentDataMultipayee addComponentiPagamentoEntiSecondariItem(PaymentComponentMultipayee componentiPagamentoEntiSecondariItem) {
        if (this.componentiPagamentoEntiSecondari == null) {
            this.componentiPagamentoEntiSecondari = new ArrayList<PaymentComponentMultipayee>();
        }
        this.componentiPagamentoEntiSecondari.add(componentiPagamentoEntiSecondariItem);
        return this;
    }

    /**
     * Get componentiPagamentoEntiSecondari
     *
     * @return componentiPagamentoEntiSecondari
     **/
    public List<PaymentComponentMultipayee> getComponentiPagamentoEntiSecondari() {
        return componentiPagamentoEntiSecondari;
    }

    public void setComponentiPagamentoEntiSecondari(List<PaymentComponentMultipayee> componentiPagamentoEntiSecondari) {
        this.componentiPagamentoEntiSecondari = componentiPagamentoEntiSecondari;
    }

    public PaymentDataMultipayee dataFineValidita(OffsetDateTime dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
        return this;
    }

    /**
     * Get dataFineValidita
     *
     * @return dataFineValidita
     **/
    public OffsetDateTime getDataFineValidita() {
        return dataFineValidita;
    }

    public void setDataFineValidita(OffsetDateTime dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    public PaymentDataMultipayee dataInizioValidita(OffsetDateTime dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
        return this;
    }

    /**
     * Get dataInizioValidita
     *
     * @return dataInizioValidita
     **/
    public OffsetDateTime getDataInizioValidita() {
        return dataInizioValidita;
    }

    public void setDataInizioValidita(OffsetDateTime dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    public PaymentDataMultipayee dataScadenza(OffsetDateTime dataScadenza) {
        this.dataScadenza = dataScadenza;
        return this;
    }

    /**
     * Get dataScadenza
     *
     * @return dataScadenza
     **/
    public OffsetDateTime getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(OffsetDateTime dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public PaymentDataMultipayee email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get email
     *
     * @return email
     **/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PaymentDataMultipayee identificativoPagamento(String identificativoPagamento) {
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

    public PaymentDataMultipayee importoTotaleEntePrimario(BigDecimal importoTotaleEntePrimario) {
        this.importoTotaleEntePrimario = importoTotaleEntePrimario;
        return this;
    }

    /**
     * Get importoTotaleEntePrimario
     *
     * @return importoTotaleEntePrimario
     **/
    public BigDecimal getImportoTotaleEntePrimario() {
        return importoTotaleEntePrimario;
    }

    public void setImportoTotaleEntePrimario(BigDecimal importoTotaleEntePrimario) {
        this.importoTotaleEntePrimario = importoTotaleEntePrimario;
    }

    public PaymentDataMultipayee importoTotaleEntiSecondari(BigDecimal importoTotaleEntiSecondari) {
        this.importoTotaleEntiSecondari = importoTotaleEntiSecondari;
        return this;
    }

    /**
     * Get importoTotaleEntiSecondari
     *
     * @return importoTotaleEntiSecondari
     **/
    public BigDecimal getImportoTotaleEntiSecondari() {
        return importoTotaleEntiSecondari;
    }

    public void setImportoTotaleEntiSecondari(BigDecimal importoTotaleEntiSecondari) {
        this.importoTotaleEntiSecondari = importoTotaleEntiSecondari;
    }

    public PaymentDataMultipayee importoTotale(BigDecimal importoTotale) {
        this.importoTotale = importoTotale;
        return this;
    }

    /**
     * Get importoTotale
     *
     * @return importoTotale
     **/
    public BigDecimal getImportoTotale() {
        return importoTotale;
    }

    public void setImportoTotale(BigDecimal importoTotale) {
        this.importoTotale = importoTotale;
    }

    public PaymentDataMultipayee nome(String nome) {
        this.nome = nome;
        return this;
    }

    /**
     * Get nome
     *
     * @return nome
     **/
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PaymentDataMultipayee notePerIlPagatore(String notePerIlPagatore) {
        this.notePerIlPagatore = notePerIlPagatore;
        return this;
    }

    /**
     * Get notePerIlPagatore
     *
     * @return notePerIlPagatore
     **/
    public String getNotePerIlPagatore() {
        return notePerIlPagatore;
    }

    public void setNotePerIlPagatore(String notePerIlPagatore) {
        this.notePerIlPagatore = notePerIlPagatore;
    }

    public PaymentDataMultipayee ragioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
        return this;
    }

    /**
     * Get ragioneSociale
     *
     * @return ragioneSociale
     **/
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public PaymentDataMultipayee requiresCostUpdate(Boolean requiresCostUpdate) {
        this.requiresCostUpdate = requiresCostUpdate;
        return this;
    }

    /**
     * Get requiresCostUpdate
     *
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
        PaymentDataMultipayee paymentDataMultipayee = (PaymentDataMultipayee) o;
        return Objects.equals(this.causale, paymentDataMultipayee.causale) &&
                Objects.equals(this.codiceFiscalePartitaIVAPagatore, paymentDataMultipayee.codiceFiscalePartitaIVAPagatore) &&
                Objects.equals(this.cognome, paymentDataMultipayee.cognome) &&
                Objects.equals(this.componentiPagamentoEntePrimario, paymentDataMultipayee.componentiPagamentoEntePrimario) &&
                Objects.equals(this.componentiPagamentoEntiSecondari, paymentDataMultipayee.componentiPagamentoEntiSecondari) &&
                Objects.equals(this.dataFineValidita, paymentDataMultipayee.dataFineValidita) &&
                Objects.equals(this.dataInizioValidita, paymentDataMultipayee.dataInizioValidita) &&
                Objects.equals(this.dataScadenza, paymentDataMultipayee.dataScadenza) &&
                Objects.equals(this.email, paymentDataMultipayee.email) &&
                Objects.equals(this.identificativoPagamento, paymentDataMultipayee.identificativoPagamento) &&
                Objects.equals(this.importoTotaleEntePrimario, paymentDataMultipayee.importoTotaleEntePrimario) &&
                Objects.equals(this.importoTotaleEntiSecondari, paymentDataMultipayee.importoTotaleEntiSecondari) &&
                Objects.equals(this.importoTotale, paymentDataMultipayee.importoTotale) &&
                Objects.equals(this.nome, paymentDataMultipayee.nome) &&
                Objects.equals(this.notePerIlPagatore, paymentDataMultipayee.notePerIlPagatore) &&
                Objects.equals(this.ragioneSociale, paymentDataMultipayee.ragioneSociale) &&
                Objects.equals(this.requiresCostUpdate, paymentDataMultipayee.requiresCostUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(causale, codiceFiscalePartitaIVAPagatore, cognome, componentiPagamentoEntePrimario, componentiPagamentoEntiSecondari, dataFineValidita, dataInizioValidita, dataScadenza, email, identificativoPagamento, importoTotaleEntePrimario, importoTotaleEntiSecondari, importoTotale, nome, notePerIlPagatore, ragioneSociale, requiresCostUpdate);
    }


    @Override
    public String toString() {
        return "class PaymentDataMultipayee {\n" +
                "    causale: " + toIndentedString(causale) + "\n" +
                "    codiceFiscalePartitaIVAPagatore: " + toIndentedString(codiceFiscalePartitaIVAPagatore) + "\n" +
                "    cognome: " + toIndentedString(cognome) + "\n" +
                "    componentiPagamentoEntePrimario: " + toIndentedString(componentiPagamentoEntePrimario) + "\n" +
                "    componentiPagamentoEntiSecondari: " + toIndentedString(componentiPagamentoEntiSecondari) + "\n" +
                "    dataFineValidita: " + toIndentedString(dataFineValidita) + "\n" +
                "    dataInizioValidita: " + toIndentedString(dataInizioValidita) + "\n" +
                "    dataScadenza: " + toIndentedString(dataScadenza) + "\n" +
                "    email: " + toIndentedString(email) + "\n" +
                "    identificativoPagamento: " + toIndentedString(identificativoPagamento) + "\n" +
                "    importoTotaleEntePrimario: " + toIndentedString(importoTotaleEntePrimario) + "\n" +
                "    importoTotaleEntiSecondari: " + toIndentedString(importoTotaleEntiSecondari) + "\n" +
                "    importoTotale: " + toIndentedString(importoTotale) + "\n" +
                "    nome: " + toIndentedString(nome) + "\n" +
                "    notePerIlPagatore: " + toIndentedString(notePerIlPagatore) + "\n" +
                "    ragioneSociale: " + toIndentedString(ragioneSociale) + "\n" +
                "    requiresCostUpdate: " + toIndentedString(requiresCostUpdate) + "\n" +
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
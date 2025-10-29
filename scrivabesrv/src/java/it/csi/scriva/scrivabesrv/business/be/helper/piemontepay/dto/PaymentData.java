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
 * PaymentData
 */
public class PaymentData {
    private String causale = null;

    private String codiceFiscalePartitaIVAPagatore = null;

    private String cognome = null;

    private String email = null;

    private String identificativoPagamento = null;

    private BigDecimal importo = null;

    private String nome = null;

    private String notePerIlPagatore = null;

    private String ragioneSociale = null;

    public PaymentData causale(String causale) {
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

    public PaymentData codiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {
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

    public PaymentData cognome(String cognome) {
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

    public PaymentData email(String email) {
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

    public PaymentData identificativoPagamento(String identificativoPagamento) {
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

    public PaymentData importo(BigDecimal importo) {
        this.importo = importo;
        return this;
    }

    /**
     * Get importo
     *
     * @return importo
     **/
    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public PaymentData nome(String nome) {
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

    public PaymentData notePerIlPagatore(String notePerIlPagatore) {
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

    public PaymentData ragioneSociale(String ragioneSociale) {
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


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentData paymentData = (PaymentData) o;
        return Objects.equals(this.causale, paymentData.causale) &&
                Objects.equals(this.codiceFiscalePartitaIVAPagatore, paymentData.codiceFiscalePartitaIVAPagatore) &&
                Objects.equals(this.cognome, paymentData.cognome) &&
                Objects.equals(this.email, paymentData.email) &&
                Objects.equals(this.identificativoPagamento, paymentData.identificativoPagamento) &&
                Objects.equals(this.importo, paymentData.importo) &&
                Objects.equals(this.nome, paymentData.nome) &&
                Objects.equals(this.notePerIlPagatore, paymentData.notePerIlPagatore) &&
                Objects.equals(this.ragioneSociale, paymentData.ragioneSociale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(causale, codiceFiscalePartitaIVAPagatore, cognome, email, identificativoPagamento, importo, nome, notePerIlPagatore, ragioneSociale);
    }


    @Override
    public String toString() {
        return "class PaymentData {\n" +
                "    causale: " + toIndentedString(causale) + "\n" +
                "    codiceFiscalePartitaIVAPagatore: " + toIndentedString(codiceFiscalePartitaIVAPagatore) + "\n" +
                "    cognome: " + toIndentedString(cognome) + "\n" +
                "    email: " + toIndentedString(email) + "\n" +
                "    identificativoPagamento: " + toIndentedString(identificativoPagamento) + "\n" +
                "    importo: " + toIndentedString(importo) + "\n" +
                "    nome: " + toIndentedString(nome) + "\n" +
                "    notePerIlPagatore: " + toIndentedString(notePerIlPagatore) + "\n" +
                "    ragioneSociale: " + toIndentedString(ragioneSociale) + "\n" +
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
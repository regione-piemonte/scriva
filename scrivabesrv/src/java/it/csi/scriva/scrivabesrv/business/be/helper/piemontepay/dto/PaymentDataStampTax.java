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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * PaymentDataStampTax
 */
public class PaymentDataStampTax {
    private String causale = null;

    private String codiceFiscalePartitaIVAPagatore = null;

    private String cognome = null;

    private List<PaymentComponent> componentiPagamento = null;

    private String email = null;

    private Boolean flagSoloMarca = null;

    private String hashDocumento = null;

    private String identificativoPagamento = null;

    private BigDecimal importo = null;

    private BigDecimal importoBollo = null;

    private String iuvIstanzaAssociata = null;

    private String nome = null;

    private String provincia = null;

    private Integer quantita = null;

    private String ragioneSociale = null;

    private String tipoBollo = null;

    public PaymentDataStampTax causale(String causale) {
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

    public PaymentDataStampTax codiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {
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

    public PaymentDataStampTax cognome(String cognome) {
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

    public PaymentDataStampTax componentiPagamento(List<PaymentComponent> componentiPagamento) {
        this.componentiPagamento = componentiPagamento;
        return this;
    }

    public PaymentDataStampTax addComponentiPagamentoItem(PaymentComponent componentiPagamentoItem) {
        if (this.componentiPagamento == null) {
            this.componentiPagamento = new ArrayList<PaymentComponent>();
        }
        this.componentiPagamento.add(componentiPagamentoItem);
        return this;
    }

    /**
     * Get componentiPagamento
     *
     * @return componentiPagamento
     **/
    public List<PaymentComponent> getComponentiPagamento() {
        return componentiPagamento;
    }

    public void setComponentiPagamento(List<PaymentComponent> componentiPagamento) {
        this.componentiPagamento = componentiPagamento;
    }

    public PaymentDataStampTax email(String email) {
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

    public PaymentDataStampTax flagSoloMarca(Boolean flagSoloMarca) {
        this.flagSoloMarca = flagSoloMarca;
        return this;
    }

    /**
     * Get flagSoloMarca
     *
     * @return flagSoloMarca
     **/
    public Boolean isFlagSoloMarca() {
        return flagSoloMarca;
    }

    public void setFlagSoloMarca(Boolean flagSoloMarca) {
        this.flagSoloMarca = flagSoloMarca;
    }

    public PaymentDataStampTax hashDocumento(String hashDocumento) {
        this.hashDocumento = hashDocumento;
        return this;
    }

    /**
     * Get hashDocumento
     *
     * @return hashDocumento
     **/
    public String getHashDocumento() {
        return hashDocumento;
    }

    public void setHashDocumento(String hashDocumento) {
        this.hashDocumento = hashDocumento;
    }

    public PaymentDataStampTax identificativoPagamento(String identificativoPagamento) {
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

    public PaymentDataStampTax importo(BigDecimal importo) {
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

    public PaymentDataStampTax importoBollo(BigDecimal importoBollo) {
        this.importoBollo = importoBollo;
        return this;
    }

    /**
     * Get importoBollo
     *
     * @return importoBollo
     **/
    public BigDecimal getImportoBollo() {
        return importoBollo;
    }

    public void setImportoBollo(BigDecimal importoBollo) {
        this.importoBollo = importoBollo;
    }

    public PaymentDataStampTax iuvIstanzaAssociata(String iuvIstanzaAssociata) {
        this.iuvIstanzaAssociata = iuvIstanzaAssociata;
        return this;
    }

    /**
     * Get iuvIstanzaAssociata
     *
     * @return iuvIstanzaAssociata
     **/
    public String getIuvIstanzaAssociata() {
        return iuvIstanzaAssociata;
    }

    public void setIuvIstanzaAssociata(String iuvIstanzaAssociata) {
        this.iuvIstanzaAssociata = iuvIstanzaAssociata;
    }

    public PaymentDataStampTax nome(String nome) {
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

    public PaymentDataStampTax provincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    /**
     * Get provincia
     *
     * @return provincia
     **/
    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public PaymentDataStampTax quantita(Integer quantita) {
        this.quantita = quantita;
        return this;
    }

    /**
     * Get quantita
     *
     * @return quantita
     **/
    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public PaymentDataStampTax ragioneSociale(String ragioneSociale) {
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

    public PaymentDataStampTax tipoBollo(String tipoBollo) {
        this.tipoBollo = tipoBollo;
        return this;
    }

    /**
     * Get tipoBollo
     *
     * @return tipoBollo
     **/
    public String getTipoBollo() {
        return tipoBollo;
    }

    public void setTipoBollo(String tipoBollo) {
        this.tipoBollo = tipoBollo;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentDataStampTax paymentDataStampTax = (PaymentDataStampTax) o;
        return Objects.equals(this.causale, paymentDataStampTax.causale) &&
                Objects.equals(this.codiceFiscalePartitaIVAPagatore, paymentDataStampTax.codiceFiscalePartitaIVAPagatore) &&
                Objects.equals(this.cognome, paymentDataStampTax.cognome) &&
                Objects.equals(this.componentiPagamento, paymentDataStampTax.componentiPagamento) &&
                Objects.equals(this.email, paymentDataStampTax.email) &&
                Objects.equals(this.flagSoloMarca, paymentDataStampTax.flagSoloMarca) &&
                Objects.equals(this.hashDocumento, paymentDataStampTax.hashDocumento) &&
                Objects.equals(this.identificativoPagamento, paymentDataStampTax.identificativoPagamento) &&
                Objects.equals(this.importo, paymentDataStampTax.importo) &&
                Objects.equals(this.importoBollo, paymentDataStampTax.importoBollo) &&
                Objects.equals(this.iuvIstanzaAssociata, paymentDataStampTax.iuvIstanzaAssociata) &&
                Objects.equals(this.nome, paymentDataStampTax.nome) &&
                Objects.equals(this.provincia, paymentDataStampTax.provincia) &&
                Objects.equals(this.quantita, paymentDataStampTax.quantita) &&
                Objects.equals(this.ragioneSociale, paymentDataStampTax.ragioneSociale) &&
                Objects.equals(this.tipoBollo, paymentDataStampTax.tipoBollo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(causale, codiceFiscalePartitaIVAPagatore, cognome, componentiPagamento, email, flagSoloMarca, hashDocumento, identificativoPagamento, importo, importoBollo, iuvIstanzaAssociata, nome, provincia, quantita, ragioneSociale, tipoBollo);
    }


    @Override
    public String toString() {
        return "class PaymentDataStampTax {\n" +
                "    causale: " + toIndentedString(causale) + "\n" +
                "    codiceFiscalePartitaIVAPagatore: " + toIndentedString(codiceFiscalePartitaIVAPagatore) + "\n" +
                "    cognome: " + toIndentedString(cognome) + "\n" +
                "    componentiPagamento: " + toIndentedString(componentiPagamento) + "\n" +
                "    email: " + toIndentedString(email) + "\n" +
                "    flagSoloMarca: " + toIndentedString(flagSoloMarca) + "\n" +
                "    hashDocumento: " + toIndentedString(hashDocumento) + "\n" +
                "    identificativoPagamento: " + toIndentedString(identificativoPagamento) + "\n" +
                "    importo: " + toIndentedString(importo) + "\n" +
                "    importoBollo: " + toIndentedString(importoBollo) + "\n" +
                "    iuvIstanzaAssociata: " + toIndentedString(iuvIstanzaAssociata) + "\n" +
                "    nome: " + toIndentedString(nome) + "\n" +
                "    provincia: " + toIndentedString(provincia) + "\n" +
                "    quantita: " + toIndentedString(quantita) + "\n" +
                "    ragioneSociale: " + toIndentedString(ragioneSociale) + "\n" +
                "    tipoBollo: " + toIndentedString(tipoBollo) + "\n" +
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
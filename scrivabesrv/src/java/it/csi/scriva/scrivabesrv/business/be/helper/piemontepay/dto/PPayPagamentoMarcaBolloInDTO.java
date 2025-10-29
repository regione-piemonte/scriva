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
import java.util.List;
import java.util.Objects;

/**
 * The type P pay pagamento marca bollo in dto.
 *
 * @author CSI PIEMONTE
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PPayPagamentoMarcaBolloInDTO {

    private String identificativoPagamento;

    private String codiceFiscaleEnte;

    private String causale;

    private String tipoPagamento;

    private BigDecimal importo;

    private String nome;

    private String cognome;

    private String ragioneSociale;

    private String email;

    private String codiceFiscalePartitaIVAPagatore;

    private String hashDocumento;

    private Boolean flagSoloMarca;

    private String provincia;

    private String tipoBollo;

    private BigDecimal importoBollo;

    private Integer quantita;

    private String iuvIstanzaAssociata;

    private List<PPayComponentePagamentoDTO> componentiPagamento;

    /**
     * Gets identificativo pagamento.
     *
     * @return identificativoPagamento identificativo pagamento
     */
    public String getIdentificativoPagamento() {
        return identificativoPagamento;
    }

    /**
     * Sets identificativo pagamento.
     *
     * @param identificativoPagamento identificativoPagamento
     */
    public void setIdentificativoPagamento(String identificativoPagamento) {
        this.identificativoPagamento = identificativoPagamento;
    }

    /**
     * Gets codice fiscale ente.
     *
     * @return codiceFiscaleEnte codice fiscale ente
     */
    public String getCodiceFiscaleEnte() {
        return codiceFiscaleEnte;
    }

    /**
     * Sets codice fiscale ente.
     *
     * @param codiceFiscaleEnte codiceFiscaleEnte
     */
    public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {
        this.codiceFiscaleEnte = codiceFiscaleEnte;
    }

    /**
     * Gets causale.
     *
     * @return causale causale
     */
    public String getCausale() {
        return causale;
    }

    /**
     * Sets causale.
     *
     * @param causale causale
     */
    public void setCausale(String causale) {
        this.causale = causale;
    }

    /**
     * Gets tipo pagamento.
     *
     * @return tipoPagamento tipo pagamento
     */
    public String getTipoPagamento() {
        return tipoPagamento;
    }

    /**
     * Sets tipo pagamento.
     *
     * @param tipoPagamento tipoPagamento
     */
    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    /**
     * Gets importo.
     *
     * @return importo importo
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Sets importo.
     *
     * @param importo importo
     */
    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    /**
     * Gets nome.
     *
     * @return nome nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param nome nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets cognome.
     *
     * @return cognome cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets cognome.
     *
     * @param cognome cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Gets ragione sociale.
     *
     * @return ragioneSociale ragione sociale
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    /**
     * Sets ragione sociale.
     *
     * @param ragioneSociale ragioneSociale
     */
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    /**
     * Gets email.
     *
     * @return email email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets codice fiscale partita iva pagatore.
     *
     * @return codiceFiscalePartitaIVAPagatore codice fiscale partita iva pagatore
     */
    public String getCodiceFiscalePartitaIVAPagatore() {
        return codiceFiscalePartitaIVAPagatore;
    }

    /**
     * Sets codice fiscale partita iva pagatore.
     *
     * @param codiceFiscalePartitaIVAPagatore codiceFiscalePartitaIVAPagatore
     */
    public void setCodiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {
        this.codiceFiscalePartitaIVAPagatore = codiceFiscalePartitaIVAPagatore;
    }

    /**
     * Gets hash documento.
     *
     * @return hashDocumento hash documento
     */
    public String getHashDocumento() {
        return hashDocumento;
    }

    /**
     * Sets hash documento.
     *
     * @param hashDocumento hashDocumento
     */
    public void setHashDocumento(String hashDocumento) {
        this.hashDocumento = hashDocumento;
    }

    /**
     * Gets flag solo marca.
     *
     * @return flagSoloMarca flag solo marca
     */
    public Boolean getFlagSoloMarca() {
        return flagSoloMarca;
    }

    /**
     * Sets flag solo marca.
     *
     * @param flagSoloMarca flagSoloMarca
     */
    public void setFlagSoloMarca(Boolean flagSoloMarca) {
        this.flagSoloMarca = flagSoloMarca;
    }

    /**
     * Gets provincia.
     *
     * @return provincia provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Sets provincia.
     *
     * @param provincia provincia
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Gets tipo bollo.
     *
     * @return tipoBollo tipo bollo
     */
    public String getTipoBollo() {
        return tipoBollo;
    }

    /**
     * Sets tipo bollo.
     *
     * @param tipoBollo tipoBollo
     */
    public void setTipoBollo(String tipoBollo) {
        this.tipoBollo = tipoBollo;
    }

    /**
     * Gets importo bollo.
     *
     * @return importoBollo importo bollo
     */
    public BigDecimal getImportoBollo() {
        return importoBollo;
    }

    /**
     * Sets importo bollo.
     *
     * @param importoBollo importoBollo
     */
    public void setImportoBollo(BigDecimal importoBollo) {
        this.importoBollo = importoBollo;
    }

    /**
     * Gets quantita.
     *
     * @return quantita quantita
     */
    public Integer getQuantita() {
        return quantita;
    }

    /**
     * Sets quantita.
     *
     * @param quantita quantita
     */
    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    /**
     * Gets iuv istanza associata.
     *
     * @return iuvIstanzaAssociata iuv istanza associata
     */
    public String getIuvIstanzaAssociata() {
        return iuvIstanzaAssociata;
    }

    /**
     * Sets iuv istanza associata.
     *
     * @param iuvIstanzaAssociata iuvIstanzaAssociata
     */
    public void setIuvIstanzaAssociata(String iuvIstanzaAssociata) {
        this.iuvIstanzaAssociata = iuvIstanzaAssociata;
    }

    /**
     * Gets componenti pagamento.
     *
     * @return componentiPagamento componenti pagamento
     */
    public List<PPayComponentePagamentoDTO> getComponentiPagamento() {
        return componentiPagamento;
    }

    /**
     * Sets componenti pagamento.
     *
     * @param componentiPagamento componentiPagamento
     */
    public void setComponentiPagamento(List<PPayComponentePagamentoDTO> componentiPagamento) {
        this.componentiPagamento = componentiPagamento;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PPayPagamentoMarcaBolloInDTO that = (PPayPagamentoMarcaBolloInDTO) o;
        return Objects.equals(identificativoPagamento, that.identificativoPagamento) && Objects.equals(codiceFiscaleEnte, that.codiceFiscaleEnte) && Objects.equals(causale, that.causale) && Objects.equals(tipoPagamento, that.tipoPagamento) && Objects.equals(importo, that.importo) && Objects.equals(nome, that.nome) && Objects.equals(cognome, that.cognome) && Objects.equals(ragioneSociale, that.ragioneSociale) && Objects.equals(email, that.email) && Objects.equals(codiceFiscalePartitaIVAPagatore, that.codiceFiscalePartitaIVAPagatore) && Objects.equals(hashDocumento, that.hashDocumento) && Objects.equals(flagSoloMarca, that.flagSoloMarca) && Objects.equals(provincia, that.provincia) && Objects.equals(tipoBollo, that.tipoBollo) && Objects.equals(importoBollo, that.importoBollo) && Objects.equals(quantita, that.quantita) && Objects.equals(iuvIstanzaAssociata, that.iuvIstanzaAssociata);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(identificativoPagamento, codiceFiscaleEnte, causale, tipoPagamento, importo, nome, cognome, ragioneSociale, email, codiceFiscalePartitaIVAPagatore, hashDocumento, flagSoloMarca, provincia, tipoBollo, importoBollo, quantita, iuvIstanzaAssociata);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PPayPagamentoMarcaBolloInDTO {\n");
        sb.append("         identificativoPagamento:'").append(identificativoPagamento).append("'\n");
        sb.append(",         codiceFiscaleEnte:'").append(codiceFiscaleEnte).append("'\n");
        sb.append(",         causale:'").append(causale).append("'\n");
        sb.append(",         tipoPagamento:'").append(tipoPagamento).append("'\n");
        sb.append(",         importo:").append(importo).append("\n");
        sb.append(",         nome:'").append(nome).append("'\n");
        sb.append(",         cognome:'").append(cognome).append("'\n");
        sb.append(",         ragioneSociale:'").append(ragioneSociale).append("'\n");
        sb.append(",         email:'").append(email).append("'\n");
        sb.append(",         codiceFiscalePartitaIVAPagatore:'").append(codiceFiscalePartitaIVAPagatore).append("'\n");
        sb.append(",         hashDocumento:'").append(hashDocumento).append("'\n");
        sb.append(",         flagSoloMarca:").append(flagSoloMarca).append("\n");
        sb.append(",         provincia:'").append(provincia).append("'\n");
        sb.append(",         tipoBollo:'").append(tipoBollo).append("'\n");
        sb.append(",         importoBollo:").append(importoBollo).append("\n");
        sb.append(",         quantita:").append(quantita).append("\n");
        sb.append(",         iuvIstanzaAssociata:'").append(iuvIstanzaAssociata).append("'\n");
        sb.append(",         componentiPagamento:").append(componentiPagamento).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Index metadata property dto.
 */
public class IndexMetadataPropertyDTO implements Serializable {

    private String idIstanza;

    private String codiceIstanza;

    private String codicePratica;

    private String dimensioneByte;

    private String codiceAllegato;

    private String codiceTipoAdempimento;

    private String descrizioneTipoAdempimento;

    private String codiceAdempimento;

    private String descrizioneAdempimento;

    private String codiceAutoritaCompetente;

    private String codiceMultipleAutoritaCompetenti;

    private String codiceCategoriaAllegato;

    private String descrizioneCategoriaAllegato;

    private String codiceTipologiaAllegato;

    private String descrizioneTipologiaAllegato;

    private String noteAllegato;

    private String documentoObbligatorio;

    private String codiceIntegrazione;

    private String descrizioneIntegrazione;

    // Aspect properties
    private String firmato;

    private String tipoFirma;

    private String errorCode;

    private String cancellato;

    private String dataCancellazione;

    private String riservato;


    /**
     * Gets id istanza.
     *
     * @return idIstanza id istanza
     */
    public String getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza idIstanza
     */
    public void setIdIstanza(String idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets codice istanza.
     *
     * @return codiceIstanza codice istanza
     */
    public String getCodiceIstanza() {
        return codiceIstanza;
    }

    /**
     * Sets codice istanza.
     *
     * @param codiceIstanza codiceIstanza
     */
    public void setCodiceIstanza(String codiceIstanza) {
        this.codiceIstanza = codiceIstanza;
    }

    /**
     * Gets codice pratica.
     *
     * @return codicePratica codice pratica
     */
    public String getCodicePratica() {
        return codicePratica;
    }

    /**
     * Sets codice pratica.
     *
     * @param codicePratica codicePratica
     */
    public void setCodicePratica(String codicePratica) {
        this.codicePratica = codicePratica;
    }

    /**
     * Gets dimensione byte.
     *
     * @return dimensioneByte dimensione byte
     */
    public String getDimensioneByte() {
        return dimensioneByte;
    }

    /**
     * Sets dimensione byte.
     *
     * @param dimensioneByte dimensioneByte
     */
    public void setDimensioneByte(String dimensioneByte) {
        this.dimensioneByte = dimensioneByte;
    }

    /**
     * Gets codice allegato.
     *
     * @return codiceAllegato codice allegato
     */
    public String getCodiceAllegato() {
        return codiceAllegato;
    }

    /**
     * Sets codice allegato.
     *
     * @param codiceAllegato codiceAllegato
     */
    public void setCodiceAllegato(String codiceAllegato) {
        this.codiceAllegato = codiceAllegato;
    }

    /**
     * Gets codice tipo adempimento.
     *
     * @return codiceTipoAdempimento codice tipo adempimento
     */
    public String getCodiceTipoAdempimento() {
        return codiceTipoAdempimento;
    }

    /**
     * Sets codice tipo adempimento.
     *
     * @param codiceTipoAdempimento codiceTipoAdempimento
     */
    public void setCodiceTipoAdempimento(String codiceTipoAdempimento) {
        this.codiceTipoAdempimento = codiceTipoAdempimento;
    }

    /**
     * Gets descrizione tipo adempimento.
     *
     * @return descrizioneTipoAdempimento descrizione tipo adempimento
     */
    public String getDescrizioneTipoAdempimento() {
        return descrizioneTipoAdempimento;
    }

    /**
     * Sets descrizione tipo adempimento.
     *
     * @param descrizioneTipoAdempimento descrizioneTipoAdempimento
     */
    public void setDescrizioneTipoAdempimento(String descrizioneTipoAdempimento) {
        this.descrizioneTipoAdempimento = descrizioneTipoAdempimento;
    }

    /**
     * Gets codice adempimento.
     *
     * @return codiceAdempimento codice adempimento
     */
    public String getCodiceAdempimento() {
        return codiceAdempimento;
    }

    /**
     * Sets codice adempimento.
     *
     * @param codiceAdempimento codiceAdempimento
     */
    public void setCodiceAdempimento(String codiceAdempimento) {
        this.codiceAdempimento = codiceAdempimento;
    }

    /**
     * Gets descrizione adempimento.
     *
     * @return descrizioneAdempimento descrizione adempimento
     */
    public String getDescrizioneAdempimento() {
        return descrizioneAdempimento;
    }

    /**
     * Sets descrizione adempimento.
     *
     * @param descrizioneAdempimento descrizioneAdempimento
     */
    public void setDescrizioneAdempimento(String descrizioneAdempimento) {
        this.descrizioneAdempimento = descrizioneAdempimento;
    }

    /**
     * Gets codice autorita competente.
     *
     * @return codiceAutoritaCompetente codice autorita competente
     */
    public String getCodiceAutoritaCompetente() {
        return codiceAutoritaCompetente;
    }

    /**
     * Sets codice autorita competente.
     *
     * @param codiceAutoritaCompetente codiceAutoritaCompetente
     */
    public void setCodiceAutoritaCompetente(String codiceAutoritaCompetente) {
        this.codiceAutoritaCompetente = codiceAutoritaCompetente;
    }

    /**
     * Gets codice multiple autorita competenti.
     *
     * @return codiceMultipleAutoritaCompetenti codice multiple autorita competenti
     */
    public String getCodiceMultipleAutoritaCompetenti() {
        return codiceMultipleAutoritaCompetenti;
    }

    /**
     * Sets codice multiple autorita competenti.
     *
     * @param codiceMultipleAutoritaCompetenti codiceMultipleAutoritaCompetenti
     */
    public void setCodiceMultipleAutoritaCompetenti(String codiceMultipleAutoritaCompetenti) {
        this.codiceMultipleAutoritaCompetenti = codiceMultipleAutoritaCompetenti;
    }

    /**
     * Gets codice categoria allegato.
     *
     * @return codiceCategoriaAllegato codice categoria allegato
     */
    public String getCodiceCategoriaAllegato() {
        return codiceCategoriaAllegato;
    }

    /**
     * Sets codice categoria allegato.
     *
     * @param codiceCategoriaAllegato codiceCategoriaAllegato
     */
    public void setCodiceCategoriaAllegato(String codiceCategoriaAllegato) {
        this.codiceCategoriaAllegato = codiceCategoriaAllegato;
    }

    /**
     * Gets descrizione categoria allegato.
     *
     * @return descrizioneCategoriaAllegato descrizione categoria allegato
     */
    public String getDescrizioneCategoriaAllegato() {
        return descrizioneCategoriaAllegato;
    }

    /**
     * Sets descrizione categoria allegato.
     *
     * @param descrizioneCategoriaAllegato descrizioneCategoriaAllegato
     */
    public void setDescrizioneCategoriaAllegato(String descrizioneCategoriaAllegato) {
        this.descrizioneCategoriaAllegato = descrizioneCategoriaAllegato;
    }

    /**
     * Gets codice tipologia allegato.
     *
     * @return codiceTipologiaAllegato codice tipologia allegato
     */
    public String getCodiceTipologiaAllegato() {
        return codiceTipologiaAllegato;
    }

    /**
     * Sets codice tipologia allegato.
     *
     * @param codiceTipologiaAllegato codiceTipologiaAllegato
     */
    public void setCodiceTipologiaAllegato(String codiceTipologiaAllegato) {
        this.codiceTipologiaAllegato = codiceTipologiaAllegato;
    }

    /**
     * Gets descrizione tipologia allegato.
     *
     * @return descrizioneTipologiaAllegato descrizione tipologia allegato
     */
    public String getDescrizioneTipologiaAllegato() {
        return descrizioneTipologiaAllegato;
    }

    /**
     * Sets descrizione tipologia allegato.
     *
     * @param descrizioneTipologiaAllegato descrizioneTipologiaAllegato
     */
    public void setDescrizioneTipologiaAllegato(String descrizioneTipologiaAllegato) {
        this.descrizioneTipologiaAllegato = descrizioneTipologiaAllegato;
    }

    /**
     * Gets note allegato.
     *
     * @return noteAllegato note allegato
     */
    public String getNoteAllegato() {
        return noteAllegato;
    }

    /**
     * Sets note allegato.
     *
     * @param noteAllegato noteAllegato
     */
    public void setNoteAllegato(String noteAllegato) {
        this.noteAllegato = noteAllegato;
    }

    /**
     * Gets documento obbligatorio.
     *
     * @return documentoObbligatorio documento obbligatorio
     */
    public String getDocumentoObbligatorio() {
        return documentoObbligatorio;
    }

    /**
     * Sets documento obbligatorio.
     *
     * @param documentoObbligatorio documentoObbligatorio
     */
    public void setDocumentoObbligatorio(String documentoObbligatorio) {
        this.documentoObbligatorio = documentoObbligatorio;
    }

    /**
     * Gets codice integrazione.
     *
     * @return codiceIntegrazione codice integrazione
     */
    public String getCodiceIntegrazione() {
        return codiceIntegrazione;
    }

    /**
     * Sets codice integrazione.
     *
     * @param codiceIntegrazione codiceIntegrazione
     */
    public void setCodiceIntegrazione(String codiceIntegrazione) {
        this.codiceIntegrazione = codiceIntegrazione;
    }

    /**
     * Gets descrizione integrazione.
     *
     * @return descrizioneIntegrazione descrizione integrazione
     */
    public String getDescrizioneIntegrazione() {
        return descrizioneIntegrazione;
    }

    /**
     * Sets descrizione integrazione.
     *
     * @param descrizioneIntegrazione descrizioneIntegrazione
     */
    public void setDescrizioneIntegrazione(String descrizioneIntegrazione) {
        this.descrizioneIntegrazione = descrizioneIntegrazione;
    }

    /**
     * Gets firmato.
     *
     * @return firmato firmato
     */
    public String getFirmato() {
        return firmato;
    }

    /**
     * Sets firmato.
     *
     * @param firmato firmato
     */
    public void setFirmato(String firmato) {
        this.firmato = firmato;
    }

    /**
     * Gets tipo firma.
     *
     * @return tipoFirma tipo firma
     */
    public String getTipoFirma() {
        return tipoFirma;
    }

    /**
     * Sets tipo firma.
     *
     * @param tipoFirma tipoFirma
     */
    public void setTipoFirma(String tipoFirma) {
        this.tipoFirma = tipoFirma;
    }

    /**
     * Gets error code.
     *
     * @return errorCode error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets error code.
     *
     * @param errorCode errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets cancellato.
     *
     * @return cancellato cancellato
     */
    public String getCancellato() {
        return cancellato;
    }

    /**
     * Sets cancellato.
     *
     * @param cancellato cancellato
     */
    public void setCancellato(String cancellato) {
        this.cancellato = cancellato;
    }

    /**
     * Gets data cancellazione.
     *
     * @return dataCancellazione data cancellazione
     */
    public String getDataCancellazione() {
        return dataCancellazione;
    }

    /**
     * Sets data cancellazione.
     *
     * @param dataCancellazione dataCancellazione
     */
    public void setDataCancellazione(String dataCancellazione) {
        this.dataCancellazione = dataCancellazione;
    }

    /**
     * Gets riservato.
     *
     * @return riservato riservato
     */
    public String getRiservato() {
        return riservato;
    }

    /**
     * Sets riservato.
     *
     * @param riservato riservato
     */
    public void setRiservato(String riservato) {
        this.riservato = riservato;
    }

    /**
     * @param o Object
     * @return boolena
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexMetadataPropertyDTO that = (IndexMetadataPropertyDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(codiceIstanza, that.codiceIstanza) && Objects.equals(codicePratica, that.codicePratica) && Objects.equals(dimensioneByte, that.dimensioneByte) && Objects.equals(codiceAllegato, that.codiceAllegato) && Objects.equals(codiceTipoAdempimento, that.codiceTipoAdempimento) && Objects.equals(descrizioneTipoAdempimento, that.descrizioneTipoAdempimento) && Objects.equals(codiceAdempimento, that.codiceAdempimento) && Objects.equals(descrizioneAdempimento, that.descrizioneAdempimento) && Objects.equals(codiceAutoritaCompetente, that.codiceAutoritaCompetente) && Objects.equals(codiceMultipleAutoritaCompetenti, that.codiceMultipleAutoritaCompetenti) && Objects.equals(codiceCategoriaAllegato, that.codiceCategoriaAllegato) && Objects.equals(descrizioneCategoriaAllegato, that.descrizioneCategoriaAllegato) && Objects.equals(codiceTipologiaAllegato, that.codiceTipologiaAllegato) && Objects.equals(descrizioneTipologiaAllegato, that.descrizioneTipologiaAllegato) && Objects.equals(noteAllegato, that.noteAllegato) && Objects.equals(documentoObbligatorio, that.documentoObbligatorio) && Objects.equals(codiceIntegrazione, that.codiceIntegrazione) && Objects.equals(descrizioneIntegrazione, that.descrizioneIntegrazione) && Objects.equals(firmato, that.firmato) && Objects.equals(tipoFirma, that.tipoFirma) && Objects.equals(errorCode, that.errorCode) && Objects.equals(cancellato, that.cancellato) && Objects.equals(dataCancellazione, that.dataCancellazione) && Objects.equals(riservato, that.riservato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, codiceIstanza, codicePratica, dimensioneByte, codiceAllegato, codiceTipoAdempimento, descrizioneTipoAdempimento, codiceAdempimento, descrizioneAdempimento, codiceAutoritaCompetente, codiceMultipleAutoritaCompetenti, codiceCategoriaAllegato, descrizioneCategoriaAllegato, codiceTipologiaAllegato, descrizioneTipologiaAllegato, noteAllegato, documentoObbligatorio, codiceIntegrazione, descrizioneIntegrazione, firmato, tipoFirma, errorCode, cancellato, dataCancellazione, riservato);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IndexMetadataPropertyDTO {");
        sb.append("         idIstanza:'").append(idIstanza).append("'");
        sb.append(",         codiceIstanza:'").append(codiceIstanza).append("'");
        sb.append(",         codicePratica:'").append(codicePratica).append("'");
        sb.append(",         dimensioneByte:'").append(dimensioneByte).append("'");
        sb.append(",         codiceAllegato:'").append(codiceAllegato).append("'");
        sb.append(",         codiceTipoAdempimento:'").append(codiceTipoAdempimento).append("'");
        sb.append(",         descrizioneTipoAdempimento:'").append(descrizioneTipoAdempimento).append("'");
        sb.append(",         codiceAdempimento:'").append(codiceAdempimento).append("'");
        sb.append(",         descrizioneAdempimento:'").append(descrizioneAdempimento).append("'");
        sb.append(",         codiceAutoritaCompetente:'").append(codiceAutoritaCompetente).append("'");
        sb.append(",         codiceMultipleAutoritaCompetenti:'").append(codiceMultipleAutoritaCompetenti).append("'");
        sb.append(",         codiceCategoriaAllegato:'").append(codiceCategoriaAllegato).append("'");
        sb.append(",         descrizioneCategoriaAllegato:'").append(descrizioneCategoriaAllegato).append("'");
        sb.append(",         codiceTipologiaAllegato:'").append(codiceTipologiaAllegato).append("'");
        sb.append(",         descrizioneTipologiaAllegato:'").append(descrizioneTipologiaAllegato).append("'");
        sb.append(",         noteAllegato:'").append(noteAllegato).append("'");
        sb.append(",         documentoObbligatorio:'").append(documentoObbligatorio).append("'");
        sb.append(",         codiceIntegrazione:'").append(codiceIntegrazione).append("'");
        sb.append(",         descrizioneIntegrazione:'").append(descrizioneIntegrazione).append("'");
        sb.append(",         firmato:'").append(firmato).append("'");
        sb.append(",         tipoFirma:'").append(tipoFirma).append("'");
        sb.append(",         errorCode:'").append(errorCode).append("'");
        sb.append(",         cancellato:'").append(cancellato).append("'");
        sb.append(",         dataCancellazione:'").append(dataCancellazione).append("'");
        sb.append(",         riservato:'").append(riservato).append("'");
        sb.append("}");
        return sb.toString();
    }
}
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Pub doc istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubDocIstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_allegato_istanza")
    private Long idAllegatoIstanza;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("flg_riservato")
    private Boolean flgRiservato;

    @JsonProperty("cod_allegato")
    private String codAllegato;

    @JsonProperty("nome_allegato")
    private String nomeAllegato;

    @JsonProperty("dimensione_upload")
    private Long dimensioneUpload;

    @JsonProperty("flg_cancellato")
    private Boolean flgCancellato;

    @JsonProperty("ind_firma")
    private Integer indFirma;

    @JsonProperty("note")
    private String note;

    @JsonProperty("id_allegato_padre")
    private Long idAllegatoPadre;

    @JsonProperty("cod_allegato_padre")
    private String codAllegatoPadre;

    @JsonProperty("flg_da_pubblicare")
    private Boolean flgDaPubblicare;

    @JsonProperty("data_pubblicazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataPubblicazione;

    @JsonProperty("num_protocollo_allegato")
    private String numProtocolloAllegato;

    @JsonProperty("data_protocollo_allegato")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataProtocolloAllegato;

    @JsonProperty("url_documento")
    private String urlDocumento;

    @JsonProperty("titolo_allegato")
    private String titoloAllegato;

    @JsonProperty("autore_allegato")
    private String autoreAllegato;

    /**
     * Gets id allegato istanza.
     *
     * @return the id allegato istanza
     */
    public Long getIdAllegatoIstanza() {
        return idAllegatoIstanza;
    }

    /**
     * Sets id allegato istanza.
     *
     * @param idAllegatoIstanza the id allegato istanza
     */
    public void setIdAllegatoIstanza(Long idAllegatoIstanza) {
        this.idAllegatoIstanza = idAllegatoIstanza;
    }

    /**
     * Gets id istanza.
     *
     * @return the id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza the id istanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets flg riservato.
     *
     * @return the flg riservato
     */
    public Boolean getFlgRiservato() {
        return flgRiservato;
    }

    /**
     * Sets flg riservato.
     *
     * @param flgRiservato the flg riservato
     */
    public void setFlgRiservato(Boolean flgRiservato) {
        this.flgRiservato = flgRiservato;
    }

    /**
     * Gets cod allegato.
     *
     * @return the cod allegato
     */
    public String getCodAllegato() {
        return codAllegato;
    }

    /**
     * Sets cod allegato.
     *
     * @param codAllegato the cod allegato
     */
    public void setCodAllegato(String codAllegato) {
        this.codAllegato = codAllegato;
    }

    /**
     * Gets nome allegato.
     *
     * @return the nome allegato
     */
    public String getNomeAllegato() {
        return nomeAllegato;
    }

    /**
     * Sets nome allegato.
     *
     * @param nomeAllegato the nome allegato
     */
    public void setNomeAllegato(String nomeAllegato) {
        this.nomeAllegato = nomeAllegato;
    }

    /**
     * Gets dimensione upload.
     *
     * @return the dimensione upload
     */
    public Long getDimensioneUpload() {
        return dimensioneUpload;
    }

    /**
     * Sets dimensione upload.
     *
     * @param dimensioneUpload the dimensione upload
     */
    public void setDimensioneUpload(Long dimensioneUpload) {
        this.dimensioneUpload = dimensioneUpload;
    }

    /**
     * Gets flg cancellato.
     *
     * @return the flg cancellato
     */
    public Boolean getFlgCancellato() {
        return flgCancellato;
    }

    /**
     * Sets flg cancellato.
     *
     * @param flgCancellato the flg cancellato
     */
    public void setFlgCancellato(Boolean flgCancellato) {
        this.flgCancellato = flgCancellato;
    }

    /**
     * Gets ind firma.
     *
     * @return the ind firma
     */
    public Integer getIndFirma() {
        return indFirma;
    }

    /**
     * Sets ind firma.
     *
     * @param indFirma the ind firma
     */
    public void setIndFirma(Integer indFirma) {
        this.indFirma = indFirma;
    }

    /**
     * Gets note.
     *
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets note.
     *
     * @param note the note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Gets id allegato padre.
     *
     * @return the id allegato padre
     */
    public Long getIdAllegatoPadre() {
        return idAllegatoPadre;
    }

    /**
     * Sets id allegato padre.
     *
     * @param idAllegatoPadre the id allegato padre
     */
    public void setIdAllegatoPadre(Long idAllegatoPadre) {
        this.idAllegatoPadre = idAllegatoPadre;
    }

    /**
     * Gets cod allegato padre.
     *
     * @return the cod allegato padre
     */
    public String getCodAllegatoPadre() {
        return codAllegatoPadre;
    }

    /**
     * Sets cod allegato padre.
     *
     * @param codAllegatoPadre the cod allegato padre
     */
    public void setCodAllegatoPadre(String codAllegatoPadre) {
        this.codAllegatoPadre = codAllegatoPadre;
    }

    /**
     * Gets flg da pubblicare.
     *
     * @return the flg da pubblicare
     */
    public Boolean getFlgDaPubblicare() {
        return flgDaPubblicare;
    }

    /**
     * Sets flg da pubblicare.
     *
     * @param flgDaPubblicare the flg da pubblicare
     */
    public void setFlgDaPubblicare(Boolean flgDaPubblicare) {
        this.flgDaPubblicare = flgDaPubblicare;
    }

    /**
     * Gets data pubblicazione.
     *
     * @return the data pubblicazione
     */
    public Timestamp getDataPubblicazione() {
        return dataPubblicazione;
    }

    /**
     * Sets data pubblicazione.
     *
     * @param dataPubblicazione the data pubblicazione
     */
    public void setDataPubblicazione(Timestamp dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    /**
     * Gets num protocollo allegato.
     *
     * @return the num protocollo allegato
     */
    public String getNumProtocolloAllegato() {
        return numProtocolloAllegato;
    }

    /**
     * Sets num protocollo allegato.
     *
     * @param numProtocolloAllegato the num protocollo allegato
     */
    public void setNumProtocolloAllegato(String numProtocolloAllegato) {
        this.numProtocolloAllegato = numProtocolloAllegato;
    }

    /**
     * Gets data protocollo allegato.
     *
     * @return the data protocollo allegato
     */
    public Timestamp getDataProtocolloAllegato() {
        return dataProtocolloAllegato;
    }

    /**
     * Sets data protocollo allegato.
     *
     * @param dataProtocolloAllegato the data protocollo allegato
     */
    public void setDataProtocolloAllegato(Timestamp dataProtocolloAllegato) {
        this.dataProtocolloAllegato = dataProtocolloAllegato;
    }

    /**
     * Gets url documento.
     *
     * @return the url documento
     */
    public String getUrlDocumento() {
        return urlDocumento;
    }

    /**
     * Sets url documento.
     *
     * @param urlDocumento the url documento
     */
    public void setUrlDocumento(String urlDocumento) {
        this.urlDocumento = urlDocumento;
    }

    /**
     * Gets titolo allegato.
     *
     * @return the titolo allegato
     */
    public String getTitoloAllegato() {
        return titoloAllegato;
    }

    /**
     * Sets titolo allegato.
     *
     * @param titoloAllegato the titolo allegato
     */
    public void setTitoloAllegato(String titoloAllegato) {
        this.titoloAllegato = titoloAllegato;
    }

    /**
     * Gets autore allegato.
     *
     * @return the autore allegato
     */
    public String getAutoreAllegato() {
        return autoreAllegato;
    }

    /**
     * Sets autore allegato.
     *
     * @param autoreAllegato the autore allegato
     */
    public void setAutoreAllegato(String autoreAllegato) {
        this.autoreAllegato = autoreAllegato;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PubDocIstanzaDTO that = (PubDocIstanzaDTO) o;
        return Objects.equals(idAllegatoIstanza, that.idAllegatoIstanza) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(flgRiservato, that.flgRiservato) && Objects.equals(codAllegato, that.codAllegato) && Objects.equals(nomeAllegato, that.nomeAllegato) && Objects.equals(dimensioneUpload, that.dimensioneUpload) && Objects.equals(flgCancellato, that.flgCancellato) && Objects.equals(indFirma, that.indFirma) && Objects.equals(note, that.note) && Objects.equals(idAllegatoPadre, that.idAllegatoPadre) && Objects.equals(codAllegatoPadre, that.codAllegatoPadre) && Objects.equals(flgDaPubblicare, that.flgDaPubblicare) && Objects.equals(dataPubblicazione, that.dataPubblicazione) && Objects.equals(numProtocolloAllegato, that.numProtocolloAllegato) && Objects.equals(dataProtocolloAllegato, that.dataProtocolloAllegato) && Objects.equals(urlDocumento, that.urlDocumento) && Objects.equals(titoloAllegato, that.titoloAllegato) && Objects.equals(autoreAllegato, that.autoreAllegato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idAllegatoIstanza, idIstanza, flgRiservato, codAllegato, nomeAllegato, dimensioneUpload, flgCancellato, indFirma, note, idAllegatoPadre, codAllegatoPadre, flgDaPubblicare, dataPubblicazione, numProtocolloAllegato, dataProtocolloAllegato, urlDocumento, titoloAllegato, autoreAllegato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "PubDocIstanzaDTO {\n" +
                "         idAllegatoIstanza:" + idAllegatoIstanza +
                ",\n         idIstanza:" + idIstanza +
                ",\n         flgRiservato:" + flgRiservato +
                ",\n         codAllegato:'" + codAllegato + "'" +
                ",\n         nomeAllegato:'" + nomeAllegato + "'" +
                ",\n         dimensioneUpload:" + dimensioneUpload +
                ",\n         flgCancellato:" + flgCancellato +
                ",\n         indFirma:" + indFirma +
                ",\n         note:'" + note + "'" +
                ",\n         idAllegatoPadre:" + idAllegatoPadre +
                ",\n         codAllegatoPadre:'" + codAllegatoPadre + "'" +
                ",\n         flgDaPubblicare:" + flgDaPubblicare +
                ",\n         dataPubblicazione:" + dataPubblicazione +
                ",\n         numProtocolloAllegato:'" + numProtocolloAllegato + "'" +
                ",\n         dataProtocolloAllegato:" + dataProtocolloAllegato +
                ",\n         urlDocumento:'" + urlDocumento + "'" +
                ",\n         titoloAllegato:'" + titoloAllegato + "'" +
                ",\n         autoreAllegato:'" + autoreAllegato + "'" +
                "}\n";
    }

}
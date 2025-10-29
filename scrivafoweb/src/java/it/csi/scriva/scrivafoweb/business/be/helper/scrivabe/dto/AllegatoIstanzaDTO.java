/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Allegato istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegatoIstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_allegato_istanza")
    private Long idAllegatoIstanza;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_tipologia_allegato")
    private Long idTipologiaAllegato;

    @JsonProperty("id_tipo_integra_allegato")
    private Long idTipoIntegraAllegato;

    @JsonProperty("uuid_index")
    private String uuidIndex;

    @JsonProperty("flg_riservato")
    private Boolean flgRiservato;

    @JsonProperty("cod_allegato")
    private String codAllegato;

    @JsonProperty("nome_allegato")
    private String nomeAllegato;

    @JsonProperty("dimensione_upload")
    private Long dimensioneUpload;

    @JsonProperty("data_upload")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dataUpload;

    @JsonProperty("data_integrazione")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dataIntegrazione;

    @JsonProperty("data_cancellazione")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dataCancellazione;

    @JsonProperty("flg_cancellato")
    private Boolean flgCancellato;

    @JsonProperty("ind_firma")
    private Integer indFirma;

    @JsonProperty("note")
    private String note;

    @JsonProperty("id_istanza_attore")
    private Long idIstanzaAttore;

    @JsonProperty("id_funzionario")
    private Long idFunzionario;

    @JsonProperty("id_classe_allegato")
    private Long idClasseAllegato;

    @JsonProperty("id_allegato_istanza_padre")
    private Long idAllegatoIstanzaPadre;

    @JsonProperty("flg_da_pubblicare")
    private Boolean flgDaPubblicare;

    @JsonProperty("data_pubblicazione")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dataPubblicazione;

    @JsonProperty("num_protocollo_allegato")
    private String numProtocolloAllegato;

    @JsonProperty("data_protocollo_allegato")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dataProtocolloAllegato;

    @JsonProperty("url_doc")
    private String urlDoc;

    @JsonProperty("id_istanza_osservazione")
    private Long idIstanzaOsservazione;

    @JsonProperty("num_atto")
    private String numAtto;

    @JsonProperty("data_atto")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataAtto;

    @JsonProperty("titolo_allegato")
    private String titoloAllegato;

    @JsonProperty("autore_allegato")
    private String autoreAllegato;

    @JsonProperty("data_invio_esterno")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dataInvioEsterno;

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
     * Gets id tipologia allegato.
     *
     * @return the id tipologia allegato
     */
    public Long getIdTipologiaAllegato() {
        return idTipologiaAllegato;
    }

    /**
     * Sets id tipologia allegato.
     *
     * @param idTipologiaAllegato the id tipologia allegato
     */
    public void setIdTipologiaAllegato(Long idTipologiaAllegato) {
        this.idTipologiaAllegato = idTipologiaAllegato;
    }

    /**
     * Gets id tipo integra allegato.
     *
     * @return the id tipo integra allegato
     */
    public Long getIdTipoIntegraAllegato() {
        return idTipoIntegraAllegato;
    }

    /**
     * Sets id tipo integra allegato.
     *
     * @param idTipoIntegraAllegato the id tipo integra allegato
     */
    public void setIdTipoIntegraAllegato(Long idTipoIntegraAllegato) {
        this.idTipoIntegraAllegato = idTipoIntegraAllegato;
    }

    /**
     * Gets uuid index.
     *
     * @return the uuid index
     */
    public String getUuidIndex() {
        return uuidIndex;
    }

    /**
     * Sets uuid index.
     *
     * @param uuidIndex the uuid index
     */
    public void setUuidIndex(String uuidIndex) {
        this.uuidIndex = uuidIndex;
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
     * Gets data upload.
     *
     * @return the data upload
     */
    public Timestamp getDataUpload() {
        return dataUpload;
    }

    /**
     * Sets data upload.
     *
     * @param dataUpload the data upload
     */
    public void setDataUpload(Timestamp dataUpload) {
        this.dataUpload = dataUpload;
    }

    /**
     * Gets data integrazione.
     *
     * @return the data integrazione
     */
    public Timestamp getDataIntegrazione() {
        return dataIntegrazione;
    }

    /**
     * Sets data integrazione.
     *
     * @param dataIntegrazione the data integrazione
     */
    public void setDataIntegrazione(Timestamp dataIntegrazione) {
        this.dataIntegrazione = dataIntegrazione;
    }

    /**
     * Gets data cancellazione.
     *
     * @return the data cancellazione
     */
    public Timestamp getDataCancellazione() {
        return dataCancellazione;
    }

    /**
     * Sets data cancellazione.
     *
     * @param dataCancellazione the data cancellazione
     */
    public void setDataCancellazione(Timestamp dataCancellazione) {
        this.dataCancellazione = dataCancellazione;
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
     * Gets id istanza attore.
     *
     * @return the id istanza attore
     */
    public Long getIdIstanzaAttore() {
        return idIstanzaAttore;
    }

    /**
     * Sets id istanza attore.
     *
     * @param idIstanzaAttore the id istanza attore
     */
    public void setIdIstanzaAttore(Long idIstanzaAttore) {
        this.idIstanzaAttore = idIstanzaAttore;
    }

    /**
     * Gets id funzionario.
     *
     * @return the id funzionario
     */
    public Long getIdFunzionario() {
        return idFunzionario;
    }

    /**
     * Sets id funzionario.
     *
     * @param idFunzionario the id funzionario
     */
    public void setIdFunzionario(Long idFunzionario) {
        this.idFunzionario = idFunzionario;
    }

    /**
     * Gets id classe allegato.
     *
     * @return the id classe allegato
     */
    public Long getIdClasseAllegato() {
        return idClasseAllegato;
    }

    /**
     * Sets id classe allegato.
     *
     * @param idClasseAllegato the id classe allegato
     */
    public void setIdClasseAllegato(Long idClasseAllegato) {
        this.idClasseAllegato = idClasseAllegato;
    }

    /**
     * Gets id allegato istanza padre.
     *
     * @return the id allegato istanza padre
     */
    public Long getIdAllegatoIstanzaPadre() {
        return idAllegatoIstanzaPadre;
    }

    /**
     * Sets id allegato istanza padre.
     *
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     */
    public void setIdAllegatoIstanzaPadre(Long idAllegatoIstanzaPadre) {
        this.idAllegatoIstanzaPadre = idAllegatoIstanzaPadre;
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
     * Gets url doc.
     *
     * @return the url doc
     */
    public String getUrlDoc() {
        return urlDoc;
    }

    /**
     * Sets url doc.
     *
     * @param urlDoc the url doc
     */
    public void setUrlDoc(String urlDoc) {
        this.urlDoc = urlDoc;
    }

    /**
     * Gets id istanza osservazione.
     *
     * @return the id istanza osservazione
     */
    public Long getIdIstanzaOsservazione() {
        return idIstanzaOsservazione;
    }

    /**
     * Sets id istanza osservazione.
     *
     * @param idIstanzaOsservazione the id istanza osservazione
     */
    public void setIdIstanzaOsservazione(Long idIstanzaOsservazione) {
        this.idIstanzaOsservazione = idIstanzaOsservazione;
    }

    /**
     * Gets num atto.
     *
     * @return the num atto
     */
    public String getNumAtto() {
        return numAtto;
    }

    /**
     * Sets num atto.
     *
     * @param numAtto the num atto
     */
    public void setNumAtto(String numAtto) {
        this.numAtto = numAtto;
    }

    /**
     * Gets data atto.
     *
     * @return the data atto
     */
    public Date getDataAtto() {
        return dataAtto;
    }

    /**
     * Sets data atto.
     *
     * @param dataAtto the data atto
     */
    public void setDataAtto(Date dataAtto) {
        this.dataAtto = dataAtto;
    }

    public String getTitoloAllegato() {
        return titoloAllegato;
    }

    public void setTitoloAllegato(String titoloAllegato) {
        this.titoloAllegato = titoloAllegato;
    }

    public String getAutoreAllegato() {
        return autoreAllegato;
    }

    public void setAutoreAllegato(String autoreAllegato) {
        this.autoreAllegato = autoreAllegato;
    }

    public Timestamp getDataInvioEsterno() {
        return dataInvioEsterno;
    }

    public void setDataInvioEsterno(Timestamp dataInvioEsterno) {
        this.dataInvioEsterno = dataInvioEsterno;
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
        AllegatoIstanzaDTO that = (AllegatoIstanzaDTO) o;
        return Objects.equals(idAllegatoIstanza, that.idAllegatoIstanza) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idTipologiaAllegato, that.idTipologiaAllegato) && Objects.equals(idTipoIntegraAllegato, that.idTipoIntegraAllegato) && Objects.equals(uuidIndex, that.uuidIndex) && Objects.equals(flgRiservato, that.flgRiservato) && Objects.equals(codAllegato, that.codAllegato) && Objects.equals(nomeAllegato, that.nomeAllegato) && Objects.equals(dimensioneUpload, that.dimensioneUpload) && Objects.equals(dataUpload, that.dataUpload) && Objects.equals(dataIntegrazione, that.dataIntegrazione) && Objects.equals(dataCancellazione, that.dataCancellazione) && Objects.equals(flgCancellato, that.flgCancellato) && Objects.equals(indFirma, that.indFirma) && Objects.equals(note, that.note) && Objects.equals(idIstanzaAttore, that.idIstanzaAttore) && Objects.equals(idFunzionario, that.idFunzionario) && Objects.equals(idClasseAllegato, that.idClasseAllegato) && Objects.equals(idAllegatoIstanzaPadre, that.idAllegatoIstanzaPadre) && Objects.equals(flgDaPubblicare, that.flgDaPubblicare) && Objects.equals(dataPubblicazione, that.dataPubblicazione) && Objects.equals(numProtocolloAllegato, that.numProtocolloAllegato) && Objects.equals(dataProtocolloAllegato, that.dataProtocolloAllegato) && Objects.equals(urlDoc, that.urlDoc) && Objects.equals(idIstanzaOsservazione, that.idIstanzaOsservazione) && Objects.equals(numAtto, that.numAtto) && Objects.equals(dataAtto, that.dataAtto) && Objects.equals(titoloAllegato, that.titoloAllegato) && Objects.equals(autoreAllegato, that.autoreAllegato) && Objects.equals(dataInvioEsterno, that.dataInvioEsterno);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idAllegatoIstanza, idIstanza, idTipologiaAllegato, idTipoIntegraAllegato, uuidIndex, flgRiservato, codAllegato, nomeAllegato, dimensioneUpload, dataUpload, dataIntegrazione, dataCancellazione, flgCancellato, indFirma, note, idIstanzaAttore, idFunzionario, idClasseAllegato, idAllegatoIstanzaPadre, flgDaPubblicare, dataPubblicazione, numProtocolloAllegato, dataProtocolloAllegato, urlDoc, idIstanzaOsservazione, numAtto, dataAtto, titoloAllegato, autoreAllegato, dataInvioEsterno);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "AllegatoIstanzaDTO {\n" +
                "         idAllegatoIstanza:" + idAllegatoIstanza +
                ",\n         idIstanza:" + idIstanza +
                ",\n         idTipologiaAllegato:" + idTipologiaAllegato +
                ",\n         idTipoIntegraAllegato:" + idTipoIntegraAllegato +
                ",\n         uuidIndex:'" + uuidIndex + "'" +
                ",\n         flgRiservato:" + flgRiservato +
                ",\n         codAllegato:'" + codAllegato + "'" +
                ",\n         nomeAllegato:'" + nomeAllegato + "'" +
                ",\n         dimensioneUpload:" + dimensioneUpload +
                ",\n         dataUpload:" + dataUpload +
                ",\n         dataIntegrazione:" + dataIntegrazione +
                ",\n         dataCancellazione:" + dataCancellazione +
                ",\n         flgCancellato:" + flgCancellato +
                ",\n         indFirma:" + indFirma +
                ",\n         note:'" + note + "'" +
                ",\n         idIstanzaAttore:" + idIstanzaAttore +
                ",\n         idFunzionario:" + idFunzionario +
                ",\n         idClasseAllegato:" + idClasseAllegato +
                ",\n         idAllegatoIstanzaPadre:" + idAllegatoIstanzaPadre +
                ",\n         flgDaPubblicare:" + flgDaPubblicare +
                ",\n         dataPubblicazione:" + dataPubblicazione +
                ",\n         numProtocolloAllegato:'" + numProtocolloAllegato + "'" +
                ",\n         dataProtocolloAllegato:" + dataProtocolloAllegato +
                ",\n         urlDoc:'" + urlDoc + "'" +
                ",\n         idIstanzaOsservazione:" + idIstanzaOsservazione +
                ",\n         numAtto:'" + numAtto + "'" +
                ",\n         dataAtto:" + dataAtto +
                ",\n         titoloAllegato:'" + titoloAllegato + "'" +
                ",\n         autoreAllegato:'" + autoreAllegato + "'" +
                ",\n         dataInvioEsterno:" + dataInvioEsterno +
                super.toString() +
                "}\n";
    }

}
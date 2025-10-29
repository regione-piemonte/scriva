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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Stato istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatoIstanzaDTO implements Serializable {

    @JsonProperty("id_stato_istanza")
    private Long idStatoIstanza;

    @JsonProperty("codice_stato_istanza")
    private String codiceStatoIstanza;

    @JsonProperty("descrizione_stato_istanza")
    private String descrizioneStatoIstanza;

    @JsonProperty("flg_storico_istanza")
    private Boolean flgStoricoIstanza;

    @JsonProperty("ind_visibile")
    private String indVisibile;

    @JsonProperty("des_estesa_stato_istanza")
    private String desEstesaStatoIstanza;

    @JsonProperty("label_stato")
    private String labelStato;

    @JsonIgnore
    private String pswRuolo;

    @JsonIgnore
    private Boolean flgAggiornaOggetto;

    @JsonIgnore
    private String indRicercaOggetto;

    @JsonIgnore
    private String indAggiornaOggetto;

    /**
     * Gets id stato istanza.
     *
     * @return the id stato istanza
     */
    public Long getIdStatoIstanza() {
        return idStatoIstanza;
    }

    /**
     * Sets id stato istanza.
     *
     * @param idStatoIstanza the id stato istanza
     */
    public void setIdStatoIstanza(Long idStatoIstanza) {
        this.idStatoIstanza = idStatoIstanza;
    }

    /**
     * Gets codice stato istanza.
     *
     * @return the codice stato istanza
     */
    public String getCodiceStatoIstanza() {
        return codiceStatoIstanza;
    }

    /**
     * Sets codice stato istanza.
     *
     * @param codiceStatoIstanza the codice stato istanza
     */
    public void setCodiceStatoIstanza(String codiceStatoIstanza) {
        this.codiceStatoIstanza = codiceStatoIstanza;
    }

    /**
     * Gets descrizione stato istanza.
     *
     * @return the descrizione stato istanza
     */
    public String getDescrizioneStatoIstanza() {
        return descrizioneStatoIstanza;
    }

    /**
     * Sets descrizione stato istanza.
     *
     * @param descrizioneStatoIstanza the descrizione stato istanza
     */
    public void setDescrizioneStatoIstanza(String descrizioneStatoIstanza) {
        this.descrizioneStatoIstanza = descrizioneStatoIstanza;
    }

    /**
     * Gets flg storico istanza.
     *
     * @return the flg storico istanza
     */
    public Boolean getFlgStoricoIstanza() {
        return flgStoricoIstanza;
    }

    /**
     * Sets flg storico istanza.
     *
     * @param flgStoricoIstanza the flg storico istanza
     */
    public void setFlgStoricoIstanza(Boolean flgStoricoIstanza) {
        this.flgStoricoIstanza = flgStoricoIstanza;
    }

    /**
     * Gets ind visibile.
     *
     * @return the ind visibile
     */
    public String getIndVisibile() {
        return indVisibile;
    }

    /**
     * Sets ind visibile.
     *
     * @param indVisibile the ind visibile
     */
    public void setIndVisibile(String indVisibile) {
        this.indVisibile = indVisibile;
    }

    /**
     * Gets des estesa stato istanza.
     *
     * @return the des estesa stato istanza
     */
    public String getDesEstesaStatoIstanza() {
        return desEstesaStatoIstanza;
    }

    /**
     * Sets des estesa stato istanza.
     *
     * @param desEstesaStatoIstanza the des estesa stato istanza
     */
    public void setDesEstesaStatoIstanza(String desEstesaStatoIstanza) {
        this.desEstesaStatoIstanza = desEstesaStatoIstanza;
    }

    /**
     * Gets label stato.
     *
     * @return the label stato
     */
    public String getLabelStato() {
        return labelStato;
    }

    /**
     * Sets label stato.
     *
     * @param labelStato the label stato
     */
    public void setLabelStato(String labelStato) {
        this.labelStato = labelStato;
    }

    /**
     * Gets psw ruolo.
     *
     * @return the psw ruolo
     */
    public String getPswRuolo() {
        return pswRuolo;
    }

    /**
     * Sets psw ruolo.
     *
     * @param pswRuolo the psw ruolo
     */
    public void setPswRuolo(String pswRuolo) {
        this.pswRuolo = pswRuolo;
    }

    /**
     * Gets flg aggiorna oggetto.
     *
     * @return the flg aggiorna oggetto
     */
    public Boolean getFlgAggiornaOggetto() {
        return flgAggiornaOggetto;
    }

    /**
     * Sets flg aggiorna oggetto.
     *
     * @param flgAggiornaOggetto the flg aggiorna oggetto
     */
    public void setFlgAggiornaOggetto(Boolean flgAggiornaOggetto) {
        this.flgAggiornaOggetto = flgAggiornaOggetto;
    }

    /**
     * Gets ind ricerca oggetto.
     *
     * @return the ind ricerca oggetto
     */
    public String getIndRicercaOggetto() {
        return indRicercaOggetto;
    }

    /**
     * Sets ind ricerca oggetto.
     *
     * @param indRicercaOggetto the ind ricerca oggetto
     */
    public void setIndRicercaOggetto(String indRicercaOggetto) {
        this.indRicercaOggetto = indRicercaOggetto;
    }

    /**
     * Gets ind aggiorna oggetto.
     *
     * @return the ind aggiorna oggetto
     */
    public String getIndAggiornaOggetto() {
        return indAggiornaOggetto;
    }

    /**
     * Sets ind aggiorna oggetto.
     *
     * @param indAggiornaOggetto the ind aggiorna oggetto
     */
    public void setIndAggiornaOggetto(String indAggiornaOggetto) {
        this.indAggiornaOggetto = indAggiornaOggetto;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatoIstanzaDTO that = (StatoIstanzaDTO) o;
        return Objects.equals(idStatoIstanza, that.idStatoIstanza) && 
        Objects.equals(codiceStatoIstanza, that.codiceStatoIstanza) && 
        Objects.equals(descrizioneStatoIstanza, that.descrizioneStatoIstanza) && 
        Objects.equals(flgStoricoIstanza, that.flgStoricoIstanza) && 
        Objects.equals(indVisibile, that.indVisibile) && 
        Objects.equals(desEstesaStatoIstanza, that.desEstesaStatoIstanza) && 
        Objects.equals(labelStato, that.labelStato) && 
        Objects.equals(pswRuolo, that.pswRuolo) && 
        Objects.equals(flgAggiornaOggetto, that.flgAggiornaOggetto) && 
        Objects.equals(indRicercaOggetto, that.indRicercaOggetto) &&
        Objects.equals(indAggiornaOggetto, that.indAggiornaOggetto);
    }



    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idStatoIstanza, codiceStatoIstanza, descrizioneStatoIstanza, flgStoricoIstanza, 
        indVisibile, desEstesaStatoIstanza, labelStato, pswRuolo, flgAggiornaOggetto, indRicercaOggetto, indAggiornaOggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatoIstanzaDTO {\n");
        sb.append("         idStatoIstanza:").append(idStatoIstanza);
        sb.append(",\n         codiceStatoIstanza:'").append(codiceStatoIstanza).append("'");
        sb.append(",\n         descrizioneStatoIstanza:'").append(descrizioneStatoIstanza).append("'");
        sb.append(",\n         flgStoricoIstanza:").append(flgStoricoIstanza);
        sb.append(",\n         indVisibile:'").append(indVisibile).append("'");
        sb.append(",\n         desEstesaStatoIstanza:'").append(desEstesaStatoIstanza).append("'");
        sb.append(",\n         labelStato:'").append(labelStato).append("'");
        sb.append(",\n         pswRuolo:'").append(pswRuolo).append("'");
        sb.append(",\n         flgAggiornaOggetto:").append(flgAggiornaOggetto);
        sb.append(",\n         indRicercaOggetto:'").append(indRicercaOggetto).append("'");
        sb.append(",\n         indAggiornaOggetto:'").append(indAggiornaOggetto).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}
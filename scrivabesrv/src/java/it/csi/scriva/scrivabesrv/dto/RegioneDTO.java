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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * The type Regione dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegioneDTO implements Serializable {

    @JsonProperty("id_regione")
    private Long idRegione;

    @JsonProperty("cod_regione")
    private String codRegione;

    @JsonProperty("denom_regione")
    private String denomRegione;

    @JsonProperty("id_nazione")
    private Long idNazione;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    /**
     * Gets id regione.
     *
     * @return the id regione
     */
    public Long getIdRegione() {
        return idRegione;
    }

    /**
     * Sets id regione.
     *
     * @param idRegione the id regione
     */
    public void setIdRegione(Long idRegione) {
        this.idRegione = idRegione;
    }

    /**
     * Gets cod regione.
     *
     * @return the cod regione
     */
    public String getCodRegione() {
        return codRegione;
    }

    /**
     * Sets cod regione.
     *
     * @param codRegione the cod regione
     */
    public void setCodRegione(String codRegione) {
        this.codRegione = codRegione;
    }

    /**
     * Gets denom regione.
     *
     * @return the denom regione
     */
    public String getDenomRegione() {
        return denomRegione;
    }

    /**
     * Sets denom regione.
     *
     * @param denomRegione the denom regione
     */
    public void setDenomRegione(String denomRegione) {
        this.denomRegione = denomRegione;
    }

    /**
     * Gets id nazione.
     *
     * @return the id nazione
     */
    public Long getIdNazione() {
        return idNazione;
    }

    /**
     * Sets id nazione.
     *
     * @param idNazione the id nazione
     */
    public void setIdNazione(Long idNazione) {
        this.idNazione = idNazione;
    }

    /**
     * Gets data inizio validita.
     *
     * @return the data inizio validita
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets data inizio validita.
     *
     * @param dataInizioValidita the data inizio validita
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * Gets data fine validita.
     *
     * @return the data fine validita
     */
    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets data fine validita.
     *
     * @param dataFineValidita the data fine validita
     */
    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegioneDTO that = (RegioneDTO) o;
        return Objects.equals(idRegione, that.idRegione) && Objects.equals(codRegione, that.codRegione) && Objects.equals(denomRegione, that.denomRegione) && Objects.equals(idNazione, that.idNazione) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRegione, codRegione, denomRegione, idNazione, dataInizioValidita, dataFineValidita);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RegioneDTO {");
        sb.append("         idRegione:").append(idRegione);
        sb.append(",         codRegione:'").append(codRegione).append("'");
        sb.append(",         denomRegione:'").append(denomRegione).append("'");
        sb.append(",         idNazione:").append(idNazione);
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataFineValidita:").append(dataFineValidita);
        sb.append("}");
        return sb.toString();
    }
}
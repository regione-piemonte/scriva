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
 * The type Nazione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NazioneDTO implements Serializable {

    @JsonProperty("id_nazione")
    private Long idNazione;

    @JsonProperty("cod_istat_nazione")
    private String codIstatNazione;

    @JsonProperty("cod_belfiore_nazione")
    private String codBelfioreNazione;

    @JsonProperty("denom_nazione")
    private String denomNazione;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    @JsonProperty("dt_id_stato")
    private Long dtIdStato;

    @JsonProperty("dt_id_stato_prev")
    private Long dtIdStatoPrev;

    @JsonProperty("dt_id_stato_next")
    private Long dtIdStatoNext;

    @JsonProperty("id_origine")
    private Long idOrigine;

    @JsonProperty("cod_iso2")
    private String codIso2;

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
     * Gets cod istat nazione.
     *
     * @return the cod istat nazione
     */
    public String getCodIstatNazione() {
        return codIstatNazione;
    }

    /**
     * Sets cod istat nazione.
     *
     * @param codIstatNazione the cod istat nazione
     */
    public void setCodIstatNazione(String codIstatNazione) {
        this.codIstatNazione = codIstatNazione;
    }

    /**
     * Gets cod belfiore nazione.
     *
     * @return the cod belfiore nazione
     */
    public String getCodBelfioreNazione() {
        return codBelfioreNazione;
    }

    /**
     * Sets cod belfiore nazione.
     *
     * @param codBelfioreNazione the cod belfiore nazione
     */
    public void setCodBelfioreNazione(String codBelfioreNazione) {
        this.codBelfioreNazione = codBelfioreNazione;
    }

    /**
     * Gets denom nazione.
     *
     * @return the denom nazione
     */
    public String getDenomNazione() {
        return denomNazione;
    }

    /**
     * Sets denom nazione.
     *
     * @param denomNazione the denom nazione
     */
    public void setDenomNazione(String denomNazione) {
        this.denomNazione = denomNazione;
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

    /**
     * Gets dt id stato.
     *
     * @return the dt id stato
     */
    public Long getDtIdStato() {
        return dtIdStato;
    }

    /**
     * Sets dt id stato.
     *
     * @param dtIdStato the dt id stato
     */
    public void setDtIdStato(Long dtIdStato) {
        this.dtIdStato = dtIdStato;
    }

    /**
     * Gets dt id stato prev.
     *
     * @return the dt id stato prev
     */
    public Long getDtIdStatoPrev() {
        return dtIdStatoPrev;
    }

    /**
     * Sets dt id stato prev.
     *
     * @param dtIdStatoPrev the dt id stato prev
     */
    public void setDtIdStatoPrev(Long dtIdStatoPrev) {
        this.dtIdStatoPrev = dtIdStatoPrev;
    }

    /**
     * Gets dt id stato next.
     *
     * @return the dt id stato next
     */
    public Long getDtIdStatoNext() {
        return dtIdStatoNext;
    }

    /**
     * Sets dt id stato next.
     *
     * @param dtIdStatoNext the dt id stato next
     */
    public void setDtIdStatoNext(Long dtIdStatoNext) {
        this.dtIdStatoNext = dtIdStatoNext;
    }

    /**
     * Gets id origine.
     *
     * @return the id origine
     */
    public Long getIdOrigine() {
        return idOrigine;
    }

    /**
     * Sets id origine.
     *
     * @param idOrigine the id origine
     */
    public void setIdOrigine(Long idOrigine) {
        this.idOrigine = idOrigine;
    }

    /**
     * Gets cod iso 2.
     *
     * @return the cod iso 2
     */
    public String getCodIso2() {
        return codIso2;
    }

    /**
     * Sets cod iso 2.
     *
     * @param codIso2 the cod iso 2
     */
    public void setCodIso2(String codIso2) {
        this.codIso2 = codIso2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NazioneDTO that = (NazioneDTO) o;
        return Objects.equals(idNazione, that.idNazione) && Objects.equals(codIstatNazione, that.codIstatNazione) && Objects.equals(codBelfioreNazione, that.codBelfioreNazione) && Objects.equals(denomNazione, that.denomNazione) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita) && Objects.equals(dtIdStato, that.dtIdStato) && Objects.equals(dtIdStatoPrev, that.dtIdStatoPrev) && Objects.equals(dtIdStatoNext, that.dtIdStatoNext) && Objects.equals(idOrigine, that.idOrigine) && Objects.equals(codIso2, that.codIso2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNazione, codIstatNazione, codBelfioreNazione, denomNazione, dataInizioValidita, dataFineValidita, dtIdStato, dtIdStatoPrev, dtIdStatoNext, idOrigine, codIso2);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NazioneDTO {\n");
        sb.append("         idNazione:").append(idNazione);
        sb.append(",\n         codIstatNazione:'").append(codIstatNazione).append("'");
        sb.append(",\n         codBelfioreNazione:'").append(codBelfioreNazione).append("'");
        sb.append(",\n         denomNazione:'").append(denomNazione).append("'");
        sb.append(",\n         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",\n         dataFineValidita:").append(dataFineValidita);
        sb.append(",\n         dtIdStato:").append(dtIdStato);
        sb.append(",\n         dtIdStatoPrev:").append(dtIdStatoPrev);
        sb.append(",\n         dtIdStatoNext:").append(dtIdStatoNext);
        sb.append(",\n         idOrigine:").append(idOrigine);
        sb.append(",\n         codIso2:'").append(codIso2).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}
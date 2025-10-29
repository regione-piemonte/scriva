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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * The type Comune dto.
 */
public class ComuneDTO implements Serializable {

    @JsonProperty("id_comune")
    private Long idComune;

    @JsonProperty("cod_istat_comune")
    private String codIstatComune;

    @JsonProperty("cod_belfiore_comune")
    private String codBelfioreComune;

    @JsonProperty("denom_comune")
    private String denomComune;

    @JsonProperty("id_provincia")
    private Long idProvincia;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    @JsonProperty("dt_id_comune")
    private Long dtIdComune;

    @JsonProperty("dt_id_comune_prev")
    private Long dtIdComunePrev;

    @JsonProperty("dt_id_comune_next")
    private Long dtIdComuneNext;

    @JsonProperty("cap_comune")
    private String capComune;

    /**
     * Gets id comune.
     *
     * @return the id comune
     */
    public Long getIdComune() {
        return idComune;
    }

    /**
     * Sets id comune.
     *
     * @param idComune the id comune
     */
    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }

    /**
     * Gets cod istat comune.
     *
     * @return the cod istat comune
     */
    public String getCodIstatComune() {
        return codIstatComune;
    }

    /**
     * Sets cod istat comune.
     *
     * @param codIstatComune the cod istat comune
     */
    public void setCodIstatComune(String codIstatComune) {
        this.codIstatComune = codIstatComune;
    }

    /**
     * Gets cod belfiore comune.
     *
     * @return the cod belfiore comune
     */
    public String getCodBelfioreComune() {
        return codBelfioreComune;
    }

    /**
     * Sets cod belfiore comune.
     *
     * @param codBelfioreComune the cod belfiore comune
     */
    public void setCodBelfioreComune(String codBelfioreComune) {
        this.codBelfioreComune = codBelfioreComune;
    }

    /**
     * Gets denom comune.
     *
     * @return the denom comune
     */
    public String getDenomComune() {
        return denomComune;
    }

    /**
     * Sets denom comune.
     *
     * @param denomComune the denom comune
     */
    public void setDenomComune(String denomComune) {
        this.denomComune = denomComune;
    }

    /**
     * Gets id provincia.
     *
     * @return the id provincia
     */
    public Long getIdProvincia() {
        return idProvincia;
    }

    /**
     * Sets id provincia.
     *
     * @param idProvincia the id provincia
     */
    public void setIdProvincia(Long idProvincia) {
        this.idProvincia = idProvincia;
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
     * Gets dt id comune.
     *
     * @return the dt id comune
     */
    public Long getDtIdComune() {
        return dtIdComune;
    }

    /**
     * Sets dt id comune.
     *
     * @param dtIdComune the dt id comune
     */
    public void setDtIdComune(Long dtIdComune) {
        this.dtIdComune = dtIdComune;
    }

    /**
     * Gets dt id comune prev.
     *
     * @return the dt id comune prev
     */
    public Long getDtIdComunePrev() {
        return dtIdComunePrev;
    }

    /**
     * Sets dt id comune prev.
     *
     * @param dtIdComunePrev the dt id comune prev
     */
    public void setDtIdComunePrev(Long dtIdComunePrev) {
        this.dtIdComunePrev = dtIdComunePrev;
    }

    /**
     * Gets dt id comune next.
     *
     * @return the dt id comune next
     */
    public Long getDtIdComuneNext() {
        return dtIdComuneNext;
    }

    /**
     * Sets dt id comune next.
     *
     * @param dtIdComuneNext the dt id comune next
     */
    public void setDtIdComuneNext(Long dtIdComuneNext) {
        this.dtIdComuneNext = dtIdComuneNext;
    }

    /**
     * Gets cap comune.
     *
     * @return the cap comune
     */
    public String getCapComune() {
        return capComune;
    }

    /**
     * Sets cap comune.
     *
     * @param capComune the cap comune
     */
    public void setCapComune(String capComune) {
        this.capComune = capComune;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComuneDTO comuneDTO = (ComuneDTO) o;
        return Objects.equals(idComune, comuneDTO.idComune) && Objects.equals(codIstatComune, comuneDTO.codIstatComune) && Objects.equals(codBelfioreComune, comuneDTO.codBelfioreComune) && Objects.equals(denomComune, comuneDTO.denomComune) && Objects.equals(idProvincia, comuneDTO.idProvincia) && Objects.equals(dataInizioValidita, comuneDTO.dataInizioValidita) && Objects.equals(dataFineValidita, comuneDTO.dataFineValidita) && Objects.equals(dtIdComune, comuneDTO.dtIdComune) && Objects.equals(dtIdComunePrev, comuneDTO.dtIdComunePrev) && Objects.equals(dtIdComuneNext, comuneDTO.dtIdComuneNext) && Objects.equals(capComune, comuneDTO.capComune);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idComune, codIstatComune, codBelfioreComune, denomComune, idProvincia, dataInizioValidita, dataFineValidita, dtIdComune, dtIdComunePrev, dtIdComuneNext, capComune);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ComuneDTO {");
        sb.append("         idComune:").append(idComune);
        sb.append(",         codIstatComune:'").append(codIstatComune).append("'");
        sb.append(",         codBelfioreComune:'").append(codBelfioreComune).append("'");
        sb.append(",         denomComune:'").append(denomComune).append("'");
        sb.append(",         idProvincia:").append(idProvincia);
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataFineValidita:").append(dataFineValidita);
        sb.append(",         dtIdComune:").append(dtIdComune);
        sb.append(",         dtIdComunePrev:").append(dtIdComunePrev);
        sb.append(",         dtIdComuneNext:").append(dtIdComuneNext);
        sb.append(",         capComune:'").append(capComune).append("'");
        sb.append("}");
        return sb.toString();
    }
}
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
 * The type Provincia dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProvinciaDTO implements Serializable {

    @JsonProperty("id_provincia")
    private Long idProvincia;

    @JsonProperty("cod_provincia")
    private String codProvincia;

    @JsonProperty("denom_provincia")
    private String denomProvincia;

    @JsonProperty("sigla_provincia")
    private String siglaProvincia;

    @JsonProperty("id_regione")
    private Long idRegione;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    @JsonProperty("ordinamento_adempi_provincia")
    private Integer ordinamentoAdempiProvincia;

    @JsonProperty("flg_limitrofa")
    private Boolean flgLimitrofa;

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
     * Gets cod provincia.
     *
     * @return the cod provincia
     */
    public String getCodProvincia() {
        return codProvincia;
    }

    /**
     * Sets cod provincia.
     *
     * @param codProvincia the cod provincia
     */
    public void setCodProvincia(String codProvincia) {
        this.codProvincia = codProvincia;
    }

    /**
     * Gets denom provincia.
     *
     * @return the denom provincia
     */
    public String getDenomProvincia() {
        return denomProvincia;
    }

    /**
     * Sets denom provincia.
     *
     * @param denomProvincia the denom provincia
     */
    public void setDenomProvincia(String denomProvincia) {
        this.denomProvincia = denomProvincia;
    }

    /**
     * Gets sigla provincia.
     *
     * @return the sigla provincia
     */
    public String getSiglaProvincia() {
        return siglaProvincia;
    }

    /**
     * Sets sigla provincia.
     *
     * @param siglaProvincia the sigla provincia
     */
    public void setSiglaProvincia(String siglaProvincia) {
        this.siglaProvincia = siglaProvincia;
    }

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
     * Gets ordinamento adempi provincia.
     *
     * @return the ordinamento adempi provincia
     */
    public Integer getOrdinamentoAdempiProvincia() {
        return ordinamentoAdempiProvincia;
    }

    /**
     * Sets ordinamento adempi provincia.
     *
     * @param ordinamentoAdempiProvincia the ordinamento adempi provincia
     */
    public void setOrdinamentoAdempiProvincia(Integer ordinamentoAdempiProvincia) {
        this.ordinamentoAdempiProvincia = ordinamentoAdempiProvincia;
    }

    /**
     * Gets flg limitrofa.
     *
     * @return the flg limitrofa
     */
    public Boolean getFlgLimitrofa() {
        return flgLimitrofa;
    }

    /**
     * Sets flg limitrofa.
     *
     * @param flgLimitrofa the flg limitrofa
     */
    public void setFlgLimitrofa(Boolean flgLimitrofa) {
        this.flgLimitrofa = flgLimitrofa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProvinciaDTO that = (ProvinciaDTO) o;
        return Objects.equals(idProvincia, that.idProvincia) && Objects.equals(codProvincia, that.codProvincia) && Objects.equals(denomProvincia, that.denomProvincia) && Objects.equals(siglaProvincia, that.siglaProvincia) && Objects.equals(idRegione, that.idRegione) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita) && Objects.equals(ordinamentoAdempiProvincia, that.ordinamentoAdempiProvincia) && Objects.equals(flgLimitrofa, that.flgLimitrofa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProvincia, codProvincia, denomProvincia, siglaProvincia, idRegione, dataInizioValidita, dataFineValidita, ordinamentoAdempiProvincia, flgLimitrofa);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProvinciaDTO {\n");
        sb.append("         idProvincia:").append(idProvincia);
        sb.append(",\n         codProvincia:'").append(codProvincia).append("'");
        sb.append(",\n         denomProvincia:'").append(denomProvincia).append("'");
        sb.append(",\n         siglaProvincia:'").append(siglaProvincia).append("'");
        sb.append(",\n         idRegione:").append(idRegione);
        sb.append(",\n         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",\n         dataFineValidita:").append(dataFineValidita);
        sb.append(",\n         ordinamentoAdempiProvincia:").append(ordinamentoAdempiProvincia);
        sb.append(",\n         flgLimitrofa:").append(flgLimitrofa);
        sb.append("}\n");
        return sb.toString();
    }
}
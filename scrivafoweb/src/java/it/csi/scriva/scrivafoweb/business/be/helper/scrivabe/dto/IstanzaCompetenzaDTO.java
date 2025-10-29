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


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * The type Istanza competenza dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IstanzaCompetenzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("competenza_territorio")
    private CompetenzaTerritorioDTO competenzaTerritorio;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    @JsonProperty("flg_autorita_principale")
    private Boolean flgAutoritaPrincipale;

    @JsonProperty("flg_autorita_assegnata_bo")
    private Boolean flgAutoritaAssegnataBO;
    
    @JsonProperty("ind_assegnata_da_sistema")
    private String indAssegnataDaSistema;
    
    


	/**
     * Gets id istanza.
     *
     * @return idIstanza id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza idIstanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets competenza territorio.
     *
     * @return CompetenzaTerritorio competenza territorio
     */
    public CompetenzaTerritorioDTO getCompetenzaTerritorio() {
        return competenzaTerritorio;
    }

    /**
     * Sets competenza territorio.
     *
     * @param CompetenzaTerritorio CompetenzaTerritorio
     */
    public void setCompetenzaTerritorio(CompetenzaTerritorioDTO competenzaTerritorio) {
        this.competenzaTerritorio = competenzaTerritorio;
    }

    /**
     * Gets data inizio validita.
     *
     * @return dataInizioValidita data inizio validita
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets data inizio validita.
     *
     * @param dataInizioValidita dataInizioValidita
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * Gets data fine validita.
     *
     * @return dataFineValidita data fine validita
     */
    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets data fine validita.
     *
     * @param dataFineValidita dataFineValidita
     */
    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    /**
     * Gets flg autorita principale.
     *
     * @return flgAutoritaPrincipale flg autorita principale
     */
    public Boolean getFlgAutoritaPrincipale() {
        return flgAutoritaPrincipale;
    }

    /**
     * Sets flg autorita principale.
     *
     * @param flgAutoritaPrincipale flgAutoritaPrincipale
     */
    public void setFlgAutoritaPrincipale(Boolean flgAutoritaPrincipale) {
        this.flgAutoritaPrincipale = flgAutoritaPrincipale;
    }

    /**
     * Gets flg autorita assegnata bo.
     *
     * @return flgAutoritaAssegnataBO flg autorita assegnata bo
     */
    public Boolean getFlgAutoritaAssegnataBO() {
        return flgAutoritaAssegnataBO;
    }

    /**
     * Sets flg autorita assegnata bo.
     *
     * @param flgAutoritaAssegnataBO flgAutoritaAssegnataBO
     */
    public void setFlgAutoritaAssegnataBO(Boolean flgAutoritaAssegnataBO) {
        this.flgAutoritaAssegnataBO = flgAutoritaAssegnataBO;
    }
    
    /**
     * Gets the ind assegnata da sistema.
     *
     * @return the indAssegnataDaSistema
     */
	public String getIndAssegnataDaSistema() {
		return indAssegnataDaSistema;
	}

	/**
	 * Sets the ind assegnata da sistema.
	 *
	 * @param indAssegnataDaSistema the indAssegnataDaSistema to set
	 */
	public void setIndAssegnataDaSistema(String indAssegnataDaSistema) {
		this.indAssegnataDaSistema = indAssegnataDaSistema;
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
        IstanzaCompetenzaDTO that = (IstanzaCompetenzaDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(competenzaTerritorio, that.competenzaTerritorio) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita) && Objects.equals(flgAutoritaPrincipale, that.flgAutoritaPrincipale) && Objects.equals(flgAutoritaAssegnataBO, that.flgAutoritaAssegnataBO) && Objects.equals(indAssegnataDaSistema, that.indAssegnataDaSistema);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIstanza, competenzaTerritorio, dataInizioValidita, dataFineValidita, flgAutoritaPrincipale, flgAutoritaAssegnataBO, indAssegnataDaSistema);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaCompetenzaDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idIstanza:").append(idIstanza);
        sb.append(",         idCompetenzaTerritorio:").append(competenzaTerritorio);
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataFineValidita:").append(dataFineValidita);
        sb.append(",         flgAutoritaPrincipale:").append(flgAutoritaPrincipale);
        sb.append(",         flgAutoritaAssegnataBO:").append(flgAutoritaAssegnataBO);
        sb.append(",         indAssegnataDaSistema:").append(indAssegnataDaSistema);
        sb.append("}");
        return sb.toString();
    }
}
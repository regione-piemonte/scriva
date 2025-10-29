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
import java.util.List;
import java.util.Objects;

/**
 * The type Search notifiche dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchNotificheDTO implements Serializable {

	@JsonProperty("id_notifica_applicativa")
    private Long  idNotificaApplicativa;

    @JsonProperty("cod_stato_notifica")
    private String codStatoNotifica;

    @JsonProperty("num_istanza")
    private String numIstanza;

    @JsonProperty("id_adempimento")
    private List<Long> idAdempimentoList;

    @JsonProperty("data_inizio")
    private Date  dataInizio;

    @JsonProperty("data_fine")
    private Date dataFine;




    /**
	 * @return the idNotificaApplicativa
	 */
	public Long getIdNotificaApplicativa() {
		return idNotificaApplicativa;
	}

	/**
	 * @param idNotificaApplicativa the idNotificaApplicativa to set
	 */
	public void setIdNotificaApplicativa(Long idNotificaApplicativa) {
		this.idNotificaApplicativa = idNotificaApplicativa;
	}

	/**
     * Gets cod stato notifica.
     *
     * @return the cod stato notifica
     */
    public String getCodStatoNotifica() {
        return codStatoNotifica;
    }

    /**
     * Sets cod stato notifica.
     *
     * @param codStatoNotifica the cod stato notifica
     */
    public void setCodStatoNotifica(String codStatoNotifica) {
        this.codStatoNotifica = codStatoNotifica;
    }

    /**
     * Gets num istanza.
     *
     * @return the num istanza
     */
    public String getNumIstanza() {
        return numIstanza;
    }

    /**
     * Sets num istanza.
     *
     * @param numIstanza the num istanza
     */
    public void setNumIstanza(String numIstanza) {
        this.numIstanza = numIstanza;
    }

    /**
     * Gets id adempimento list.
     *
     * @return the id adempimento list
     */
    public List<Long> getIdAdempimentoList() {
        return idAdempimentoList;
    }

    /**
     * Sets id adempimento list.
     *
     * @param idAdempimentoList the id adempimento list
     */
    public void setIdAdempimentoList(List<Long> idAdempimentoList) {
        this.idAdempimentoList = idAdempimentoList;
    }

    /**
     * Gets data inizio.
     *
     * @return the data inizio
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * Sets data inizio.
     *
     * @param dataInizio the data inizio
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * Gets data fine.
     *
     * @return the data fine
     */
    public Date getDataFine() {
        return dataFine;
    }

    /**
     * Sets data fine.
     *
     * @param dataFine the data fine
     */
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchNotificheDTO that = (SearchNotificheDTO) o;
        return Objects.equals(idNotificaApplicativa, that.idNotificaApplicativa) && Objects.equals(codStatoNotifica, that.codStatoNotifica) && Objects.equals(numIstanza, that.numIstanza) && Objects.equals(idAdempimentoList, that.idAdempimentoList) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idNotificaApplicativa, codStatoNotifica, numIstanza, idAdempimentoList, dataInizio, dataFine);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "SearchNotificheDTO {\n" +
                "            idNotificaApplicativa:'" + idNotificaApplicativa + "'" +
                ",\n         codStatoNotifica:'" + codStatoNotifica + "'" +
                ",\n         numIstanza:'" + numIstanza + "'" +
                ",\n         idAdempimentoList:" + idAdempimentoList +
                ",\n         dataInizio:'" + dataInizio + "'" +
                ",\n         dataFine:'" + dataFine + "'" +
                "}\n";
    }
}
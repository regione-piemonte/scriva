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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Istanza Responsabile dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IstanzaResponsabileDTO extends BaseDTO implements Serializable {

	@JsonProperty("id_istanza_responsabile")
    private Long idIstanzaResponsabile;

	@JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_tipo_responsabile")
    private Long idTipoResponsabile;

    @JsonProperty("label_responsabile")
    private String labelResponsabile;

    @JsonProperty("nominativo_responsabile")
    private String nominativoResponsabile;

    @JsonProperty("recapito_responsabile")
    private String recapitoResponsabile;

    @JsonProperty("flg_riservato")
    private Boolean flgRiservato;

	/**
	 * Gets id istanza responsabile
	 *
	 * @return idIstanzaResponsabile
	 */
	public Long getIdIstanzaResponsabile() {
		return idIstanzaResponsabile;
	}

	/**
	 * Sets id istanza responsabile
	 *
	 * @param idIstanzaResponsabile
	 */
	public void setIdIstanzaResponsabile(Long idIstanzaResponsabile) {
		this.idIstanzaResponsabile = idIstanzaResponsabile;
	}

	/**
	 * Gets id istanza
	 *
	 * @return idIstanza
	 */
	public Long getIdIstanza() {
		return idIstanza;
	}

	/**
	 * Sets id istanza
	 *
	 * @param idIstanza
	 */
	public void setIdIstanza(Long idIstanza) {
		this.idIstanza = idIstanza;
	}

	/**
	 * Gets id tipo responsabile
	 *
	 * @return idTipoResponsabile
	 */
	public Long getIdTipoResponsabile() {
		return idTipoResponsabile;
	}

	/**
	 * Sets id tipo responsabile
	 *
	 * @param idTipoResponsabile
	 */
	public void setIdTipoResponsabile(Long idTipoResponsabile) {
		this.idTipoResponsabile = idTipoResponsabile;
	}

	/**
	 * Gets label responsabile
	 *
	 * @return
	 */
	public String getLabelResponsabile() {
		return labelResponsabile;
	}

	/**
	 * Sets label responsabile
	 *
	 * @param labelResponsabile
	 */
	public void setLabelResponsabile(String labelResponsabile) {
		this.labelResponsabile = labelResponsabile;
	}

	/**
	 * Gets nominativo responsabile
	 *
	 * @return nominativoResponsabile
	 */
	public String getNominativoResponsabile() {
		return nominativoResponsabile;
	}

	/**
	 * Sets nominativo responsabile
	 *
	 * @param nominativoResponsabile
	 */
	public void setNominativoResponsabile(String nominativoResponsabile) {
		this.nominativoResponsabile = nominativoResponsabile;
	}

	/**
	 * Gets recapito responsabile
	 *
	 * @return recapitoResponsabile
	 */
	public String getRecapitoResponsabile() {
		return recapitoResponsabile;
	}

	/**
	 * Sets recapito responsabile
	 *
	 * @param recapitoResponsabile
	 */
	public void setRecapitoResponsabile(String recapitoResponsabile) {
		this.recapitoResponsabile = recapitoResponsabile;
	}

	/**
	 * Gets flg riservato
	 *
	 * @return flgRiservato
	 */
	public Boolean getFlgRiservato() {
		return flgRiservato;
	}

	/**
	 * Sets flg riservato
	 *
	 * @param flgRiservato
	 */
	public void setFlgRiservato(Boolean flgRiservato) {
		this.flgRiservato = flgRiservato;
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
		IstanzaResponsabileDTO that = (IstanzaResponsabileDTO) o;
		return Objects.equals(idIstanzaResponsabile, that.idIstanzaResponsabile) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idTipoResponsabile, that.idTipoResponsabile) && Objects.equals(labelResponsabile, that.labelResponsabile) && Objects.equals(nominativoResponsabile, that.nominativoResponsabile) && Objects.equals(recapitoResponsabile, that.recapitoResponsabile) && Objects.equals(flgRiservato, that.flgRiservato);
	}

	/**
	 * @return int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), idIstanzaResponsabile, idIstanza, idTipoResponsabile, labelResponsabile, nominativoResponsabile, recapitoResponsabile, flgRiservato);
	}

	/**
	 * @return string
	 */
	@Override
	public String toString() {
		return "IstanzaResponsabileDTO {\n" +
				"         idIstanzaResponsabile:" + idIstanzaResponsabile +
				",\n         idIstanza:" + idIstanza +
				",\n         idTipoResponsabile:" + idTipoResponsabile +
				",\n         labelResponsabile:'" + labelResponsabile + "'" +
				",\n         nominativoResponsabile:'" + nominativoResponsabile + "'" +
				",\n         recapitoResponsabile:'" + recapitoResponsabile + "'" +
				",\n         flgRiservato:" + flgRiservato +
				"}\n";
	}
}
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

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Istanza osservazione extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoOsservazioneDTO extends BaseDTO implements Serializable {

	@JsonProperty("id_istanza_osservazione")
	private Long idIstanzaOsservazione;

	@JsonProperty("id_istanza")
	private Long idIstanza;

	@JsonProperty("id_stato_osservazione")
	private Long idStatoOsservazione;

	@JsonProperty("id_funzionario")
	private Long idFunzionario;

	@JsonProperty("cf_osservazione_ins")
	private String cfOsservazioneIns;

	@JsonProperty("data_osservazione")
	private Timestamp dataOsservazione;

	@JsonProperty("data_pubblicazione")
	private Timestamp dataPubblicazione;

	@JsonProperty("num_protocollo_osservazione")
	private String numProtocolloOsservazione;

	@JsonProperty("data_protocollo_osservazione")
	private Timestamp dataProtocolloOsservazione;
	
	//Aggiunti nuovi
	@JsonProperty("cod_pratica")
	private String codPratica;
	
	@JsonProperty("cod_stato_osservazione")
	private String codStatoOsservazione;
	
	@JsonProperty("des_stato_osservazione")
	private String desStatoOsservazione;
	
	@JsonProperty("des_adempimento")
	private String desAdempimento;
	
	@JsonProperty("den_oggetto")
	private String denOggetto;
	
	public String getCfOsservazioneIns() {
		return cfOsservazioneIns;
	}

	public void setCfOsservazioneIns(String cfOsservazioneIns) {
		this.cfOsservazioneIns = cfOsservazioneIns;
	}

	public Timestamp getDataOsservazione() {
		return dataOsservazione;
	}

	public void setDataOsservazione(Timestamp dataOsservazione) {
		this.dataOsservazione = dataOsservazione;
	}

	public Timestamp getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Timestamp dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public String getNumProtocolloOsservazione() {
		return numProtocolloOsservazione;
	}

	public void setNumProtocolloOsservazione(String numProtocolloOsservazione) {
		this.numProtocolloOsservazione = numProtocolloOsservazione;
	}

	public Timestamp getDataProtocolloOsservazione() {
		return dataProtocolloOsservazione;
	}

	public void setDataProtocolloOsservazione(Timestamp dataProtocolloOsservazione) {
		this.dataProtocolloOsservazione = dataProtocolloOsservazione;
	}

	public Long getIdIstanzaOsservazione() {
		return idIstanzaOsservazione;
	}

	public void setIdIstanzaOsservazione(Long idIstanzaOsservazione) {
		this.idIstanzaOsservazione = idIstanzaOsservazione;
	}

	public Long getIdIstanza() {
		return idIstanza;
	}

	public void setIdIstanza(Long idIstanza) {
		this.idIstanza = idIstanza;
	}

	public Long getIdStatoOsservazione() {
		return idStatoOsservazione;
	}

	public void setIdStatoOsservazione(Long idStatoOsservazione) {
		this.idStatoOsservazione = idStatoOsservazione;
	}

	public Long getIdFunzionario() {
		return idFunzionario;
	}

	public void setIdFunzionario(Long idFunzionario) {
		this.idFunzionario = idFunzionario;
	}

	


	public String getCodPratica() {
		return codPratica;
	}

	public void setCodPratica(String codPratica) {
		this.codPratica = codPratica;
	}

	public String getCodStatoOsservazione() {
		return codStatoOsservazione;
	}

	public void setCodStatoOsservazione(String codStatoOsservazione) {
		this.codStatoOsservazione = codStatoOsservazione;
	}

	public String getDesStatoOsservazione() {
		return desStatoOsservazione;
	}

	public void setDesStatoOsservazione(String desStatoOsservazione) {
		this.desStatoOsservazione = desStatoOsservazione;
	}

	public String getDesAdempimento() {
		return desAdempimento;
	}

	public void setDesAdempimento(String desAdempimento) {
		this.desAdempimento = desAdempimento;
	}

	public String getDenOggetto() {
		return denOggetto;
	}

	public void setDenOggetto(String denOggetto) {
		this.denOggetto = denOggetto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cfOsservazioneIns == null) ? 0 : cfOsservazioneIns.hashCode());
		result = prime * result + ((dataOsservazione == null) ? 0 : dataOsservazione.hashCode());
		result = prime * result + ((dataProtocolloOsservazione == null) ? 0 : dataProtocolloOsservazione.hashCode());
		result = prime * result + ((dataPubblicazione == null) ? 0 : dataPubblicazione.hashCode());
		result = prime * result + ((idFunzionario == null) ? 0 : idFunzionario.hashCode());
		result = prime * result + ((idIstanza == null) ? 0 : idIstanza.hashCode());
		result = prime * result + ((idIstanzaOsservazione == null) ? 0 : idIstanzaOsservazione.hashCode());
		result = prime * result + ((idStatoOsservazione == null) ? 0 : idStatoOsservazione.hashCode());
		result = prime * result + ((numProtocolloOsservazione == null) ? 0 : numProtocolloOsservazione.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OggettoOsservazioneDTO other = (OggettoOsservazioneDTO) obj;
		if (cfOsservazioneIns == null) {
			if (other.cfOsservazioneIns != null)
				return false;
		} else if (!cfOsservazioneIns.equals(other.cfOsservazioneIns))
			return false;
		if (dataOsservazione == null) {
			if (other.dataOsservazione != null)
				return false;
		} else if (!dataOsservazione.equals(other.dataOsservazione))
			return false;
		if (dataProtocolloOsservazione == null) {
			if (other.dataProtocolloOsservazione != null)
				return false;
		} else if (!dataProtocolloOsservazione.equals(other.dataProtocolloOsservazione))
			return false;
		if (dataPubblicazione == null) {
			if (other.dataPubblicazione != null)
				return false;
		} else if (!dataPubblicazione.equals(other.dataPubblicazione))
			return false;
		if (idFunzionario == null) {
			if (other.idFunzionario != null)
				return false;
		} else if (!idFunzionario.equals(other.idFunzionario))
			return false;
		if (idIstanza == null) {
			if (other.idIstanza != null)
				return false;
		} else if (!idIstanza.equals(other.idIstanza))
			return false;
		if (idIstanzaOsservazione == null) {
			if (other.idIstanzaOsservazione != null)
				return false;
		} else if (!idIstanzaOsservazione.equals(other.idIstanzaOsservazione))
			return false;
		if (idStatoOsservazione == null) {
			if (other.idStatoOsservazione != null)
				return false;
		} else if (!idStatoOsservazione.equals(other.idStatoOsservazione))
			return false;
		if (numProtocolloOsservazione == null) {
			if (other.numProtocolloOsservazione != null)
				return false;
		} else if (!numProtocolloOsservazione.equals(other.numProtocolloOsservazione))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("OggettoOsservazioneDTO {\n");
		sb.append(super.toString()).append("\n");
		sb.append(",\n         idIstanzaOsservazione:").append(idIstanzaOsservazione);
		sb.append(",\n         idIstanza:").append(idIstanza);
		sb.append(",\n         idStatoOsservazione:").append(idStatoOsservazione);
		sb.append(",\n         cfOsservazioneIns:'").append(cfOsservazioneIns).append("'");
		sb.append(",\n         dataOsservazione:").append(dataOsservazione);
		sb.append(",\n         dataPubblicazione:").append(dataPubblicazione);
		sb.append(",\n         numProtocolloOsservazione:'").append(numProtocolloOsservazione).append("'");
		sb.append(",\n         dataProtocolloOsservazione:").append(dataProtocolloOsservazione);
		sb.append("}\n");
		return sb.toString();
	}

}
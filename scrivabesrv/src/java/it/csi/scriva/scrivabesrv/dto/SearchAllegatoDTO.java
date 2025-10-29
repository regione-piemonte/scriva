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
import java.util.Objects;

/**
 * The type search Allegato dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchAllegatoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id_istanza")
    private Long idIstanza;

	@JsonProperty("id_osservazione")
	private Long idOsservazione;

	@JsonProperty("cod_istanza")
    private String codIstanza;

	@JsonProperty("cod_tipologia_allegato")
	private String codTipologiaAllegato;

    @JsonProperty("cod_categoria_allegato")
    private String codCategoriaAllegato;

    @JsonProperty("cod_classe_allegato")
    private String codClasseAllegato;

    @JsonProperty("flg_canc_logica")
    private String flgCancLogica;

    @JsonProperty("flg_link_documento")
    private String flgLinkDocumento;

    @JsonProperty("cod_allegato")
    private String codAllegato;

    @JsonProperty("sys")
    private Boolean system;

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
	 * Gets cod categoria allegato.
	 *
	 * @return the cod categoria allegato
	 */
	public String getCodCategoriaAllegato() {
        return codCategoriaAllegato;
    }

	/**
	 * Sets cod categoria allegato.
	 *
	 * @param codCategoriaAllegato the cod categoria allegato
	 */
	public void setCodCategoriaAllegato(String codCategoriaAllegato) {
        this.codCategoriaAllegato = codCategoriaAllegato;
    }

	/**
	 * Gets cod istanza.
	 *
	 * @return the cod istanza
	 */
	public String getCodIstanza() {
		return codIstanza;
	}

	/**
	 * Sets cod istanza.
	 *
	 * @param codIstanza the cod istanza
	 */
	public void setCodIstanza(String codIstanza) {
		this.codIstanza = codIstanza;
	}

	/**
	 * Gets cod tipologia allegato.
	 *
	 * @return the cod tipologia allegato
	 */
	public String getCodTipologiaAllegato() {
		return codTipologiaAllegato;
	}

	/**
	 * Sets cod tipologia allegato.
	 *
	 * @param codTipologiaAllegato the cod tipologia allegato
	 */
	public void setCodTipologiaAllegato(String codTipologiaAllegato) {
		this.codTipologiaAllegato = codTipologiaAllegato;
	}

	/**
	 * Gets cod classe allegato.
	 *
	 * @return the cod classe allegato
	 */
	public String getCodClasseAllegato() {
		return codClasseAllegato;
	}

	/**
	 * Sets cod classe allegato.
	 *
	 * @param codClasseAllegato the cod classe allegato
	 */
	public void setCodClasseAllegato(String codClasseAllegato) {
		this.codClasseAllegato = codClasseAllegato;
	}

	/**
	 * Gets flg canc logica.
	 *
	 * @return the flg canc logica
	 */
	public String getFlgCancLogica() {
		return flgCancLogica;
	}

	/**
	 * Sets flg canc logica.
	 *
	 * @param flgCancLogica the flg canc logica
	 */
	public void setFlgCancLogica(String flgCancLogica) {
		this.flgCancLogica = flgCancLogica;
	}

	/**
	 * Gets flg link documento.
	 *
	 * @return the flg link documento
	 */
	public String getFlgLinkDocumento() {
		return flgLinkDocumento;
	}

	/**
	 * Sets flg link documento.
	 *
	 * @param flgLinkDocumento the flg link documento
	 */
	public void setFlgLinkDocumento(String flgLinkDocumento) {
		this.flgLinkDocumento = flgLinkDocumento;
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
	 * Gets id osservazione.
	 *
	 * @return the id osservazione
	 */
	public Long getIdOsservazione() {
		return idOsservazione;
	}

	/**
	 * Sets id osservazione.
	 *
	 * @param idOsservazione the id osservazione
	 */
	public void setIdOsservazione(Long idOsservazione) {
		this.idOsservazione = idOsservazione;
	}

	/**
	 * Gets sys.
	 *
	 * @return the sys
	 */
	public Boolean getSys() {
		return system;
	}

	/**
	 * Sets sys.
	 *
	 * @param sys the sys
	 */
	public void setSys(Boolean sys) {
		this.system = sys;
	}

	/**
	 * @param o Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SearchAllegatoDTO that = (SearchAllegatoDTO) o;
		return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idOsservazione, that.idOsservazione) && Objects.equals(codIstanza, that.codIstanza) && Objects.equals(codTipologiaAllegato, that.codTipologiaAllegato) && Objects.equals(codCategoriaAllegato, that.codCategoriaAllegato) && Objects.equals(codClasseAllegato, that.codClasseAllegato) && Objects.equals(flgCancLogica, that.flgCancLogica) && Objects.equals(flgLinkDocumento, that.flgLinkDocumento) && Objects.equals(codAllegato, that.codAllegato) && Objects.equals(system, that.system);
	}

	/**
	 * @return int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(idIstanza, idOsservazione, codIstanza, codTipologiaAllegato, codCategoriaAllegato, codClasseAllegato, flgCancLogica, flgLinkDocumento, codAllegato, system);
	}

	/**
	 * @return string
	 */
	@Override
	public String toString() {
		return "SearchAllegatoDTO {\n" +
				"         idIstanza:" + idIstanza +
				",\n         idOsservazione:" + idOsservazione +
				",\n         codIstanza:'" + codIstanza + "'" +
				",\n         codTipologiaAllegato:'" + codTipologiaAllegato + "'" +
				",\n         codCategoriaAllegato:'" + codCategoriaAllegato + "'" +
				",\n         codClasseAllegato:'" + codClasseAllegato + "'" +
				",\n         flgCancLogica:'" + flgCancLogica + "'" +
				",\n         flgLinkDocumento:'" + flgLinkDocumento + "'" +
				",\n         codAllegato:'" + codAllegato + "'" +
				",\n         sys:" + system +
				"}\n";
	}

}
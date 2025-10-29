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
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Pub search dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubSearchDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_ambito")
    private Long idAmbito;

    @JsonProperty("id_tipo_adempimento")
    private Long idTipoAdempimento;

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("id_competenza_territorio")
    private Long idCompetenzaTerritorio;

    @JsonProperty("anno_presentazione_progetto")
    private Integer annoPresentazioneProgetto;

    @JsonProperty("minore_di")
    private Boolean minoreDi;

    @JsonProperty("maggiore_di")
    private Boolean maggioreDi;

    @JsonProperty("codice_pratica")
    private String codicePratica;

    @JsonProperty("denominazione_oggetto")
    private String denominazioneOggetto;

    @JsonProperty("sigla_provincia_oggetto")
    private String siglaProvinciaOggetto;

    @JsonProperty("cod_istat_comune_oggetto")
    private String codIstatComuneOggetto;

//    @JsonProperty("cod_stato_istanza")
//    private String codStatoIstanza;
    
    @JsonProperty("label_stato")
    private String labelStato;

    @JsonProperty("fase_pubblicazione")
    private String fasePubblicazione;

    @JsonProperty("cod_categoria_allegato")
    private String codCategoriaAllegato;

    // DOMANDA
    @JsonProperty("flg_infrastrutture_strategiche")
    private Boolean flgInfrastruttureStrategiche;

    @JsonProperty("flg_pnrr")
    private Boolean flgPnrr;

    @JsonProperty("flg_vinca")
    private Boolean flgVinca;

    @JsonProperty("cod_categoria_oggetto")
    private String codCategoriaOggetto;

    @JsonProperty("vincolo")
    private List<String> vincolo;

    //non piu' passato dal FE
    //@JsonProperty("des_esito_procedimento_statale")
    //private String desEsitoProcedimentoStatale;
    
    @JsonProperty("cod_stato_procedimento_statale")
    private String codStatoProcedStatale;


	public List<String> getVincolo() {
		return vincolo;
	}

	public void setVincolo(List<String> vincolo) {
		this.vincolo = vincolo;
	}

	public String getCodStatoProcedStatale() {
		return codStatoProcedStatale;
	}

	public void setCodStatoProcedStatale(String codStatoProcedStatale) {
		this.codStatoProcedStatale = codStatoProcedStatale;
	}

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
     * Gets id ambito.
     *
     * @return the id ambito
     */
    public Long getIdAmbito() {
        return idAmbito;
    }

    /**
     * Sets id ambito.
     *
     * @param idAmbito the id ambito
     */
    public void setIdAmbito(Long idAmbito) {
        this.idAmbito = idAmbito;
    }

    /**
     * Gets id tipo adempimento.
     *
     * @return the id tipo adempimento
     */
    public Long getIdTipoAdempimento() {
        return idTipoAdempimento;
    }

    /**
     * Sets id tipo adempimento.
     *
     * @param idTipoAdempimento the id tipo adempimento
     */
    public void setIdTipoAdempimento(Long idTipoAdempimento) {
        this.idTipoAdempimento = idTipoAdempimento;
    }

    /**
     * Gets id adempimento.
     *
     * @return the id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento the id adempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * Gets id competenza territorio.
     *
     * @return the id competenza territorio
     */
    public Long getIdCompetenzaTerritorio() {
        return idCompetenzaTerritorio;
    }

    /**
     * Sets id competenza territorio.
     *
     * @param idCompetenzaTerritorio the id competenza territorio
     */
    public void setIdCompetenzaTerritorio(Long idCompetenzaTerritorio) {
        this.idCompetenzaTerritorio = idCompetenzaTerritorio;
    }

    /**
     * Gets anno presentazione progetto.
     *
     * @return the anno presentazione progetto
     */
    public Integer getAnnoPresentazioneProgetto() {
        return annoPresentazioneProgetto;
    }

    /**
     * Sets anno presentazione progetto.
     *
     * @param annoPresentazioneProgetto the anno presentazione progetto
     */
    public void setAnnoPresentazioneProgetto(Integer annoPresentazioneProgetto) {
        this.annoPresentazioneProgetto = annoPresentazioneProgetto;
    }

    /**
     * Gets minore di.
     *
     * @return the minore di
     */
    public Boolean getMinoreDi() {
        return minoreDi;
    }

    /**
     * Sets minore di.
     *
     * @param minoreDi the minore di
     */
    public void setMinoreDi(Boolean minoreDi) {
        this.minoreDi = minoreDi;
    }

    /**
     * Gets maggiore di.
     *
     * @return the maggiore di
     */
    public Boolean getMaggioreDi() {
        return maggioreDi;
    }

    /**
     * Sets maggiore di.
     *
     * @param maggioreDi the maggiore di
     */
    public void setMaggioreDi(Boolean maggioreDi) {
        this.maggioreDi = maggioreDi;
    }

    /**
     * Gets codice pratica.
     *
     * @return the codice pratica
     */
    public String getCodicePratica() {
        return codicePratica;
    }

    /**
     * Sets codice pratica.
     *
     * @param codicePratica the codice pratica
     */
    public void setCodicePratica(String codicePratica) {
        this.codicePratica = codicePratica;
    }

    /**
     * Gets denominazione oggetto.
     *
     * @return the denominazione oggetto
     */
    public String getDenominazioneOggetto() {
        return denominazioneOggetto;
    }

    /**
     * Sets denominazione oggetto.
     *
     * @param denominazioneOggetto the denominazione oggetto
     */
    public void setDenominazioneOggetto(String denominazioneOggetto) {
        this.denominazioneOggetto = denominazioneOggetto;
    }

    /**
     * Gets sigla provincia oggetto.
     *
     * @return the sigla provincia oggetto
     */
    public String getSiglaProvinciaOggetto() {
        return siglaProvinciaOggetto;
    }

    /**
     * Sets sigla provincia oggetto.
     *
     * @param siglaProvinciaOggetto the sigla provincia oggetto
     */
    public void setSiglaProvinciaOggetto(String siglaProvinciaOggetto) {
        this.siglaProvinciaOggetto = siglaProvinciaOggetto;
    }

    /**
     * Gets cod istat comune oggetto.
     *
     * @return the cod istat comune oggetto
     */
    public String getCodIstatComuneOggetto() {
        return codIstatComuneOggetto;
    }

    /**
     * Sets cod istat comune oggetto.
     *
     * @param codIstatComuneOggetto the cod istat comune oggetto
     */
    public void setCodIstatComuneOggetto(String codIstatComuneOggetto) {
        this.codIstatComuneOggetto = codIstatComuneOggetto;
    }

//    /**
//     * Gets cod stato istanza.
//     *
//     * @return the cod stato istanza
//     */
//    public String getCodStatoIstanza() {
//        return codStatoIstanza;
//    }
//
//    /**
//     * Sets cod stato istanza.
//     *
//     * @param codStatoIstanza the cod stato istanza
//     */
//    public void setCodStatoIstanza(String codStatoIstanza) {
//        this.codStatoIstanza = codStatoIstanza;
//    }
    
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
     * Gets fase pubblicazione.
     *
     * @return the fase pubblicazione
     */
    public String getFasePubblicazione() {
        return fasePubblicazione;
    }


	/**
     * Sets fase pubblicazione.
     *
     * @param fasePubblicazione the fase pubblicazione
     */
    public void setFasePubblicazione(String fasePubblicazione) {
        this.fasePubblicazione = fasePubblicazione;
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
     * Gets flg infrastrutture strategiche.
     *
     * @return the flg infrastrutture strategiche
     */
    public Boolean getFlgInfrastruttureStrategiche() {
        return flgInfrastruttureStrategiche;
    }

    /**
     * Sets flg infrastrutture strategiche.
     *
     * @param flgInfrastruttureStrategiche the flg infrastrutture strategiche
     */
    public void setFlgInfrastruttureStrategiche(Boolean flgInfrastruttureStrategiche) {
        this.flgInfrastruttureStrategiche = flgInfrastruttureStrategiche;
    }

    /**
     * Gets flg pnrr.
     *
     * @return the flg pnrr
     */
    public Boolean getFlgPnrr() {
        return flgPnrr;
    }

    /**
     * Sets flg pnrr.
     *
     * @param flgPnrr the flg pnrr
     */
    public void setFlgPnrr(Boolean flgPnrr) {
        this.flgPnrr = flgPnrr;
    }

    /**
     * Gets flg vinca.
     *
     * @return the flg vinca
     */
    public Boolean getFlgVinca() {
        return flgVinca;
    }

    /**
     * Sets flg vinca.
     *
     * @param flgVinca the flg vinca
     */
    public void setFlgVinca(Boolean flgVinca) {
        this.flgVinca = flgVinca;
    }

    /**
     * Gets cod categoria oggetto.
     *
     * @return the cod categoria oggetto
     */
    public String getCodCategoriaOggetto() {
        return codCategoriaOggetto;
    }

    /**
     * Sets cod categoria oggetto.
     *
     * @param codCategoriaOggetto the cod categoria oggetto
     */
    public void setCodCategoriaOggetto(String codCategoriaOggetto) {
        this.codCategoriaOggetto = codCategoriaOggetto;
    }






	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((annoPresentazioneProgetto == null) ? 0 : annoPresentazioneProgetto.hashCode());
		result = prime * result + ((codCategoriaAllegato == null) ? 0 : codCategoriaAllegato.hashCode());
		result = prime * result + ((codCategoriaOggetto == null) ? 0 : codCategoriaOggetto.hashCode());
		result = prime * result + ((codIstatComuneOggetto == null) ? 0 : codIstatComuneOggetto.hashCode());
		//result = prime * result + ((codStatoIstanza == null) ? 0 : codStatoIstanza.hashCode());
		result = prime * result + ((labelStato == null) ? 0 : labelStato.hashCode());
		result = prime * result + ((codStatoProcedStatale == null) ? 0 : codStatoProcedStatale.hashCode());
		result = prime * result + ((codicePratica == null) ? 0 : codicePratica.hashCode());
		result = prime * result + ((denominazioneOggetto == null) ? 0 : denominazioneOggetto.hashCode());
		//result = prime * result + ((desEsitoProcedimentoStatale == null) ? 0 : desEsitoProcedimentoStatale.hashCode());
		result = prime * result + ((fasePubblicazione == null) ? 0 : fasePubblicazione.hashCode());
		result = prime * result
				+ ((flgInfrastruttureStrategiche == null) ? 0 : flgInfrastruttureStrategiche.hashCode());
		result = prime * result + ((flgPnrr == null) ? 0 : flgPnrr.hashCode());
		result = prime * result + ((flgVinca == null) ? 0 : flgVinca.hashCode());
		result = prime * result + ((idAdempimento == null) ? 0 : idAdempimento.hashCode());
		result = prime * result + ((idAmbito == null) ? 0 : idAmbito.hashCode());
		result = prime * result + ((idCompetenzaTerritorio == null) ? 0 : idCompetenzaTerritorio.hashCode());
		result = prime * result + ((idIstanza == null) ? 0 : idIstanza.hashCode());
		result = prime * result + ((idTipoAdempimento == null) ? 0 : idTipoAdempimento.hashCode());
		result = prime * result + ((maggioreDi == null) ? 0 : maggioreDi.hashCode());
		result = prime * result + ((minoreDi == null) ? 0 : minoreDi.hashCode());
		result = prime * result + ((siglaProvinciaOggetto == null) ? 0 : siglaProvinciaOggetto.hashCode());
		result = prime * result + ((vincolo == null) ? 0 : vincolo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PubSearchDTO other = (PubSearchDTO) obj;
		if (annoPresentazioneProgetto == null) {
			if (other.annoPresentazioneProgetto != null)
				return false;
		} else if (!annoPresentazioneProgetto.equals(other.annoPresentazioneProgetto))
			return false;
		if (codCategoriaAllegato == null) {
			if (other.codCategoriaAllegato != null)
				return false;
		} else if (!codCategoriaAllegato.equals(other.codCategoriaAllegato))
			return false;
		if (codCategoriaOggetto == null) {
			if (other.codCategoriaOggetto != null)
				return false;
		} else if (!codCategoriaOggetto.equals(other.codCategoriaOggetto))
			return false;
		if (codIstatComuneOggetto == null) {
			if (other.codIstatComuneOggetto != null)
				return false;
		} else if (!codIstatComuneOggetto.equals(other.codIstatComuneOggetto))
			return false;
//		if (codStatoIstanza == null) {
//			if (other.codStatoIstanza != null)
//				return false;
//		} else if (!codStatoIstanza.equals(other.codStatoIstanza))
//			return false;
		if (labelStato == null) {
			if (other.labelStato != null)
				return false;
		} else if (!labelStato.equals(other.labelStato))
			return false;
		if (codStatoProcedStatale == null) {
			if (other.codStatoProcedStatale != null)
				return false;
		} else if (!codStatoProcedStatale.equals(other.codStatoProcedStatale))
			return false;
		if (codicePratica == null) {
			if (other.codicePratica != null)
				return false;
		} else if (!codicePratica.equals(other.codicePratica))
			return false;
		if (denominazioneOggetto == null) {
			if (other.denominazioneOggetto != null)
				return false;
		} else if (!denominazioneOggetto.equals(other.denominazioneOggetto))
			return false;
//		if (desEsitoProcedimentoStatale == null) {
//			if (other.desEsitoProcedimentoStatale != null)
//				return false;
//		} else if (!desEsitoProcedimentoStatale.equals(other.desEsitoProcedimentoStatale))
//			return false;
		if (fasePubblicazione == null) {
			if (other.fasePubblicazione != null)
				return false;
		} else if (!fasePubblicazione.equals(other.fasePubblicazione))
			return false;
		if (flgInfrastruttureStrategiche == null) {
			if (other.flgInfrastruttureStrategiche != null)
				return false;
		} else if (!flgInfrastruttureStrategiche.equals(other.flgInfrastruttureStrategiche))
			return false;
		if (flgPnrr == null) {
			if (other.flgPnrr != null)
				return false;
		} else if (!flgPnrr.equals(other.flgPnrr))
			return false;
		if (flgVinca == null) {
			if (other.flgVinca != null)
				return false;
		} else if (!flgVinca.equals(other.flgVinca))
			return false;
		if (idAdempimento == null) {
			if (other.idAdempimento != null)
				return false;
		} else if (!idAdempimento.equals(other.idAdempimento))
			return false;
		if (idAmbito == null) {
			if (other.idAmbito != null)
				return false;
		} else if (!idAmbito.equals(other.idAmbito))
			return false;
		if (idCompetenzaTerritorio == null) {
			if (other.idCompetenzaTerritorio != null)
				return false;
		} else if (!idCompetenzaTerritorio.equals(other.idCompetenzaTerritorio))
			return false;
		if (idIstanza == null) {
			if (other.idIstanza != null)
				return false;
		} else if (!idIstanza.equals(other.idIstanza))
			return false;
		if (idTipoAdempimento == null) {
			if (other.idTipoAdempimento != null)
				return false;
		} else if (!idTipoAdempimento.equals(other.idTipoAdempimento))
			return false;
		if (maggioreDi == null) {
			if (other.maggioreDi != null)
				return false;
		} else if (!maggioreDi.equals(other.maggioreDi))
			return false;
		if (minoreDi == null) {
			if (other.minoreDi != null)
				return false;
		} else if (!minoreDi.equals(other.minoreDi))
			return false;
		if (siglaProvinciaOggetto == null) {
			if (other.siglaProvinciaOggetto != null)
				return false;
		} else if (!siglaProvinciaOggetto.equals(other.siglaProvinciaOggetto))
			return false;
		if (vincolo == null) {
			if (other.vincolo != null)
				return false;
		} else if (!vincolo.equals(other.vincolo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PubSearchDTO [idIstanza=" + idIstanza + ", idAmbito=" + idAmbito + ", idTipoAdempimento="
				+ idTipoAdempimento + ", idAdempimento=" + idAdempimento + ", idCompetenzaTerritorio="
				+ idCompetenzaTerritorio + ", annoPresentazioneProgetto=" + annoPresentazioneProgetto + ", minoreDi="
				+ minoreDi + ", maggioreDi=" + maggioreDi + ", codicePratica=" + codicePratica
				+ ", denominazioneOggetto=" + denominazioneOggetto + ", siglaProvinciaOggetto=" + siglaProvinciaOggetto
				+ ", codIstatComuneOggetto=" + codIstatComuneOggetto + ", labelStato=" + labelStato
				+ ", fasePubblicazione=" + fasePubblicazione + ", codCategoriaAllegato=" + codCategoriaAllegato
				+ ", flgInfrastruttureStrategiche=" + flgInfrastruttureStrategiche + ", flgPnrr=" + flgPnrr
				+ ", flgVinca=" + flgVinca + ", codCategoriaOggetto=" + codCategoriaOggetto + ", vincolo=" + vincolo
				+ ", codStatoProcedStatale=" + codStatoProcedStatale + "]";
	}






}
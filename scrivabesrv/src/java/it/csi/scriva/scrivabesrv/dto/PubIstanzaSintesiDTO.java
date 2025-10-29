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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * The type Pub istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PubIstanzaSintesiDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id_istanza")
    private Long idIstanza;

	@JsonProperty("cod_pratica")
	private String codPratica;

	@JsonProperty("des_adempimento")
    private String desAdempimento;

	@JsonProperty("cod_ambito")
    private String codAmbito;

	@JsonProperty("cod_tipo_adempimento")
	private String codTipoAdempimento;

	@JsonProperty("label_stato")
    private String labelStato;

    @JsonProperty("data_inserimento_pratica")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataInserimentoPratica;

    @JsonProperty("data_pubblicazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataPubblicazione;

    @JsonProperty("data_fine_osservazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataFineOsservazione;

    @JsonProperty("data_protocollo_istanza")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataProtocolloIstanza;

    @JsonProperty("fase_pubblicazione")
    private String fasePubblicazione;

    @JsonProperty("flg_osservazione")
    private Boolean flgOsservazione;

    @JsonProperty("des_competenza_territorio")
    private String desCompetenzaTerritorio;

    @JsonProperty("den_oggetto")
    private String denOggetto;

    @JsonProperty("des_oggetto")
    private String desOggetto;

    @JsonProperty("denom_comune")
    private String denomComune;

    @JsonProperty("flag_conta_consultazione")
    private int flagContaConsultazione;

    @JsonProperty("data_conclusione_procedimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")
    private Timestamp dataConclusioneProcedimento;

    @JsonProperty("flag_conta_conclusa")
    private int flagContaConclusa;

    @JsonProperty("flag_conta_in_corso")
    private int flagContaInCorso;


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
     * Gets data pubblicazione.
     *
     * @return the data pubblicazione
     */
    public Timestamp getDataPubblicazione() {
        return dataPubblicazione;
    }

    /**
     * Sets data pubblicazione.
     *
     * @param dataPubblicazione the data pubblicazione
     */
    public void setDataPubblicazione(Timestamp dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    /**
     * Gets cod pratica.
     *
     * @return the cod pratica
     */
    public String getCodPratica() {
        return codPratica;
    }

    /**
     * Sets cod pratica.
     *
     * @param codPratica the cod pratica
     */
    public void setCodPratica(String codPratica) {
        this.codPratica = codPratica;
    }

    /**
     * Gets data inserimento pratica.
     *
     * @return the data inserimento pratica
     */
    public Timestamp getDataInserimentoPratica() {
        return dataInserimentoPratica;
    }

    /**
     * Sets data inserimento pratica.
     *
     * @param dataInserimentoPratica the data inserimento pratica
     */
    public void setDataInserimentoPratica(Timestamp dataInserimentoPratica) {
        this.dataInserimentoPratica = dataInserimentoPratica;
    }

    /**
     * Gets data fine osservazione.
     *
     * @return the data fine osservazione
     */
    public Timestamp getDataFineOsservazione() {
        return dataFineOsservazione;
    }

    /**
     * Sets data fine osservazione.
     *
     * @param dataFineOsservazione the data fine osservazione
     */
    public void setDataFineOsservazione(Timestamp dataFineOsservazione) {
        this.dataFineOsservazione = dataFineOsservazione;
    }

     /**
     * Gets flg osservazione.
     *
     * @return the flg osservazione
     */
    public Boolean getFlgOsservazione() {
        return flgOsservazione;
    }

    /**
     * Sets flg osservazione.
     *
     * @param flgOsservazione the flg osservazione
     */
    public void setFlgOsservazione(Boolean flgOsservazione) {
        this.flgOsservazione = flgOsservazione;
    }


    public Timestamp getDataConclusioneProcedimento() {
		return dataConclusioneProcedimento;
	}

	public void setDataConclusioneProcedimento(Timestamp dataConclusioneProcedimento) {
		this.dataConclusioneProcedimento = dataConclusioneProcedimento;
	}

	public String getDesAdempimento() {
		return desAdempimento;
	}

	public void setDesAdempimento(String desAdempimento) {
		this.desAdempimento = desAdempimento;
	}

	public String getLabelStato() {
		return labelStato;
	}

	public void setLabelStato(String labelStato) {
		this.labelStato = labelStato;
	}

	public String getDesCompetenzaTerritorio() {
		return desCompetenzaTerritorio;
	}

	public void setDesCompetenzaTerritorio(String desCompetenzaTerritorio) {
		this.desCompetenzaTerritorio = desCompetenzaTerritorio;
	}

	public String getDenOggetto() {
		return denOggetto;
	}

	public void setDenOggetto(String denOggetto) {
		this.denOggetto = denOggetto;
	}

	public String getDesOggetto() {
		return desOggetto;
	}

	public void setDesOggetto(String desOggetto) {
		this.desOggetto = desOggetto;
	}

	public String getDenomComune() {
		return denomComune;
	}

	public void setDenomComune(String denomComune) {
		this.denomComune = denomComune;
	}

	public int getFlagContaConsultazione() {
		return flagContaConsultazione;
	}

	public void setFlagContaConsultazione(int flagContaConsultazione) {
		this.flagContaConsultazione = flagContaConsultazione;
	}

	public int getFlagContaConclusa() {
		return flagContaConclusa;
	}

	public void setFlagContaConclusa(int flagContaConclusa) {
		this.flagContaConclusa = flagContaConclusa;
	}

	public int getFlagContaInCorso() {
		return flagContaInCorso;
	}

	public void setFlagContaInCorso(int flagContaInCorso) {
		this.flagContaInCorso = flagContaInCorso;
	}


	public Timestamp getDataProtocolloIstanza() {
		return dataProtocolloIstanza;
	}

	public void setDataProtocolloIstanza(Timestamp dataProtocolloIstanza) {
		this.dataProtocolloIstanza = dataProtocolloIstanza;
	}

	public String getCodTipoAdempimento() {
		return codTipoAdempimento;
	}

	public void setCodTipoAdempimento(String codTipoAdempimento) {
		this.codTipoAdempimento = codTipoAdempimento;
	}

	public String getCodAmbito() {
		return codAmbito;
	}

	public void setCodAmbito(String codAmbito) {
		this.codAmbito = codAmbito;
	}

	/**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubIstanzaSintesiDTO that = (PubIstanzaSintesiDTO) o;
        return Objects.equals(idIstanza, that.idIstanza)
        	&& Objects.equals(dataPubblicazione, that.dataPubblicazione)
        	&& Objects.equals(codPratica, that.codPratica)
        	&& Objects.equals(desAdempimento, that.desAdempimento)
        	&& Objects.equals(codTipoAdempimento, that.codTipoAdempimento)
        	&& Objects.equals(codAmbito, that.codAmbito)
        	&& Objects.equals(labelStato, that.labelStato)
        	&& Objects.equals(dataInserimentoPratica, that.dataInserimentoPratica)
        	&& Objects.equals(dataFineOsservazione, that.dataFineOsservazione)
        	&& Objects.equals(flgOsservazione, that.flgOsservazione)
        	&& Objects.equals(desCompetenzaTerritorio, that.desCompetenzaTerritorio)
        	&& Objects.equals(denOggetto, that.denOggetto)
        	&& Objects.equals(desOggetto, that.desOggetto)
        	&& Objects.equals(denomComune, that.denomComune)
        	&& Objects.equals(fasePubblicazione, that.fasePubblicazione)
        	&& Objects.equals(dataConclusioneProcedimento, that.dataConclusioneProcedimento)
        	&& Objects.equals(flagContaConsultazione, that.flagContaConsultazione)
        	&& Objects.equals(flagContaConclusa, that.flagContaConclusa)
        	&& Objects.equals(flagContaInCorso, that.flagContaInCorso);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, codPratica, desAdempimento, codAmbito, codTipoAdempimento, labelStato, dataInserimentoPratica, dataPubblicazione, dataFineOsservazione, dataProtocolloIstanza, fasePubblicazione, flgOsservazione, desCompetenzaTerritorio, denOggetto, desOggetto, denomComune, flagContaConsultazione, dataConclusioneProcedimento, flagContaConclusa, flagContaInCorso);
    }

    @Override
	public String toString() {
		return "PubIstanzaSintesiDTO [idIstanza=" + idIstanza + ", codPratica=" + codPratica + ", desAdempimento=" + desAdempimento + ", codTipoAdempimento=" + codTipoAdempimento + ", labelStato=" + labelStato + ", dataInserimentoPratica="
			+ dataInserimentoPratica + ", dataPubblicazione=" + dataPubblicazione + ", dataFineOsservazione=" + dataFineOsservazione + ", fasePubblicazione=" + fasePubblicazione + ", flgOsservazione="
			+ flgOsservazione + ", desCompetenzaTerritorio=" + desCompetenzaTerritorio + ", denOggetto=" + denOggetto + ", desOggetto=" + desOggetto + ", denomComune=" + denomComune
			+ ", flagContaConsultazione=" + flagContaConsultazione + ", dataConclusioneProcedimento=" + dataConclusioneProcedimento + ", flagContaConclusa=" + flagContaConclusa + ", flagContaInCorso="
			+ flagContaInCorso + "]";
	}

	/**
     * To json string string.
     *
     * @return string string
     */
    @JsonIgnore
    public String toJsonString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * To json obj json object.
     *
     * @return JSONObject json object
     */
    @JsonIgnore
    public JSONObject toJsonObj() {
        String sJson = this.toJsonString();
        JSONObject obj = new JSONObject(sJson);
        obj.remove("json_data");
        return obj;
    }
}
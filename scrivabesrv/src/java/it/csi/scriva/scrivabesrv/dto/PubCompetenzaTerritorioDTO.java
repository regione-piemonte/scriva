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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * The type Pub competenza territorio dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubCompetenzaTerritorioDTO implements Serializable {

    @JsonProperty("id_competenza_territorio")
    private Long idCompetenzaTerritorio;

    @JsonProperty("comune_competenza")
    private ComuneExtendedDTO comuneCompetenza;

    @JsonProperty("tipo_competenza")
    private TipoCompetenzaDTO tipoCompetenza;

    @JsonProperty("cod_competenza_territorio")
    private String codCompetenzaTerritorio;

    @JsonProperty("des_competenza_territorio")
    private String desCompetenzaTerritorio;

    @JsonProperty("indirizzo_competenza")
    private String indirizzoCompetenza;

    @JsonProperty("num_civico_indirizzo")
    private String numCivicoIndirizzo;

    @JsonProperty("cap_competenza")
    private String capCompetenza;

    @JsonProperty("des_mail_pec")
    private String desMailPec;

    @JsonProperty("orario")
    private String orario;

    @JsonProperty("sito_web")
    private String sitoWeb;

    @JsonProperty("responsabile")
    private String responsabile;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;
    
    // SCRIVA-1006
    @JsonProperty("flg_autorita_principale")
    private Boolean flgAutoritaPrincipale;
    @JsonProperty("flg_autorita_assegnata_bo")
    private Boolean flgAutoritaAssegnataBo;
    @JsonProperty("ind_assegnata_da_sistema")
    private String indAssegnataDaSistema;
    
    
    @JsonIgnore
    private String codIpa;

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
     * Gets comune competenza.
     *
     * @return the comune competenza
     */
    public ComuneExtendedDTO getComuneCompetenza() {
        return comuneCompetenza;
    }

    /**
     * Sets comune competenza.
     *
     * @param comuneCompetenza the comune competenza
     */
    public void setComuneCompetenza(ComuneExtendedDTO comuneCompetenza) {
        this.comuneCompetenza = comuneCompetenza;
    }

    /**
     * Gets tipo competenza.
     *
     * @return the tipo competenza
     */
    public TipoCompetenzaDTO getTipoCompetenza() {
        return tipoCompetenza;
    }

    /**
     * Sets tipo competenza.
     *
     * @param tipoCompetenza the tipo competenza
     */
    public void setTipoCompetenza(TipoCompetenzaDTO tipoCompetenza) {
        this.tipoCompetenza = tipoCompetenza;
    }

    /**
     * Gets cod competenza territorio.
     *
     * @return the cod competenza territorio
     */
    public String getCodCompetenzaTerritorio() {
        return codCompetenzaTerritorio;
    }

    /**
     * Sets cod competenza territorio.
     *
     * @param codCompetenzaTerritorio the cod competenza territorio
     */
    public void setCodCompetenzaTerritorio(String codCompetenzaTerritorio) {
        this.codCompetenzaTerritorio = codCompetenzaTerritorio;
    }

    /**
     * Gets des competenza territorio.
     *
     * @return the des competenza territorio
     */
    public String getDesCompetenzaTerritorio() {
        return desCompetenzaTerritorio;
    }

    /**
     * Sets des competenza territorio.
     *
     * @param desCompetenzaTerritorio the des competenza territorio
     */
    public void setDesCompetenzaTerritorio(String desCompetenzaTerritorio) {
        this.desCompetenzaTerritorio = desCompetenzaTerritorio;
    }

    /**
     * Gets indirizzo competenza.
     *
     * @return the indirizzo competenza
     */
    public String getIndirizzoCompetenza() {
        return indirizzoCompetenza;
    }

    /**
     * Sets indirizzo competenza.
     *
     * @param indirizzoCompetenza the indirizzo competenza
     */
    public void setIndirizzoCompetenza(String indirizzoCompetenza) {
        this.indirizzoCompetenza = indirizzoCompetenza;
    }

    /**
     * Gets num civico indirizzo.
     *
     * @return the num civico indirizzo
     */
    public String getNumCivicoIndirizzo() {
        return numCivicoIndirizzo;
    }

    /**
     * Sets num civico indirizzo.
     *
     * @param numCivicoIndirizzo the num civico indirizzo
     */
    public void setNumCivicoIndirizzo(String numCivicoIndirizzo) {
        this.numCivicoIndirizzo = numCivicoIndirizzo;
    }

    /**
     * Gets cap competenza.
     *
     * @return the cap competenza
     */
    public String getCapCompetenza() {
        return capCompetenza;
    }

    /**
     * Sets cap competenza.
     *
     * @param capCompetenza the cap competenza
     */
    public void setCapCompetenza(String capCompetenza) {
        this.capCompetenza = capCompetenza;
    }

    /**
     * Gets des mail pec.
     *
     * @return the des mail pec
     */
    public String getDesMailPec() {
        return desMailPec;
    }

    /**
     * Sets des mail pec.
     *
     * @param desMailPec the des mail pec
     */
    public void setDesMailPec(String desMailPec) {
        this.desMailPec = desMailPec;
    }

    /**
     * Gets orario.
     *
     * @return the orario
     */
    public String getOrario() {
        return orario;
    }

    /**
     * Sets orario.
     *
     * @param orario the orario
     */
    public void setOrario(String orario) {
        this.orario = orario;
    }

    /**
     * Gets sito web.
     *
     * @return the sito web
     */
    public String getSitoWeb() {
        return sitoWeb;
    }

    /**
     * Sets sito web.
     *
     * @param sitoWeb the sito web
     */
    public void setSitoWeb(String sitoWeb) {
        this.sitoWeb = sitoWeb;
    }

    /**
     * Gets responsabile.
     *
     * @return the responsabile
     */
    public String getResponsabile() {
        return responsabile;
    }

    /**
     * Sets responsabile.
     *
     * @param responsabile the responsabile
     */
    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
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
     * Gets cod ipa.
     *
     * @return the cod ipa
     */
    public String getCodIpa() {
        return codIpa;
    }

    public Boolean getFlgAutoritaPrincipale() {
		return flgAutoritaPrincipale;
	}

	public void setFlgAutoritaPrincipale(Boolean flgAutoritaPrincipale) {
		this.flgAutoritaPrincipale = flgAutoritaPrincipale;
	}

	public Boolean getFlgAutoritaAssegnataBo() {
		return flgAutoritaAssegnataBo;
	}

	public void setFlgAutoritaAssegnataBo(Boolean flgAutoritaAssegnataBo) {
		this.flgAutoritaAssegnataBo = flgAutoritaAssegnataBo;
	}

	public String getIndAssegnataDaSistema() {
		return indAssegnataDaSistema;
	}

	public void setIndAssegnataDaSistema(String indAssegnataDaSistema) {
		this.indAssegnataDaSistema = indAssegnataDaSistema;
	}

	/**
     * Sets cod ipa.
     *
     * @param codIpa the cod ipa
     */
    public void setCodIpa(String codIpa) {
        this.codIpa = codIpa;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubCompetenzaTerritorioDTO that = (PubCompetenzaTerritorioDTO) o;
        return Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio) && Objects.equals(comuneCompetenza, that.comuneCompetenza) && Objects.equals(tipoCompetenza, that.tipoCompetenza) && Objects.equals(codCompetenzaTerritorio, that.codCompetenzaTerritorio) && Objects.equals(desCompetenzaTerritorio, that.desCompetenzaTerritorio) && Objects.equals(indirizzoCompetenza, that.indirizzoCompetenza) && Objects.equals(numCivicoIndirizzo, that.numCivicoIndirizzo) && Objects.equals(capCompetenza, that.capCompetenza) && Objects.equals(desMailPec, that.desMailPec) && Objects.equals(orario, that.orario) && Objects.equals(sitoWeb, that.sitoWeb) && Objects.equals(responsabile, that.responsabile) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCompetenzaTerritorio, comuneCompetenza, tipoCompetenza, codCompetenzaTerritorio, desCompetenzaTerritorio, indirizzoCompetenza, numCivicoIndirizzo, capCompetenza, desMailPec, orario, sitoWeb, responsabile, dataInizioValidita, dataFineValidita);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PubCompetenzaTerritorioDTO {\n");
        sb.append("         idCompetenzaTerritorio:").append(idCompetenzaTerritorio);
        sb.append(",\n         comuneCompetenza:").append(comuneCompetenza);
        sb.append(",\n         tipoCompetenza:").append(tipoCompetenza);
        sb.append(",\n         codCompetenzaTerritorio:'").append(codCompetenzaTerritorio).append("'");
        sb.append(",\n         desCompetenzaTerritorio:'").append(desCompetenzaTerritorio).append("'");
        sb.append(",\n         indirizzoCompetenza:'").append(indirizzoCompetenza).append("'");
        sb.append(",\n         numCivicoIndirizzo:'").append(numCivicoIndirizzo).append("'");
        sb.append(",\n         capCompetenza:'").append(capCompetenza).append("'");
        sb.append(",\n         desMailPec:'").append(desMailPec).append("'");
        sb.append(",\n         orario:'").append(orario).append("'");
        sb.append(",\n         sitoWeb:'").append(sitoWeb).append("'");
        sb.append(",\n         responsabile:'").append(responsabile).append("'");
        sb.append(",\n         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",\n         dataFineValidita:").append(dataFineValidita);
        sb.append(",\n         flgAutoritaPrincipale:").append(flgAutoritaPrincipale).append("'");
        sb.append(",\n         flgAutoritaAssegnataBo:").append(flgAutoritaAssegnataBo).append("'");
        sb.append(",\n         indAssegnataDaSistema:").append(indAssegnataDaSistema).append("'");
        sb.append("}\n");
        return sb.toString();
    }

}
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * The type Competenza territorio dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompetenzaTerritorioDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_competenza_territorio")
    private Long idCompetenzaTerritorio;

    @JsonProperty("id_tipo_competenza")
    private Long idTipoCompetenza;

    @JsonProperty("id_ente_creditore")
    private Long idEnteCreditore;

    @JsonProperty("id_comune_competenza")
    private Long idComuneCompetenza;

    @JsonProperty("cod_competenza_territorio")
    private String codCompetenzaTerritorio;

    @JsonProperty("des_competenza_territorio")
    private String desCompetenzaTerritorio;

    @JsonProperty("des_competenza_territorio_estesa")
    private String desCompetenzaTerritorioEstesa;

    @JsonProperty("cod_suap")
    private String codSuap;

    @JsonProperty("indirizzo_competenza")
    private String indirizzoCompetenza;

    @JsonProperty("num_civico_indirizzo")
    private String numCivicoIndirizzo;

    @JsonProperty("cap_competenza")
    private String capCompetenza;

    @JsonProperty("des_email_pec")
    private String desEmailPec;

    @JsonProperty("des_email")
    private String desEmail;

    @JsonProperty("orario")
    private String orario;

    @JsonProperty("sito_web")
    private String sitoWeb;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    @JsonProperty("ind_visibile")
    private String indVisibile;

    @JsonIgnore
    private String codIpa;

    /**
     * Gets id competenza territorio.
     *
     * @return idCompetenzaTerritorio id competenza territorio
     */
    public Long getIdCompetenzaTerritorio() {
        return idCompetenzaTerritorio;
    }

    /**
     * Sets id competenza territorio.
     *
     * @param idCompetenzaTerritorio idCompetenzaTerritorio
     */
    public void setIdCompetenzaTerritorio(Long idCompetenzaTerritorio) {
        this.idCompetenzaTerritorio = idCompetenzaTerritorio;
    }

    /**
     * Gets id tipo competenza.
     *
     * @return idTipoCompetenza id tipo competenza
     */
    public Long getIdTipoCompetenza() {
        return idTipoCompetenza;
    }

    /**
     * Sets id tipo competenza.
     *
     * @param idTipoCompetenza idTipoCompetenza
     */
    public void setIdTipoCompetenza(Long idTipoCompetenza) {
        this.idTipoCompetenza = idTipoCompetenza;
    }

    /**
     * Gets id ente creditore.
     *
     * @return idEnteCreditore id ente creditore
     */
    public Long getIdEnteCreditore() {
        return idEnteCreditore;
    }

    /**
     * Sets id ente creditore.
     *
     * @param idEnteCreditore idEnteCreditore
     */
    public void setIdEnteCreditore(Long idEnteCreditore) {
        this.idEnteCreditore = idEnteCreditore;
    }

    /**
     * Gets id comune competenza.
     *
     * @return idComuneCompetenza id comune competenza
     */
    public Long getIdComuneCompetenza() {
        return idComuneCompetenza;
    }

    /**
     * Sets id comune competenza.
     *
     * @param idComuneCompetenza idComuneCompetenza
     */
    public void setIdComuneCompetenza(Long idComuneCompetenza) {
        this.idComuneCompetenza = idComuneCompetenza;
    }

    /**
     * Gets cod competenza territorio.
     *
     * @return codCompetenzaTerritorio cod competenza territorio
     */
    public String getCodCompetenzaTerritorio() {
        return codCompetenzaTerritorio;
    }

    /**
     * Sets cod competenza territorio.
     *
     * @param codCompetenzaTerritorio codCompetenzaTerritorio
     */
    public void setCodCompetenzaTerritorio(String codCompetenzaTerritorio) {
        this.codCompetenzaTerritorio = codCompetenzaTerritorio;
    }

    /**
     * Gets des competenza territorio.
     *
     * @return desCompetenzaTerritorio des competenza territorio
     */
    public String getDesCompetenzaTerritorio() {
        return desCompetenzaTerritorio;
    }

    /**
     * Sets des competenza territorio.
     *
     * @param desCompetenzaTerritorio desCompetenzaTerritorio
     */
    public void setDesCompetenzaTerritorio(String desCompetenzaTerritorio) {
        this.desCompetenzaTerritorio = desCompetenzaTerritorio;
    }

    /**
     * Gets des competenza territorio estesa.
     *
     * @return desCompetenzaTerritorioEstesa des competenza territorio estesa
     */
    public String getDesCompetenzaTerritorioEstesa() {
        return desCompetenzaTerritorioEstesa;
    }

    /**
     * Sets des competenza territorio estesa.
     *
     * @param desCompetenzaTerritorioEstesa desCompetenzaTerritorioEstesa
     */
    public void setDesCompetenzaTerritorioEstesa(String desCompetenzaTerritorioEstesa) {
        this.desCompetenzaTerritorioEstesa = desCompetenzaTerritorioEstesa;
    }

    /**
     * Gets cod suap.
     *
     * @return codSuap cod suap
     */
    public String getCodSuap() {
        return codSuap;
    }

    /**
     * Sets cod suap.
     *
     * @param codSuap codSuap
     */
    public void setCodSuap(String codSuap) {
        this.codSuap = codSuap;
    }

    /**
     * Gets indirizzo competenza.
     *
     * @return indirizzoCompetenza indirizzo competenza
     */
    public String getIndirizzoCompetenza() {
        return indirizzoCompetenza;
    }

    /**
     * Sets indirizzo competenza.
     *
     * @param indirizzoCompetenza indirizzoCompetenza
     */
    public void setIndirizzoCompetenza(String indirizzoCompetenza) {
        this.indirizzoCompetenza = indirizzoCompetenza;
    }

    /**
     * Gets num civico indirizzo.
     *
     * @return numCivicoIndirizzo num civico indirizzo
     */
    public String getNumCivicoIndirizzo() {
        return numCivicoIndirizzo;
    }

    /**
     * Sets num civico indirizzo.
     *
     * @param numCivicoIndirizzo numCivicoIndirizzo
     */
    public void setNumCivicoIndirizzo(String numCivicoIndirizzo) {
        this.numCivicoIndirizzo = numCivicoIndirizzo;
    }

    /**
     * Gets cap competenza.
     *
     * @return capCompetenza cap competenza
     */
    public String getCapCompetenza() {
        return capCompetenza;
    }

    /**
     * Sets cap competenza.
     *
     * @param capCompetenza capCompetenza
     */
    public void setCapCompetenza(String capCompetenza) {
        this.capCompetenza = capCompetenza;
    }

    /**
     * Gets des email pec.
     *
     * @return desEmailPec des email pec
     */
    public String getDesEmailPec() {
        return desEmailPec;
    }

    /**
     * Sets des email pec.
     *
     * @param desEmailPec desEmailPec
     */
    public void setDesEmailPec(String desEmailPec) {
        this.desEmailPec = desEmailPec;
    }

    /**
     * Gets des email.
     *
     * @return desEmail des email
     */
    public String getDesEmail() {
        return desEmail;
    }

    /**
     * Sets des email.
     *
     * @param desEmail desEmail
     */
    public void setDesEmail(String desEmail) {
        this.desEmail = desEmail;
    }

    /**
     * Gets orario.
     *
     * @return orario orario
     */
    public String getOrario() {
        return orario;
    }

    /**
     * Sets orario.
     *
     * @param orario orario
     */
    public void setOrario(String orario) {
        this.orario = orario;
    }

    /**
     * Gets sito web.
     *
     * @return sitoWeb sito web
     */
    public String getSitoWeb() {
        return sitoWeb;
    }

    /**
     * Sets sito web.
     *
     * @param sitoWeb sitoWeb
     */
    public void setSitoWeb(String sitoWeb) {
        this.sitoWeb = sitoWeb;
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
     * Gets ind visibile.
     *
     * @return indVisibile ind visibile
     */
    public String getIndVisibile() {
        return indVisibile;
    }

    /**
     * Sets ind visibile.
     *
     * @param indVisibile indVisibile
     */
    public void setIndVisibile(String indVisibile) {
        this.indVisibile = indVisibile;
    }

    /**
     * Gets cod ipa.
     *
     * @return the cod ipa
     */
    public String getCodIpa() {
        return codIpa;
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
        if (!super.equals(o)) return false;
        CompetenzaTerritorioDTO that = (CompetenzaTerritorioDTO) o;
        return Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio) && Objects.equals(idTipoCompetenza, that.idTipoCompetenza) && Objects.equals(idEnteCreditore, that.idEnteCreditore) && Objects.equals(idComuneCompetenza, that.idComuneCompetenza) && Objects.equals(codCompetenzaTerritorio, that.codCompetenzaTerritorio) && Objects.equals(desCompetenzaTerritorio, that.desCompetenzaTerritorio) && Objects.equals(desCompetenzaTerritorioEstesa, that.desCompetenzaTerritorioEstesa) && Objects.equals(codSuap, that.codSuap) && Objects.equals(indirizzoCompetenza, that.indirizzoCompetenza) && Objects.equals(numCivicoIndirizzo, that.numCivicoIndirizzo) && Objects.equals(capCompetenza, that.capCompetenza) && Objects.equals(desEmailPec, that.desEmailPec) && Objects.equals(desEmail, that.desEmail) && Objects.equals(orario, that.orario) && Objects.equals(sitoWeb, that.sitoWeb) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita) && Objects.equals(indVisibile, that.indVisibile) && Objects.equals(codIpa, that.codIpa);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idCompetenzaTerritorio, idTipoCompetenza, idEnteCreditore, idComuneCompetenza, codCompetenzaTerritorio, desCompetenzaTerritorio, desCompetenzaTerritorioEstesa, codSuap, indirizzoCompetenza, numCivicoIndirizzo, capCompetenza, desEmailPec, desEmail, orario, sitoWeb, dataInizioValidita, dataFineValidita, indVisibile, codIpa);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "CompetenzaTerritorioDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idCompetenzaTerritorio:" + idCompetenzaTerritorio +
                ",\n         idTipoCompetenza:" + idTipoCompetenza +
                ",\n         idEnteCreditore:" + idEnteCreditore +
                ",\n         idComuneCompetenza:" + idComuneCompetenza +
                ",\n         codCompetenzaTerritorio:'" + codCompetenzaTerritorio + "'" +
                ",\n         desCompetenzaTerritorio:'" + desCompetenzaTerritorio + "'" +
                ",\n         desCompetenzaTerritorioEstesa:'" + desCompetenzaTerritorioEstesa + "'" +
                ",\n         codSuap:'" + codSuap + "'" +
                ",\n         indirizzoCompetenza:'" + indirizzoCompetenza + "'" +
                ",\n         numCivicoIndirizzo:'" + numCivicoIndirizzo + "'" +
                ",\n         capCompetenza:'" + capCompetenza + "'" +
                ",\n         desEmailPec:'" + desEmailPec + "'" +
                ",\n         desEmail:'" + desEmail + "'" +
                ",\n         orario:'" + orario + "'" +
                ",\n         sitoWeb:'" + sitoWeb + "'" +
                ",\n         dataInizioValidita:" + dataInizioValidita +
                ",\n         dataFineValidita:" + dataFineValidita +
                ",\n         indVisibile:'" + indVisibile + "'" +
                ",\n         codIpa:'" + codIpa + "'" +
                "}\n";
    }
}
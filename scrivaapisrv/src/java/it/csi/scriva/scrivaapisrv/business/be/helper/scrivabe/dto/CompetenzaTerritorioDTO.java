/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */

package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author CSI PIEMONTE
 */
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

    @JsonProperty("responsabile")
    private String responsabile;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    @JsonProperty("ind_visibile")
    private String indVisibile;

    /**
     * @return idCompetenzaTerritorio
     */
    public Long getIdCompetenzaTerritorio() {
        return idCompetenzaTerritorio;
    }

    /**
     * @param idCompetenzaTerritorio idCompetenzaTerritorio
     */
    public void setIdCompetenzaTerritorio(Long idCompetenzaTerritorio) {
        this.idCompetenzaTerritorio = idCompetenzaTerritorio;
    }

    /**
     * @return idTipoCompetenza
     */
    public Long getIdTipoCompetenza() {
        return idTipoCompetenza;
    }

    /**
     * @param idTipoCompetenza idTipoCompetenza
     */
    public void setIdTipoCompetenza(Long idTipoCompetenza) {
        this.idTipoCompetenza = idTipoCompetenza;
    }

    /**
     * @return idEnteCreditore
     */
    public Long getIdEnteCreditore() {
        return idEnteCreditore;
    }

    /**
     * @param idEnteCreditore idEnteCreditore
     */
    public void setIdEnteCreditore(Long idEnteCreditore) {
        this.idEnteCreditore = idEnteCreditore;
    }

    /**
     * @return idComuneCompetenza
     */
    public Long getIdComuneCompetenza() {
        return idComuneCompetenza;
    }

    /**
     * @param idComuneCompetenza idComuneCompetenza
     */
    public void setIdComuneCompetenza(Long idComuneCompetenza) {
        this.idComuneCompetenza = idComuneCompetenza;
    }

    /**
     * @return codCompetenzaTerritorio
     */
    public String getCodCompetenzaTerritorio() {
        return codCompetenzaTerritorio;
    }

    /**
     * @param codCompetenzaTerritorio codCompetenzaTerritorio
     */
    public void setCodCompetenzaTerritorio(String codCompetenzaTerritorio) {
        this.codCompetenzaTerritorio = codCompetenzaTerritorio;
    }

    /**
     * @return desCompetenzaTerritorio
     */
    public String getDesCompetenzaTerritorio() {
        return desCompetenzaTerritorio;
    }

    /**
     * @param desCompetenzaTerritorio desCompetenzaTerritorio
     */
    public void setDesCompetenzaTerritorio(String desCompetenzaTerritorio) {
        this.desCompetenzaTerritorio = desCompetenzaTerritorio;
    }

    /**
     * @return desCompetenzaTerritorioEstesa
     */
    public String getDesCompetenzaTerritorioEstesa() {
        return desCompetenzaTerritorioEstesa;
    }

    /**
     * @param desCompetenzaTerritorioEstesa desCompetenzaTerritorioEstesa
     */
    public void setDesCompetenzaTerritorioEstesa(String desCompetenzaTerritorioEstesa) {
        this.desCompetenzaTerritorioEstesa = desCompetenzaTerritorioEstesa;
    }

    /**
     * @return codSuap
     */
    public String getCodSuap() {
        return codSuap;
    }

    /**
     * @param codSuap codSuap
     */
    public void setCodSuap(String codSuap) {
        this.codSuap = codSuap;
    }

    /**
     * @return indirizzoCompetenza
     */
    public String getIndirizzoCompetenza() {
        return indirizzoCompetenza;
    }

    /**
     * @param indirizzoCompetenza indirizzoCompetenza
     */
    public void setIndirizzoCompetenza(String indirizzoCompetenza) {
        this.indirizzoCompetenza = indirizzoCompetenza;
    }

    /**
     * @return numCivicoIndirizzo
     */
    public String getNumCivicoIndirizzo() {
        return numCivicoIndirizzo;
    }

    /**
     * @param numCivicoIndirizzo numCivicoIndirizzo
     */
    public void setNumCivicoIndirizzo(String numCivicoIndirizzo) {
        this.numCivicoIndirizzo = numCivicoIndirizzo;
    }

    /**
     * @return capCompetenza
     */
    public String getCapCompetenza() {
        return capCompetenza;
    }

    /**
     * @param capCompetenza capCompetenza
     */
    public void setCapCompetenza(String capCompetenza) {
        this.capCompetenza = capCompetenza;
    }

    /**
     * @return desEmailPec
     */
    public String getDesEmailPec() {
        return desEmailPec;
    }

    /**
     * @param desEmailPec desEmailPec
     */
    public void setDesEmailPec(String desEmailPec) {
        this.desEmailPec = desEmailPec;
    }

    /**
     * @return desEmail
     */
    public String getDesEmail() {
        return desEmail;
    }

    /**
     * @param desEmail desEmail
     */
    public void setDesEmail(String desEmail) {
        this.desEmail = desEmail;
    }

    /**
     * @return orario
     */
    public String getOrario() {
        return orario;
    }

    /**
     * @param orario orario
     */
    public void setOrario(String orario) {
        this.orario = orario;
    }

    /**
     * @return sitoWeb
     */
    public String getSitoWeb() {
        return sitoWeb;
    }

    /**
     * @param sitoWeb sitoWeb
     */
    public void setSitoWeb(String sitoWeb) {
        this.sitoWeb = sitoWeb;
    }

    /**
     * @return responsabile
     */
    public String getResponsabile() {
        return responsabile;
    }

    /**
     * @param responsabile responsabile
     */
    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

    /**
     * @return dataInizioValidita
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * @param dataInizioValidita dataInizioValidita
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * @return dataFineValidita
     */
    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * @param dataFineValidita dataFineValidita
     */
    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    /**
     * @return indVisibile
     */
    public String getIndVisibile() {
        return indVisibile;
    }

    /**
     * @param indVisibile indVisibile
     */
    public void setIndVisibile(String indVisibile) {
        this.indVisibile = indVisibile;
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
        return Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio) && Objects.equals(idTipoCompetenza, that.idTipoCompetenza) && Objects.equals(idEnteCreditore, that.idEnteCreditore) && Objects.equals(idComuneCompetenza, that.idComuneCompetenza) && Objects.equals(codCompetenzaTerritorio, that.codCompetenzaTerritorio) && Objects.equals(desCompetenzaTerritorio, that.desCompetenzaTerritorio) && Objects.equals(desCompetenzaTerritorioEstesa, that.desCompetenzaTerritorioEstesa) && Objects.equals(codSuap, that.codSuap) && Objects.equals(indirizzoCompetenza, that.indirizzoCompetenza) && Objects.equals(numCivicoIndirizzo, that.numCivicoIndirizzo) && Objects.equals(capCompetenza, that.capCompetenza) && Objects.equals(desEmailPec, that.desEmailPec) && Objects.equals(desEmail, that.desEmail) && Objects.equals(orario, that.orario) && Objects.equals(sitoWeb, that.sitoWeb) && Objects.equals(responsabile, that.responsabile) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita) && Objects.equals(indVisibile, that.indVisibile);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idCompetenzaTerritorio, idTipoCompetenza, idEnteCreditore, idComuneCompetenza, codCompetenzaTerritorio, desCompetenzaTerritorio, desCompetenzaTerritorioEstesa, codSuap, indirizzoCompetenza, numCivicoIndirizzo, capCompetenza, desEmailPec, desEmail, orario, sitoWeb, responsabile, dataInizioValidita, dataFineValidita, indVisibile);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompetenzaTerritorioDTO {");
        sb.append("         idCompetenzaTerritorio:").append(idCompetenzaTerritorio);
        sb.append(",         idTipoCompetenza:").append(idTipoCompetenza);
        sb.append(",         idEnteCreditore:").append(idEnteCreditore);
        sb.append(",         idComuneCompetenza:").append(idComuneCompetenza);
        sb.append(",         codCompetenzaTerritorio:'").append(codCompetenzaTerritorio).append("'");
        sb.append(",         desCompetenzaTerritorio:'").append(desCompetenzaTerritorio).append("'");
        sb.append(",         desCompetenzaTerritorioEstesa:'").append(desCompetenzaTerritorioEstesa).append("'");
        sb.append(",         codSuap:'").append(codSuap).append("'");
        sb.append(",         indirizzoCompetenza:'").append(indirizzoCompetenza).append("'");
        sb.append(",         numCivicoIndirizzo:'").append(numCivicoIndirizzo).append("'");
        sb.append(",         capCompetenza:'").append(capCompetenza).append("'");
        sb.append(",         desEmailPec:'").append(desEmailPec).append("'");
        sb.append(",         desEmail:'").append(desEmail).append("'");
        sb.append(",         orario:'").append(orario).append("'");
        sb.append(",         sitoWeb:'").append(sitoWeb).append("'");
        sb.append(",         responsabile:'").append(responsabile).append("'");
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataFineValidita:").append(dataFineValidita);
        sb.append(",         indVisibile:'").append(indVisibile).append("'");
        sb.append("}");
        return sb.toString();
    }
}
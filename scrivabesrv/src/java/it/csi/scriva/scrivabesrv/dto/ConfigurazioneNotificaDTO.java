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

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * The type Configurazione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurazioneNotificaDTO extends BaseDTO implements Serializable {

    private Long idNotificaConfigurazione;

    private Long idCanaleNotifica;

    private Long idTipoNotificaEvento;

    private Long idTipoNotificaEventoAdempi;

    private Long idDestinatario;

    private Long idCompetenzaTerritorio;

    private Long idTipoCompetenza;

    private String oggettoNotifica;

    private String contenutoNotifica;

    private String desEmail;

    private String numCellulare;

    private String desServizioApplicativo;

    private String desPec;

    private String desNotificaCc;

    private Boolean flgVerificaPreferenzeSogg;

    private String indEscludiApplicativo;

    private Date dataInizio;

    private Date dataFine;

    public Long getIdNotificaConfigurazione() {
        return idNotificaConfigurazione;
    }

    public void setIdNotificaConfigurazione(Long idNotificaConfigurazione) {
        this.idNotificaConfigurazione = idNotificaConfigurazione;
    }

    public Long getIdCanaleNotifica() {
        return idCanaleNotifica;
    }

    public void setIdCanaleNotifica(Long idCanaleNotifica) {
        this.idCanaleNotifica = idCanaleNotifica;
    }

    public Long getIdTipoNotificaEvento() {
        return idTipoNotificaEvento;
    }

    public void setIdTipoNotificaEvento(Long idTipoNotificaEvento) {
        this.idTipoNotificaEvento = idTipoNotificaEvento;
    }

    public Long getIdTipoNotificaEventoAdempi() {
        return idTipoNotificaEventoAdempi;
    }

    public void setIdTipoNotificaEventoAdempi(Long idTipoNotificaEventoAdempi) {
        this.idTipoNotificaEventoAdempi = idTipoNotificaEventoAdempi;
    }

    public Long getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Long idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public Long getIdCompetenzaTerritorio() {
        return idCompetenzaTerritorio;
    }

    public void setIdCompetenzaTerritorio(Long idCompetenzaTerritorio) {
        this.idCompetenzaTerritorio = idCompetenzaTerritorio;
    }

    public Long getIdTipoCompetenza() {
        return idTipoCompetenza;
    }

    public void setIdTipoCompetenza(Long idTipoCompetenza) {
        this.idTipoCompetenza = idTipoCompetenza;
    }

    public String getOggettoNotifica() {
        return oggettoNotifica;
    }

    public void setOggettoNotifica(String oggettoNotifica) {
        this.oggettoNotifica = oggettoNotifica;
    }

    public String getContenutoNotifica() {
        return contenutoNotifica;
    }

    public void setContenutoNotifica(String contenutoNotifica) {
        this.contenutoNotifica = contenutoNotifica;
    }

    public String getDesEmail() {
        return desEmail;
    }

    public void setDesEmail(String desEmail) {
        this.desEmail = desEmail;
    }

    public String getNumCellulare() {
        return numCellulare;
    }

    public void setNumCellulare(String numCellulare) {
        this.numCellulare = numCellulare;
    }

    public String getDesServizioApplicativo() {
        return desServizioApplicativo;
    }

    public void setDesServizioApplicativo(String desServizioApplicativo) {
        this.desServizioApplicativo = desServizioApplicativo;
    }

    public String getDesPec() {
        return desPec;
    }

    public void setDesPec(String desPec) {
        this.desPec = desPec;
    }

    public String getDesNotificaCc() {
        return desNotificaCc;
    }

    public void setDesNotificaCc(String desNotificaCc) {
        this.desNotificaCc = desNotificaCc;
    }

    public Boolean getFlgVerificaPreferenzeSogg() {
        return flgVerificaPreferenzeSogg;
    }

    public void setFlgVerificaPreferenzeSogg(Boolean flgVerificaPreferenzeSogg) {
        this.flgVerificaPreferenzeSogg = flgVerificaPreferenzeSogg;
    }

    public String getIndEscludiApplicativo() {
        return indEscludiApplicativo;
    }

    public void setIndEscludiApplicativo(String indEscludiApplicativo) {
        this.indEscludiApplicativo = indEscludiApplicativo;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

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
        if (!super.equals(o)) return false;
        ConfigurazioneNotificaDTO that = (ConfigurazioneNotificaDTO) o;
        return Objects.equals(idNotificaConfigurazione, that.idNotificaConfigurazione) && Objects.equals(idCanaleNotifica, that.idCanaleNotifica) && Objects.equals(idTipoNotificaEvento, that.idTipoNotificaEvento) && Objects.equals(idTipoNotificaEventoAdempi, that.idTipoNotificaEventoAdempi) && Objects.equals(idDestinatario, that.idDestinatario) && Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio) && Objects.equals(idTipoCompetenza, that.idTipoCompetenza) && Objects.equals(oggettoNotifica, that.oggettoNotifica) && Objects.equals(contenutoNotifica, that.contenutoNotifica) && Objects.equals(desEmail, that.desEmail) && Objects.equals(numCellulare, that.numCellulare) && Objects.equals(desServizioApplicativo, that.desServizioApplicativo) && Objects.equals(desPec, that.desPec) && Objects.equals(desNotificaCc, that.desNotificaCc) && Objects.equals(flgVerificaPreferenzeSogg, that.flgVerificaPreferenzeSogg) && Objects.equals(indEscludiApplicativo, that.indEscludiApplicativo) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idNotificaConfigurazione, idCanaleNotifica, idTipoNotificaEvento, idTipoNotificaEventoAdempi, idDestinatario, idCompetenzaTerritorio, idTipoCompetenza, oggettoNotifica, contenutoNotifica, desEmail, numCellulare, desServizioApplicativo, desPec, desNotificaCc, flgVerificaPreferenzeSogg, indEscludiApplicativo, dataInizio, dataFine);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "ConfigurazioneNotificaDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idNotificaConfigurazione:" + idNotificaConfigurazione +
                ",\n         idCanaleNotifica:" + idCanaleNotifica +
                ",\n         idTipoNotificaEvento:" + idTipoNotificaEvento +
                ",\n         idTipoNotificaEventoAdempi:" + idTipoNotificaEventoAdempi +
                ",\n         idDestinatario:" + idDestinatario +
                ",\n         idCompetenzaTerritorio:" + idCompetenzaTerritorio +
                ",\n         idTipoCompetenza:" + idTipoCompetenza +
                ",\n         oggettoNotifica:'" + oggettoNotifica + "'" +
                ",\n         contenutoNotifica:'" + contenutoNotifica + "'" +
                ",\n         desEmail:'" + desEmail + "'" +
                ",\n         numCellulare:'" + numCellulare + "'" +
                ",\n         desServizioApplicativo:'" + desServizioApplicativo + "'" +
                ",\n         desPec:'" + desPec + "'" +
                ",\n         desNotificaCc:'" + desNotificaCc + "'" +
                ",\n         flgVerificaPreferenzeSogg:" + flgVerificaPreferenzeSogg +
                ",\n         indEscludiApplicativo:'" + indEscludiApplicativo + "'" +
                ",\n         dataInizio:" + dataInizio +
                ",\n         dataFine:" + dataFine +
                "}\n";
    }

}
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
 * The type Configurazione notifica allegato dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurazioneNotificaAllegatoDTO extends BaseDTO implements Serializable {

    private Long idNotificaConfigAllegato;
    private Long idNotificaConfigurazione;
    private Date dataInizio;
    private Date dataFine;
    private String notificaCodTipologiaAllegato;
    private String notificaCodCategoriaAllegato;
    private String notificaCodClasseAllegato;
    private String indAllegaFigli;
    private String indAllegaPadre;
    private Boolean flgPubblicati;
    private Boolean flgAllegatoRifProtocollo;
    private Boolean flgUltimoDoc;

    /**
     * Gets id notifica config allegato.
     *
     * @return the id notifica config allegato
     */
    public Long getIdNotificaConfigAllegato() {
        return idNotificaConfigAllegato;
    }

    /**
     * Sets id notifica config allegato.
     *
     * @param idNotificaConfigAllegato the id notifica config allegato
     */
    public void setIdNotificaConfigAllegato(Long idNotificaConfigAllegato) {
        this.idNotificaConfigAllegato = idNotificaConfigAllegato;
    }

    /**
     * Gets id notifica configurazione.
     *
     * @return the id notifica configurazione
     */
    public Long getIdNotificaConfigurazione() {
        return idNotificaConfigurazione;
    }

    /**
     * Sets id notifica configurazione.
     *
     * @param idNotificaConfigurazione the id notifica configurazione
     */
    public void setIdNotificaConfigurazione(Long idNotificaConfigurazione) {
        this.idNotificaConfigurazione = idNotificaConfigurazione;
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
     * Gets notifica cod tipologia allegato.
     *
     * @return the notifica cod tipologia allegato
     */
    public String getNotificaCodTipologiaAllegato() {
        return notificaCodTipologiaAllegato;
    }

    /**
     * Sets notifica cod tipologia allegato.
     *
     * @param notificaCodTipologiaAllegato the notifica cod tipologia allegato
     */
    public void setNotificaCodTipologiaAllegato(String notificaCodTipologiaAllegato) {
        this.notificaCodTipologiaAllegato = notificaCodTipologiaAllegato;
    }

    /**
     * Gets notifica cod categoria allegato.
     *
     * @return the notifica cod categoria allegato
     */
    public String getNotificaCodCategoriaAllegato() {
        return notificaCodCategoriaAllegato;
    }

    /**
     * Sets notifica cod categoria allegato.
     *
     * @param notificaCodCategoriaAllegato the notifica cod categoria allegato
     */
    public void setNotificaCodCategoriaAllegato(String notificaCodCategoriaAllegato) {
        this.notificaCodCategoriaAllegato = notificaCodCategoriaAllegato;
    }

    /**
     * Gets notifica cod classe allegato.
     *
     * @return the notifica cod classe allegato
     */
    public String getNotificaCodClasseAllegato() {
        return notificaCodClasseAllegato;
    }

    /**
     * Sets notifica cod classe allegato.
     *
     * @param notificaCodClasseAllegato the notifica cod classe allegato
     */
    public void setNotificaCodClasseAllegato(String notificaCodClasseAllegato) {
        this.notificaCodClasseAllegato = notificaCodClasseAllegato;
    }

    /**
     * Gets ind allega figli.
     *
     * @return the ind allega figli
     */
    public String getIndAllegaFigli() {
        return indAllegaFigli;
    }

    /**
     * Sets ind allega figli.
     *
     * @param indAllegaFigli the ind allega figli
     */
    public void setIndAllegaFigli(String indAllegaFigli) {
        this.indAllegaFigli = indAllegaFigli;
    }

    /**
     * Gets ind allega padre.
     *
     * @return the ind allega padre
     */
    public String getIndAllegaPadre() {
        return indAllegaPadre;
    }

    /**
     * Sets ind allega padre.
     *
     * @param indAllegaPadre the ind allega padre
     */
    public void setIndAllegaPadre(String indAllegaPadre) {
        this.indAllegaPadre = indAllegaPadre;
    }

    /**
     * Gets flg pubblicati.
     *
     * @return the flg pubblicati
     */
    public Boolean getFlgPubblicati() {
        return flgPubblicati;
    }

    /**
     * Sets flg pubblicati.
     *
     * @param flgPubblicati the flg pubblicati
     */
    public void setFlgPubblicati(Boolean flgPubblicati) {
        this.flgPubblicati = flgPubblicati;
    }

    /**
     * Gets flg allegato rif protocollo.
     *
     * @return the flg allegato rif protocollo
     */
    public Boolean getFlgAllegatoRifProtocollo() {
        return flgAllegatoRifProtocollo;
    }

    /**
     * Sets flg allegato rif protocollo.
     *
     * @param flgAllegatoRifProtocollo the flg allegato rif protocollo
     */
    public void setFlgAllegatoRifProtocollo(Boolean flgAllegatoRifProtocollo) {
        this.flgAllegatoRifProtocollo = flgAllegatoRifProtocollo;
    }

    /**
     * Gets flg ultimo doc.
     *
     * @return the flg ultimo doc
     */
    public Boolean getFlgUltimoDoc() {
        return flgUltimoDoc;
    }

    /**
     * Sets flg ultimo doc.
     *
     * @param flgUltimoDoc the flg ultimo doc
     */
    public void setFlgUltimoDoc(Boolean flgUltimoDoc) {
        this.flgUltimoDoc = flgUltimoDoc;
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
        ConfigurazioneNotificaAllegatoDTO that = (ConfigurazioneNotificaAllegatoDTO) o;
        return Objects.equals(idNotificaConfigAllegato, that.idNotificaConfigAllegato) && Objects.equals(idNotificaConfigurazione, that.idNotificaConfigurazione) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine) && Objects.equals(notificaCodTipologiaAllegato, that.notificaCodTipologiaAllegato) && Objects.equals(notificaCodCategoriaAllegato, that.notificaCodCategoriaAllegato) && Objects.equals(notificaCodClasseAllegato, that.notificaCodClasseAllegato) && Objects.equals(indAllegaFigli, that.indAllegaFigli) && Objects.equals(indAllegaPadre, that.indAllegaPadre) && Objects.equals(flgPubblicati, that.flgPubblicati) && Objects.equals(flgAllegatoRifProtocollo, that.flgAllegatoRifProtocollo) && Objects.equals(flgUltimoDoc, that.flgUltimoDoc);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idNotificaConfigAllegato, idNotificaConfigurazione, dataInizio, dataFine, notificaCodTipologiaAllegato, notificaCodCategoriaAllegato, notificaCodClasseAllegato, indAllegaFigli, indAllegaPadre, flgPubblicati, flgAllegatoRifProtocollo, flgUltimoDoc);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "ConfigurazioneNotificaAllegatoDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idNotificaConfigAllegato:" + idNotificaConfigAllegato +
                ",\n         idNotificaConfigurazione:" + idNotificaConfigurazione +
                ",\n         dataInizio:" + dataInizio +
                ",\n         dataFine:" + dataFine +
                ",\n         notificaCodTipologiaAllegato:'" + notificaCodTipologiaAllegato + "'" +
                ",\n         notificaCodCategoriaAllegato:'" + notificaCodCategoriaAllegato + "'" +
                ",\n         notificaCodClasseAllegato:'" + notificaCodClasseAllegato + "'" +
                ",\n         indAllegaFigli:'" + indAllegaFigli + "'" +
                ",\n         indAllegaPadre:'" + indAllegaPadre + "'" +
                ",\n         flgPubblicati:" + flgPubblicati +
                ",\n         flgAllegatoRifProtocollo:" + flgAllegatoRifProtocollo +
                ",\n         flgUltimoDoc:" + flgUltimoDoc +
                "}\n";
    }

}
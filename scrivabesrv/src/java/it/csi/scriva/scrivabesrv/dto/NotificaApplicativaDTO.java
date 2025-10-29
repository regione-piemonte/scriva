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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Notifica applicativa dto.
 */
public class NotificaApplicativaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_notifica_applicativa")
    private Long idNotificaApplicativa;

    @JsonProperty("id_notifica")
    private Long idNotifica;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_componente_app_push")
    private Long idComponenteAppPush;

    @JsonProperty("cf_destinatario")
    private String cfDestinatario;

    @JsonProperty("des_oggetto")
    private String desOggetto;

    @JsonProperty("des_messaggio")
    private String desMessaggio;

    @JsonProperty("data_inserimento")
    private Timestamp dataInserimento;

    @JsonProperty("data_lettura")
    private Timestamp dataLettura;

    @JsonProperty("data_cancellazione")
    private Timestamp dataCancellazione;

    /**
     * Gets id notifica applicativa.
     *
     * @return the id notifica applicativa
     */
    public Long getIdNotificaApplicativa() {
        return idNotificaApplicativa;
    }

    /**
     * Sets id notifica applicativa.
     *
     * @param idNotificaApplicativa the id notifica applicativa
     */
    public void setIdNotificaApplicativa(Long idNotificaApplicativa) {
        this.idNotificaApplicativa = idNotificaApplicativa;
    }

    /**
     * Gets id notifica.
     *
     * @return the id notifica
     */
    public Long getIdNotifica() {
        return idNotifica;
    }

    /**
     * Sets id notifica.
     *
     * @param idNotifica the id notifica
     */
    public void setIdNotifica(Long idNotifica) {
        this.idNotifica = idNotifica;
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
     * Gets id componente app push.
     *
     * @return the id componente app push
     */
    public Long getIdComponenteAppPush() {
        return idComponenteAppPush;
    }

    /**
     * Sets id componente app push.
     *
     * @param idComponenteAppPush the id componente app push
     */
    public void setIdComponenteAppPush(Long idComponenteAppPush) {
        this.idComponenteAppPush = idComponenteAppPush;
    }

    /**
     * Gets cf destinatario.
     *
     * @return the cf destinatario
     */
    public String getCfDestinatario() {
        return cfDestinatario;
    }

    /**
     * Sets cf destinatario.
     *
     * @param cfDestinatario the cf destinatario
     */
    public void setCfDestinatario(String cfDestinatario) {
        this.cfDestinatario = cfDestinatario;
    }

    /**
     * Gets des oggetto.
     *
     * @return the des oggetto
     */
    public String getDesOggetto() {
        return desOggetto;
    }

    /**
     * Sets des oggetto.
     *
     * @param desOggetto the des oggetto
     */
    public void setDesOggetto(String desOggetto) {
        this.desOggetto = desOggetto;
    }

    /**
     * Gets des messaggio.
     *
     * @return the des messaggio
     */
    public String getDesMessaggio() {
        return desMessaggio;
    }

    /**
     * Sets des messaggio.
     *
     * @param desMessaggio the des messaggio
     */
    public void setDesMessaggio(String desMessaggio) {
        this.desMessaggio = desMessaggio;
    }

    /**
     * Gets data inserimento.
     *
     * @return the data inserimento
     */
    public Timestamp getDataInserimento() {
        return dataInserimento;
    }

    /**
     * Sets data inserimento.
     *
     * @param dataInserimento the data inserimento
     */
    public void setDataInserimento(Timestamp dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    /**
     * Gets data lettura.
     *
     * @return the data lettura
     */
    public Timestamp getDataLettura() {
        return dataLettura;
    }

    /**
     * Sets data lettura.
     *
     * @param dataLettura the data lettura
     */
    public void setDataLettura(Timestamp dataLettura) {
        this.dataLettura = dataLettura;
    }

    /**
     * Gets data cancellazione.
     *
     * @return the data cancellazione
     */
    public Timestamp getDataCancellazione() {
        return dataCancellazione;
    }

    /**
     * Sets data cancellazione.
     *
     * @param dataCancellazione the data cancellazione
     */
    public void setDataCancellazione(Timestamp dataCancellazione) {
        this.dataCancellazione = dataCancellazione;
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
        NotificaApplicativaDTO that = (NotificaApplicativaDTO) o;
        return Objects.equals(idNotificaApplicativa, that.idNotificaApplicativa) && Objects.equals(idNotifica, that.idNotifica) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idComponenteAppPush, that.idComponenteAppPush) && Objects.equals(cfDestinatario, that.cfDestinatario) && Objects.equals(desOggetto, that.desOggetto) && Objects.equals(desMessaggio, that.desMessaggio) && Objects.equals(dataInserimento, that.dataInserimento) && Objects.equals(dataLettura, that.dataLettura) && Objects.equals(dataCancellazione, that.dataCancellazione);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idNotificaApplicativa, idNotifica, idIstanza, idComponenteAppPush, cfDestinatario, desOggetto, desMessaggio, dataInserimento, dataLettura, dataCancellazione);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "NotificaApplicativaDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idNotificaApplicativa:" + idNotificaApplicativa +
                ",\n         idNotifica:" + idNotifica +
                ",\n         idIstanza:" + idIstanza +
                ",\n         idComponenteAppPush:" + idComponenteAppPush +
                ",\n         cfDestinatario:'" + cfDestinatario + "'" +
                ",\n         desOggetto:'" + desOggetto + "'" +
                ",\n         desMessaggio:'" + desMessaggio + "'" +
                ",\n         dataInserimento:" + dataInserimento +
                ",\n         dataLettura:" + dataLettura +
                ",\n         dataCancellazione:" + dataCancellazione +
                "}\n";
    }
}
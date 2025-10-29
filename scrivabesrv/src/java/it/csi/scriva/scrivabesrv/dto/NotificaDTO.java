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
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;


/**
 * The type Notifica dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_notifica")
    private Long idNotifica;

    @JsonProperty("id_stato_notifica")
    private Long idStatoNotifica;

    @JsonProperty("id_notifica_configurazione")
    private Long idNotificaConfigurazione;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_componente_app_push")
    private Long idComponenteAppPush;

    @JsonProperty("cf_destinatario")
    private String cfDestinatario;

    @JsonProperty("rif_canale")
    private String rifCanale;

    @JsonProperty("rif_canale_cc")
    private String rifCanaleCc;

    @JsonProperty("des_oggetto")
    private String desOggetto;

    @JsonProperty("des_messaggio")
    private String desMessaggio;

    @JsonProperty("data_inserimento")
    private Timestamp dataInserimento;

    @JsonProperty("data_invio")
    private Timestamp dataInvio;

    @JsonProperty("tentativi_invio")
    private Long tentativiInvio;

    @JsonProperty("des_segnalazione")
    private String desSegnalazione;

    @JsonIgnore
    private List<NotificaAllegatoDTO> notificaAllegatoList;

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
     * Gets id stato notifica.
     *
     * @return the id stato notifica
     */
    public Long getIdStatoNotifica() {
        return idStatoNotifica;
    }

    /**
     * Sets id stato notifica.
     *
     * @param idStatoNotifica the id stato notifica
     */
    public void setIdStatoNotifica(Long idStatoNotifica) {
        this.idStatoNotifica = idStatoNotifica;
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
     * Gets rif canale.
     *
     * @return the rif canale
     */
    public String getRifCanale() {
        return rifCanale;
    }

    /**
     * Sets rif canale.
     *
     * @param rifCanale the rif canale
     */
    public void setRifCanale(String rifCanale) {
        this.rifCanale = rifCanale;
    }

    /**
     * Gets rif canale cc.
     *
     * @return the rif canale cc
     */
    public String getRifCanaleCc() {
        return rifCanaleCc;
    }

    /**
     * Sets rif canale cc.
     *
     * @param rifCanaleCc the rif canale cc
     */
    public void setRifCanaleCc(String rifCanaleCc) {
        this.rifCanaleCc = rifCanaleCc;
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
     * Gets data invio.
     *
     * @return the data invio
     */
    public Timestamp getDataInvio() {
        return dataInvio;
    }

    /**
     * Sets data invio.
     *
     * @param dataInvio the data invio
     */
    public void setDataInvio(Timestamp dataInvio) {
        this.dataInvio = dataInvio;
    }

    /**
     * Gets tentativi invio.
     *
     * @return the tentativi invio
     */
    public Long getTentativiInvio() {
        return tentativiInvio;
    }

    /**
     * Sets tentativi invio.
     *
     * @param tentativiInvio the tentativi invio
     */
    public void setTentativiInvio(Long tentativiInvio) {
        this.tentativiInvio = tentativiInvio;
    }

    /**
     * Gets des esito.
     *
     * @return the des esito
     */
    public String getDesSegnalazione() {
        return desSegnalazione;
    }

    /**
     * Sets des esito.
     *
     * @param desSegnalazione the des esito
     */
    public void setDesSegnalazione(String desSegnalazione) {
        this.desSegnalazione = desSegnalazione;
    }

    /**
     * Gets notifica allegato list.
     *
     * @return the notifica allegato list
     */
    public List<NotificaAllegatoDTO> getNotificaAllegatoList() {
        return notificaAllegatoList;
    }

    /**
     * Sets notifica allegato list.
     *
     * @param notificaAllegatoList the notifica allegato list
     */
    public void setNotificaAllegatoList(List<NotificaAllegatoDTO> notificaAllegatoList) {
        this.notificaAllegatoList = notificaAllegatoList;
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
        NotificaDTO that = (NotificaDTO) o;
        return Objects.equals(idNotifica, that.idNotifica) && Objects.equals(idStatoNotifica, that.idStatoNotifica) && Objects.equals(idNotificaConfigurazione, that.idNotificaConfigurazione) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idComponenteAppPush, that.idComponenteAppPush) && Objects.equals(cfDestinatario, that.cfDestinatario) && Objects.equals(rifCanale, that.rifCanale) && Objects.equals(rifCanaleCc, that.rifCanaleCc) && Objects.equals(desOggetto, that.desOggetto) && Objects.equals(desMessaggio, that.desMessaggio) && Objects.equals(dataInserimento, that.dataInserimento) && Objects.equals(dataInvio, that.dataInvio) && Objects.equals(tentativiInvio, that.tentativiInvio) && Objects.equals(desSegnalazione, that.desSegnalazione) && Objects.equals(notificaAllegatoList, that.notificaAllegatoList);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idNotifica, idStatoNotifica, idNotificaConfigurazione, idIstanza, idComponenteAppPush, cfDestinatario, rifCanale, rifCanaleCc, desOggetto, desMessaggio, dataInserimento, dataInvio, tentativiInvio, desSegnalazione);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "NotificaDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idNotifica:" + idNotifica +
                ",\n         idStatoNotifica:" + idStatoNotifica +
                ",\n         idNotificaConfigurazione:" + idNotificaConfigurazione +
                ",\n         idIstanza:" + idIstanza +
                ",\n         idComponenteAppPush:" + idComponenteAppPush +
                ",\n         cfDestinatario:'" + cfDestinatario + "'" +
                ",\n         rifCanale:'" + rifCanale + "'" +
                ",\n         rifCanaleCc:'" + rifCanaleCc + "'" +
                ",\n         desOggetto:'" + desOggetto + "'" +
                ",\n         desMessaggio:'" + desMessaggio + "'" +
                ",\n         dataInserimento:" + dataInserimento +
                ",\n         dataInvio:" + dataInvio +
                ",\n         tentativiInvio:" + tentativiInvio +
                ",\n         desSegnalazione:'" + desSegnalazione + "'" +
                ",\n         notificaAllegatoList: " + notificaAllegatoList +
                "}\n";
    }
}
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

import java.util.List;
import java.util.Objects;

/**
 * The type Notifica Allegato Search dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificaAllegatoSearchDTO {

    private Long idIstanza;
    private Long idNotificaConfigurazione;
    private Long idNotificaConfigurazioneAllegato;
    private String codTipologiaAllegato;
    private String gestUidRichiesta;
    private String tipoRichiesta;
    private List<Long> idAllegatoPadreList;
    private int flgUltimoDoc;
    private int flgRifAllegatoProtocollo;
    private int flgPubblicabili;

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
     * Gets id notifica configurazione allegato.
     *
     * @return the id notifica configurazione allegato
     */
    public Long getIdNotificaConfigurazioneAllegato() {
        return idNotificaConfigurazioneAllegato;
    }

    /**
     * Sets id notifica configurazione allegato.
     *
     * @param idNotificaConfigurazioneAllegato the id notifica configurazione allegato
     */
    public void setIdNotificaConfigurazioneAllegato(Long idNotificaConfigurazioneAllegato) {
        this.idNotificaConfigurazioneAllegato = idNotificaConfigurazioneAllegato;
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
     * Gets gest uid richiesta.
     *
     * @return the gest uid richiesta
     */
    public String getGestUidRichiesta() {
        return gestUidRichiesta;
    }

    /**
     * Sets gest uid richiesta.
     *
     * @param gestUidRichiesta the gest uid richiesta
     */
    public void setGestUidRichiesta(String gestUidRichiesta) {
        this.gestUidRichiesta = gestUidRichiesta;
    }

    /**
     * Gets tipo richiesta.
     *
     * @return the tipo richiesta
     */
    public String getTipoRichiesta() {
        return tipoRichiesta;
    }

    /**
     * Sets tipo richiesta.
     *
     * @param tipoRichiesta the tipo richiesta
     */
    public void setTipoRichiesta(String tipoRichiesta) {
        this.tipoRichiesta = tipoRichiesta;
    }

    /**
     * Gets id allegato padre list.
     *
     * @return the id allegato padre list
     */
    public List<Long> getIdAllegatoPadreList() {
        return idAllegatoPadreList;
    }

    /**
     * Sets id allegato padre list.
     *
     * @param idAllegatoPadreList the id allegato padre list
     */
    public void setIdAllegatoPadreList(List<Long> idAllegatoPadreList) {
        this.idAllegatoPadreList = idAllegatoPadreList;
    }

    /**
     * Gets flg ultimo doc.
     *
     * @return the flg ultimo doc
     */
    public int getFlgUltimoDoc() {
        return flgUltimoDoc;
    }

    /**
     * Sets flg ultimo doc.
     *
     * @param flgUltimoDoc the flg ultimo doc
     */
    public void setFlgUltimoDoc(int flgUltimoDoc) {
        this.flgUltimoDoc = flgUltimoDoc;
    }

    /**
     * Gets flg rif allegato protocollo.
     *
     * @return the flg rif allegato protocollo
     */
    public int getFlgRifAllegatoProtocollo() {
        return flgRifAllegatoProtocollo;
    }

    /**
     * Sets flg rif allegato protocollo.
     *
     * @param flgRifAllegatoProtocollo the flg rif allegato protocollo
     */
    public void setFlgRifAllegatoProtocollo(int flgRifAllegatoProtocollo) {
        this.flgRifAllegatoProtocollo = flgRifAllegatoProtocollo;
    }

    /**
     * Gets flg pubblicabili.
     *
     * @return the flg pubblicabili
     */
    public int getFlgPubblicabili() {
        return flgPubblicabili;
    }

    /**
     * Sets flg pubblicabili.
     *
     * @param flgPubblicabili the flg pubblicabili
     */
    public void setFlgPubblicabili(int flgPubblicabili) {
        this.flgPubblicabili = flgPubblicabili;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificaAllegatoSearchDTO that = (NotificaAllegatoSearchDTO) o;
        return flgUltimoDoc == that.flgUltimoDoc && flgRifAllegatoProtocollo == that.flgRifAllegatoProtocollo && flgPubblicabili == that.flgPubblicabili && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idNotificaConfigurazione, that.idNotificaConfigurazione) && Objects.equals(idNotificaConfigurazioneAllegato, that.idNotificaConfigurazioneAllegato) && Objects.equals(codTipologiaAllegato, that.codTipologiaAllegato) && Objects.equals(gestUidRichiesta, that.gestUidRichiesta) && Objects.equals(tipoRichiesta, that.tipoRichiesta) && Objects.equals(idAllegatoPadreList, that.idAllegatoPadreList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, idNotificaConfigurazione, idNotificaConfigurazioneAllegato, codTipologiaAllegato, gestUidRichiesta, tipoRichiesta, idAllegatoPadreList, flgUltimoDoc, flgRifAllegatoProtocollo, flgPubblicabili);
    }

    @Override
    public String toString() {
        return "NotificaAllegatoSearchDTO{" +
                "idIstanza=" + idIstanza +
                ", idNotificaConfigurazione=" + idNotificaConfigurazione +
                ", idNotificaConfigurazioneAllegato=" + idNotificaConfigurazioneAllegato +
                ", codTipologiaAllegato='" + codTipologiaAllegato + '\'' +
                ", gestUidRichiesta='" + gestUidRichiesta + '\'' +
                ", tipoRichiesta='" + tipoRichiesta + '\'' +
                ", idAllegatoPadreList=" + idAllegatoPadreList +
                ", flgUltimoDoc=" + flgUltimoDoc +
                ", flgRifAllegatoProtocollo=" + flgRifAllegatoProtocollo +
                ", flgPubblicabili=" + flgPubblicabili +
                '}';
    }
}
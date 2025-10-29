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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Notifica applicativa dto.
 */
public class NotificaApplicativaExtendedDTO extends NotificaApplicativaDTO implements Serializable {

    @JsonProperty("istanza")
    private IstanzaExtendedDTO istanza;

    /**
     * Gets istanza.
     *
     * @return the istanza
     */
    public IstanzaExtendedDTO getIstanza() {
        return istanza;
    }

    /**
     * Sets istanza.
     *
     * @param istanza the istanza
     */
    public void setIstanza(IstanzaExtendedDTO istanza) {
        this.istanza = istanza;
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
        NotificaApplicativaExtendedDTO that = (NotificaApplicativaExtendedDTO) o;
        return Objects.equals(istanza, that.istanza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), istanza);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "NotificaApplicativaExtendedDTO {\n" +
                super.toString() +
                "         istanza:" + istanza +
                "}\n";
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public NotificaApplicativaDTO getDTO() {
        NotificaApplicativaDTO dto = new NotificaApplicativaDTO();
        dto.setIdNotificaApplicativa(this.getIdNotificaApplicativa());
        dto.setIdNotifica(this.getIdNotifica());
        if (this.getIstanza() != null) {
            dto.setIdIstanza(this.getIstanza().getIdIstanza());
        }
        dto.setIdComponenteAppPush(this.getIdComponenteAppPush());
        dto.setCfDestinatario(this.getCfDestinatario());
        dto.setDesOggetto(this.getDesOggetto());
        dto.setDesMessaggio(this.getDesMessaggio());
        dto.setDataInserimento(this.getDataInserimento());
        dto.setDataLettura(this.getDataLettura());
        dto.setDataCancellazione(this.getDataCancellazione());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }
}
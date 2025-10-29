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
 * The type Destinatario canale dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DestinatarioCanaleDTO extends BaseDTO implements Serializable {

    private Long idDestinatarioCanale;

    private Long idDestinatario;

    private Long idCanaleNotifica;

    private Boolean flgCanaleDefault;

    private Date dataInizio;

    private Date dataFine;

    /**
     * Gets id destinatario canale.
     *
     * @return the id destinatario canale
     */
    public Long getIdDestinatarioCanale() {
        return idDestinatarioCanale;
    }

    /**
     * Sets id destinatario canale.
     *
     * @param idDestinatarioCanale the id destinatario canale
     */
    public void setIdDestinatarioCanale(Long idDestinatarioCanale) {
        this.idDestinatarioCanale = idDestinatarioCanale;
    }

    /**
     * Gets id destinatario.
     *
     * @return the id destinatario
     */
    public Long getIdDestinatario() {
        return idDestinatario;
    }

    /**
     * Sets id destinatario.
     *
     * @param idDestinatario the id destinatario
     */
    public void setIdDestinatario(Long idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    /**
     * Gets id canale notifica.
     *
     * @return the id canale notifica
     */
    public Long getIdCanaleNotifica() {
        return idCanaleNotifica;
    }

    /**
     * Sets id canale notifica.
     *
     * @param idCanaleNotifica the id canale notifica
     */
    public void setIdCanaleNotifica(Long idCanaleNotifica) {
        this.idCanaleNotifica = idCanaleNotifica;
    }

    /**
     * Gets flg canale default.
     *
     * @return the flg canale default
     */
    public Boolean getFlgCanaleDefault() {
        return flgCanaleDefault;
    }

    /**
     * Sets flg canale default.
     *
     * @param flgCanaleDefault the flg canale default
     */
    public void setFlgCanaleDefault(Boolean flgCanaleDefault) {
        this.flgCanaleDefault = flgCanaleDefault;
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
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DestinatarioCanaleDTO that = (DestinatarioCanaleDTO) o;
        return Objects.equals(idDestinatarioCanale, that.idDestinatarioCanale) && Objects.equals(idDestinatario, that.idDestinatario) && Objects.equals(idCanaleNotifica, that.idCanaleNotifica) && Objects.equals(flgCanaleDefault, that.flgCanaleDefault) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idDestinatarioCanale, idDestinatario, idCanaleNotifica, flgCanaleDefault, dataInizio, dataFine);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "DestinatarioCanaleDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idDestinatarioCanale:" + idDestinatarioCanale +
                ",\n         idDestinatario:" + idDestinatario +
                ",\n         idCanaleNotifica:" + idCanaleNotifica +
                ",\n         flgCanaleDefault:" + flgCanaleDefault +
                ",\n         dataInizio:" + dataInizio +
                ",\n         dataFine:" + dataFine +
                "}\n";
    }
}
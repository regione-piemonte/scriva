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

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * The type Tipo notifica evento dto.
 *
 * @author CSI PIEMONTE
 */
public class TipoNotificaEventoDTO extends BaseDTO implements Serializable {

    private Long idTipoNotificaEvento;

    private Long idTipoNotifica;

    private Long idTipoEvento;

    private Date dataInizio;

    private Date dataFine;

    /**
     * Gets id tipo notifica evento.
     *
     * @return the id tipo notifica evento
     */
    public Long getIdTipoNotificaEvento() {
        return idTipoNotificaEvento;
    }

    /**
     * Sets id tipo notifica evento.
     *
     * @param idTipoNotificaEvento the id tipo notifica evento
     */
    public void setIdTipoNotificaEvento(Long idTipoNotificaEvento) {
        this.idTipoNotificaEvento = idTipoNotificaEvento;
    }

    /**
     * Gets id tipo notifica.
     *
     * @return the id tipo notifica
     */
    public Long getIdTipoNotifica() {
        return idTipoNotifica;
    }

    /**
     * Sets id tipo notifica.
     *
     * @param idTipoNotifica the id tipo notifica
     */
    public void setIdTipoNotifica(Long idTipoNotifica) {
        this.idTipoNotifica = idTipoNotifica;
    }

    /**
     * Gets id tipo evento.
     *
     * @return the id tipo evento
     */
    public Long getIdTipoEvento() {
        return idTipoEvento;
    }

    /**
     * Sets id tipo evento.
     *
     * @param idTipoEvento the id tipo evento
     */
    public void setIdTipoEvento(Long idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
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
        TipoNotificaEventoDTO that = (TipoNotificaEventoDTO) o;
        return Objects.equals(idTipoNotificaEvento, that.idTipoNotificaEvento) && Objects.equals(idTipoNotifica, that.idTipoNotifica) && Objects.equals(idTipoEvento, that.idTipoEvento) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idTipoNotificaEvento, idTipoNotifica, idTipoEvento, dataInizio, dataFine);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipoNotificaEventoDTO {\n" +
                "         idTipoNotificaEvento:" + idTipoNotificaEvento +
                ",\n         idTipoNotifica:" + idTipoNotifica +
                ",\n         idTipoEvento:" + idTipoEvento +
                ",\n         dataInizio:" + dataInizio +
                ",\n         dataFine:" + dataFine +
                super.toString() +
                "}\n";
    }
}
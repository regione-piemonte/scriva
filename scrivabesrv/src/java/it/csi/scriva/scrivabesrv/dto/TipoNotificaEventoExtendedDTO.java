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
import java.util.Objects;

/**
 * The type Tipo notifica evento extended dto.
 *
 * @author CSI PIEMONTE
 */
public class TipoNotificaEventoExtendedDTO extends TipoNotificaEventoDTO implements Serializable {

    private TipoNotificaDTO tipoNotifica;

    private TipoEventoExtendedDTO tipoEvento;

    /**
     * Gets tipo notifica.
     *
     * @return the tipo notifica
     */
    public TipoNotificaDTO getTipoNotifica() {
        return tipoNotifica;
    }

    /**
     * Sets tipo notifica.
     *
     * @param tipoNotifica the tipo notifica
     */
    public void setTipoNotifica(TipoNotificaDTO tipoNotifica) {
        this.tipoNotifica = tipoNotifica;
    }

    /**
     * Gets tipo evento.
     *
     * @return the tipo evento
     */
    public TipoEventoExtendedDTO getTipoEvento() {
        return tipoEvento;
    }

    /**
     * Sets tipo evento.
     *
     * @param tipoEvento the tipo evento
     */
    public void setTipoEvento(TipoEventoExtendedDTO tipoEvento) {
        this.tipoEvento = tipoEvento;
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
        TipoNotificaEventoExtendedDTO that = (TipoNotificaEventoExtendedDTO) o;
        return Objects.equals(tipoNotifica, that.tipoNotifica) && Objects.equals(tipoEvento, that.tipoEvento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoNotifica, tipoEvento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipoNotificaEventoExtendedDTO {\n" +
                "         tipoNotifica:" + tipoNotifica +
                ",\n         tipoEvento:" + tipoEvento +
                super.toString() +
                "}\n";
    }
}
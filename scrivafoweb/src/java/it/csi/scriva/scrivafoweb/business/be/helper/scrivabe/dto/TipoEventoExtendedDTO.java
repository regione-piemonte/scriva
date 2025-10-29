/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipo evento extended dto.
 *
 TipoAbilitazioneDAO * @author CSI PIEMONTE
 */
public class TipoEventoExtendedDTO extends TipoEventoDTO implements Serializable {


    @JsonProperty("stato_istanza_evento")
    private StatoIstanzaDTO statoIstanzaEvento;

    /**
     * Gets stato istanza evento.
     *
     * @return the stato istanza evento
     */
    public StatoIstanzaDTO getStatoIstanzaEvento() {
        return statoIstanzaEvento;
    }

    /**
     * Sets stato istanza evento.
     *
     * @param statoIstanzaEvento the stato istanza evento
     */
    public void setStatoIstanzaEvento(StatoIstanzaDTO statoIstanzaEvento) {
        this.statoIstanzaEvento = statoIstanzaEvento;
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
        TipoEventoExtendedDTO that = (TipoEventoExtendedDTO) o;
        return Objects.equals(statoIstanzaEvento, that.statoIstanzaEvento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), statoIstanzaEvento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoEventoExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         statoIstanzaEvento:").append(statoIstanzaEvento);
        sb.append("}\n");
        return sb.toString();
    }
}
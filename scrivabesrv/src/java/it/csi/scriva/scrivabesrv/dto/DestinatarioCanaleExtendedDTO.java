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
import java.util.Objects;

/**
 * The type Destinatario canale extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DestinatarioCanaleExtendedDTO extends DestinatarioCanaleDTO implements Serializable {

    private DestinatarioExtendedDTO destinatario;

    private CanaleNotificaExtendedDTO canaleNotifica;

    /**
     * Gets destinatario.
     *
     * @return the destinatario
     */
    public DestinatarioExtendedDTO getDestinatario() {
        return destinatario;
    }

    /**
     * Sets destinatario.
     *
     * @param destinatario the destinatario
     */
    public void setDestinatario(DestinatarioExtendedDTO destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * Gets canale notifica.
     *
     * @return the canale notifica
     */
    public CanaleNotificaExtendedDTO getCanaleNotifica() {
        return canaleNotifica;
    }

    /**
     * Sets canale notifica.
     *
     * @param canaleNotifica the canale notifica
     */
    public void setCanaleNotifica(CanaleNotificaExtendedDTO canaleNotifica) {
        this.canaleNotifica = canaleNotifica;
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
        DestinatarioCanaleExtendedDTO that = (DestinatarioCanaleExtendedDTO) o;
        return Objects.equals(destinatario, that.destinatario) && Objects.equals(canaleNotifica, that.canaleNotifica);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), destinatario, canaleNotifica);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "DestinatarioCanaleExtendedDTO {\n" +
                "         destinatario:" + destinatario +
                ",\n         canaleNotifica:" + canaleNotifica +
                super.toString() +
                "}\n";
    }

}
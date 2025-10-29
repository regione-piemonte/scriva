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

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Pratica collegata.
 */
public class PraticaCollegataDTO extends IstanzaDTO implements Serializable {

    private SoggettoIstanzaDTO soggetto;

    /**
     * Gets soggetto.
     *
     * @return the soggetto
     */
    public SoggettoIstanzaDTO getSoggetto() {
        return soggetto;
    }

    /**
     * Sets soggetto.
     *
     * @param soggetto the soggetto
     */
    public void setSoggetto(SoggettoIstanzaDTO soggetto) {
        this.soggetto = soggetto;
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
        PraticaCollegataDTO that = (PraticaCollegataDTO) o;
        return Objects.equals(soggetto, that.soggetto);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), soggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PraticaCollegataDTO {\n");
        sb.append(super.toString());
        sb.append("         soggetto:").append(soggetto);
        sb.append("}\n");
        return sb.toString();
    }

}
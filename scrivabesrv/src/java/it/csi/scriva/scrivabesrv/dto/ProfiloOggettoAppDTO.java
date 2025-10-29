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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Profilo app extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfiloOggettoAppDTO extends ProfiloAppExtendedDTO implements Serializable {

    @JsonProperty("oggetto_app")
    private OggettoAppExtendedDTO oggettoApp;

    /**
     * Gets oggetto app.
     *
     * @return the oggetto app
     */
    public OggettoAppExtendedDTO getOggettoApp() {
        return oggettoApp;
    }

    /**
     * Sets oggetto app.
     *
     * @param oggettoApp the oggetto app
     */
    public void setOggettoApp(OggettoAppExtendedDTO oggettoApp) {
        this.oggettoApp = oggettoApp;
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
        ProfiloOggettoAppDTO that = (ProfiloOggettoAppDTO) o;
        return Objects.equals(oggettoApp, that.oggettoApp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oggettoApp);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "ProfiloOggettoAppDTO {\n" +
                "         oggettoApp:" + oggettoApp +
                "}\n";
    }
}
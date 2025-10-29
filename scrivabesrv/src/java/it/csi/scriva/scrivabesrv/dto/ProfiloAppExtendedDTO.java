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
public class ProfiloAppExtendedDTO extends ProfiloAppDTO implements Serializable {

    @JsonProperty("componente_app")
    private ComponenteAppDTO componenteApp;

    /**
     * Gets componente app.
     *
     * @return ComponenteAppDTO componente app
     */
    public ComponenteAppDTO getComponenteApp() {
        return componenteApp;
    }

    /**
     * Sets componente app.
     *
     * @param componenteApp ComponenteAppDTO
     */
    public void setComponenteApp(ComponenteAppDTO componenteApp) {
        this.componenteApp = componenteApp;
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
        ProfiloAppExtendedDTO that = (ProfiloAppExtendedDTO) o;
        return Objects.equals(componenteApp, that.componenteApp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), componenteApp);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProfiloAppExtendedDTO {");
        sb.append(super.toString());
        sb.append("         componenteApp:").append(componenteApp);
        sb.append("}");
        return sb.toString();
    }
}
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
 * The type Destinatario dto.
 *
 * @author CSI PIEMONTE
 */
public class DestinatarioExtendedDTO extends DestinatarioDTO implements Serializable {

    private TipoDestinatarioDTO tipoDestinatario;

    private ProfiloAppDTO profiloApp;

    private ComponenteAppDTO componenteApp;

    /**
     * Gets tipo destinatario.
     *
     * @return the tipo destinatario
     */
    public TipoDestinatarioDTO getTipoDestinatario() {
        return tipoDestinatario;
    }

    /**
     * Sets tipo destinatario.
     *
     * @param tipoDestinatario the tipo destinatario
     */
    public void setTipoDestinatario(TipoDestinatarioDTO tipoDestinatario) {
        this.tipoDestinatario = tipoDestinatario;
    }

    /**
     * Gets profilo app.
     *
     * @return the profilo app
     */
    public ProfiloAppDTO getProfiloApp() {
        return profiloApp;
    }

    /**
     * Sets profilo app.
     *
     * @param profiloApp the profilo app
     */
    public void setProfiloApp(ProfiloAppDTO profiloApp) {
        this.profiloApp = profiloApp;
    }

    /**
     * Gets componente app.
     *
     * @return the componente app
     */
    public ComponenteAppDTO getComponenteApp() {
        return componenteApp;
    }

    /**
     * Sets componente app.
     *
     * @param componenteApp the componente app
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
        DestinatarioExtendedDTO that = (DestinatarioExtendedDTO) o;
        return Objects.equals(tipoDestinatario, that.tipoDestinatario) && Objects.equals(profiloApp, that.profiloApp) && Objects.equals(componenteApp, that.componenteApp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoDestinatario, profiloApp, componenteApp);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "DestinatarioExtendedDTO {\n" +
                "         tipoDestinatario:" + tipoDestinatario +
                ",\n         profiloApp:" + profiloApp +
                ",\n         componenteApp:" + componenteApp +
                "}\n";
    }
}
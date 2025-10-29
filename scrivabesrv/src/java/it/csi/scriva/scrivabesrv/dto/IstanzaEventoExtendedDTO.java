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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Istanza evento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IstanzaEventoExtendedDTO extends IstanzaEventoDTO implements Serializable {

    @JsonProperty("tipo_evento")
    private TipoEventoExtendedDTO tipoEvento;

    @JsonProperty("componente_app")
    private ComponenteAppDTO componenteApp;

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
        IstanzaEventoExtendedDTO that = (IstanzaEventoExtendedDTO) o;
        return Objects.equals(tipoEvento, that.tipoEvento) && Objects.equals(componenteApp, that.componenteApp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoEvento, componenteApp);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaEventoExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         tipoEvento:").append(tipoEvento);
        sb.append(",\n         componenteApp:").append(componenteApp);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public IstanzaEventoDTO getDTO() {
        IstanzaEventoDTO dto = new IstanzaEventoDTO();
        dto.setIdIstanzaEvento(this.getIdIstanzaEvento());
        dto.setIdIstanza(this.getIdIstanza());
        dto.setIdIstanzaAttore(this.getIdIstanzaAttore());
        if (this.getTipoEvento() != null) {
            dto.setIdTipoEvento(this.getTipoEvento().getIdTipoEvento());
        }
        dto.setDataEvento(this.getDataEvento());
        dto.setIdIstanzaAttore(this.getIdIstanzaAttore());
        dto.setIdFunzionario(this.getIdFunzionario());

        if (this.getComponenteApp() != null) {
            dto.setIdComponenteApp(this.getComponenteApp().getIdComponenteApp());
        }
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }
}
/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */

package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class OggettoAppExtendedDTO extends OggettoAppDTO implements Serializable {

    @JsonProperty("tipo_oggetto_app")
    private TipoOggettoAppDTO tipoOggettoAppDTO;

    /**
     * @return tipoOggettoApp
     */
    @JsonProperty("tipo_oggetto_app")
    public TipoOggettoAppDTO getTipoOggettoApp() {
        return tipoOggettoAppDTO;
    }

    /**
     * @param tipoOggettoAppDTO tipoOggettoApp
     */
    public void setTipoOggettoApp(TipoOggettoAppDTO tipoOggettoAppDTO) {
        this.tipoOggettoAppDTO = tipoOggettoAppDTO;
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
        OggettoAppExtendedDTO that = (OggettoAppExtendedDTO) o;
        return Objects.equals(tipoOggettoAppDTO, that.tipoOggettoAppDTO);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoOggettoAppDTO);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoAppExtendedDTO {");
        sb.append(super.toString());
        sb.append("         tipoOggettoApp:").append(tipoOggettoAppDTO);
        sb.append("}");
        return sb.toString();
    }
}
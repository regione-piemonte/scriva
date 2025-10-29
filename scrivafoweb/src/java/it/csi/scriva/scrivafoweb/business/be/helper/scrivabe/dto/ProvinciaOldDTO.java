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

public class ProvinciaOldDTO implements Serializable {
    @JsonProperty("des_provincia")
    private String provincia;

    @JsonProperty("sigla_provincia")
    private String siglaProvincia;

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getSiglaProvincia() {
        return siglaProvincia;
    }

    public void setSiglaProvincia(String siglaProvincia) {
        this.siglaProvincia = siglaProvincia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(provincia, siglaProvincia);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProvinciaOldDTO other = (ProvinciaOldDTO) obj;
        return Objects.equals(provincia, other.provincia) && Objects.equals(siglaProvincia, other.siglaProvincia);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProvinciaOldDTO [provincia=").append(provincia).append("\n  siglaProvincia=").append(siglaProvincia).append("]");
        return builder.toString();
    }


}
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

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComuneOldDTO {

    @JsonProperty("id_comune")
    private Long idComune;

    @JsonProperty("codice_istat_comune")
    private String codiceIstatComune;

    @JsonProperty("toponimo")
    private String toponimo;

    @JsonProperty("cap_comune")
    private String capComune;

    @JsonProperty("des_provincia")
    private String provincia;

    @JsonProperty("sigla_provincia")
    private String siglaProvincia;

    public Long getIdComune() {
        return idComune;
    }

    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }

    public String getCodiceIstatComune() {
        return codiceIstatComune;
    }

    public void setCodiceIstatComune(String codiceIstatComune) {
        this.codiceIstatComune = codiceIstatComune;
    }

    public String getToponimo() {
        return toponimo;
    }

    public void setToponimo(String toponimo) {
        this.toponimo = toponimo;
    }

    public String getCapComune() {
        return capComune;
    }

    public void setCapComune(String capComune) {
        this.capComune = capComune;
    }

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
        return Objects.hash(capComune, codiceIstatComune, idComune, provincia, siglaProvincia, toponimo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComuneOldDTO other = (ComuneOldDTO) obj;
        return Objects.equals(capComune, other.capComune) && Objects.equals(codiceIstatComune, other.codiceIstatComune) && Objects.equals(idComune, other.idComune) && Objects.equals(provincia, other.provincia) && Objects.equals(siglaProvincia, other.siglaProvincia) && Objects.equals(toponimo, other.toponimo);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ComuneOldDTO [idComune=").append(idComune).append("\n  codiceIstatComune=").append(codiceIstatComune).append("\n  toponimo=").append(toponimo).append("\n  capComune=").append(capComune).append("\n  provincia=").append(provincia).append("\n  siglaProvincia=").append(siglaProvincia).append("]");
        return builder.toString();
    }

}
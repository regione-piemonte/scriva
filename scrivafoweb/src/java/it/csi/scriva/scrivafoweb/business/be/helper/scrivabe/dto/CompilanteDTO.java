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

public class CompilanteDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_compilante")
    private Long idCompilante;

    @JsonProperty("cf_compilante")
    private String codiceFiscaleCompilante;

    @JsonProperty("cognome_compilante")
    private String cognomeCompilante;

    @JsonProperty("nome_compilante")
    private String nomeCompilante;

    @JsonProperty("des_email_compilante")
    private String desEmailCompilante;

    public Long getIdCompilante() {
        return idCompilante;
    }

    public void setIdCompilante(Long idCompilante) {
        this.idCompilante = idCompilante;
    }

    public String getCodiceFiscaleCompilante() {
        return codiceFiscaleCompilante;
    }

    public void setCodiceFiscaleCompilante(String codiceFiscaleCompilante) {
        this.codiceFiscaleCompilante = codiceFiscaleCompilante;
    }

    public String getCognomeCompilante() {
        return cognomeCompilante;
    }

    public void setCognomeCompilante(String cognomeCompilante) {
        this.cognomeCompilante = cognomeCompilante;
    }

    public String getNomeCompilante() {
        return nomeCompilante;
    }

    public void setNomeCompilante(String nomeCompilante) {
        this.nomeCompilante = nomeCompilante;
    }

    public String getDesEmailCompilante() {
        return desEmailCompilante;
    }

    public void setDesEmailCompilante(String desEmailCompilante) {
        this.desEmailCompilante = desEmailCompilante;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceFiscaleCompilante, cognomeCompilante, desEmailCompilante, idCompilante, nomeCompilante);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompilanteDTO other = (CompilanteDTO) obj;
        return Objects.equals(codiceFiscaleCompilante, other.codiceFiscaleCompilante) && Objects.equals(cognomeCompilante, other.cognomeCompilante) && Objects.equals(desEmailCompilante, other.desEmailCompilante) && Objects.equals(idCompilante, other.idCompilante) && Objects.equals(nomeCompilante, other.nomeCompilante);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CompilanteDTO [idCompilante=").append(idCompilante).append("\n  codiceFiscaleCompilante=").append(codiceFiscaleCompilante).append("\n  cognomeCompilante=").append(cognomeCompilante).append("\n  nomeCompilante=").append(nomeCompilante).append("\n  desEmailCompilante=").append(desEmailCompilante).append("]");
        return builder.toString();
    }

}
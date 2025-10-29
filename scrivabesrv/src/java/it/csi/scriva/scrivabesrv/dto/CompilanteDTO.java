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
 * The type Compilante dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    /**
     * Gets id compilante.
     *
     * @return the id compilante
     */
    public Long getIdCompilante() {
        return idCompilante;
    }

    /**
     * Sets id compilante.
     *
     * @param idCompilante the id compilante
     */
    public void setIdCompilante(Long idCompilante) {
        this.idCompilante = idCompilante;
    }

    /**
     * Gets codice fiscale compilante.
     *
     * @return the codice fiscale compilante
     */
    public String getCodiceFiscaleCompilante() {
        return codiceFiscaleCompilante;
    }

    /**
     * Sets codice fiscale compilante.
     *
     * @param codiceFiscaleCompilante the codice fiscale compilante
     */
    public void setCodiceFiscaleCompilante(String codiceFiscaleCompilante) {
        this.codiceFiscaleCompilante = codiceFiscaleCompilante;
    }

    /**
     * Gets cognome compilante.
     *
     * @return the cognome compilante
     */
    public String getCognomeCompilante() {
        return cognomeCompilante;
    }

    /**
     * Sets cognome compilante.
     *
     * @param cognomeCompilante the cognome compilante
     */
    public void setCognomeCompilante(String cognomeCompilante) {
        this.cognomeCompilante = cognomeCompilante;
    }

    /**
     * Gets nome compilante.
     *
     * @return the nome compilante
     */
    public String getNomeCompilante() {
        return nomeCompilante;
    }

    /**
     * Sets nome compilante.
     *
     * @param nomeCompilante the nome compilante
     */
    public void setNomeCompilante(String nomeCompilante) {
        this.nomeCompilante = nomeCompilante;
    }

    /**
     * Gets des email compilante.
     *
     * @return the des email compilante
     */
    public String getDesEmailCompilante() {
        return desEmailCompilante;
    }

    /**
     * Sets des email compilante.
     *
     * @param desEmailCompilante the des email compilante
     */
    public void setDesEmailCompilante(String desEmailCompilante) {
        this.desEmailCompilante = desEmailCompilante;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codiceFiscaleCompilante, cognomeCompilante, desEmailCompilante, idCompilante, nomeCompilante);
    }

    /**
     * @param obj Object
     * @return boolean
     */
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

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CompilanteDTO [idCompilante=").append(idCompilante).append("\n  codiceFiscaleCompilante=").append(codiceFiscaleCompilante).append("\n  cognomeCompilante=").append(cognomeCompilante).append("\n  nomeCompilante=").append(nomeCompilante).append("\n  desEmailCompilante=").append(desEmailCompilante).append("]");
        return builder.toString();
    }

}
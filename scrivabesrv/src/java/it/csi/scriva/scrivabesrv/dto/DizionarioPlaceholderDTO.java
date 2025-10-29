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
 * The type Dizionario placeholder dto.
 *
 * @author CSI PIEMONTE
 */
public class DizionarioPlaceholderDTO implements Serializable {

    private Long idDizionarioPlaceholder;

    private String codDizionarioPlaceholder;

    private String nomeTabella;

    private String nomeCampo;

    private String desDizionarioPlaceholder;

    /**
     * Gets id dizionario placeholder.
     *
     * @return the id dizionario placeholder
     */
    public Long getIdDizionarioPlaceholder() {
        return idDizionarioPlaceholder;
    }

    /**
     * Sets id dizionario placeholder.
     *
     * @param idDizionarioPlaceholder the id dizionario placeholder
     */
    public void setIdDizionarioPlaceholder(Long idDizionarioPlaceholder) {
        this.idDizionarioPlaceholder = idDizionarioPlaceholder;
    }

    /**
     * Gets cod dizionario placeholder.
     *
     * @return the cod dizionario placeholder
     */
    public String getCodDizionarioPlaceholder() {
        return codDizionarioPlaceholder;
    }

    /**
     * Sets cod dizionario placeholder.
     *
     * @param codDizionarioPlaceholder the cod dizionario placeholder
     */
    public void setCodDizionarioPlaceholder(String codDizionarioPlaceholder) {
        this.codDizionarioPlaceholder = codDizionarioPlaceholder;
    }

    /**
     * Gets nome tabella.
     *
     * @return the nome tabella
     */
    public String getNomeTabella() {
        return nomeTabella;
    }

    /**
     * Sets nome tabella.
     *
     * @param nomeTabella the nome tabella
     */
    public void setNomeTabella(String nomeTabella) {
        this.nomeTabella = nomeTabella;
    }

    /**
     * Gets nome campo.
     *
     * @return the nome campo
     */
    public String getNomeCampo() {
        return nomeCampo;
    }

    /**
     * Sets nome campo.
     *
     * @param nomeCampo the nome campo
     */
    public void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }

    /**
     * Gets des dizionario placeholder.
     *
     * @return the des dizionario placeholder
     */
    public String getDesDizionarioPlaceholder() {
        return desDizionarioPlaceholder;
    }

    /**
     * Sets des dizionario placeholder.
     *
     * @param desDizionarioPlaceholder the des dizionario placeholder
     */
    public void setDesDizionarioPlaceholder(String desDizionarioPlaceholder) {
        this.desDizionarioPlaceholder = desDizionarioPlaceholder;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DizionarioPlaceholderDTO that = (DizionarioPlaceholderDTO) o;
        return Objects.equals(idDizionarioPlaceholder, that.idDizionarioPlaceholder) && Objects.equals(codDizionarioPlaceholder, that.codDizionarioPlaceholder) && Objects.equals(nomeTabella, that.nomeTabella) && Objects.equals(nomeCampo, that.nomeCampo) && Objects.equals(desDizionarioPlaceholder, that.desDizionarioPlaceholder);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idDizionarioPlaceholder, codDizionarioPlaceholder, nomeTabella, nomeCampo, desDizionarioPlaceholder);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "DizionarioPlaceholderDTO {\n" +
                "         idDizionarioPlaceholder:" + idDizionarioPlaceholder +
                ",\n         codDizionarioPlaceholder:'" + codDizionarioPlaceholder + "'" +
                ",\n         nomeTabella:'" + nomeTabella + "'" +
                ",\n         nomeCampo:'" + nomeCampo + "'" +
                ",\n         desDizionarioPlaceholder:'" + desDizionarioPlaceholder + "'" +
                "}\n";
    }
}
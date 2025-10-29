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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Gestione attore dto.
 *
 * @author CSI PIEMONTE
 */
public class GestioneAttoreDTO implements Serializable {

    @JsonProperty("id_gestione_attore")
    private Long idGestioneAttore;

    @JsonProperty("cod_gestione_attore")
    private String codGestioneAttore;

    @JsonProperty("des_gestione_attore")
    private String desGestioneAttore;

    @JsonProperty("ordinamento_gestione_attore")
    private Long ordinamentoGestioneAttore;

    /**
     * Gets id gestione attore.
     *
     * @return the id gestione attore
     */
    public Long getIdGestioneAttore() {
        return this.idGestioneAttore;
    }

    /**
     * Sets id gestione attore.
     *
     * @param idGestioneAttore the id gestione attore
     */
    public void setIdGestioneAttore(Long idGestioneAttore) {
        this.idGestioneAttore = idGestioneAttore;
    }

    /**
     * Gets cod gestione attore.
     *
     * @return the cod gestione attore
     */
    public String getCodGestioneAttore() {
        return this.codGestioneAttore;
    }

    /**
     * Sets cod gestione attore.
     *
     * @param codGestioneAttore the cod gestione attore
     */
    public void setCodGestioneAttore(String codGestioneAttore) {
        this.codGestioneAttore = codGestioneAttore;
    }

    /**
     * Gets des gestione attore.
     *
     * @return the des gestione attore
     */
    public String getDesGestioneAttore() {
        return this.desGestioneAttore;
    }

    /**
     * Sets des gestione attore.
     *
     * @param desGestioneAttore the des gestione attore
     */
    public void setDesGestioneAttore(String desGestioneAttore) {
        this.desGestioneAttore = desGestioneAttore;
    }

    /**
     * Gets ordinamento gestione attore.
     *
     * @return the ordinamento gestione attore
     */
    public Long getOrdinamentoGestioneAttore() {
        return this.ordinamentoGestioneAttore;
    }

    /**
     * Sets ordinamento gestione attore.
     *
     * @param ordinamentoGestioneAttore the ordinamento gestione attore
     */
    public void setOrdinamentoGestioneAttore(Long ordinamentoGestioneAttore) {
        this.ordinamentoGestioneAttore = ordinamentoGestioneAttore;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GestioneAttoreDTO that = (GestioneAttoreDTO) o;
        return Objects.equals(idGestioneAttore, that.idGestioneAttore) && Objects.equals(codGestioneAttore, that.codGestioneAttore) && Objects.equals(desGestioneAttore, that.desGestioneAttore) && Objects.equals(ordinamentoGestioneAttore, that.ordinamentoGestioneAttore);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idGestioneAttore, codGestioneAttore, desGestioneAttore, ordinamentoGestioneAttore);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "GestioneAttoreDTO {\n" +
                "         idGestioneAttore:" + idGestioneAttore +
                ",\n         codGestioneAttore:'" + codGestioneAttore + "'" +
                ",\n         desGestioneAttore:'" + desGestioneAttore + "'" +
                ",\n         ordinamentoGestioneAttore:" + ordinamentoGestioneAttore +
                "}\n";
    }
}
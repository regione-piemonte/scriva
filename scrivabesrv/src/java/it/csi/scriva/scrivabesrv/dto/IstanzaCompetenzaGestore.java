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
 * The type Istanza competenza gestore.
 */
public class IstanzaCompetenzaGestore implements Serializable {

    /**
     * The Id istanza.
     */
    Long idIstanza;

    /**
     * The Id competenza territorio.
     */
    Long idCompetenzaTerritorio;

    /**
     * The Id componente gestore processo.
     */
    Long idComponenteGestoreProcesso;

    /**
     * Gets id istanza.
     *
     * @return the id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza the id istanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets id competenza territorio.
     *
     * @return the id competenza territorio
     */
    public Long getIdCompetenzaTerritorio() {
        return idCompetenzaTerritorio;
    }

    /**
     * Sets id competenza territorio.
     *
     * @param idCompetenzaTerritorio the id competenza territorio
     */
    public void setIdCompetenzaTerritorio(Long idCompetenzaTerritorio) {
        this.idCompetenzaTerritorio = idCompetenzaTerritorio;
    }

    /**
     * Gets id componente gestore processo.
     *
     * @return the id componente gestore processo
     */
    public Long getIdComponenteGestoreProcesso() {
        return idComponenteGestoreProcesso;
    }

    /**
     * Sets id componente gestore processo.
     *
     * @param idComponenteGestoreProcesso the id componente gestore processo
     */
    public void setIdComponenteGestoreProcesso(Long idComponenteGestoreProcesso) {
        this.idComponenteGestoreProcesso = idComponenteGestoreProcesso;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IstanzaCompetenzaGestore that = (IstanzaCompetenzaGestore) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio) && Objects.equals(idComponenteGestoreProcesso, that.idComponenteGestoreProcesso);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, idCompetenzaTerritorio, idComponenteGestoreProcesso);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IstanzaCompetenzaGestore {\n" +
                "         idIstanza:" + idIstanza +
                ",\n         idCompetenzaTerritorio:" + idCompetenzaTerritorio +
                ",\n         idComponenteGestoreProcesso:" + idComponenteGestoreProcesso +
                "}\n";
    }
}
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Soggetto istanza dto.
 *
 * @author CSI PIEMONTE
 */
public class SoggettoIstanzaDTO extends SoggettoDTO implements Serializable {

    @JsonProperty("id_soggetto_istanza")
    private Long idSoggettoIstanza;

    @JsonProperty("id_soggetto_padre")
    private Long idSoggettoPadre;

    @JsonProperty("id_ruolo_soggetto")
    private Long idRuoloSoggetto;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_ruolo_compilante")
    private Long idRuoloCompilante;

    @JsonIgnore
    private Long idIstanzaAttore;

    /**
     * Gets id soggetto istanza.
     *
     * @return the id soggetto istanza
     */
    public Long getIdSoggettoIstanza() {
        return idSoggettoIstanza;
    }

    /**
     * Sets id soggetto istanza.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     */
    public void setIdSoggettoIstanza(Long idSoggettoIstanza) {
        this.idSoggettoIstanza = idSoggettoIstanza;
    }

    /**
     * Gets id soggetto padre.
     *
     * @return the id soggetto padre
     */
    public Long getIdSoggettoPadre() {
        return idSoggettoPadre;
    }

    /**
     * Sets id soggetto padre.
     *
     * @param idSoggettoPadre the id soggetto padre
     */
    public void setIdSoggettoPadre(Long idSoggettoPadre) {
        this.idSoggettoPadre = idSoggettoPadre;
    }

    /**
     * Gets id ruolo soggetto.
     *
     * @return the id ruolo soggetto
     */
    public Long getIdRuoloSoggetto() {
        return idRuoloSoggetto;
    }

    /**
     * Sets id ruolo soggetto.
     *
     * @param idRuoloSoggetto the id ruolo soggetto
     */
    public void setIdRuoloSoggetto(Long idRuoloSoggetto) {
        this.idRuoloSoggetto = idRuoloSoggetto;
    }

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
     * Gets id ruolo compilante.
     *
     * @return the id ruolo compilante
     */
    public Long getIdRuoloCompilante() {
        return idRuoloCompilante;
    }

    /**
     * Sets id ruolo compilante.
     *
     * @param idRuoloCompilante the id ruolo compilante
     */
    public void setIdRuoloCompilante(Long idRuoloCompilante) {
        this.idRuoloCompilante = idRuoloCompilante;
    }

    /**
     * Gets id istanza attore.
     *
     * @return the id istanza attore
     */
    public Long getIdIstanzaAttore() {
        return idIstanzaAttore;
    }

    /**
     * Sets id istanza attore.
     *
     * @param idIstanzaAttore the id istanza attore
     */
    public void setIdIstanzaAttore(Long idIstanzaAttore) {
        this.idIstanzaAttore = idIstanzaAttore;
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
        SoggettoIstanzaDTO that = (SoggettoIstanzaDTO) o;
        return Objects.equals(idSoggettoIstanza, that.idSoggettoIstanza) && Objects.equals(idSoggettoPadre, that.idSoggettoPadre) && Objects.equals(idRuoloSoggetto, that.idRuoloSoggetto) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idRuoloCompilante, that.idRuoloCompilante) && Objects.equals(idIstanzaAttore, that.idIstanzaAttore);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idSoggettoIstanza, idSoggettoPadre, idRuoloSoggetto, idIstanza, idRuoloCompilante, idIstanzaAttore);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "SoggettoIstanzaDTO {\n" +
                "         idSoggettoIstanza:" + idSoggettoIstanza +
                ",\n         idSoggettoPadre:" + idSoggettoPadre +
                ",\n         idRuoloSoggetto:" + idRuoloSoggetto +
                ",\n         idIstanza:" + idIstanza +
                ",\n         idRuoloCompilante:" + idRuoloCompilante +
                ",\n         idIstanzaAttore:" + idIstanzaAttore +
                super.toString() +
                "}\n";
    }

}
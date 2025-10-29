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

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Config json data dto.
 *
 * @author CSI PIEMONTE
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConfigJsonDataDTO implements Serializable {

    private Long idConfigJsonData;
    private Long idTipoQuadro;
    private String codTipoQuadro;
    private boolean flgObbligo;
    private String desTabella;
    private String desTag;
    private String queryEstrazione;

    /**
     * Gets id config json data.
     *
     * @return the id config json data
     */
    public Long getIdConfigJsonData() {
        return idConfigJsonData;
    }

    /**
     * Sets id config json data.
     *
     * @param idConfigJsonData the id config json data
     */
    public void setIdConfigJsonData(Long idConfigJsonData) {
        this.idConfigJsonData = idConfigJsonData;
    }

    /**
     * Gets id tipo quadro.
     *
     * @return the id tipo quadro
     */
    public Long getIdTipoQuadro() {
        return idTipoQuadro;
    }

    /**
     * Sets id tipo quadro.
     *
     * @param idTipoQuadro the id tipo quadro
     */
    public void setIdTipoQuadro(Long idTipoQuadro) {
        this.idTipoQuadro = idTipoQuadro;
    }

    /**
     * Gets cod tipo quadro.
     *
     * @return the cod tipo quadro
     */
    public String getCodTipoQuadro() {
        return codTipoQuadro;
    }

    /**
     * Sets cod tipo quadro.
     *
     * @param codTipoQuadro the cod tipo quadro
     */
    public void setCodTipoQuadro(String codTipoQuadro) {
        this.codTipoQuadro = codTipoQuadro;
    }

    /**
     * Is flg obbligo boolean.
     *
     * @return the boolean
     */
    public boolean isFlgObbligo() {
        return flgObbligo;
    }

    /**
     * Sets flg obbligo.
     *
     * @param flgObbligo the flg obbligo
     */
    public void setFlgObbligo(boolean flgObbligo) {
        this.flgObbligo = flgObbligo;
    }

    /**
     * Gets des tabella.
     *
     * @return the des tabella
     */
    public String getDesTabella() {
        return desTabella;
    }

    /**
     * Sets des tabella.
     *
     * @param desTabella the des tabella
     */
    public void setDesTabella(String desTabella) {
        this.desTabella = desTabella;
    }

    /**
     * Gets des tag.
     *
     * @return the des tag
     */
    public String getDesTag() {
        return desTag;
    }

    /**
     * Sets des tag.
     *
     * @param desTag the des tag
     */
    public void setDesTag(String desTag) {
        this.desTag = desTag;
    }

    /**
     * Gets query estrazione.
     *
     * @return the query estrazione
     */
    public String getQueryEstrazione() {
        return queryEstrazione;
    }

    /**
     * Sets query estrazione.
     *
     * @param queryEstrazione the query estrazione
     */
    public void setQueryEstrazione(String queryEstrazione) {
        this.queryEstrazione = queryEstrazione;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigJsonDataDTO that = (ConfigJsonDataDTO) o;
        return flgObbligo == that.flgObbligo && Objects.equals(idConfigJsonData, that.idConfigJsonData) && Objects.equals(idTipoQuadro, that.idTipoQuadro) && Objects.equals(codTipoQuadro, that.codTipoQuadro) && Objects.equals(desTabella, that.desTabella) && Objects.equals(desTag, that.desTag) && Objects.equals(queryEstrazione, that.queryEstrazione);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idConfigJsonData, idTipoQuadro, codTipoQuadro, flgObbligo, desTabella, desTag, queryEstrazione);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "ConfigJsonDataDTO {\n" +
                "         idConfigJsonData:" + idConfigJsonData +
                ",\n         idTipoQuadro:" + idTipoQuadro +
                ",\n         codTipoQuadro:'" + codTipoQuadro + "'" +
                ",\n         flgObbligo:" + flgObbligo +
                ",\n         desTabella:'" + desTabella + "'" +
                ",\n         desTag:'" + desTag + "'" +
                ",\n         queryEstrazione:'" + queryEstrazione + "'" +
                "}\n";
    }
}
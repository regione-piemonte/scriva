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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Quadro dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuadroDTO implements Serializable {

    @JsonProperty("id_quadro")
    private Long idQuadro;

    @JsonProperty("id_tipo_quadro")
    private Long idTipoQuadro;

    @JsonProperty("cod_quadro")
    private String codQuadro;

    @JsonProperty("des_quadro")
    private String desQuadro;

    @JsonProperty("flg_tipo_gestione")
    private String flgTipoGestione;

    @JsonProperty("json_configura_quadro")
    private String jsonConfiguraQuadro;

    @JsonProperty("json_configura_riepilogo")
    private String jsonConfiguraRiepilogo;

    @JsonIgnore
    private Boolean flgAttivo;

    /**
     * Gets id quadro.
     *
     * @return the id quadro
     */
    public Long getIdQuadro() {
        return idQuadro;
    }

    /**
     * Sets id quadro.
     *
     * @param idQuadro the id quadro
     */
    public void setIdQuadro(Long idQuadro) {
        this.idQuadro = idQuadro;
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
     * Gets cod quadro.
     *
     * @return the cod quadro
     */
    public String getCodQuadro() {
        return codQuadro;
    }

    /**
     * Sets cod quadro.
     *
     * @param codQuadro the cod quadro
     */
    public void setCodQuadro(String codQuadro) {
        this.codQuadro = codQuadro;
    }

    /**
     * Gets des quadro.
     *
     * @return the des quadro
     */
    public String getDesQuadro() {
        return desQuadro;
    }

    /**
     * Sets des quadro.
     *
     * @param desQuadro the des quadro
     */
    public void setDesQuadro(String desQuadro) {
        this.desQuadro = desQuadro;
    }

    /**
     * Gets flg tipo gestione.
     *
     * @return the flg tipo gestione
     */
    public String getFlgTipoGestione() {
        return flgTipoGestione;
    }

    /**
     * Sets flg tipo gestione.
     *
     * @param flgTipoGestione the flg tipo gestione
     */
    public void setFlgTipoGestione(String flgTipoGestione) {
        this.flgTipoGestione = flgTipoGestione;
    }

    /**
     * Gets json configura quadro.
     *
     * @return the json configura quadro
     */
    public String getJsonConfiguraQuadro() {
        return jsonConfiguraQuadro;
    }

    /**
     * Sets json configura quadro.
     *
     * @param jsonConfiguraQuadro the json configura quadro
     */
    public void setJsonConfiguraQuadro(String jsonConfiguraQuadro) {
        this.jsonConfiguraQuadro = jsonConfiguraQuadro;
    }

    /**
     * Gets json configura riepilogo.
     *
     * @return the json configura riepilogo
     */
    public String getJsonConfiguraRiepilogo() {
        return jsonConfiguraRiepilogo;
    }

    /**
     * Sets json configura riepilogo.
     *
     * @param jsonConfiguraRiepilogo the json configura riepilogo
     */
    public void setJsonConfiguraRiepilogo(String jsonConfiguraRiepilogo) {
        this.jsonConfiguraRiepilogo = jsonConfiguraRiepilogo;
    }

    /**
     * Gets flg attivo.
     *
     * @return the flg attivo
     */
    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    /**
     * Sets flg attivo.
     *
     * @param flgAttivo the flg attivo
     */
    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuadroDTO quadroDTO = (QuadroDTO) o;
        return Objects.equals(idQuadro, quadroDTO.idQuadro) && Objects.equals(idTipoQuadro, quadroDTO.idTipoQuadro) && Objects.equals(codQuadro, quadroDTO.codQuadro) && Objects.equals(desQuadro, quadroDTO.desQuadro) && Objects.equals(flgTipoGestione, quadroDTO.flgTipoGestione) && Objects.equals(jsonConfiguraQuadro, quadroDTO.jsonConfiguraQuadro) && Objects.equals(jsonConfiguraRiepilogo, quadroDTO.jsonConfiguraRiepilogo) && Objects.equals(flgAttivo, quadroDTO.flgAttivo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idQuadro, idTipoQuadro, codQuadro, desQuadro, flgTipoGestione, jsonConfiguraQuadro, jsonConfiguraRiepilogo, flgAttivo);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "QuadroDTO {\n" +
                "         idQuadro:" + idQuadro +
                ",\n         idTipoQuadro:" + idTipoQuadro +
                ",\n         codQuadro:'" + codQuadro + "'" +
                ",\n         desQuadro:'" + desQuadro + "'" +
                ",\n         flgTipoGestione:'" + flgTipoGestione + "'" +
                ",\n         jsonConfiguraQuadro:'" + jsonConfiguraQuadro + "'" +
                ",\n         jsonConfiguraRiepilogo:'" + jsonConfiguraRiepilogo + "'" +
                ",\n         flgAttivo:" + flgAttivo +
                "}\n";
    }
}
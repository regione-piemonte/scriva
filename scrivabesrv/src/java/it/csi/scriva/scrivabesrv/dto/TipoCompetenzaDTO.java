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
 * The type Tipo competenza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoCompetenzaDTO implements Serializable {

    @JsonProperty("id_tipo_competenza")
    private Long idTipoCompetenza;

    @JsonProperty("cod_tipo_competenza")
    private String codTipoCompetenza;

    @JsonProperty("des_tipo_competenza")
    private String desTipoCompetenza;

    @JsonProperty("des_tipo_competenza_estesa")
    private String desTipoCompetenzaEstesa;

    @JsonProperty("ordinamento_tipo_competenza")
    private Integer ordinamentoTipoCompetenza;

    @JsonIgnore
    private Long idCategoriaOggetto;

    /**
     * Gets id tipo competenza.
     *
     * @return idTipoCompetenza id tipo competenza
     */
    public Long getIdTipoCompetenza() {
        return idTipoCompetenza;
    }

    /**
     * Sets id tipo competenza.
     *
     * @param idTipoCompetenza idTipoCompetenza
     */
    public void setIdTipoCompetenza(Long idTipoCompetenza) {
        this.idTipoCompetenza = idTipoCompetenza;
    }

    /**
     * Gets cod tipo competenza.
     *
     * @return codTipoCompetenza cod tipo competenza
     */
    public String getCodTipoCompetenza() {
        return codTipoCompetenza;
    }

    /**
     * Sets cod tipo competenza.
     *
     * @param codTipoCompetenza codTipoCompetenza
     */
    public void setCodTipoCompetenza(String codTipoCompetenza) {
        this.codTipoCompetenza = codTipoCompetenza;
    }

    /**
     * Gets des tipo competenza.
     *
     * @return desTipoCompetenza des tipo competenza
     */
    public String getDesTipoCompetenza() {
        return desTipoCompetenza;
    }

    /**
     * Sets des tipo competenza.
     *
     * @param desTipoCompetenza desTipoCompetenza
     */
    public void setDesTipoCompetenza(String desTipoCompetenza) {
        this.desTipoCompetenza = desTipoCompetenza;
    }

    /**
     * Gets des tipo competenza estesa.
     *
     * @return desTipoCompetenzaEstesa des tipo competenza estesa
     */
    public String getDesTipoCompetenzaEstesa() {
        return desTipoCompetenzaEstesa;
    }

    /**
     * Sets des tipo competenza estesa.
     *
     * @param desTipoCompetenzaEstesa desTipoCompetenzaEstesa
     */
    public void setDesTipoCompetenzaEstesa(String desTipoCompetenzaEstesa) {
        this.desTipoCompetenzaEstesa = desTipoCompetenzaEstesa;
    }

    /**
     * Gets ordinamento tipo competenza.
     *
     * @return ordinamentoTipoCompetenza ordinamento tipo competenza
     */
    public Integer getOrdinamentoTipoCompetenza() {
        return ordinamentoTipoCompetenza;
    }

    /**
     * Sets ordinamento tipo competenza.
     *
     * @param ordinamentoTipoCompetenza ordinamentoTipoCompetenza
     */
    public void setOrdinamentoTipoCompetenza(Integer ordinamentoTipoCompetenza) {
        this.ordinamentoTipoCompetenza = ordinamentoTipoCompetenza;
    }

    /**
     * Gets id categoria oggetto.
     *
     * @return the id categoria oggetto
     */
    public Long getIdCategoriaOggetto() {
        return idCategoriaOggetto;
    }

    /**
     * Sets id categoria oggetto.
     *
     * @param idCategoriaOggetto the id categoria oggetto
     */
    public void setIdCategoriaOggetto(Long idCategoriaOggetto) {
        this.idCategoriaOggetto = idCategoriaOggetto;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoCompetenzaDTO that = (TipoCompetenzaDTO) o;
        return Objects.equals(idTipoCompetenza, that.idTipoCompetenza) && Objects.equals(codTipoCompetenza, that.codTipoCompetenza) && Objects.equals(desTipoCompetenza, that.desTipoCompetenza) && Objects.equals(desTipoCompetenzaEstesa, that.desTipoCompetenzaEstesa) && Objects.equals(ordinamentoTipoCompetenza, that.ordinamentoTipoCompetenza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipoCompetenza, codTipoCompetenza, desTipoCompetenza, desTipoCompetenzaEstesa, ordinamentoTipoCompetenza);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoCompetenzaDTO {");
        sb.append("         idTipoCompetenza:").append(idTipoCompetenza);
        sb.append(",         codTipoCompetenza:'").append(codTipoCompetenza).append("'");
        sb.append(",         desTipoCompetenza:'").append(desTipoCompetenza).append("'");
        sb.append(",         desTipoCompetenzaEstesa:'").append(desTipoCompetenzaEstesa).append("'");
        sb.append(",         ordinamentoTipoCompetenza:").append(ordinamentoTipoCompetenza);
        sb.append("}");
        return sb.toString();
    }
}
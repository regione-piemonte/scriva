/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author CSI PIEMONTE
 */
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

    /**
     * @return idTipoCompetenza
     */
    public Long getIdTipoCompetenza() {
        return idTipoCompetenza;
    }

    /**
     * @param idTipoCompetenza idTipoCompetenza
     */
    public void setIdTipoCompetenza(Long idTipoCompetenza) {
        this.idTipoCompetenza = idTipoCompetenza;
    }

    /**
     * @return codTipoCompetenza
     */
    public String getCodTipoCompetenza() {
        return codTipoCompetenza;
    }

    /**
     * @param codTipoCompetenza codTipoCompetenza
     */
    public void setCodTipoCompetenza(String codTipoCompetenza) {
        this.codTipoCompetenza = codTipoCompetenza;
    }

    /**
     * @return desTipoCompetenza
     */
    public String getDesTipoCompetenza() {
        return desTipoCompetenza;
    }

    /**
     * @param desTipoCompetenza desTipoCompetenza
     */
    public void setDesTipoCompetenza(String desTipoCompetenza) {
        this.desTipoCompetenza = desTipoCompetenza;
    }

    /**
     * @return desTipoCompetenzaEstesa
     */
    public String getDesTipoCompetenzaEstesa() {
        return desTipoCompetenzaEstesa;
    }

    /**
     * @param desTipoCompetenzaEstesa desTipoCompetenzaEstesa
     */
    public void setDesTipoCompetenzaEstesa(String desTipoCompetenzaEstesa) {
        this.desTipoCompetenzaEstesa = desTipoCompetenzaEstesa;
    }

    /**
     * @return ordinamentoTipoCompetenza
     */
    public Integer getOrdinamentoTipoCompetenza() {
        return ordinamentoTipoCompetenza;
    }

    /**
     * @param ordinamentoTipoCompetenza ordinamentoTipoCompetenza
     */
    public void setOrdinamentoTipoCompetenza(Integer ordinamentoTipoCompetenza) {
        this.ordinamentoTipoCompetenza = ordinamentoTipoCompetenza;
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
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipo soggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TipoSoggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_tipo_soggetto")
    private Long idTipoSoggetto;

    @JsonProperty("cod_tipo_soggetto")
    private String codiceTipoSoggetto;

    @JsonProperty("des_tipo_soggetto")
    private String descrizioneTipoSoggetto;

    /**
     * Gets id tipo soggetto.
     *
     * @return the id tipo soggetto
     */
    public Long getIdTipoSoggetto() {
        return idTipoSoggetto;
    }

    /**
     * Sets id tipo soggetto.
     *
     * @param idTipoSoggetto the id tipo soggetto
     */
    public void setIdTipoSoggetto(Long idTipoSoggetto) {
        this.idTipoSoggetto = idTipoSoggetto;
    }

    /**
     * Gets codice tipo soggetto.
     *
     * @return the codice tipo soggetto
     */
    public String getCodiceTipoSoggetto() {
        return codiceTipoSoggetto;
    }

    /**
     * Sets codice tipo soggetto.
     *
     * @param codiceTipoSoggetto the codice tipo soggetto
     */
    public void setCodiceTipoSoggetto(String codiceTipoSoggetto) {
        this.codiceTipoSoggetto = codiceTipoSoggetto;
    }

    /**
     * Gets descrizione tipo soggetto.
     *
     * @return the descrizione tipo soggetto
     */
    public String getDescrizioneTipoSoggetto() {
        return descrizioneTipoSoggetto;
    }

    /**
     * Sets descrizione tipo soggetto.
     *
     * @param descrizioneTipoSoggetto the descrizione tipo soggetto
     */
    public void setDescrizioneTipoSoggetto(String descrizioneTipoSoggetto) {
        this.descrizioneTipoSoggetto = descrizioneTipoSoggetto;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codiceTipoSoggetto, descrizioneTipoSoggetto, idTipoSoggetto);
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
        TipoSoggettoDTO other = (TipoSoggettoDTO) obj;
        return Objects.equals(codiceTipoSoggetto, other.codiceTipoSoggetto)
                && Objects.equals(descrizioneTipoSoggetto, other.descrizioneTipoSoggetto)
                && Objects.equals(idTipoSoggetto, other.idTipoSoggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoSoggettoDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idTipoSoggetto:").append(idTipoSoggetto);
        sb.append(",         codiceTipoSoggetto:'").append(codiceTipoSoggetto).append("'");
        sb.append(",         descrizioneTipoSoggetto:'").append(descrizioneTipoSoggetto).append("'");
        sb.append("}");
        return sb.toString();
    }
}
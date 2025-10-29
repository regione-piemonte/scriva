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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Tipo Responsabile dto.
 *
 * @author CSI PIEMONTE
 */
/**
 * @author stdiana
 *
 */
/**
 * @author stdiana
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TipoResponsabileDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_tipo_responsabile")
    private Long idTipoResponsabile;

    @JsonProperty("cod_tipo_responsabile")
    private String codiceTipoResponsabile;

    @JsonProperty("des_tipo_responsabile")
    private String descrizioneTipoResponsabile;

    
    /**
     * Gets id tipo responsabile.
     *
     * @return the id tipo responsabile
     */
	public Long getIdTipoResponsabile() {
		return idTipoResponsabile;
	}

    /**
     * Sets id tipo responsabile.
     *
     * @param idTipoResponsabile idTipoResponsabile
     */
	public void setIdTipoResponsabile(Long idTipoResponsabile) {
		this.idTipoResponsabile = idTipoResponsabile;
	}

    /**
     * Gets codice tipo responsabile.
     *
     * @return the codice tipo responsabile
     */
	public String getCodiceTipoResponsabile() {
		return codiceTipoResponsabile;
	}

	/**
	 * Sets codice tipo responsabile.
	 * 
	 * @param codiceTipoResponsabile
	 */
	public void setCodiceTipoResponsabile(String codiceTipoResponsabile) {
		this.codiceTipoResponsabile = codiceTipoResponsabile;
	}

	/**
	 * Gets descrizione tipo responsabile
	 * 
	 * @return descrizione tipo responsabile
	 */
	public String getDescrizioneTipoResponsabile() {
		return descrizioneTipoResponsabile;
	}

	/**
	 * Sets descrizione tipo responsabile
	 * 
	 * @param descrizioneTipoResponsabile
	 */
	public void setDescrizioneTipoResponsabile(String descrizioneTipoResponsabile) {
		this.descrizioneTipoResponsabile = descrizioneTipoResponsabile;
	}

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codiceTipoResponsabile, descrizioneTipoResponsabile, idTipoResponsabile);
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
        TipoResponsabileDTO other = (TipoResponsabileDTO) obj;
        return Objects.equals(codiceTipoResponsabile, other.codiceTipoResponsabile)
                && Objects.equals(descrizioneTipoResponsabile, other.descrizioneTipoResponsabile)
                && Objects.equals(idTipoResponsabile, other.idTipoResponsabile);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoResponsabileDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idTipoResponsabile:").append(idTipoResponsabile);
        sb.append(",         codiceTipoResponsabile:'").append(codiceTipoResponsabile).append("'");
        sb.append(",         descrizioneTipoResponsabile:'").append(descrizioneTipoResponsabile).append("'");
        sb.append("}");
        return sb.toString();
    }
}
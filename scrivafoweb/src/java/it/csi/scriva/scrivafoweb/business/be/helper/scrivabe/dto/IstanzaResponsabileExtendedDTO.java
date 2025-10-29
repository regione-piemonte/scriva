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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Istanza Responsabile dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IstanzaResponsabileExtendedDTO extends IstanzaResponsabileDTO implements Serializable {

    @JsonProperty("tipo_responsabile")
    private TipoResponsabileDTO tipoResponsabile;

	/**
	 * Gets tipo responsabile
	 *
	 * @return tipoResponsabile tipo responsabile
	 */
	public TipoResponsabileDTO getTipoResponsabile() {
        return tipoResponsabile;
    }

	/**
	 * Sets tipo responsabile
	 *
	 * @param tipoResponsabile the tipo responsabile
	 */
	public void setTipoResponsabile(TipoResponsabileDTO tipoResponsabile) {
        this.tipoResponsabile = tipoResponsabile;
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
        IstanzaResponsabileExtendedDTO that = (IstanzaResponsabileExtendedDTO) o;
        return Objects.equals(tipoResponsabile, that.tipoResponsabile);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoResponsabile);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IstanzaResponsabileExtendedDTO {\n" +
                super.toString() +
                "         tipoResponsabile:" + tipoResponsabile +
                "}\n";
    }

}
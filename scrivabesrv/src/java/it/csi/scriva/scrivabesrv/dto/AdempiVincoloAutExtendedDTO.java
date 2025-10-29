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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Adempi vincolo aut dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempiVincoloAutExtendedDTO extends AdempiVincoloAutDTO implements Serializable {

    @JsonProperty("vincolo_autorizza")
    private VincoloAutorizzaExtendedDTO vincoloAutorizza;

    /**
     * Gets vincolo autorizza.
     *
     * @return the vincolo autorizza
     */
    public VincoloAutorizzaExtendedDTO getVincoloAutorizza() {
        return vincoloAutorizza;
    }

    /**
     * Sets vincolo autorizza.
     *
     * @param vincoloAutorizza the vincolo autorizza
     */
    public void setVincoloAutorizza(VincoloAutorizzaExtendedDTO vincoloAutorizza) {
        this.vincoloAutorizza = vincoloAutorizza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdempiVincoloAutExtendedDTO that = (AdempiVincoloAutExtendedDTO) o;
        return Objects.equals(vincoloAutorizza, that.vincoloAutorizza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vincoloAutorizza);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdempiVincoloAutExtendedDTO {");
        sb.append(super.toString());
        sb.append("         vincoloAutorizza:").append(vincoloAutorizza);
        sb.append("}");
        return sb.toString();
    }

}
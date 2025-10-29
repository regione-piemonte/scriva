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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Profilo app extended dto.
 *
 * @author CSI PIEMONTE
 */
public class ProfiloOggettoAppExtendedDTO extends ProfiloAppExtendedDTO implements Serializable {

    @JsonProperty("oggetti_app")
    private List<OggettoAppExtendedDTO> oggettoAppList;

    public List<OggettoAppExtendedDTO> getOggettoAppList() {
        return oggettoAppList;
    }

    public void setOggettoAppList(List<OggettoAppExtendedDTO> oggettoAppList) {
        this.oggettoAppList = oggettoAppList;
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
        ProfiloOggettoAppExtendedDTO that = (ProfiloOggettoAppExtendedDTO) o;
        return Objects.equals(oggettoAppList, that.oggettoAppList);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oggettoAppList);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "ProfiloOggettoAppExtendedDTO {\n" +
                "         oggettoAppList:" + oggettoAppList +
                "}\n";
    }
}
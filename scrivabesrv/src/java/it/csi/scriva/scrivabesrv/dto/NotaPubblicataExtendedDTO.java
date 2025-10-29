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
 * The type Nota pubblicata dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotaPubblicataExtendedDTO extends NotaPubblicataDTO implements Serializable {

    @JsonProperty("funzionario")
    private FunzionarioDTO funzionario;

    public FunzionarioDTO getFunzionario() {
        return funzionario;
    }

    public void setFunzionario(FunzionarioDTO funzionario) {
        this.funzionario = funzionario;
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
        NotaPubblicataExtendedDTO that = (NotaPubblicataExtendedDTO) o;
        return Objects.equals(funzionario, that.funzionario);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), funzionario);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "NotaPubblicataExtendedDTO {\n" +
                super.toString() +
                "         funzionario:" + funzionario +
                "}\n";
    }
}
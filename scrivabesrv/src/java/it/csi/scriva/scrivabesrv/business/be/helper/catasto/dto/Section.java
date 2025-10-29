/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Section
 */

@JsonInclude(Include.NON_NULL)
public class Section extends BaseCata {
    @JsonProperty("nome_sezione")
    private String nomeSezione = null;

    public Section nomeSezione(String nomeSezione) {
        this.nomeSezione = nomeSezione;
        return this;
    }

    /**
     * Get nomeSezione
     *
     * @return nomeSezione
     **/
    public String getNomeSezione() {
        return nomeSezione;
    }

    public void setNomeSezione(String nomeSezione) {
        this.nomeSezione = nomeSezione;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Section section = (Section) o;
        return Objects.equals(this.nomeSezione, section.nomeSezione) &&
                super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeSezione, super.hashCode());
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Section {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    nomeSezione: ").append(toIndentedString(nomeSezione)).append("\n");
        sb.append("}");
        return sb.toString();
    }

}
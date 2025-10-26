/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Pub doc istanza extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubDocIstanzaLinkDTO extends PubDocIstanzaExtendedDTO implements Serializable {

    @JsonProperty("link_documento")
    private String linkDocumento;

    /**
     * Gets link documento.
     *
     * @return the link documento
     */
    public String getLinkDocumento() {
        return linkDocumento;
    }

    /**
     * Sets link documento.
     *
     * @param linkDocumento the link documento
     */
    public void setLinkDocumento(String linkDocumento) {
        this.linkDocumento = linkDocumento;
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
        PubDocIstanzaLinkDTO that = (PubDocIstanzaLinkDTO) o;
        return Objects.equals(linkDocumento, that.linkDocumento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), linkDocumento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "PubDocIstanzaLinkDTO {\n" +
                super.toString() +
                "         linkDocumento:'" + linkDocumento + "'" +
                "}\n";
    }
}
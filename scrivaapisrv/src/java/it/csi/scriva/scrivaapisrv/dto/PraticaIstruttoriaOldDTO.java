/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Pratica istruttoria dto.
 *
 * @author CSI PIEMONTE
 */
public class PraticaIstruttoriaOldDTO implements Serializable {

    @JsonProperty("dati_istanza")
    private PubIstanzaDTO datiIstanza;

    @JsonProperty("dati_documento")
    private PubDocIstanzaExtendedDTO datiDocumento;

    @JsonProperty("link_documento")
    private String linkDocumento;

    /**
     * Gets dati istanza.
     *
     * @return the dati istanza
     */
    public PubIstanzaDTO getDatiIstanza() {
        return datiIstanza;
    }

    /**
     * Sets dati istanza.
     *
     * @param datiIstanza the dati istanza
     */
    public void setDatiIstanza(PubIstanzaDTO datiIstanza) {
        this.datiIstanza = datiIstanza;
    }

    /**
     * Gets dati documento.
     *
     * @return the dati documento
     */
    public PubDocIstanzaExtendedDTO getDatiDocumento() {
        return datiDocumento;
    }

    /**
     * Sets dati documento.
     *
     * @param datiDocumento the dati documento
     */
    public void setDatiDocumento(PubDocIstanzaExtendedDTO datiDocumento) {
        this.datiDocumento = datiDocumento;
    }

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
        PraticaIstruttoriaOldDTO that = (PraticaIstruttoriaOldDTO) o;
        return Objects.equals(datiIstanza, that.datiIstanza) && Objects.equals(datiDocumento, that.datiDocumento) && Objects.equals(linkDocumento, that.linkDocumento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(datiIstanza, datiDocumento, linkDocumento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "PraticaIstruttoriaOldDTO {\n" +
                super.toString() +
                "         datiIstanza:" + datiIstanza +
                ",\n         datiDocumento:" + datiDocumento +
                ",\n         linkDocumento:'" + linkDocumento + "'" +
                "}\n";
    }
}
/*

========================LICENSE_START=================================
Copyright (C) 2025 Regione Piemonte
SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
SPDX-License-Identifier: EUPL-1.2
=========================LICENSE_END==================================
*/
package it.csi.scriva.scrivaapisrv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Pratica istruttoria dto.
 *
 * @author CSI PIEMONTE
 */
public class PraticaIstruttoriaDTO implements Serializable {

    @JsonProperty("dati_istanza")
    private PubIstanzaDTO datiIstanza;

    @JsonProperty("dati_documento")
    private List<PubDocIstanzaLinkDTO> datiDocumento;

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
    public List<PubDocIstanzaLinkDTO> getDatiDocumento() {
        return datiDocumento;
    }

    /**
     * Sets dati documento.
     *
     * @param datiDocumento the dati documento
     */
    public void setDatiDocumento(List<PubDocIstanzaLinkDTO> datiDocumento) {
        this.datiDocumento = datiDocumento;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PraticaIstruttoriaDTO that = (PraticaIstruttoriaDTO) o;
        return Objects.equals(datiIstanza, that.datiIstanza) && Objects.equals(datiDocumento, that.datiDocumento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(datiIstanza, datiDocumento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "PraticaIstruttoriaDTO {\n" +
                "         datiIstanza:" + datiIstanza +
                ",\n         datiDocumento:" + datiDocumento +
                "}\n";
    }
}
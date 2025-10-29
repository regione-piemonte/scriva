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
 * The type Adempimento estensione allegato dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempimentoEstensioneAllegatoDTO implements Serializable {

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("estensione_allegato")
    private String estensioneAllegato;

    @JsonProperty("des_estensione_allegato")
    private String desEstensioneAllegato;

    /**
     * Gets id adempimento.
     *
     * @return idAdempimento id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento idAdempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * Gets estensione allegato.
     *
     * @return estensioneAllegato estensione allegato
     */
    public String getEstensioneAllegato() {
        return estensioneAllegato;
    }

    /**
     * Sets estensione allegato.
     *
     * @param estensioneAllegato estensioneAllegato
     */
    public void setEstensioneAllegato(String estensioneAllegato) {
        this.estensioneAllegato = estensioneAllegato;
    }

    /**
     * Gets des estensione allegato.
     *
     * @return desEstensioneAllegato des estensione allegato
     */
    public String getDesEstensioneAllegato() {
        return desEstensioneAllegato;
    }

    /**
     * Sets des estensione allegato.
     *
     * @param desEstensioneAllegato desEstensioneAllegato
     */
    public void setDesEstensioneAllegato(String desEstensioneAllegato) {
        this.desEstensioneAllegato = desEstensioneAllegato;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdempimentoEstensioneAllegatoDTO that = (AdempimentoEstensioneAllegatoDTO) o;
        return Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(estensioneAllegato, that.estensioneAllegato) && Objects.equals(desEstensioneAllegato, that.desEstensioneAllegato);
    }

    /**
     * @return  int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idAdempimento, estensioneAllegato, desEstensioneAllegato);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdempimentoEstensioneAllegatoDTO {");
        sb.append("         idAdempimento:").append(idAdempimento);
        sb.append(",         estensioneAllegato:'").append(estensioneAllegato).append("'");
        sb.append(",         desEstensioneAllegato:'").append(desEstensioneAllegato).append("'");
        sb.append("}");
        return sb.toString();
    }
}
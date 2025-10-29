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
 * The type Oggetto istanza figlio dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoIstanzaFiglioDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_oggetto_istanza_padre")
    private Long idOggettoIstanzaPadre;

    @JsonProperty("id_oggetto_istanza_figlio")
    private Long idOggettoIstanzaFiglio;

    /**
     * Gets id oggetto istanza padre.
     *
     * @return the id oggetto istanza padre
     */
    public Long getIdOggettoIstanzaPadre() {
        return idOggettoIstanzaPadre;
    }

    /**
     * Sets id oggetto istanza padre.
     *
     * @param idOggettoIstanzaPadre the id oggetto istanza padre
     */
    public void setIdOggettoIstanzaPadre(Long idOggettoIstanzaPadre) {
        this.idOggettoIstanzaPadre = idOggettoIstanzaPadre;
    }

    /**
     * Gets id oggetto istanza figlio.
     *
     * @return the id oggetto istanza figlio
     */
    public Long getIdOggettoIstanzaFiglio() {
        return idOggettoIstanzaFiglio;
    }

    /**
     * Sets id oggetto istanza figlio.
     *
     * @param idOggettoIstanzaFiglio the id oggetto istanza figlio
     */
    public void setIdOggettoIstanzaFiglio(Long idOggettoIstanzaFiglio) {
        this.idOggettoIstanzaFiglio = idOggettoIstanzaFiglio;
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
        OggettoIstanzaFiglioDTO that = (OggettoIstanzaFiglioDTO) o;
        return Objects.equals(idOggettoIstanzaPadre, that.idOggettoIstanzaPadre) && Objects.equals(idOggettoIstanzaFiglio, that.idOggettoIstanzaFiglio);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOggettoIstanzaPadre, idOggettoIstanzaFiglio);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "OggettoIstanzaFiglioDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idOggettoIstanzaPadre:" + idOggettoIstanzaPadre +
                ",\n         idOggettoIstanzaFiglio:" + idOggettoIstanzaFiglio +
                "}\n";
    }
}
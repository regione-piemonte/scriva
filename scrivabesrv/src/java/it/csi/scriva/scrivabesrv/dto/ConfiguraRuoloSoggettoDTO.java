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
 * The type Configura ruolo soggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfiguraRuoloSoggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_ruolo_soggetto")
    private Long idRuoloSoggetto;

    @JsonProperty("id_ruolo_compilante")
    private Long idRuoloCompilante;

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    /**
     * Gets id ruolo soggetto.
     *
     * @return idRuoloSoggetto id ruolo soggetto
     */
    public Long getIdRuoloSoggetto() {
        return idRuoloSoggetto;
    }

    /**
     * Sets id ruolo soggetto.
     *
     * @param idRuoloSoggetto idRuoloSoggetto
     */
    public void setIdRuoloSoggetto(Long idRuoloSoggetto) {
        this.idRuoloSoggetto = idRuoloSoggetto;
    }

    /**
     * Gets id ruolo compilante.
     *
     * @return idRuoloCompilante id ruolo compilante
     */
    public Long getIdRuoloCompilante() {
        return idRuoloCompilante;
    }

    /**
     * Sets id ruolo compilante.
     *
     * @param idRuoloCompilante idRuoloCompilante
     */
    public void setIdRuoloCompilante(Long idRuoloCompilante) {
        this.idRuoloCompilante = idRuoloCompilante;
    }

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
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idRuoloCompilante, idRuoloSoggetto, idAdempimento);
    }

    /**
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConfiguraRuoloSoggettoDTO other = (ConfiguraRuoloSoggettoDTO) obj;
        return Objects.equals(idRuoloCompilante, other.idRuoloCompilante)
                && Objects.equals(idRuoloSoggetto, other.idRuoloSoggetto)
                && Objects.equals(idAdempimento, other.idAdempimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ConfiguraRuoloSoggettoDTO [idRuoloSoggetto=").append(idRuoloSoggetto)
                .append("\n  idRuoloCompilante=").append(idRuoloCompilante).append("\n  idAdempimento=")
                .append(idAdempimento).append("]");
        return builder.toString();
    }


}
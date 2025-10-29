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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Configura ruolo soggetto extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfiguraRuoloSoggettoExtendedDTO extends ConfiguraRuoloSoggettoDTO implements Serializable {

    @JsonProperty("ruolo_soggetto")
    private RuoloSoggettoDTO ruoloSoggetto;

    @JsonProperty("adempimento")
    private AdempimentoExtendedDTO adempimento;

    @JsonProperty("ruolo_compilante")
    private RuoloCompilanteDTO ruoloCompilante;

    /**
     * Gets ruolo soggetto.
     *
     * @return ruoloSoggetto ruolo soggetto
     */
    public RuoloSoggettoDTO getRuoloSoggetto() {
        return ruoloSoggetto;
    }

    /**
     * Sets ruolo soggetto.
     *
     * @param ruoloSoggetto ruoloSoggetto
     */
    public void setRuoloSoggetto(RuoloSoggettoDTO ruoloSoggetto) {
        this.ruoloSoggetto = ruoloSoggetto;
    }

    /**
     * Gets adempimento.
     *
     * @return adempimento adempimento
     */
    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    /**
     * Sets adempimento.
     *
     * @param adempimento adempimento
     */
    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
    }

    /**
     * Gets ruolo compilante.
     *
     * @return ruoloCompilante ruolo compilante
     */
    public RuoloCompilanteDTO getRuoloCompilante() {
        return ruoloCompilante;
    }

    /**
     * Sets ruolo compilante.
     *
     * @param ruoloCompilante ruoloCompilante
     */
    public void setRuoloCompilante(RuoloCompilanteDTO ruoloCompilante) {
        this.ruoloCompilante = ruoloCompilante;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(ruoloCompilante, ruoloSoggetto, adempimento);
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
        ConfiguraRuoloSoggettoExtendedDTO other = (ConfiguraRuoloSoggettoExtendedDTO) obj;
        return Objects.equals(ruoloCompilante, other.ruoloCompilante) && Objects.equals(ruoloSoggetto, other.ruoloSoggetto) && Objects.equals(adempimento, other.adempimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "ConfiguraRuoloSoggettoExtendedDTO {\n" +
                "         ruoloSoggetto:" + ruoloSoggetto +
                ",\n         adempimento:" + adempimento +
                ",\n         ruoloCompilante:" + ruoloCompilante +
                "}\n";
    }

    /**
     * Gets dto.
     *
     * @return ConfiguraRuoloSoggettoDTO dto
     */
    @JsonIgnore
    public ConfiguraRuoloSoggettoDTO getDTO() {
        ConfiguraRuoloSoggettoDTO dto = new ConfiguraRuoloSoggettoDTO();
        if (null != this.getRuoloCompilante()) {
            dto.setIdRuoloCompilante(this.getRuoloCompilante().getIdRuoloCompilante());
        }
        if (null != this.getAdempimento()) {
            dto.setIdRuoloSoggetto(this.getRuoloSoggetto().getIdRuoloSoggetto());
        }
        if (null != this.getAdempimento()) {
            dto.setIdAdempimento(this.getAdempimento().getIdTipoAdempimento());
        }
        return dto;
    }

}
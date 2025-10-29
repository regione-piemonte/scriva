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
import java.util.Objects;

public class ConfiguraRuoloSoggettoExtendedDTO implements Serializable {

    @JsonProperty("ruolo_soggetto")
    private RuoloSoggettoDTO ruoloSoggetto;

    @JsonProperty("adempimento")
    private AdempimentoExtendedDTO adempimento;

    @JsonProperty("ruolo_compilante")
    private RuoloCompilanteDTO ruoloCompilante;

    public RuoloSoggettoDTO getRuoloSoggetto() {
        return ruoloSoggetto;
    }

    public void setRuoloSoggetto(RuoloSoggettoDTO ruoloSoggetto) {
        this.ruoloSoggetto = ruoloSoggetto;
    }

    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
    }

    public RuoloCompilanteDTO getRuoloCompilante() {
        return ruoloCompilante;
    }

    public void setRuoloCompilante(RuoloCompilanteDTO ruoloCompilante) {
        this.ruoloCompilante = ruoloCompilante;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruoloCompilante, ruoloSoggetto, adempimento);
    }

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ConfiguraRuoloSoggettoExtendedDTO [ruoloSoggetto=").append(ruoloSoggetto).append("\n  adempimento=").append(adempimento).append("\n  ruoloCompilante=").append(ruoloCompilante).append("]");
        return builder.toString();
    }


}
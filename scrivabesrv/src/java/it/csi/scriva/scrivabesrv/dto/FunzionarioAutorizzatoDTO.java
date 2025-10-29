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
import java.util.List;
import java.util.Objects;

/**
 * The type Funzionario autorizzato dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunzionarioAutorizzatoDTO implements Serializable {

    /**
     * The Funzionario.
     */
    @JsonProperty("funzionario")
    FunzionarioDTO funzionario;

    /**
     * The Funzionario competenza.
     */
    @JsonProperty("funzionario_competenza")
    List<FunzionarioCompetenzaExtendedDTO> funzionarioCompetenza;

    /**
     * The Funzionario profilo.
     */
    @JsonProperty("funzionario_profilo")
    List<FunzionarioProfiloExtendedDTO> funzionarioProfilo;

    /**
     * Gets funzionario.
     *
     * @return the funzionario
     */
    public FunzionarioDTO getFunzionario() {
        return funzionario;
    }

    /**
     * Sets funzionario.
     *
     * @param funzionario the funzionario
     */
    public void setFunzionario(FunzionarioDTO funzionario) {
        this.funzionario = funzionario;
    }

    /**
     * Gets funzionario competenza.
     *
     * @return the funzionario competenza
     */
    public List<FunzionarioCompetenzaExtendedDTO> getFunzionarioCompetenza() {
        return funzionarioCompetenza;
    }

    /**
     * Sets funzionario competenza.
     *
     * @param funzionarioCompetenza the funzionario competenza
     */
    public void setFunzionarioCompetenza(List<FunzionarioCompetenzaExtendedDTO> funzionarioCompetenza) {
        this.funzionarioCompetenza = funzionarioCompetenza;
    }

    /**
     * Gets funzionario profilo.
     *
     * @return the funzionario profilo
     */
    public List<FunzionarioProfiloExtendedDTO> getFunzionarioProfilo() {
        return funzionarioProfilo;
    }

    /**
     * Sets funzionario profilo.
     *
     * @param funzionarioProfilo the funzionario profilo
     */
    public void setFunzionarioProfilo(List<FunzionarioProfiloExtendedDTO> funzionarioProfilo) {
        this.funzionarioProfilo = funzionarioProfilo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunzionarioAutorizzatoDTO that = (FunzionarioAutorizzatoDTO) o;
        return Objects.equals(funzionario, that.funzionario) && Objects.equals(funzionarioCompetenza, that.funzionarioCompetenza) && Objects.equals(funzionarioProfilo, that.funzionarioProfilo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(funzionario, funzionarioCompetenza, funzionarioProfilo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FunzionarioAutorizzatoDTO {");
        sb.append("         funzionario:").append(funzionario);
        sb.append(",         funzionarioCompetenza:").append(funzionarioCompetenza);
        sb.append(",         funzionarioProfilo:").append(funzionarioProfilo);
        sb.append("}");
        return sb.toString();
    }
}
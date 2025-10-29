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
 * The type Adempi vincolo aut dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempiVincoloAutDTO implements Serializable {

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("id_vincolo_autorizza")
    private Long idVincoloAutorizza;

    @JsonProperty("ordinamento_adempi_vincolo")
    private Long ordinamentoAdempiVincolo;

    /**
     * Gets id adempimento.
     *
     * @return the id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento the id adempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * Gets id vincolo autorizza.
     *
     * @return the id vincolo autorizza
     */
    public Long getIdVincoloAutorizza() {
        return idVincoloAutorizza;
    }

    /**
     * Sets id vincolo autorizza.
     *
     * @param idVincoloAutorizza the id vincolo autorizza
     */
    public void setIdVincoloAutorizza(Long idVincoloAutorizza) {
        this.idVincoloAutorizza = idVincoloAutorizza;
    }

    /**
     * Gets ordinamento adempi vincolo.
     *
     * @return the ordinamento adempi vincolo
     */
    public Long getOrdinamentoAdempiVincolo() {
        return ordinamentoAdempiVincolo;
    }

    /**
     * Sets ordinamento adempi vincolo.
     *
     * @param ordinamentoAdempiVincolo the ordinamento adempi vincolo
     */
    public void setOrdinamentoAdempiVincolo(Long ordinamentoAdempiVincolo) {
        this.ordinamentoAdempiVincolo = ordinamentoAdempiVincolo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdempiVincoloAutDTO that = (AdempiVincoloAutDTO) o;
        return Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(idVincoloAutorizza, that.idVincoloAutorizza) && Objects.equals(ordinamentoAdempiVincolo, that.ordinamentoAdempiVincolo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdempimento, idVincoloAutorizza, ordinamentoAdempiVincolo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdempiVincoloAutDTO {");
        sb.append("         idAdempimento:").append(idAdempimento);
        sb.append(",         idVincoloAutorizza:").append(idVincoloAutorizza);
        sb.append(",         ordinamentoAdempiVincolo:").append(ordinamentoAdempiVincolo);
        sb.append("}");
        return sb.toString();
    }
}
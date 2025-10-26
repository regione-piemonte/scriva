/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Vincolo tipo allegato dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VincoloTipoAllegatoExtendedDTO extends VincoloTipoAllegatoDTO implements Serializable {

    @JsonProperty("vincolo_autorizza")
    private VincoloAutorizzaExtendedDTO vincoloAutorizza;

    @JsonProperty("tipologia_allegato")
    private TipologiaAllegatoExtendedDTO tipologiaAllegato;

    /**
     * Gets vincolo autorizza.
     *
     * @return the vincolo autorizza
     */
    public VincoloAutorizzaExtendedDTO getVincoloAutorizza() {
        return vincoloAutorizza;
    }

    /**
     * Sets vincolo autorizza.
     *
     * @param vincoloAutorizza the vincolo autorizza
     */
    public void setVincoloAutorizza(VincoloAutorizzaExtendedDTO vincoloAutorizza) {
        this.vincoloAutorizza = vincoloAutorizza;
    }

    /**
     * Gets tipologia allegato.
     *
     * @return the tipologia allegato
     */
    public TipologiaAllegatoExtendedDTO getTipologiaAllegato() {
        return tipologiaAllegato;
    }

    /**
     * Sets tipologia allegato.
     *
     * @param tipologiaAllegato the tipologia allegato
     */
    public void setTipologiaAllegato(TipologiaAllegatoExtendedDTO tipologiaAllegato) {
        this.tipologiaAllegato = tipologiaAllegato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VincoloTipoAllegatoExtendedDTO that = (VincoloTipoAllegatoExtendedDTO) o;
        return Objects.equals(vincoloAutorizza, that.vincoloAutorizza) && Objects.equals(tipologiaAllegato, that.tipologiaAllegato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vincoloAutorizza, tipologiaAllegato);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VincoloTipoAllegatoExtendedDTO {");
        sb.append(super.toString());
        sb.append("         vincoloAutorizza:").append(vincoloAutorizza);
        sb.append(",         tipologiaAllegato:").append(tipologiaAllegato);
        sb.append("}");
        return sb.toString();
    }
}
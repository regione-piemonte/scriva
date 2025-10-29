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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipo vincolo aut dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VincoloAutorizzaExtendedDTO extends VincoloAutorizzaDTO implements Serializable {

    @JsonProperty("tipo_vincolo_aut")
    private TipoVincoloAutDTO tipoVincoloAut;

    /**
     * Gets tipo vincolo aut.
     *
     * @return the tipo vincolo aut
     */
    public TipoVincoloAutDTO getTipoVincoloAut() {
        return tipoVincoloAut;
    }

    /**
     * Sets tipo vincolo aut.
     *
     * @param tipoVincoloAut the tipo vincolo aut
     */
    public void setTipoVincoloAut(TipoVincoloAutDTO tipoVincoloAut) {
        this.tipoVincoloAut = tipoVincoloAut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VincoloAutorizzaExtendedDTO that = (VincoloAutorizzaExtendedDTO) o;
        return Objects.equals(tipoVincoloAut, that.tipoVincoloAut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoVincoloAut);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VincoloAutorizzaExtendedDTO {");
        sb.append(super.toString());
        sb.append("         tipoVincoloAut:").append(tipoVincoloAut);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public VincoloAutorizzaDTO getDTO() {
        VincoloAutorizzaDTO dto = new VincoloAutorizzaDTO();
        dto.setIdVincoloAutorizza(this.getIdVincoloAutorizza());

        if (this.getTipoVincoloAut() != null) {
            dto.setIdTipoVincoloAut(this.getTipoVincoloAut().getIdTipoVincoloAut());
        }

        dto.setCodVincoloAutorizza(this.getCodVincoloAutorizza());
        dto.setDesVincoloAutorizza(this.getDesVincoloAutorizza());
        dto.setDesRifNormativo(this.getDesRifNormativo());
        dto.setDataInizioValidita(this.getDataInizioValidita());
        dto.setDataFineValidita(this.getDataFineValidita());
        dto.setFlgModifica(this.getFlgModifica());
        dto.setIndVisibile(this.getIndVisibile());
        dto.setOrdinamentoVincoloAut(this.getOrdinamentoVincoloAut());
        return dto;
    }
}
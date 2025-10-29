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
 * The type Istanza vincolo aut dto.
 */
/*
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IstanzaVincoloAutExtendedDTO extends IstanzaVincoloAutDTO implements Serializable {

    @JsonProperty("vincolo_autorizza")
    private VincoloAutorizzaExtendedDTO vincoloAutorizza;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IstanzaVincoloAutExtendedDTO that = (IstanzaVincoloAutExtendedDTO) o;
        return Objects.equals(vincoloAutorizza, that.vincoloAutorizza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vincoloAutorizza);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaVincoloAutExtendedDTO {");
        sb.append(super.toString());
        sb.append("         vincoloAutorizza:").append(vincoloAutorizza);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public IstanzaVincoloAutDTO getDTO() {
        IstanzaVincoloAutDTO dto = new IstanzaVincoloAutDTO();
        dto.setIdIstanzaVincoloAut(this.getIdIstanzaVincoloAut());
        dto.setIdIstanza(this.getIdIstanza());

        if (this.getVincoloAutorizza() != null) {
            dto.setIdVincoloAutorizza(this.getVincoloAutorizza().getIdVincoloAutorizza());
        }

        dto.setDesVincoloCalcolato(this.getDesVincoloCalcolato());
        dto.setDesEnteUtente(this.getDesEnteUtente());
        dto.setDesEmailPecEnteUtente(this.getDesEmailPecEnteUtente());
        dto.setFlgRichiestaPost(this.getFlgRichiestaPost());
        dto.setDesMotivoRichiestaPort(this.getDesMotivoRichiestaPort());
        //dto.setIdIstanzaAttore(this.getIdIstanzaAttore());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataUpd(this.getGestDataUpd());
        return dto;
    }
}
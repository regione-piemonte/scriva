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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Oggetto istanza vincolo autorizza extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OggettoIstanzaVincoloAutorizzaExtendedDTO extends OggettoIstanzaVincoloAutorizzaDTO implements Serializable {

    @JsonProperty("oggetto_istanza")
    private OggettoIstanzaDTO oggettoIstanza;

    @JsonProperty("vincolo_autorizza")
    private VincoloAutorizzaExtendedDTO vincoloAutorizza;

    /**
     * Gets oggetto istanza.
     *
     * @return the oggetto istanza
     */
    public OggettoIstanzaDTO getOggettoIstanza() {
        return oggettoIstanza;
    }

    /**
     * Sets oggetto istanza.
     *
     * @param oggettoIstanza the oggetto istanza
     */
    public void setOggettoIstanza(OggettoIstanzaDTO oggettoIstanza) {
        this.oggettoIstanza = oggettoIstanza;
    }

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
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OggettoIstanzaVincoloAutorizzaExtendedDTO that = (OggettoIstanzaVincoloAutorizzaExtendedDTO) o;
        return Objects.equals(oggettoIstanza, that.oggettoIstanza) && Objects.equals(vincoloAutorizza, that.vincoloAutorizza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oggettoIstanza, vincoloAutorizza);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoIstanzaVincoloAutorizzaExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         oggettoIstanza:").append(oggettoIstanza);
        sb.append(",\n         vincoloAutorizza:").append(vincoloAutorizza);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public OggettoIstanzaVincoloAutorizzaDTO getDTO() {
        OggettoIstanzaVincoloAutorizzaDTO dto = new OggettoIstanzaVincoloAutorizzaDTO();
        dto.setIdOggettoVincoloAut(this.getIdOggettoVincoloAut());

        if (this.oggettoIstanza != null) {
            dto.setIdOggettoIstanza(this.oggettoIstanza.getIdOggettoIstanza());
        }
        if (this.vincoloAutorizza != null) {
            dto.setIdVincoloAutorizza(this.vincoloAutorizza.getIdVincoloAutorizza());
        }
        dto.setDesVincoloCalcolato(this.getDesVincoloCalcolato());
        dto.setDesEnteUtente(this.getDesEnteUtente());
        dto.setDesEmailPecEnteUtente(this.getDesEmailPecEnteUtente());
        dto.setFlgRichiestaPost(this.getFlgRichiestaPost());
        dto.setDesMotivoRichiestaPost(this.getDesMotivoRichiestaPost());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }
}
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
 * The type Oggetto extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoExtendedDTO extends OggettoDTO implements Serializable {

    @JsonProperty("tipologia_oggetto")
    private TipologiaOggettoExtendedDTO tipologiaOggetto;

    @JsonProperty("stato_oggetto")
    private StatoOggettoDTO statoOggetto;

    /**
     * Gets tipologia oggetto.
     *
     * @return tipologiaOggetto tipologia oggetto
     */
    public TipologiaOggettoExtendedDTO getTipologiaOggetto() {
        return tipologiaOggetto;
    }

    /**
     * Sets tipologia oggetto.
     *
     * @param tipologiaOggetto tipologiaOggetto
     */
    public void setTipologiaOggetto(TipologiaOggettoExtendedDTO tipologiaOggetto) {
        this.tipologiaOggetto = tipologiaOggetto;
    }

    /**
     * Gets stato oggetto.
     *
     * @return statoOggetto stato oggetto
     */
    public StatoOggettoDTO getStatoOggetto() {
        return statoOggetto;
    }

    /**
     * Sets stato oggetto.
     *
     * @param statoOggetto statoOggetto
     */
    public void setStatoOggetto(StatoOggettoDTO statoOggetto) {
        this.statoOggetto = statoOggetto;
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
        OggettoExtendedDTO that = (OggettoExtendedDTO) o;
        return Objects.equals(tipologiaOggetto, that.tipologiaOggetto) && Objects.equals(statoOggetto, that.statoOggetto);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipologiaOggetto, statoOggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         tipologiaOggetto:").append(tipologiaOggetto);
        sb.append(",\n         statoOggetto:").append(statoOggetto);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * @return OggettoDTO
     */
    @JsonIgnore
    public OggettoDTO getDTO() {
        OggettoDTO dto = new OggettoDTO();
        dto.setIdOggetto(this.getIdOggetto());
        if (null != this.getStatoOggetto()) {
            dto.setIdStatoOggetto(this.getStatoOggetto().getIdStatoOggetto());
        }
        if (null != this.getTipologiaOggetto()) {
            dto.setIdTipologiaOggetto(this.getTipologiaOggetto().getIdTipologiaOggetto());
        }
        dto.setIdMasterdata(this.getIdMasterdata());
        dto.setIdMasterdataOrigine(this.getIdMasterdataOrigine());
        dto.setCodScriva(this.getCodScriva());
        dto.setCodOggettoFonte(this.getCodOggettoFonte());
        dto.setCoordinataX(this.getCoordinataX());
        dto.setCoordinataY(this.getCoordinataY());
        dto.setDenOggetto(this.getDenOggetto());
        dto.setDesOggetto(this.getDesOggetto());
        dto.setIdIstanzaAggiornamento(this.getIdIstanzaAggiornamento());
        dto.setIdFunzionario(this.getIdFunzionario());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }

}
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
 * The type Istanza osservazione extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoOsservazioneExtendedDTO extends OggettoOsservazioneDTO implements Serializable {

    @JsonProperty("istanza")
    private PubIstanzaDTO istanza;

    @JsonProperty("stato_osservazione")
    private StatoOsservazioneDTO statoOsservazione;

    public PubIstanzaDTO getIstanza() {
        return istanza;
    }

    public void setIstanza(PubIstanzaDTO istanza) {
        this.istanza = istanza;
    }

    public StatoOsservazioneDTO getStatoOsservazione() {
        return statoOsservazione;
    }

    public void setStatoOsservazione(StatoOsservazioneDTO statoOsservazione) {
        this.statoOsservazione = statoOsservazione;
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
        OggettoOsservazioneExtendedDTO that = (OggettoOsservazioneExtendedDTO) o;
        return Objects.equals(istanza, that.istanza) && Objects.equals(statoOsservazione, that.statoOsservazione);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), istanza, statoOsservazione);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "OggettoOsservazioneExtendedDTO {\n" +
                super.toString() +
                "         istanza:" + istanza +
                ",\n         statoOsservazione:" + statoOsservazione +
                "}\n";
    }

    @JsonIgnore
    public OggettoOsservazioneDTO getDTO() {
        OggettoOsservazioneDTO dto = new OggettoOsservazioneDTO();

        if (null != this.getIstanza()) {
            dto.setIdIstanza(this.getIstanza().getIdIstanza());
        }

        if (null != this.getStatoOsservazione()) {
            dto.setIdStatoOsservazione(this.getStatoOsservazione().getIdStatoOsservazione());
        }

        dto.setCfOsservazioneIns(this.getCfOsservazioneIns());
        dto.setDataOsservazione(this.getDataOsservazione());
        dto.setDataProtocolloOsservazione(this.getDataProtocolloOsservazione());
        dto.setDataPubblicazione(this.getDataPubblicazione());
        dto.setIdFunzionario(this.getIdFunzionario());
        dto.setIdIstanzaOsservazione(this.getIdIstanzaOsservazione());
        dto.setNumProtocolloOsservazione(this.getNumProtocolloOsservazione());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }


}
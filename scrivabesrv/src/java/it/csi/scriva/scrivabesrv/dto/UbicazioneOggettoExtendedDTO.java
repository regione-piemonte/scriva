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
 * The type Ubicazione oggetto extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UbicazioneOggettoExtendedDTO extends UbicazioneOggettoDTO implements Serializable {

    @JsonProperty("comune")
    private ComuneExtendedDTO comune;

    public ComuneExtendedDTO getComune() {
        return comune;
    }

    public void setComune(ComuneExtendedDTO comune) {
        this.comune = comune;
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
        UbicazioneOggettoExtendedDTO that = (UbicazioneOggettoExtendedDTO) o;
        return Objects.equals(comune, that.comune);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), comune);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "UbicazioneOggettoExtendedDTO {\n" +
                super.toString() +
                "         comune:" + comune +
                "}\n";
    }

    /**
     * @return UbicazioneOggettoDTO
     */
    @JsonIgnore
    public UbicazioneOggettoDTO getDTO() {
        UbicazioneOggettoDTO dto = new UbicazioneOggettoDTO();
        dto.setIdUbicazioneOggetto(this.getIdUbicazioneOggetto());
        dto.setIdOggetto(this.getIdOggetto());
        if (null != this.getComune()) {
            dto.setIdComune(this.getComune().getIdComune());
        }
        dto.setDenIndirizzo(this.getDenIndirizzo());
        dto.setNumCivico(this.getNumCivico());
        dto.setDesLocalita(this.getDesLocalita());
        dto.setIndGeoProvenienza(this.getIndGeoProvenienza());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }

}
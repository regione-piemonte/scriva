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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Oggetto ubicazione extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoUbicazioneExtendedDTO extends OggettoExtendedDTO implements Serializable {

    @JsonProperty("ubicazione_oggetto")
    private List<UbicazioneOggettoExtendedDTO> ubicazioneOggetto;


    /**
     * Instantiates a new Oggetto ubicazione extended dto.
     */
    public OggettoUbicazioneExtendedDTO() {
        super();
    }

    /**
     * Instantiates a new Oggetto ubicazione extended dto.
     *
     * @param another the another
     */
    public OggettoUbicazioneExtendedDTO(OggettoUbicazioneExtendedDTO another) {
        this.setIdOggetto(another.getIdOggetto());
        this.setTipologiaOggetto(another.getTipologiaOggetto());
        this.setStatoOggetto(another.getStatoOggetto());
        this.setCodScriva(another.getCodScriva());
        this.setDenOggetto(another.getDenOggetto());
        this.setDesOggetto(another.getDesOggetto());
        this.setCoordinataX(another.getCoordinataX());
        this.setCoordinataY(another.getCoordinataY());
        this.setIdMasterdataOrigine(another.getIdMasterdataOrigine());
        this.setIdMasterdata(another.getIdMasterdata());
        this.setCodOggettoFonte(another.getCodOggettoFonte());
        this.setGestAttoreIns(another.getGestAttoreIns());
        this.setGestAttoreUpd(another.getGestAttoreUpd());
        this.setGestDataIns(another.getGestDataIns());
        this.setGestDataUpd(another.getGestDataUpd());
        this.setGestUID(another.getGestUID());
        this.setIdIstanzaAggiornamento(another.getIdIstanzaAggiornamento());
        this.setUbicazioneOggetto(another.getUbicazioneOggetto());
    }

    /**
     * Gets ubicazione oggetto.
     *
     * @return ubicazioneOggetto ubicazione oggetto
     */
    public List<UbicazioneOggettoExtendedDTO> getUbicazioneOggetto() {
        return ubicazioneOggetto;
    }

    /**
     * Sets ubicazione oggetto.
     *
     * @param ubicazioneOggetto ubicazioneOggetto
     */
    public void setUbicazioneOggetto(List<UbicazioneOggettoExtendedDTO> ubicazioneOggetto) {
        this.ubicazioneOggetto = ubicazioneOggetto;
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
        OggettoUbicazioneExtendedDTO that = (OggettoUbicazioneExtendedDTO) o;
        return Objects.equals(ubicazioneOggetto, that.ubicazioneOggetto);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ubicazioneOggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoUbicazioneExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         ubicazioneOggetto:").append(ubicazioneOggetto);
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
        	if(this.getStatoOggetto().getIdStatoOggetto() != null && this.getStatoOggetto().getIdStatoOggetto() != 0)
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
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        dto.setIdIstanzaAggiornamento(this.getIdIstanzaAggiornamento());
        return dto;
    }

    /**
     * Gets ubicazione oggetto dto.
     *
     * @return List<UbicazioneOggettoDTO>  ubicazione oggetto dto
     */
    @JsonIgnore
    public List<UbicazioneOggettoDTO> getUbicazioneOggettoDTO() {
        List<UbicazioneOggettoDTO> ubicazioneOggettoDTOList = new ArrayList<>();
        List<UbicazioneOggettoExtendedDTO> ubicazioneOggettoExtendedDTOList = this.getUbicazioneOggetto();
        if (!ubicazioneOggettoExtendedDTOList.isEmpty()) {
            for (UbicazioneOggettoExtendedDTO ubicazioneOggettoExtendedDTO : ubicazioneOggettoExtendedDTOList) {
                UbicazioneOggettoDTO ubicazioneOggettoDTO = new UbicazioneOggettoDTO();
                ubicazioneOggettoDTO.setIdUbicazioneOggetto(ubicazioneOggettoExtendedDTO.getIdUbicazioneOggetto());
                ubicazioneOggettoDTO.setIdOggetto(ubicazioneOggettoExtendedDTO.getIdOggetto());
                if (null != ubicazioneOggettoExtendedDTO.getComune()) {
                    ubicazioneOggettoDTO.setIdComune(ubicazioneOggettoExtendedDTO.getComune().getIdComune());
                }
                ubicazioneOggettoDTO.setDenIndirizzo(ubicazioneOggettoExtendedDTO.getDenIndirizzo());
                ubicazioneOggettoDTO.setNumCivico(ubicazioneOggettoExtendedDTO.getNumCivico());
                ubicazioneOggettoDTO.setDesLocalita(ubicazioneOggettoExtendedDTO.getDesLocalita());
                ubicazioneOggettoDTO.setIndGeoProvenienza(ubicazioneOggettoExtendedDTO.getIndGeoProvenienza());
                ubicazioneOggettoDTO.setGestAttoreIns(ubicazioneOggettoExtendedDTO.getGestAttoreIns());
                ubicazioneOggettoDTO.setGestAttoreUpd(ubicazioneOggettoExtendedDTO.getGestAttoreUpd());
                ubicazioneOggettoDTO.setGestDataIns(ubicazioneOggettoExtendedDTO.getGestDataIns());
                ubicazioneOggettoDTO.setGestDataUpd(ubicazioneOggettoExtendedDTO.getGestDataUpd());
                ubicazioneOggettoDTO.setGestUID(ubicazioneOggettoExtendedDTO.getGestUID());
                ubicazioneOggettoDTOList.add(ubicazioneOggettoDTO);
            }
        }
        return ubicazioneOggettoDTOList;
    }

}
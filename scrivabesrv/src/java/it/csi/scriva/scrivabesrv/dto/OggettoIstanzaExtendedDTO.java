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
 * The type Oggetto istanza extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoIstanzaExtendedDTO extends OggettoIstanzaDTO implements Serializable {

    @JsonProperty("oggetto")
    private OggettoExtendedDTO oggetto;

    @JsonProperty("tipologia_oggetto")
    private TipologiaOggettoExtendedDTO tipologiaOggetto;

    /**
     * Gets oggetto.
     *
     * @return oggetto oggetto
     */
    public OggettoExtendedDTO getOggetto() {
        return oggetto;
    }

    /**
     * Sets oggetto.
     *
     * @param oggetto oggetto
     */
    public void setOggetto(OggettoExtendedDTO oggetto) {
        this.oggetto = oggetto;
    }

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
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OggettoIstanzaExtendedDTO that = (OggettoIstanzaExtendedDTO) o;
        return Objects.equals(oggetto, that.oggetto) && Objects.equals(tipologiaOggetto, that.tipologiaOggetto);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oggetto, tipologiaOggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoIstanzaExtendedDTO {");
        sb.append(super.toString());
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         oggetto:").append(oggetto);
        sb.append(",         tipologiaOggetto:").append(tipologiaOggetto);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return OggettoIstanzaDTO dto
     */
    @JsonIgnore
    public OggettoIstanzaDTO getDTO() {
        OggettoIstanzaDTO dto = new OggettoIstanzaDTO();
        dto.setIdOggettoIstanza(this.getIdOggettoIstanza());
        if (null != this.getOggetto()) {
            dto.setIdOggetto(this.getOggetto().getIdOggetto());
        }
        dto.setIdIstanza(this.getIdIstanza());
        if (null != this.getTipologiaOggetto()) {
            dto.setIdTipologiaOggetto(this.getTipologiaOggetto().getIdTipologiaOggetto());
        }
        dto.setIndGeoStato(this.getIndGeoStato());
        dto.setDenOggetto(this.getDenOggetto());
        dto.setDesOggetto(this.getDesOggetto());
        dto.setCoordinataX(this.getCoordinataX());
        dto.setCoordinataY(this.getCoordinataY());
        dto.setGestAttoreIns(this.gestAttoreIns);
        dto.setGestAttoreUpd(this.gestAttoreUpd);
        dto.setGestDataIns(this.gestDataIns);
        dto.setGestDataUpd(this.gestDataUpd);
        dto.setGestUID(this.gestUID);
        dto.setIdMasterdata(this.getIdMasterdata());
        dto.setIdMasterdataOrigine(this.getIdMasterdataOrigine());
        dto.setIdIstanzaAttore(this.getIdIstanzaAttore());
        dto.setIdFunzionario(this.getIdFunzionario());
        dto.setCodOggettoFonte(this.getCodOggettoFonte());
        dto.setCodUtenza(this.getCodUtenza());
        dto.setNoteAttoPrecedente(this.getNoteAttoPrecedente());
        dto.setIndLivello(this.getIndLivello());
        return dto;
    }

}
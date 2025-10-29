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
 * The type Adempimento tipo oggetto extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempimentoTipoOggettoExtendedDTO extends AdempimentoTipoOggettoDTO implements Serializable {

    @JsonProperty("adempimento")
    private AdempimentoExtendedDTO adempimento;

    @JsonProperty("tipologia_oggetto")
    private TipologiaOggettoDTO tipologiaOggetto;

    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
    }

    public TipologiaOggettoDTO getTipologiaOggetto() {
        return tipologiaOggetto;
    }

    public void setTipologiaOggetto(TipologiaOggettoDTO tipologiaOggetto) {
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
        AdempimentoTipoOggettoExtendedDTO that = (AdempimentoTipoOggettoExtendedDTO) o;
        return Objects.equals(adempimento, that.adempimento) && Objects.equals(tipologiaOggetto, that.tipologiaOggetto);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), adempimento, tipologiaOggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "AdempimentoTipoOggettoExtendedDTO {\n" +
                "         adempimento:" + adempimento +
                ",\n         tipologiaOggetto:" + tipologiaOggetto +
                "}\n";
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public AdempimentoTipoOggettoDTO getDTO() {
        AdempimentoTipoOggettoDTO dto = new AdempimentoTipoOggettoDTO();
        if (this.adempimento != null) {
            dto.setIdAdempimento(this.adempimento.getIdAdempimento());
        }
        if (this.tipologiaOggetto != null) {
            dto.setIdTipologiaOggetto(this.tipologiaOggetto.getIdTipologiaOggetto());
        }
        dto.setFlgAssegna(this.getFlgAssegna());
        return dto;
    }
}
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
 * The type Tipologia oggetto extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TipologiaOggettoExtendedDTO extends TipologiaOggettoDTO implements Serializable {

    @JsonProperty("natura_oggetto")
    private NaturaOggettoDTO naturaOggetto;

    @JsonProperty("adempimento")
    private AdempimentoExtendedDTO adempimento;

    /**
     * Gets natura oggetto.
     *
     * @return naturaOggetto natura oggetto
     */
    public NaturaOggettoDTO getNaturaOggetto() {
        return naturaOggetto;
    }

    /**
     * Sets natura oggetto.
     *
     * @param naturaOggetto naturaOggetto
     */
    public void setNaturaOggetto(NaturaOggettoDTO naturaOggetto) {
        this.naturaOggetto = naturaOggetto;
    }

    /**
     * Gets adempimento.
     *
     * @return adempimento adempimento
     */
    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    /**
     * Sets adempimento.
     *
     * @param adempimento adempimento
     */
    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), naturaOggetto, adempimento);
    }

    /**
     * @param o Object
     * @return booelan
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        TipologiaOggettoExtendedDTO that = (TipologiaOggettoExtendedDTO) o;
        return Objects.equals(naturaOggetto, that.naturaOggetto) && Objects.equals(adempimento, that.adempimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipologiaOggettoExtendedDTO {");
        sb.append(super.toString());
        sb.append(",         naturaOggetto:").append(naturaOggetto);
        sb.append(",         adempimento:").append(adempimento);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return TipologiaOggettoDTO dto
     */
    @JsonIgnore
    public TipologiaOggettoDTO getDTO() {
        TipologiaOggettoDTO dto = new TipologiaOggettoDTO();
        dto.setIdTipologiaOggetto(this.getIdTipologiaOggetto());
        if (null != this.getNaturaOggetto()) {
            dto.setIdNaturaOggetto(this.getNaturaOggetto().getIdNaturaOggetto());
        }
        dto.setCodTipologiaOggetto(this.getCodTipologiaOggetto());
        dto.setDesTipologiaOggetto(this.getDesTipologiaOggetto());
        dto.setIdLayer(this.getIdLayer());
        return dto;
    }

}
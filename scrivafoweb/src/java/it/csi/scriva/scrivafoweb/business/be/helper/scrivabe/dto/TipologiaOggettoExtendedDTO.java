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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class TipologiaOggettoExtendedDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_tipologia_oggetto")
    private Long idTipologiaOggetto;

    @JsonProperty("natura_oggetto")
    private NaturaOggettoDTO naturaOggetto;

    @JsonProperty("cod_tipologia_oggetto")
    private String codTipologiaOggetto;

    @JsonProperty("des_tipologia_oggetto")
    private String desTipologiaOggetto;

    public Long getIdTipologiaOggetto() {
        return idTipologiaOggetto;
    }

    public void setIdTipologiaOggetto(Long idTipologiaOggetto) {
        this.idTipologiaOggetto = idTipologiaOggetto;
    }

    public NaturaOggettoDTO getNaturaOggetto() {
        return naturaOggetto;
    }

    public void setNaturaOggetto(NaturaOggettoDTO naturaOggetto) {
        this.naturaOggetto = naturaOggetto;
    }

    public String getCodTipologiaOggetto() {
        return codTipologiaOggetto;
    }

    public void setCodTipologiaOggetto(String codTipologiaOggetto) {
        this.codTipologiaOggetto = codTipologiaOggetto;
    }

    public String getDesTipologiaOggetto() {
        return desTipologiaOggetto;
    }

    public void setDesTipologiaOggetto(String desTipologiaOggetto) {
        this.desTipologiaOggetto = desTipologiaOggetto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codTipologiaOggetto, desTipologiaOggetto, idTipologiaOggetto, naturaOggetto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TipologiaOggettoExtendedDTO other = (TipologiaOggettoExtendedDTO) obj;
        return Objects.equals(codTipologiaOggetto, other.codTipologiaOggetto) && Objects.equals(desTipologiaOggetto, other.desTipologiaOggetto) && Objects.equals(idTipologiaOggetto, other.idTipologiaOggetto) && Objects.equals(naturaOggetto, other.naturaOggetto);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TipologiaOggettoExtendedDTO {\n    idTipologiaOggetto: ").append(idTipologiaOggetto).append("\n    naturaOggetto: ").append(naturaOggetto).append("\n    codTipologiaOggetto: ").append(codTipologiaOggetto).append("\n    desTipologiaOggetto: ").append(desTipologiaOggetto).append("\n}");
        return builder.toString();
    }

}
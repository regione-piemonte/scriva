/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */

package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoTipoAllegatoExtendedDTO extends AdempimentoTipoAllegatoDTO implements Serializable {

    @JsonProperty("adempimento")
    private AdempimentoExtendedDTO adempimento;

    @JsonProperty("tipologia_allegato")
    private TipologiaAllegatoExtendedDTO tipologiaAllegato;

    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
    }

    public TipologiaAllegatoExtendedDTO getTipologiaAllegato() {
        return tipologiaAllegato;
    }

    public void setTipologiaAllegato(TipologiaAllegatoExtendedDTO tipologiaAllegato) {
        this.tipologiaAllegato = tipologiaAllegato;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        AdempimentoTipoAllegatoExtendedDTO that = (AdempimentoTipoAllegatoExtendedDTO) o;
        return Objects.equals(adempimento, that.adempimento) && Objects.equals(tipologiaAllegato, that.tipologiaAllegato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), adempimento, tipologiaAllegato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdempimentoTipoAllegatoExtendedDTO {");
        sb.append(super.toString());
        sb.append("         adempimento:").append(adempimento);
        sb.append(",         tipologiaAllegato:").append(tipologiaAllegato);
        sb.append("}");
        return sb.toString();
    }

    /**
     * @return AdempimentoTipoAllegatoDTO
     */
    @JsonIgnore
    public AdempimentoTipoAllegatoDTO getDTO() {
        AdempimentoTipoAllegatoDTO dto = new AdempimentoTipoAllegatoDTO();
        if(null != this.adempimento){ dto.setIdAdempimento(this.adempimento.getIdAdempimento()); }
        if(null != this.tipologiaAllegato){ dto.setIdTipologiaAllegato(this.tipologiaAllegato.getIdTipologiaAllegato()); }
        dto.setFlgFirmaDigitale(this.getFlgFirmaDigitale());
        dto.setFlgFirmaNonValidaBloccante(this.getFlgFirmaNonValidaBloccante());
        dto.setFlgNascondiFO(this.getFlgNascondiFO());
        dto.setFlgRiservato(this.getFlgRiservato());
        dto.setFlgObbligo(this.getFlgObbligo());
        dto.setFlgNota(this.getFlgNota());
        return dto;
    }
}
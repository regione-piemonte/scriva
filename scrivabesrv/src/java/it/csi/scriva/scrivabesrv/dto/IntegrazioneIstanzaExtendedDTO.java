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
import java.util.List;
import java.util.Objects;


/**
 * The type Integrazione istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IntegrazioneIstanzaExtendedDTO extends IntegrazioneIstanzaDTO implements Serializable {

    @JsonProperty("tipo_integrazione")
    private TipoIntegrazioneDTO tipoIntegrazione;

    @JsonProperty("allegato_integrazione")
    private List<AllegatoIntegrazioneDTO> allegatoIntegrazione;


    /**
     * Gets tipo integrazione.
     *
     * @return the tipo integrazione
     */
    public TipoIntegrazioneDTO getTipoIntegrazione() {
        return tipoIntegrazione;
    }

    /**
     * Sets tipo integrazione.
     *
     * @param tipoIntegrazione the tipo integrazione
     */
    public void setTipoIntegrazione(TipoIntegrazioneDTO tipoIntegrazione) {
        this.tipoIntegrazione = tipoIntegrazione;
    }

    /**
     * Gets allegato integrazione.
     *
     * @return the allegato integrazione
     */
    public List<AllegatoIntegrazioneDTO> getAllegatoIntegrazione() {
        return allegatoIntegrazione;
    }

    /**
     * Sets allegato integrazione.
     *
     * @param allegatoIntegrazione the allegato integrazione
     */
    public void setAllegatoIntegrazione(List<AllegatoIntegrazioneDTO> allegatoIntegrazione) {
        this.allegatoIntegrazione = allegatoIntegrazione;
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
        IntegrazioneIstanzaExtendedDTO that = (IntegrazioneIstanzaExtendedDTO) o;
        return Objects.equals(tipoIntegrazione, that.tipoIntegrazione) && Objects.equals(allegatoIntegrazione, that.allegatoIntegrazione);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoIntegrazione, allegatoIntegrazione);
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public IntegrazioneIstanzaDTO getDTO() {
        IntegrazioneIstanzaDTO dto = new IntegrazioneIstanzaDTO();
        dto.setIdIntegrazioneIstanza(this.getIdIntegrazioneIstanza());
        dto.setIdIstanza(this.getIdIstanza());
        if (this.getTipoIntegrazione() != null) {
            dto.setIdTipoIntegrazione(this.getTipoIntegrazione().getIdTipoIntegrazione());
        }
        dto.setDataInserimento(this.getDataInserimento());
        dto.setDataInvio(this.getDataInvio());
        dto.setNumProtocollo(this.getNumProtocollo());
        dto.setDataProtocollo(this.getDataProtocollo());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }

}
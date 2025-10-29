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
public class IstanzaOsservazioneExtendedDTO extends IstanzaOsservazioneDTO implements Serializable {

    @JsonProperty("stato_osservazione")
    private StatoOsservazioneDTO statoOsservazione;

    /**
     * Gets stato osservazione.
     *
     * @return the stato osservazione
     */
    public StatoOsservazioneDTO getStatoOsservazione() {
        return statoOsservazione;
    }

    /**
     * Sets stato osservazione.
     *
     * @param statoOsservazione the stato osservazione
     */
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
        IstanzaOsservazioneExtendedDTO that = (IstanzaOsservazioneExtendedDTO) o;
        return Objects.equals(statoOsservazione, that.statoOsservazione);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), statoOsservazione);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaOsservazioneExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         statoOsservazione:").append(statoOsservazione);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public IstanzaOsservazioneDTO getDTO() {
        IstanzaOsservazioneDTO dto = new IstanzaOsservazioneDTO();
        dto.setIdIstanzaOsservazione(this.getIdIstanzaOsservazione());
        dto.setIdIstanza(this.getIdIstanza());
        if (null != this.statoOsservazione) {
            dto.setIdStatoOsservazione(this.statoOsservazione.getIdStatoOsservazione());
        }
        dto.setIdFunzionario(this.getIdFunzionario());
        dto.setCfOsservazioneIns(this.getCfOsservazioneIns());
        dto.setDataOsservazione(this.getDataOsservazione());
        dto.setDataPubblicazione(this.getDataPubblicazione());
        dto.setNumProtocolloOsservazione(this.getNumProtocolloOsservazione());
        dto.setDataProtocolloOsservazione(this.getDataProtocolloOsservazione());
        dto.setGestAttoreIns(this.gestAttoreIns);
        dto.setGestDataIns(this.gestDataIns);
        dto.setGestAttoreUpd(this.gestAttoreUpd);
        dto.setGestDataUpd(this.gestDataUpd);
        dto.setGestUID(this.gestUID);
        return dto;
    }
}
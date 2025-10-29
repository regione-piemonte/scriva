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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Regione dto.
 */
public class RegioneExtendedDTO extends RegioneDTO implements Serializable {

    @JsonProperty("nazione")
    private NazioneDTO nazione;

    /**
     * Gets nazione.
     *
     * @return the nazione
     */
    public NazioneDTO getNazione() {
        return nazione;
    }

    /**
     * Sets nazione.
     *
     * @param nazione the nazione
     */
    public void setNazione(NazioneDTO nazione) {
        this.nazione = nazione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RegioneExtendedDTO that = (RegioneExtendedDTO) o;
        return Objects.equals(nazione, that.nazione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nazione);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RegioneExtendedDTO {");
        sb.append(super.toString());
        sb.append("         nazione:").append(nazione);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public RegioneDTO getDTO() {
        RegioneDTO dto = new RegioneDTO();
        dto.setIdRegione(this.getIdRegione());
        dto.setCodRegione(this.getCodRegione());
        dto.setDenomRegione(this.getDenomRegione());
        if (this.nazione != null) {
            dto.setIdNazione(this.nazione.getIdNazione());
        }
        dto.setDataInizioValidita(this.getDataInizioValidita());
        dto.setDataFineValidita(this.getDataFineValidita());
        return dto;
    }

}
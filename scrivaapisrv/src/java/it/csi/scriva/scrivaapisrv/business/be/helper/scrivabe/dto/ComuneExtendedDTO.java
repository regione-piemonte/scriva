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
 * The type Comune dto.
 */
public class ComuneExtendedDTO extends ComuneDTO implements Serializable {


    @JsonProperty("provincia")
    private ProvinciaExtendedDTO provincia;

    /**
     * Gets provincia.
     *
     * @return the provincia
     */
    public ProvinciaExtendedDTO getProvincia() {
        return provincia;
    }

    /**
     * Sets provincia.
     *
     * @param provincia the provincia
     */
    public void setProvincia(ProvinciaExtendedDTO provincia) {
        this.provincia = provincia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ComuneExtendedDTO that = (ComuneExtendedDTO) o;
        return Objects.equals(provincia, that.provincia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), provincia);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ComuneExtendedDTO {");
        sb.append(super.toString());
        sb.append("         provincia:").append(provincia);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Get dto comune dto.
     *
     * @return the comune dto
     */
    @JsonIgnore
    public ComuneDTO getDTO(){
        ComuneDTO dto = new ComuneDTO();
        dto.setIdComune(this.getIdComune());
        dto.setCodIstatComune(this.getCodIstatComune());
        dto.setCodBelfioreComune(this.getCodBelfioreComune());
        dto.setDenomComune(this.getDenomComune());
        if (this.provincia != null){
            dto.setIdProvincia(this.provincia.getIdProvincia());
        }
        dto.setDataInizioValidita(this.getDataInizioValidita());
        dto.setDataFineValidita(this.getDataFineValidita());
        dto.setDtIdComune(this.getDtIdComune());
        dto.setDtIdComunePrev(this.getDtIdComunePrev());
        dto.setDtIdComuneNext(this.getDtIdComuneNext());
        dto.setCapComune(this.getCapComune());
        return dto;
    }
}
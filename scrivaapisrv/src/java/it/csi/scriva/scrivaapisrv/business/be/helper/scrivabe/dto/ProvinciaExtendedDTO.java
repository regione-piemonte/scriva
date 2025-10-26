/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Provincia dto.
 */
public class ProvinciaExtendedDTO extends ProvinciaDTO implements Serializable {

    @JsonProperty("regione")
    private RegioneExtendedDTO regione;

    /**
     * Gets regione.
     *
     * @return the regione
     */
    public RegioneExtendedDTO getRegione() {
        return regione;
    }

    /**
     * Sets regione.
     *
     * @param regione the regione
     */
    public void setRegione(RegioneExtendedDTO regione) {
        this.regione = regione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProvinciaExtendedDTO that = (ProvinciaExtendedDTO) o;
        return Objects.equals(regione, that.regione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regione);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProvinciaExtendedDTO {");
        sb.append(super.toString());
        sb.append("         regione:").append(regione);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Get dto provincia dto.
     *
     * @return the provincia dto
     */
    @JsonIgnore
    public ProvinciaDTO getDTO(){
        ProvinciaDTO dto = new ProvinciaDTO();
        dto.setIdProvincia(this.getIdProvincia());
        dto.setCodProvincia(this.getCodProvincia());
        dto.setDenomProvincia(this.getDenomProvincia());
        dto.setSiglaProvincia(this.getSiglaProvincia());
        if (this.regione != null) {
            dto.setIdRegione(this.regione.getIdRegione());
        }
        dto.setDataInizioValidita(this.getDataInizioValidita());
        dto.setDataFineValidita(this.getDataFineValidita());
        return dto;
    }
}
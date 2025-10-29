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
 * The type Messaggio extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessaggioExtendedDTO extends MessaggioDTO implements Serializable {

    @JsonProperty("tipo_messaggio")
    private TipoMessaggioDTO tipoMessaggio;

    /**
     * Gets tipo messaggio.
     *
     * @return the tipo messaggio
     */
    public TipoMessaggioDTO getTipoMessaggio() {
        return tipoMessaggio;
    }

    /**
     * Sets tipo messaggio.
     *
     * @param tipoMessaggio the tipo messaggio
     */
    public void setTipoMessaggio(TipoMessaggioDTO tipoMessaggio) {
        this.tipoMessaggio = tipoMessaggio;
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
        MessaggioExtendedDTO that = (MessaggioExtendedDTO) o;
        return Objects.equals(tipoMessaggio, that.tipoMessaggio);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoMessaggio);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "MessaggioExtendedDTO {\n" +
                super.toString() +
                "         tipoMessaggio:" + tipoMessaggio +
                "}\n";
    }

    /**
     * Gets dto.
     *
     * @return MessaggioDTO dto
     */
    @JsonIgnore
    public MessaggioDTO getDTO() {
        MessaggioDTO dto = new MessaggioDTO();
        dto.setIdMessaggio(this.getIdMessaggio());
        if (null != this.getTipoMessaggio()) {
            dto.setIdTipoMessaggio(this.getTipoMessaggio().getIdTipoMessaggio());
        }
        dto.setCodMessaggio(this.getCodMessaggio());
        dto.setDesTestoMessaggio(this.getDesTestoMessaggio());
        return dto;
    }

}
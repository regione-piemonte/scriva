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

public class MessaggioExtendedDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_messaggio")
    private Long idMessaggio;

    @JsonProperty("tipo_messaggio")
    private TipoMessaggioDTO tipoMessaggio;

    @JsonProperty("cod_messaggio")
    private String codMessaggio;

    @JsonProperty("des_testo_messaggio")
    private String desTestoMessaggio;

    public Long getIdMessaggio() {
        return idMessaggio;
    }

    public void setIdMessaggio(Long idMessaggio) {
        this.idMessaggio = idMessaggio;
    }

    public TipoMessaggioDTO getTipoMessaggio() {
        return tipoMessaggio;
    }

    public void setTipoMessaggio(TipoMessaggioDTO tipoMessaggio) {
        this.tipoMessaggio = tipoMessaggio;
    }

    public String getCodMessaggio() {
        return codMessaggio;
    }

    public void setCodMessaggio(String codMessaggio) {
        this.codMessaggio = codMessaggio;
    }

    public String getDesTestoMessaggio() {
        return desTestoMessaggio;
    }

    public void setDesTestoMessaggio(String desTestoMessaggio) {
        this.desTestoMessaggio = desTestoMessaggio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codMessaggio, desTestoMessaggio, idMessaggio, tipoMessaggio);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MessaggioExtendedDTO other = (MessaggioExtendedDTO) obj;
        return Objects.equals(codMessaggio, other.codMessaggio) && Objects.equals(desTestoMessaggio, other.desTestoMessaggio) && Objects.equals(idMessaggio, other.idMessaggio) && Objects.equals(tipoMessaggio, other.tipoMessaggio);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MessaggioExtendedDTO [idMessaggio=").append(idMessaggio).append("\n  tipoMessaggio=").append(tipoMessaggio).append("\n  codMessaggio=").append(codMessaggio).append("\n  desTestoMessaggio=").append(desTestoMessaggio).append("]");
        return builder.toString();
    }

}
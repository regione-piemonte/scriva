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

public class AmbitoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_ambito")
    private Long idAmbito;

    @JsonProperty("cod_ambito")
    private String codAmbito;

    @JsonProperty("des_ambito")
    private String desAmbito;

    @JsonProperty("des_estesa_ambito")
    private String desEstesaAmbito;

    @JsonIgnore
    private Boolean flgAttivo;

    public Long getIdAmbito() {
        return idAmbito;
    }

    public void setIdAmbito(Long idAmbito) {
        this.idAmbito = idAmbito;
    }

    public String getCodAmbito() {
        return codAmbito;
    }

    public void setCodAmbito(String codAmbito) {
        this.codAmbito = codAmbito;
    }

    public String getDesAmbito() {
        return desAmbito;
    }

    public void setDesAmbito(String desAmbito) {
        this.desAmbito = desAmbito;
    }

    public String getDesEstesaAmbito() {
        return desEstesaAmbito;
    }

    public void setDesEstesaAmbito(String descrizioneEstesaAmbito) {
        this.desEstesaAmbito = descrizioneEstesaAmbito;
    }

    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }


    @Override
    public int hashCode() {
        return Objects.hash(flgAttivo, codAmbito, desAmbito, desEstesaAmbito, idAmbito);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AmbitoDTO other = (AmbitoDTO) obj;
        return Objects.equals(flgAttivo, other.flgAttivo) && Objects.equals(codAmbito, other.codAmbito)
                && Objects.equals(desAmbito, other.desAmbito)
                && Objects.equals(desEstesaAmbito, other.desEstesaAmbito) && Objects.equals(idAmbito, other.idAmbito);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AmbitoDTO\n[idAmbito=").append(idAmbito).append("\n codiceAmbito=").append(codAmbito)
                .append("\n descrizioneAmbito=").append(desAmbito).append("\n descrizioneEstesaAmbito=")
                .append(desEstesaAmbito).append("\n flgAttivo=").append(flgAttivo).append("]");
        return builder.toString();
    }

}
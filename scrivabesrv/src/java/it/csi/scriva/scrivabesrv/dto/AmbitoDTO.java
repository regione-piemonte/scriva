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
 * The type Ambito dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    /**
     * Gets id ambito.
     *
     * @return the id ambito
     */
    public Long getIdAmbito() {
        return idAmbito;
    }

    /**
     * Sets id ambito.
     *
     * @param idAmbito the id ambito
     */
    public void setIdAmbito(Long idAmbito) {
        this.idAmbito = idAmbito;
    }

    /**
     * Gets cod ambito.
     *
     * @return the cod ambito
     */
    public String getCodAmbito() {
        return codAmbito;
    }

    /**
     * Sets cod ambito.
     *
     * @param codAmbito the cod ambito
     */
    public void setCodAmbito(String codAmbito) {
        this.codAmbito = codAmbito;
    }

    /**
     * Gets des ambito.
     *
     * @return the des ambito
     */
    public String getDesAmbito() {
        return desAmbito;
    }

    /**
     * Sets des ambito.
     *
     * @param desAmbito the des ambito
     */
    public void setDesAmbito(String desAmbito) {
        this.desAmbito = desAmbito;
    }

    /**
     * Gets des estesa ambito.
     *
     * @return the des estesa ambito
     */
    public String getDesEstesaAmbito() {
        return desEstesaAmbito;
    }

    /**
     * Sets des estesa ambito.
     *
     * @param descrizioneEstesaAmbito the descrizione estesa ambito
     */
    public void setDesEstesaAmbito(String descrizioneEstesaAmbito) {
        this.desEstesaAmbito = descrizioneEstesaAmbito;
    }

    /**
     * Gets flg attivo.
     *
     * @return the flg attivo
     */
    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    /**
     * Sets flg attivo.
     *
     * @param flgAttivo the flg attivo
     */
    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }


    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(flgAttivo, codAmbito, desAmbito, desEstesaAmbito, idAmbito);
    }

    /**
     * @param obj Object
     * @return boolean
     */
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

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AmbitoDTO\n[idAmbito=").append(idAmbito).append("\n codiceAmbito=").append(codAmbito)
                .append("\n descrizioneAmbito=").append(desAmbito).append("\n descrizioneEstesaAmbito=")
                .append(desEstesaAmbito).append("\n flgAttivo=").append(flgAttivo).append("]");
        return builder.toString();
    }

}
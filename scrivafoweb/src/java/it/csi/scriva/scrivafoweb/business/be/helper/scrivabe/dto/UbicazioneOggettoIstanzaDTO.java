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

/**
 * The type Ubicazione oggetto istanza dto.
 *
 * @author CSI PIEMONTE
 */
public class UbicazioneOggettoIstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_ubicazione_oggetto_istanza")
    private Long idUbicazioneOggettoIstanza;

    @JsonProperty("id_oggetto_istanza")
    private Long idOggettoIstanza;

    @JsonProperty("id_comune")
    private Long idComune;

    @JsonProperty("den_indirizzo")
    private String denIndirizzo;

    @JsonProperty("num_civico")
    private String numCivico;

    @JsonProperty("des_localita")
    private String desLocalita;

    @JsonProperty("ind_geo_provenienza")
    private String indGeoProvenienza;

    /**
     * Gets id ubicazione oggetto istanza.
     *
     * @return the id ubicazione oggetto istanza
     */
    public Long getIdUbicazioneOggettoIstanza() {
        return idUbicazioneOggettoIstanza;
    }

    /**
     * Sets id ubicazione oggetto istanza.
     *
     * @param idUbicazioneOggettoIstanza the id ubicazione oggetto istanza
     */
    public void setIdUbicazioneOggettoIstanza(Long idUbicazioneOggettoIstanza) {
        this.idUbicazioneOggettoIstanza = idUbicazioneOggettoIstanza;
    }

    /**
     * Gets id oggetto istanza.
     *
     * @return the id oggetto istanza
     */
    public Long getIdOggettoIstanza() {
        return idOggettoIstanza;
    }

    /**
     * Sets id oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     */
    public void setIdOggettoIstanza(Long idOggettoIstanza) {
        this.idOggettoIstanza = idOggettoIstanza;
    }

    /**
     * Gets id comune.
     *
     * @return the id comune
     */
    public Long getIdComune() {
        return idComune;
    }

    /**
     * Sets id comune.
     *
     * @param idComune the id comune
     */
    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }

    /**
     * Gets den indirizzo.
     *
     * @return the den indirizzo
     */
    public String getDenIndirizzo() {
        return denIndirizzo;
    }

    /**
     * Sets den indirizzo.
     *
     * @param denIndirizzo the den indirizzo
     */
    public void setDenIndirizzo(String denIndirizzo) {
        this.denIndirizzo = denIndirizzo;
    }

    /**
     * Gets num civico.
     *
     * @return the num civico
     */
    public String getNumCivico() {
        return numCivico;
    }

    /**
     * Sets num civico.
     *
     * @param numCivico the num civico
     */
    public void setNumCivico(String numCivico) {
        this.numCivico = numCivico;
    }

    /**
     * Gets des localita.
     *
     * @return the des localita
     */
    public String getDesLocalita() {
        return desLocalita;
    }

    /**
     * Sets des localita.
     *
     * @param desLocalita the des localita
     */
    public void setDesLocalita(String desLocalita) {
        this.desLocalita = desLocalita;
    }

    /**
     * Gets ind geo provenienza.
     *
     * @return the ind geo provenienza
     */
    public String getIndGeoProvenienza() {
        return indGeoProvenienza;
    }

    /**
     * Sets ind geo provenienza.
     *
     * @param indGeoProvenienza the ind geo provenienza
     */
    public void setIndGeoProvenienza(String indGeoProvenienza) {
        this.indGeoProvenienza = indGeoProvenienza;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(denIndirizzo, desLocalita, idComune, idOggettoIstanza, idUbicazioneOggettoIstanza, indGeoProvenienza, numCivico);
    }

    /**
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UbicazioneOggettoIstanzaDTO other = (UbicazioneOggettoIstanzaDTO) obj;
        return Objects.equals(denIndirizzo, other.denIndirizzo) && Objects.equals(desLocalita, other.desLocalita) && Objects.equals(idComune, other.idComune) && Objects.equals(idOggettoIstanza, other.idOggettoIstanza) && Objects.equals(idUbicazioneOggettoIstanza, other.idUbicazioneOggettoIstanza) && Objects.equals(indGeoProvenienza, other.indGeoProvenienza) && Objects.equals(numCivico, other.numCivico);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UbicazioneOggettoIstanzaDTO {\n    idUbicazioneOggettoIstanza: ").append(idUbicazioneOggettoIstanza).append("\n    idOggettoIstanza: ").append(idOggettoIstanza).append("\n    idComune: ").append(idComune).append("\n    denIndirizzo: ").append(denIndirizzo).append("\n    numCivico: ").append(numCivico).append("\n    desLocalita: ").append(desLocalita).append("\n    indGeoProvenienza: ").append(indGeoProvenienza).append("\n    gestDataIns: ").append(gestDataIns).append("\n    gestAttoreIns: ").append(gestAttoreIns).append("\n    gestDataUpd: ").append(gestDataUpd).append("\n    gestAttoreUpd: ").append(gestAttoreUpd).append("\n    gestUID: ").append(gestUID).append("\n}");
        return builder.toString();
    }

}
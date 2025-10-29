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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Ubicazione oggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UbicazioneOggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_ubicazione_oggetto")
    private Long idUbicazioneOggetto;

    @JsonProperty("id_oggetto")
    private Long idOggetto;

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
     * Gets id ubicazione oggetto.
     *
     * @return the id ubicazione oggetto
     */
    public Long getIdUbicazioneOggetto() {
        return idUbicazioneOggetto;
    }

    /**
     * Sets id ubicazione oggetto.
     *
     * @param idUbicazioneOggetto the id ubicazione oggetto
     */
    public void setIdUbicazioneOggetto(Long idUbicazioneOggetto) {
        this.idUbicazioneOggetto = idUbicazioneOggetto;
    }

    /**
     * Gets id oggetto.
     *
     * @return the id oggetto
     */
    public Long getIdOggetto() {
        return idOggetto;
    }

    /**
     * Sets id oggetto.
     *
     * @param idOggetto the id oggetto
     */
    public void setIdOggetto(Long idOggetto) {
        this.idOggetto = idOggetto;
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
        return Objects.hash(denIndirizzo, desLocalita, idComune, idOggetto, idUbicazioneOggetto, indGeoProvenienza, numCivico);
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
        UbicazioneOggettoDTO other = (UbicazioneOggettoDTO) obj;
        return Objects.equals(denIndirizzo, other.denIndirizzo) && Objects.equals(desLocalita, other.desLocalita) && Objects.equals(idComune, other.idComune) && Objects.equals(idOggetto, other.idOggetto) && Objects.equals(idUbicazioneOggetto, other.idUbicazioneOggetto) && Objects.equals(indGeoProvenienza, other.indGeoProvenienza) && Objects.equals(numCivico, other.numCivico);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UbicazioneOggettoDTO {\n    idUbicazioneOggetto: ").append(idUbicazioneOggetto).append("\n    idOggetto: ").append(idOggetto).append("\n    idComune: ").append(idComune).append("\n    denIndirizzo: ").append(denIndirizzo).append("\n    numCivico: ").append(numCivico).append("\n    desLocalita: ").append(desLocalita).append("\n    indGeoProvenienza: ").append(indGeoProvenienza).append("\n}");
        return builder.toString();
    }

}
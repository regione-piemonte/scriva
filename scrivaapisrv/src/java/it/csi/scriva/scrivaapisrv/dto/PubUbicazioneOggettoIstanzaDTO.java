/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ComuneExtendedDTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Pub ubicazione oggetto istanza dto.
 *
 * @author CSI PIEMONTE
 */
public class PubUbicazioneOggettoIstanzaDTO implements Serializable {

    @JsonProperty("id_oggetto_istanza")
    private Long idOggettoIstanza;

    @JsonProperty("comune")
    private ComuneExtendedDTO comune;

    @JsonProperty("den_indirizzo")
    private String denIndirizzo;

    @JsonProperty("num_civico")
    private String numCivico;

    @JsonProperty("des_localita")
    private String desLocalita;

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
     * Gets comune.
     *
     * @return the comune
     */
    public ComuneExtendedDTO getComune() {
        return comune;
    }

    /**
     * Sets comune.
     *
     * @param comune the comune
     */
    public void setComune(ComuneExtendedDTO comune) {
        this.comune = comune;
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
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubUbicazioneOggettoIstanzaDTO that = (PubUbicazioneOggettoIstanzaDTO) o;
        return Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(comune, that.comune) && Objects.equals(denIndirizzo, that.denIndirizzo) && Objects.equals(numCivico, that.numCivico) && Objects.equals(desLocalita, that.desLocalita);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idOggettoIstanza, comune, denIndirizzo, numCivico, desLocalita);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PubUbicazioneOggettoIstanzaDTO {\n");
        sb.append("         id_oggetto_istanza:").append(idOggettoIstanza);
        sb.append(",\n         comune:").append(comune);
        sb.append(",\n         denIndirizzo:'").append(denIndirizzo).append("'");
        sb.append(",\n         numCivico:'").append(numCivico).append("'");
        sb.append(",\n         desLocalita:'").append(desLocalita).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}
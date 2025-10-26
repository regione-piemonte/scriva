/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class UbicazioneOggettoIstanzaExtendedDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_ubicazione_oggetto_istanza")
    private Long idUbicazioneOggettoIstanza;

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

    @JsonProperty("ind_geo_provenienza")
    private String indGeoProvenienza;

    public Long getIdUbicazioneOggettoIstanza() {
        return idUbicazioneOggettoIstanza;
    }

    public void setIdUbicazioneOggettoIstanza(Long idUbicazioneOggettoIstanza) {
        this.idUbicazioneOggettoIstanza = idUbicazioneOggettoIstanza;
    }

    public Long getIdOggettoIstanza() {
        return idOggettoIstanza;
    }

    public void setIdOggettoIstanza(Long idOggettoIstanza) {
        this.idOggettoIstanza = idOggettoIstanza;
    }

    public ComuneExtendedDTO getComune() {
        return comune;
    }

    public void setComune(ComuneExtendedDTO comune) {
        this.comune = comune;
    }

    public String getDenIndirizzo() {
        return denIndirizzo;
    }

    public void setDenIndirizzo(String denIndirizzo) {
        this.denIndirizzo = denIndirizzo;
    }

    public String getNumCivico() {
        return numCivico;
    }

    public void setNumCivico(String numCivico) {
        this.numCivico = numCivico;
    }

    public String getDesLocalita() {
        return desLocalita;
    }

    public void setDesLocalita(String desLocalita) {
        this.desLocalita = desLocalita;
    }

    public String getIndGeoProvenienza() {
        return indGeoProvenienza;
    }

    public void setIndGeoProvenienza(String indGeoProvenienza) {
        this.indGeoProvenienza = indGeoProvenienza;
    }

    @Override
    public int hashCode() {
        return Objects.hash(comune, denIndirizzo, desLocalita, idOggettoIstanza, idUbicazioneOggettoIstanza, indGeoProvenienza, numCivico);
    }

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
        UbicazioneOggettoIstanzaExtendedDTO other = (UbicazioneOggettoIstanzaExtendedDTO) obj;
        return Objects.equals(comune, other.comune) && Objects.equals(denIndirizzo, other.denIndirizzo) && Objects.equals(desLocalita, other.desLocalita) && Objects.equals(idOggettoIstanza, other.idOggettoIstanza) && Objects.equals(idUbicazioneOggettoIstanza, other.idUbicazioneOggettoIstanza) && Objects.equals(indGeoProvenienza, other.indGeoProvenienza) && Objects.equals(numCivico, other.numCivico);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UbicazioneOggettoIstanzaExtendedDTO {\n    idUbicazioneOggettoIstanza: ").append(idUbicazioneOggettoIstanza).append("\n    idOggettoIstanza: ").append(idOggettoIstanza).append("\n    comune: ").append(comune).append("\n    denIndirizzo: ").append(denIndirizzo).append("\n    numCivico: ").append(numCivico).append("\n    desLocalita: ").append(desLocalita).append("\n    indGeoProvenienza: ").append(indGeoProvenienza).append("\n    gestDataIns: ").append(gestDataIns).append("\n    gestAttoreIns: ").append(gestAttoreIns).append("\n    gestDataUpd: ").append(gestDataUpd).append("\n    gestAttoreUpd: ").append(gestAttoreUpd).append("\n    gestUID: ").append(gestUID).append("\n}");
        return builder.toString();
    }

}
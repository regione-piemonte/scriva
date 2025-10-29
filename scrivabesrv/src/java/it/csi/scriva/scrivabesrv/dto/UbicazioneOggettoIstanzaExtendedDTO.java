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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Ubicazione oggetto istanza extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UbicazioneOggettoIstanzaExtendedDTO extends UbicazioneOggettoIstanzaDTO implements Serializable {

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

    @JsonProperty("dati_catastali")
    List<CatastoUbicazioneOggettoIstanzaDTO> datiCatastali;


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
     * Gets dati catastali.
     *
     * @return the dati catastali
     */
    public List<CatastoUbicazioneOggettoIstanzaDTO> getDatiCatastali() {
        return datiCatastali;
    }

    /**
     * Sets dati catastali.
     *
     * @param datiCatastali the dati catastali
     */
    public void setDatiCatastali(List<CatastoUbicazioneOggettoIstanzaDTO> datiCatastali) {
        this.datiCatastali = datiCatastali;
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
        UbicazioneOggettoIstanzaExtendedDTO that = (UbicazioneOggettoIstanzaExtendedDTO) o;
        return Objects.equals(idUbicazioneOggettoIstanza, that.idUbicazioneOggettoIstanza) && Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(comune, that.comune) && Objects.equals(denIndirizzo, that.denIndirizzo) && Objects.equals(numCivico, that.numCivico) && Objects.equals(desLocalita, that.desLocalita) && Objects.equals(indGeoProvenienza, that.indGeoProvenienza) && Objects.equals(datiCatastali, that.datiCatastali);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idUbicazioneOggettoIstanza, idOggettoIstanza, comune, denIndirizzo, numCivico, desLocalita, indGeoProvenienza, datiCatastali);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UbicazioneOggettoIstanzaExtendedDTO {\n");
        sb.append(super.toString());
        sb.append("         idUbicazioneOggettoIstanza:").append(idUbicazioneOggettoIstanza);
        sb.append(",\n         idOggettoIstanza:").append(idOggettoIstanza);
        sb.append(",\n         comune:").append(comune);
        sb.append(",\n         denIndirizzo:'").append(denIndirizzo).append("'");
        sb.append(",\n         numCivico:'").append(numCivico).append("'");
        sb.append(",\n         desLocalita:'").append(desLocalita).append("'");
        sb.append(",\n         indGeoProvenienza:'").append(indGeoProvenienza).append("'");
        sb.append(",\n         datiCatastali:").append(datiCatastali);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return UbicazioneOggettoIstanzaDTO dto
     */
    @JsonIgnore
    public UbicazioneOggettoIstanzaDTO getDTO() {
        UbicazioneOggettoIstanzaDTO dto = new UbicazioneOggettoIstanzaDTO();
        dto.setIdUbicazioneOggettoIstanza(this.idUbicazioneOggettoIstanza);
        dto.setIdOggettoIstanza(this.idOggettoIstanza);
        if (null != this.getComune()) {
            dto.setIdComune(this.getComune().getIdComune());
        }
        dto.setDenIndirizzo(this.denIndirizzo);
        dto.setNumCivico(this.numCivico);
        dto.setDesLocalita(this.desLocalita);
        dto.setIndGeoProvenienza(this.indGeoProvenienza);
        dto.setGestAttoreIns(this.gestAttoreIns);
        dto.setGestAttoreUpd(this.gestAttoreUpd);
        dto.setGestDataIns(this.gestDataIns);
        dto.setGestDataUpd(this.gestDataUpd);
        dto.setGestUID(this.gestUID);
        return dto;
    }

}
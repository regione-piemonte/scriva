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
 * The type Recapito alternativo extended dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecapitoAlternativoExtendedDTO extends RecapitoAlternativoDTO implements Serializable {

    @JsonProperty("comune_residenza")
    private ComuneExtendedDTO comuneResidenza;

    @JsonProperty("comune_sede_legale")
    private ComuneExtendedDTO comuneSedeLegale;

    @JsonProperty("nazione_residenza")
    private NazioneDTO nazioneResidenza;

    @JsonProperty("nazione_sede_legale")
    private NazioneDTO nazioneSedeLegale;

    /**
     * Gets comune residenza.
     *
     * @return the comune residenza
     */
    public ComuneExtendedDTO getComuneResidenza() {
        return comuneResidenza;
    }

    /**
     * Sets comune residenza.
     *
     * @param comuneResidenza the comune residenza
     */
    public void setComuneResidenza(ComuneExtendedDTO comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
    }

    /**
     * Gets comune sede legale.
     *
     * @return the comune sede legale
     */
    public ComuneExtendedDTO getComuneSedeLegale() {
        return comuneSedeLegale;
    }

    /**
     * Sets comune sede legale.
     *
     * @param comuneSedeLegale the comune sede legale
     */
    public void setComuneSedeLegale(ComuneExtendedDTO comuneSedeLegale) {
        this.comuneSedeLegale = comuneSedeLegale;
    }

    /**
     * Gets nazione residenza.
     *
     * @return the nazione residenza
     */
    public NazioneDTO getNazioneResidenza() {
        return nazioneResidenza;
    }

    /**
     * Sets nazione residenza.
     *
     * @param nazioneResidenza the nazione residenza
     */
    public void setNazioneResidenza(NazioneDTO nazioneResidenza) {
        this.nazioneResidenza = nazioneResidenza;
    }

    /**
     * Gets nazione sede legale.
     *
     * @return the nazione sede legale
     */
    public NazioneDTO getNazioneSedeLegale() {
        return nazioneSedeLegale;
    }

    /**
     * Sets nazione sede legale.
     *
     * @param nazioneSedeLegale the nazione sede legale
     */
    public void setNazioneSedeLegale(NazioneDTO nazioneSedeLegale) {
        this.nazioneSedeLegale = nazioneSedeLegale;
    }

    /**
     * equals
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RecapitoAlternativoExtendedDTO that = (RecapitoAlternativoExtendedDTO) o;
        return Objects.equals(comuneResidenza, that.comuneResidenza) && Objects.equals(comuneSedeLegale, that.comuneSedeLegale) && Objects.equals(nazioneResidenza, that.nazioneResidenza) && Objects.equals(nazioneSedeLegale, that.nazioneSedeLegale);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), comuneResidenza, comuneSedeLegale, nazioneResidenza, nazioneSedeLegale);
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RecapitoAlternativoExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         comuneResidenza:").append(comuneResidenza);
        sb.append(",\n         comuneSedeLegale:").append(comuneSedeLegale);
        sb.append(",\n         nazioneResidenza:").append(nazioneResidenza);
        sb.append(",\n         nazioneSedeLegale:").append(nazioneSedeLegale);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public RecapitoAlternativoDTO getDTO() {
        RecapitoAlternativoDTO dto = new RecapitoAlternativoDTO();
        dto.setIdRecapitoAlternativo(this.getIdRecapitoAlternativo());
        dto.setCodRecapitoAlternativo(this.getCodRecapitoAlternativo());
        dto.setIdSoggettoIstanza(this.getIdSoggettoIstanza());
        if (this.getComuneResidenza() != null) {
            dto.setIdComuneResidenza(this.getComuneResidenza().getIdComune());
        }
        if (this.getComuneSedeLegale() != null) {
            dto.setIdComuneSedeLegale(this.getComuneSedeLegale().getIdComune());
        }
        dto.setIndirizzoSoggetto(this.getIndirizzoSoggetto());
        dto.setNumCivicoIndirizzo(this.getNumCivicoIndirizzo());
        dto.setCittaEsteraResidenza(this.getCittaEsteraResidenza());
        if (this.getNazioneResidenza() != null) {
            dto.setIdNazioneResidenza(this.getNazioneResidenza().getIdNazione());
        }
        if (null != this.getNazioneSedeLegale()) {
            dto.setIdNazioneSedeLegale(this.getNazioneSedeLegale().getIdNazione());
        }
        dto.setPresso(this.getPresso());
        dto.setNumTelefono(this.getNumTelefono());
        dto.setNumCellulare(this.getNumCellulare());
        dto.setDesLocalita(this.getDesLocalita());
        dto.setDesEmail(this.getDesEmail());
        dto.setDesPec(this.getDesPec());
        dto.setCittaEsteraSedeLegale(this.getCittaEsteraSedeLegale());
        dto.setCapResidenza(this.getCapResidenza());
        dto.setCapSedeLegale(this.getCapSedeLegale());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        dto.setIdIstanzaAttore(this.getIdIstanzaAttore());
        return dto;
    }
}
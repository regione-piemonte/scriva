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
import java.util.Objects;

/**
 * The type Soggetto extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SoggettoExtendedDTO extends SoggettoDTO implements Serializable {

    @JsonProperty("tipo_soggetto")
    private TipoSoggettoDTO tipoSoggetto;

    @JsonProperty("tipo_natura_giuridica")
    private TipoNaturaGiuridicaDTO tipoNaturaGiuridica;

    @JsonProperty("comune_residenza")
    private ComuneExtendedDTO comuneResidenza;

    @JsonProperty("comune_nascita")
    private ComuneExtendedDTO comuneNascita;

    @JsonProperty("comune_sede_legale")
    private ComuneExtendedDTO comuneSedeLegale;

    @JsonProperty("nazione_residenza")
    private NazioneDTO nazioneResidenza;

    @JsonProperty("nazione_nascita")
    private NazioneDTO nazioneNascita;

    @JsonProperty("nazione_sede_legale")
    private NazioneDTO nazioneSedeLegale;

    /**
     * Gets tipo soggetto.
     *
     * @return the tipo soggetto
     */
    public TipoSoggettoDTO getTipoSoggetto() {
        return tipoSoggetto;
    }

    /**
     * Sets tipo soggetto.
     *
     * @param tipoSoggetto the tipo soggetto
     */
    public void setTipoSoggetto(TipoSoggettoDTO tipoSoggetto) {
        this.tipoSoggetto = tipoSoggetto;
    }

    /**
     * Gets tipo natura giuridica.
     *
     * @return the tipo natura giuridica
     */
    public TipoNaturaGiuridicaDTO getTipoNaturaGiuridica() {
        return tipoNaturaGiuridica;
    }

    /**
     * Sets tipo natura giuridica.
     *
     * @param tipoNaturaGiuridica the tipo natura giuridica
     */
    public void setTipoNaturaGiuridica(TipoNaturaGiuridicaDTO tipoNaturaGiuridica) {
        this.tipoNaturaGiuridica = tipoNaturaGiuridica;
    }

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
     * Gets comune nascita.
     *
     * @return the comune nascita
     */
    public ComuneExtendedDTO getComuneNascita() {
        return comuneNascita;
    }

    /**
     * Sets comune nascita.
     *
     * @param comuneNascita the comune nascita
     */
    public void setComuneNascita(ComuneExtendedDTO comuneNascita) {
        this.comuneNascita = comuneNascita;
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
     * Gets nazione nascita.
     *
     * @return the nazione nascita
     */
    public NazioneDTO getNazioneNascita() {
        return nazioneNascita;
    }

    /**
     * Sets nazione nascita.
     *
     * @param nazioneNascita the nazione nascita
     */
    public void setNazioneNascita(NazioneDTO nazioneNascita) {
        this.nazioneNascita = nazioneNascita;
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
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoSoggetto, tipoNaturaGiuridica, comuneResidenza, comuneNascita, comuneSedeLegale, nazioneResidenza, nazioneNascita, nazioneSedeLegale);
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
        SoggettoExtendedDTO that = (SoggettoExtendedDTO) o;
        return Objects.equals(tipoSoggetto, that.tipoSoggetto) && Objects.equals(tipoNaturaGiuridica, that.tipoNaturaGiuridica) && Objects.equals(comuneResidenza, that.comuneResidenza) && Objects.equals(comuneNascita, that.comuneNascita) && Objects.equals(comuneSedeLegale, that.comuneSedeLegale) && Objects.equals(nazioneResidenza, that.nazioneResidenza) && Objects.equals(nazioneNascita, that.nazioneNascita) && Objects.equals(nazioneSedeLegale, that.nazioneSedeLegale);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SoggettoExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         tipoSoggetto:").append(tipoSoggetto);
        sb.append(",\n         tipoNaturaGiuridica:").append(tipoNaturaGiuridica);
        sb.append(",\n         comuneResidenza:").append(comuneResidenza);
        sb.append(",\n         comuneNascita:").append(comuneNascita);
        sb.append(",\n         comuneSedeLegale:").append(comuneSedeLegale);
        sb.append(",\n         nazioneResidenza:").append(nazioneResidenza);
        sb.append(",\n         nazioneNascita:").append(nazioneNascita);
        sb.append(",\n         nazioneSedeLegale:").append(nazioneSedeLegale);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return SoggettoDTO dto
     */
    @JsonIgnore
    public SoggettoDTO getDTO() {
        SoggettoDTO dto = new SoggettoDTO();
        dto.setIdSoggetto(this.getIdSoggetto());
        if (null != this.getTipoSoggetto()) {
            dto.setIdTipoSoggetto(this.getTipoSoggetto().getIdTipoSoggetto());
        }
        if (null != this.getTipoNaturaGiuridica()) {
            dto.setIdTipoNaturaGiuridica(this.getTipoNaturaGiuridica().getIdTipoNaturaGiuridica());
        }
        if (null != this.getComuneResidenza()) {
            dto.setIdComuneResidenza(this.getComuneResidenza().getIdComune());
        }
        if (null != this.getComuneNascita()) {
            dto.setIdComuneNascita(this.getComuneNascita().getIdComune());
        }
        if (null != this.getComuneSedeLegale()) {
            dto.setIdComuneSedeLegale(this.getComuneSedeLegale().getIdComune());
        }
        if (null != this.getNazioneNascita()) {
            dto.setIdNazioneNascita(this.getNazioneNascita().getIdNazione());
        }
        if (null != this.getNazioneResidenza()) {
            dto.setIdNazioneResidenza(this.getNazioneResidenza().getIdNazione());
        }
        if (null != this.getNazioneSedeLegale()) {
            dto.setIdNazioneSedeLegale(this.getNazioneSedeLegale().getIdNazione());
        }
        dto.setDenSoggetto(this.getDenSoggetto());
        dto.setDataCessazioneSoggetto(this.getDataCessazioneSoggetto());
        dto.setNome(this.getNome());
        dto.setCognome(this.getCognome());
        dto.setIndirizzoSoggetto(this.getIndirizzoSoggetto());
        dto.setDesEmail(this.getDesEmail());
        dto.setDesPec(this.getDesPec());
        dto.setCfSoggetto(this.getCfSoggetto());
        dto.setPivaSoggetto(this.getPivaSoggetto());
        dto.setNumCivicoIndirizzo(this.getNumCivicoIndirizzo());
        dto.setNumTelefono(this.getNumTelefono());
        dto.setDenProvinciaCCIAA(this.getDenProvinciaCCIAA());
        dto.setDenAnnoCCIAA(this.getDenAnnoCCIAA());
        dto.setDenNumeroCCIAA(this.getDenNumeroCCIAA());
        dto.setDataNascitaSoggetto(this.getDataNascitaSoggetto());
        dto.setCittaEsteraNascita(this.getCittaEsteraNascita());
        dto.setCittaEsteraResidenza(this.getCittaEsteraResidenza());
        dto.setIdMasterdata(this.getIdMasterdata());
        dto.setIdMasterdataOrigine(this.getIdMasterdataOrigine());
        dto.setNumCellulare(this.getNumCellulare());
        dto.setDesLocalita(this.getDesLocalita());
        dto.setCittaEsteraSedeLegale(this.getCittaEsteraSedeLegale());
        dto.setCapResidenza(this.getCapResidenza());
        dto.setCapSedeLegale(this.getCapSedeLegale());
        dto.setDataAggiornamento(this.getDataAggiornamento());
        dto.setIdFunzionario(this.getIdFunzionario());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }

}
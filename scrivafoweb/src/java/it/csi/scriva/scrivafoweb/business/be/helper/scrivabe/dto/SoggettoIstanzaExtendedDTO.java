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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Soggetto istanza extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SoggettoIstanzaExtendedDTO extends SoggettoIstanzaDTO implements Serializable {

    @JsonProperty("soggetto")
    private SoggettoExtendedDTO soggetto;

    @JsonProperty("tipo_soggetto")
    private TipoSoggettoDTO tipoSoggetto;

    @JsonProperty("ruolo_soggetto")
    private RuoloSoggettoDTO ruoloSoggetto;

    @JsonProperty("tipo_natura_giuridica")
    private TipoNaturaGiuridicaDTO tipoNaturaGiuridica;

    @JsonProperty("ruolo_compilante")
    private RuoloCompilanteDTO ruoloCompilante;

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

    @JsonProperty("gruppo_soggetto")
    private GruppoSoggettoDTO gruppoSoggetto;

    @JsonProperty("flg_capogruppo")
    private Boolean flgCapogruppo;

    @JsonProperty("recapito_alternativo")
    private List<RecapitoAlternativoExtendedDTO> recapitoAlternativo;

    /**
     * Gets soggetto.
     *
     * @return soggetto soggetto
     */
    public SoggettoExtendedDTO getSoggetto() {
        return soggetto;
    }

    /**
     * Sets soggetto.
     *
     * @param soggetto SoggettoExtendedDTO
     */
    public void setSoggetto(SoggettoExtendedDTO soggetto) {
        this.soggetto = soggetto;
    }

    /**
     * Gets tipo soggetto.
     *
     * @return tipoSoggetto tipo soggetto
     */
    public TipoSoggettoDTO getTipoSoggetto() {
        return tipoSoggetto;
    }

    /**
     * Sets tipo soggetto.
     *
     * @param tipoSoggetto TipoSoggettoDTO
     */
    public void setTipoSoggetto(TipoSoggettoDTO tipoSoggetto) {
        this.tipoSoggetto = tipoSoggetto;
    }

    /**
     * Gets ruolo soggetto.
     *
     * @return ruoloSoggetto ruolo soggetto
     */
    public RuoloSoggettoDTO getRuoloSoggetto() {
        return ruoloSoggetto;
    }

    /**
     * Sets ruolo soggetto.
     *
     * @param ruoloSoggetto RuoloSoggettoDTO
     */
    public void setRuoloSoggetto(RuoloSoggettoDTO ruoloSoggetto) {
        this.ruoloSoggetto = ruoloSoggetto;
    }

    /**
     * Gets tipo natura giuridica.
     *
     * @return tipoNaturaGiuridica tipo natura giuridica
     */
    public TipoNaturaGiuridicaDTO getTipoNaturaGiuridica() {
        return tipoNaturaGiuridica;
    }

    /**
     * Sets tipo natura giuridica.
     *
     * @param tipoNaturaGiuridica TipoNaturaGiuridicaDTO
     */
    public void setTipoNaturaGiuridica(TipoNaturaGiuridicaDTO tipoNaturaGiuridica) {
        this.tipoNaturaGiuridica = tipoNaturaGiuridica;
    }

    /**
     * Gets ruolo compilante.
     *
     * @return ruoloCompilante ruolo compilante
     */
    public RuoloCompilanteDTO getRuoloCompilante() {
        return ruoloCompilante;
    }

    /**
     * Sets ruolo compilante.
     *
     * @param ruoloCompilante RuoloCompilanteDTO
     */
    public void setRuoloCompilante(RuoloCompilanteDTO ruoloCompilante) {
        this.ruoloCompilante = ruoloCompilante;
    }

    /**
     * Gets comune residenza.
     *
     * @return comuneResidenza comune residenza
     */
    public ComuneExtendedDTO getComuneResidenza() {
        return comuneResidenza;
    }

    /**
     * Sets comune residenza.
     *
     * @param comuneResidenza comuneResidenza
     */
    public void setComuneResidenza(ComuneExtendedDTO comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
    }

    /**
     * Gets comune nascita.
     *
     * @return comuneNascita comune nascita
     */
    public ComuneExtendedDTO getComuneNascita() {
        return comuneNascita;
    }

    /**
     * Sets comune nascita.
     *
     * @param comuneNascita comuneNascita
     */
    public void setComuneNascita(ComuneExtendedDTO comuneNascita) {
        this.comuneNascita = comuneNascita;
    }

    /**
     * Gets comune sede legale.
     *
     * @return comuneSedeLegale comune sede legale
     */
    public ComuneExtendedDTO getComuneSedeLegale() {
        return comuneSedeLegale;
    }

    /**
     * Sets comune sede legale.
     *
     * @param comuneSedeLegale comuneSedeLegale
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
     * Gets gruppo soggetto.
     *
     * @return the gruppo soggetto
     */
    public GruppoSoggettoDTO getGruppoSoggetto() {
        return gruppoSoggetto;
    }

    /**
     * Sets gruppo soggetto.
     *
     * @param gruppoSoggetto the gruppo soggetto
     */
    public void setGruppoSoggetto(GruppoSoggettoDTO gruppoSoggetto) {
        this.gruppoSoggetto = gruppoSoggetto;
    }

    /**
     * Gets flg capogruppo.
     *
     * @return the flg capogruppo
     */
    public Boolean getFlgCapogruppo() {
        return flgCapogruppo;
    }

    /**
     * Sets flg capogruppo.
     *
     * @param flgCapogruppo the flg capogruppo
     */
    public void setFlgCapogruppo(Boolean flgCapogruppo) {
        this.flgCapogruppo = flgCapogruppo;
    }

    /**
     * Gets recapito alternativo.
     *
     * @return the recapito alternativo
     */
    public List<RecapitoAlternativoExtendedDTO> getRecapitoAlternativo() {
        return recapitoAlternativo;
    }

    /**
     * Sets recapito alternativo.
     *
     * @param recapitoAlternativo the recapito alternativo
     */
    public void setRecapitoAlternativo(List<RecapitoAlternativoExtendedDTO> recapitoAlternativo) {
        this.recapitoAlternativo = recapitoAlternativo;
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
        SoggettoIstanzaExtendedDTO that = (SoggettoIstanzaExtendedDTO) o;
        return Objects.equals(soggetto, that.soggetto) && Objects.equals(tipoSoggetto, that.tipoSoggetto) && Objects.equals(ruoloSoggetto, that.ruoloSoggetto) && Objects.equals(tipoNaturaGiuridica, that.tipoNaturaGiuridica) && Objects.equals(ruoloCompilante, that.ruoloCompilante) && Objects.equals(comuneResidenza, that.comuneResidenza) && Objects.equals(comuneNascita, that.comuneNascita) && Objects.equals(comuneSedeLegale, that.comuneSedeLegale) && Objects.equals(nazioneResidenza, that.nazioneResidenza) && Objects.equals(nazioneNascita, that.nazioneNascita) && Objects.equals(nazioneSedeLegale, that.nazioneSedeLegale) && Objects.equals(gruppoSoggetto, that.gruppoSoggetto) && Objects.equals(flgCapogruppo, that.flgCapogruppo) && Objects.equals(recapitoAlternativo, that.recapitoAlternativo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), soggetto, tipoSoggetto, ruoloSoggetto, tipoNaturaGiuridica, ruoloCompilante, comuneResidenza, comuneNascita, comuneSedeLegale, nazioneResidenza, nazioneNascita, nazioneSedeLegale, gruppoSoggetto, flgCapogruppo, recapitoAlternativo);
    }

    /**
     * @return string
     */
    public String toString22() {
        final StringBuilder sb = new StringBuilder("SoggettoIstanzaExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         soggetto:").append(soggetto);
        sb.append(",\n         tipoSoggetto:").append(tipoSoggetto);
        sb.append(",\n         ruoloSoggetto:").append(ruoloSoggetto);
        sb.append(",\n         tipoNaturaGiuridica:").append(tipoNaturaGiuridica);
        sb.append(",\n         ruoloCompilante:").append(ruoloCompilante);
        sb.append(",\n         comuneResidenza:").append(comuneResidenza);
        sb.append(",\n         comuneNascita:").append(comuneNascita);
        sb.append(",\n         comuneSedeLegale:").append(comuneSedeLegale);
        sb.append(",\n         nazioneResidenza:").append(nazioneResidenza);
        sb.append(",\n         nazioneNascita:").append(nazioneNascita);
        sb.append(",\n         nazioneSedeLegale:").append(nazioneSedeLegale);
        sb.append(",\n         gruppoSoggetto:").append(gruppoSoggetto);
        sb.append(",\n         flgCapogruppo:").append(flgCapogruppo);
        sb.append(",\n         recapitoAlternativo:").append(recapitoAlternativo);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "SoggettoIstanzaExtendedDTO {\n" +
                "         soggetto:" + soggetto +
                ",\n         tipoSoggetto:" + tipoSoggetto +
                ",\n         ruoloSoggetto:" + ruoloSoggetto +
                ",\n         tipoNaturaGiuridica:" + tipoNaturaGiuridica +
                ",\n         ruoloCompilante:" + ruoloCompilante +
                ",\n         comuneResidenza:" + comuneResidenza +
                ",\n         comuneNascita:" + comuneNascita +
                ",\n         comuneSedeLegale:" + comuneSedeLegale +
                ",\n         nazioneResidenza:" + nazioneResidenza +
                ",\n         nazioneNascita:" + nazioneNascita +
                ",\n         nazioneSedeLegale:" + nazioneSedeLegale +
                ",\n         gruppoSoggetto:" + gruppoSoggetto +
                ",\n         flgCapogruppo:" + flgCapogruppo +
                ",\n         recapitoAlternativo:" + recapitoAlternativo +
                super.toString() +
                "}\n";
    }
}
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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.NazioneDTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Pub soggetto istanza dto.
 *
 * @author CSI PIEMONTE
 */
public class PubSoggettoIstanzaDTO implements Serializable {

    @JsonProperty("id_soggetto_istanza")
    private Long idSoggettoIstanza;

    @JsonProperty("id_soggetto_padre")
    private Long idSoggettoPadre;

    @JsonProperty("codice_fiscale")
    private String codiceFiscale;

    @JsonProperty("tipo_soggetto")
    private PubTipoSoggettoDTO tipoSoggetto;

    @JsonProperty("natura_giuridica")
    private PubNaturaGiuridicaDTO naturaGiuridica;

    @JsonProperty("partita_iva")
    private String partitaIva;

    @JsonProperty("denominazione")
    private String denominazione;

    @JsonProperty("comune_sede_legale")
    private ComuneExtendedDTO comuneSedeLegale;

    @JsonProperty("cap_sede_legale")
    private String capSedeLegale;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("cognome")
    private String cognome;

    @JsonProperty("nazione_sede_legale")
    private NazioneDTO nazioneSedeLegale;

    /**
     * Gets id soggetto istanza.
     *
     * @return the id soggetto istanza
     */
    public Long getIdSoggettoIstanza() {
        return idSoggettoIstanza;
    }

    /**
     * Sets id soggetto istanza.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     */
    public void setIdSoggettoIstanza(Long idSoggettoIstanza) {
        this.idSoggettoIstanza = idSoggettoIstanza;
    }

    /**
     * Gets id soggetto padre.
     *
     * @return the id soggetto padre
     */
    public Long getIdSoggettoPadre() {
        return idSoggettoPadre;
    }

    /**
     * Sets id soggetto padre.
     *
     * @param idSoggettoPadre the id soggetto padre
     */
    public void setIdSoggettoPadre(Long idSoggettoPadre) {
        this.idSoggettoPadre = idSoggettoPadre;
    }

    /**
     * Gets codice fiscale.
     *
     * @return the codice fiscale
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets codice fiscale.
     *
     * @param codiceFiscale the codice fiscale
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * Gets tipo soggetto.
     *
     * @return the tipo soggetto
     */
    public PubTipoSoggettoDTO getTipoSoggetto() {
        return tipoSoggetto;
    }

    /**
     * Sets tipo soggetto.
     *
     * @param tipoSoggetto the tipo soggetto
     */
    public void setTipoSoggetto(PubTipoSoggettoDTO tipoSoggetto) {
        this.tipoSoggetto = tipoSoggetto;
    }

    /**
     * Gets natura giuridica.
     *
     * @return the natura giuridica
     */
    public PubNaturaGiuridicaDTO getNaturaGiuridica() {
        return naturaGiuridica;
    }

    /**
     * Sets natura giuridica.
     *
     * @param naturaGiuridica the natura giuridica
     */
    public void setNaturaGiuridica(PubNaturaGiuridicaDTO naturaGiuridica) {
        this.naturaGiuridica = naturaGiuridica;
    }

    /**
     * Gets partita iva.
     *
     * @return the partita iva
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Sets partita iva.
     *
     * @param partitaIva the partita iva
     */
    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    /**
     * Gets denominazione.
     *
     * @return the denominazione
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Sets denominazione.
     *
     * @param denominazione the denominazione
     */
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
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
     * Gets cap sede legale.
     *
     * @return the cap sede legale
     */
    public String getCapSedeLegale() {
        return capSedeLegale;
    }

    /**
     * Sets cap sede legale.
     *
     * @param capSedeLegale the cap sede legale
     */
    public void setCapSedeLegale(String capSedeLegale) {
        this.capSedeLegale = capSedeLegale;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets cognome.
     *
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets cognome.
     *
     * @param cognome the cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
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
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubSoggettoIstanzaDTO that = (PubSoggettoIstanzaDTO) o;
        return Objects.equals(idSoggettoIstanza, that.idSoggettoIstanza) && Objects.equals(idSoggettoPadre, that.idSoggettoPadre) && Objects.equals(codiceFiscale, that.codiceFiscale) && Objects.equals(tipoSoggetto, that.tipoSoggetto) && Objects.equals(naturaGiuridica, that.naturaGiuridica) && Objects.equals(partitaIva, that.partitaIva) && Objects.equals(denominazione, that.denominazione) && Objects.equals(comuneSedeLegale, that.comuneSedeLegale) && Objects.equals(capSedeLegale, that.capSedeLegale) && Objects.equals(nome, that.nome) && Objects.equals(cognome, that.cognome) && Objects.equals(nazioneSedeLegale, that.nazioneSedeLegale);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idSoggettoIstanza, idSoggettoPadre, codiceFiscale, tipoSoggetto, naturaGiuridica, partitaIva, denominazione, comuneSedeLegale, capSedeLegale, nome, cognome, nazioneSedeLegale);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PubSoggettoIstanzaDTO {\n");
        sb.append("         idSoggettoIstanza:").append(idSoggettoIstanza);
        sb.append(",\n         idSoggettoPadre:").append(idSoggettoPadre);
        sb.append(",\n         codiceFiscale:'").append(codiceFiscale).append("'");
        sb.append(",\n         tipoSoggetto:").append(tipoSoggetto);
        sb.append(",\n         naturaGiuridica:").append(naturaGiuridica);
        sb.append(",\n         partitaIva:'").append(partitaIva).append("'");
        sb.append(",\n         denominazione:'").append(denominazione).append("'");
        sb.append(",\n         comuneSedeLegale:").append(comuneSedeLegale);
        sb.append(",\n         capSedeLegale:'").append(capSedeLegale).append("'");
        sb.append(",\n         nome:'").append(nome).append("'");
        sb.append(",\n         cognome:'").append(cognome).append("'");
        sb.append(",\n         nazioneSedeLegale:").append(nazioneSedeLegale);
        sb.append("}\n");
        return sb.toString();
    }
}